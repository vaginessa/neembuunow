/*
 *  Copyright (C) 2009-2010 Shashank Tulsyan
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
 *
 *
 *
 *  Linking this library statically or
 *  dynamically with other modules is making a combined work based on this library.
 *  Thus, the terms and conditions of the GNU General Public License cover the whole combination.
 *
 *
 *  As a special exception, the copyright holders of this library give you permission to
 *  link this library with independent modules to produce an executable, regardless of
 *  the license terms of these independent modules, and to copy and
 *  distribute the resulting executable under terms of your choice,
 *  provided that you also meet, for each linked independent module,
 *  the terms and conditions of the license of that module.
 *  An independent module is a module which is not derived from or based on this library.
 *  If you modify this library, you may extend this exception to your version of the library,
 *  but you are not obligated to do so. If you do not wish to do so,
 *  delete this exception statement from your version.
 */

package neembuu.swing;


import java.text.NumberFormat;
import neembuu.rangearray.Range;
import neembuu.rangearray.RangeUtils;
import neembuu.swing.RangeArrayElementColorProvider.SelectionState;

/**
 *
 * @author Shashank Tulsyan
 */
public interface RangeArrayElementToolTipTextProvider {
    
    public String getToolTipText(Range element, long absolutePosition, long largestEntry, SelectionState selectionState);
    
    
    
    public static final class Default implements RangeArrayElementToolTipTextProvider{
        
        public static Default getInstance(){ 
            return new Default(); 
        }
        
        @Override
        public String getToolTipText(Range element, long absolutePosition, long largestEntry, SelectionState selectionState) {
            if(element==null)return emptyEntry(absolutePosition, largestEntry);
            
            return makeRangeArrayElementToolTipText(element, absolutePosition, largestEntry, null);
        }
        
        public static String emptyEntry(long absolutePosition, long fileSize){
            StringBuilder sb = new StringBuilder(100);
            sb.append("<html><body><u>");
            sb.append(NumberFormat.getInstance().format((double)absolutePosition));
            sb.append("</u><p>");
            sb.append(NumberFormat.getInstance().format((double)fileSize));
            sb.append("</body></html>");
            return sb.toString();
        }
        
        public static String makeRangeArrayElementToolTipText(
                Range rae,long absolutePosition, long fileSize, String annotation){
            StringBuilder sb = new StringBuilder(100);
            sb.append("<html><body><u>");
            sb.append(NumberFormat.getInstance().format((double)absolutePosition));
            sb.append("</u><p>");
            sb.append(NumberFormat.getInstance().format((double)fileSize));
            sb.append("<p>{");
            sb.append(NumberFormat.getInstance().format(rae.starting()));
            sb.append("-to->");
            sb.append(NumberFormat.getInstance().format(rae.ending()));
            sb.append("}<p>{");
            sb.append(NumberFormat.getInstance().format(RangeUtils.getSize(rae)));
            sb.append(" , ");
            sb.append( (float)(
                    RangeUtils.getSize(
                        rae)*100)/fileSize );
            sb.append("% }<p>");
            if(annotation!=null){
                sb.append(annotation);
                sb.append("<p>");
            }
            sb.append("</body></html>");
            return sb.toString();
        }
        
    }
}
