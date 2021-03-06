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
package neembuu.vfs.connection.jdimpl;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.logging.Level;
import java.util.logging.Logger;
import neembuu.util.Throwables;
import neembuu.vfs.connection.NewConnectionParams;
import neembuu.vfs.connection.NewConnectionProvider;
import neembuu.vfs.connection.checks.CanSeek;
import neembuu.vfs.connection.checks.SeekingAbility;
import neembuu.vfs.connection.jdimpl.checks.CheckSeekingCapability;


/**
 *
 * @author Shashank Tulsyan
 */
public final class JD_DownloadManager 
        implements 
            NewConnectionProvider{
    //final DownloadLink downloadLink; // package private
    final String url; // package private
    final boolean b; // package private
    final int c; // package private
    
    private final CheckSeekingCapability csc;
    private static final Logger LOGGER = Logger.getLogger(JD_DownloadManager.class.getName());
    
    private final ConcurrentLinkedQueue<JDHTTPConnection> connection_list 
            = new ConcurrentLinkedQueue<JDHTTPConnection>();

    public JD_DownloadManager(String url) {
        this(url,true,0);
    }

    public JD_DownloadManager(
            //DownloadLink downloadLink,
            String url, boolean b, int c) {
        //this.downloadLink = downloadLink;
        this.url = url;
        this.b = b;
        this.c = c;
        this.csc = new CheckSeekingCapability(this);
    }

    final String getURL() { // package private
        return url;
    }
    
    private void connectionsRequested() {
        if(connection_list.size()>50){
            LOGGER.info("Too many connections have already been requested");
            return;
        }
        String message = "++++++++++ConnectionsRequested+++++++++\n";
        for (JDHTTPConnection e : connection_list) {
            message = message + e.getConnectionParams().toString() + "\n";
        }
        message = message + "---------ConnectionsRequested--------\n";
        
        LOGGER.info(message);
    }

    @Override
    public final String getSourceDescription() {
        return "JD_DownloadManager{"+url+"}";
    }
    
    @Override
    public final void provideNewConnection(final NewConnectionParams connectionParams) {
        Throwables.start(new Runnable() {
            @Override public void run() {
                try {
                    JDHTTPConnection c = new JDHTTPConnection(
                            JD_DownloadManager.this,
                            connectionParams);
                    c.setContentSampleListener(csc.getFirstSampleListener(connectionParams));
                    connection_list.add(c);
                    connectionsRequested();
                    c.connectAndSupply();
                } catch (Exception e) {
                    LOGGER.log(Level.INFO, "Problem in new connection ", e);
                }
            }
        }, "StartNewJDBrowserConnectionThread{" + connectionParams + "}");
        //always name thread, otherwise it can be extremely difficult to debug
    }
    
    @Override
    public final long estimateCreationTime(long offset) {
        if(csc.seekingAbility().get()==CanSeek.NO){
            return Integer.MAX_VALUE;
        }
        return averageConnectionCreationTime();
    }

    @Override
    public SeekingAbility seekingAbility() { return csc.seekingAbility(); }

    private long averageConnectionCreationTime() {
        int i = 0;
        long totalTime = 0;
        for (JDHTTPConnection connection : connection_list) {
            if(connection.succeededInCreation()){
                totalTime += connection.timeTakenForCreation();
                i++;
            }
        }
        if (i == 0) {
            return 0;//creation time is unknown
        }
        return ((totalTime) / i);
    }
    
    long[]totalProgress = {0};
}
