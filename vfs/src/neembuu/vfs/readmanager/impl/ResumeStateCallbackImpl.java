/*
 *  Copyright (C) 2014 Shashank Tulsyan
 * 
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 * 
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 * 
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package neembuu.vfs.readmanager.impl;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import neembuu.diskmanager.RegionStorageManager;
import neembuu.diskmanager.ResumeStateCallback;
import neembuu.rangearray.Range;
import neembuu.rangearray.UnsyncRangeArrayCopy;
import neembuu.vfs.progresscontrol.ThrottleFactory;
import neembuu.vfs.readmanager.RegionHandler;
import neembuu.vfs.readmanager.rqm.ReadQueueManager;

/**
 *
 * @author Shashank Tulsyan
 */
final class ResumeStateCallbackImpl implements ResumeStateCallback{
    
    private final ReadQueueManager readQueueManager;
    private final SeekableConnectionFileImpl seekableHttpFile;
    private final ThrottleFactory throttleFactory;
    
    private static final boolean DEBUG = false;

    ResumeStateCallbackImpl(ReadQueueManager readQueueManager, SeekableConnectionFileImpl seekableHttpFile, ThrottleFactory throttleFactory) {
        this.readQueueManager = readQueueManager;
        this.seekableHttpFile = seekableHttpFile;
        this.throttleFactory = throttleFactory;
    }
    
    
    @Override
    public boolean resumeState(
            List<RegionStorageManager> previouslyDownloadedData) {
        if(previouslyDownloadedData.size()>0){
            if(seekableHttpFile.getParams().getAskResume().resume()){
                resumeStateConfirmed(previouslyDownloadedData);
                return true;
            }
        }
        return false;
        
    }
    
    private void resumeStateConfirmed(
            List<RegionStorageManager> previouslyDownloadedData) {
       for(RegionStorageManager regionStorageManager : previouslyDownloadedData) {
           resumeStateForRegion(regionStorageManager);
       }
       fixAuthorityLimits();
    }
    
    private void fixAuthorityLimits(){
        synchronized (readQueueManager.getRegionHandlers().getModLock()){
            UnsyncRangeArrayCopy<RegionHandler> urac = readQueueManager.getRegionHandlers().tryToGetUnsynchronizedCopy();
            for (int i = 0; i < urac.size(); i++) {
                RegionHandler me = urac.get(i).getProperty();
                if( me instanceof BasicRegionHandler){
                    boolean changed = ((BasicRegionHandler)me).fixAuthorityLimit(readQueueManager.getRegionHandlers());
                    if(changed)
                        Logger.getLogger(BasicRegionHandler.class.getName()).log(Level.INFO,"authority limit fixed for element->"+me);
                }
            }
        }
    }
    
    private void resumeStateForRegion(RegionStorageManager regionStorageManager){
       BasicRegionHandler channel = null;
        try{
            Range region = readQueueManager.getRegionHandlers().addElement(
                    regionStorageManager.startingOffset(),
                    regionStorageManager.endingByFileSize(), null);
            channel = new BasicRegionHandler(
                    seekableHttpFile,
                    region,
                    regionStorageManager,
                    throttleFactory.createNewThrottle(),
                    regionStorageManager.endingByFileSize()
                );
            
            Range newRegion = readQueueManager.getRegionHandlers().setProperty(region, channel);
            if(newRegion!=region){
                throw new UnsupportedOperationException("This RangeArray "+
                        readQueueManager.getRegionHandlers()
                        + "doesn\'t edit the actual entry, abnormal condition");
            }
            if(DEBUG)Logger.getLogger(BasicRegionHandler.class.getName())
                    .log(Level.INFO, "added={0}", channel);
            /*channel.startConnection((int)
                    RangeUtils.getSize(region),false);*/
        }catch(Exception any){
            Logger.getLogger(BasicRegionHandler.class.getName())
                    .log(Level.INFO,"",any);
        }
    }
}
