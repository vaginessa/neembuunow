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

package neembuu.release1.api.ui;

/**
 *
 * @author Shashank Tulsyan
 */
public interface AddLinkUI {
    void addLinkProgressSet(String a);
    void addLinksPanelShow(boolean show);
    void addLinksPanelEnable(boolean enable);
    String getLinksText();
    void setLinksText(String a);
    
    Lock getLock();
    
    public static interface Lock { 
        void lock(boolean f);
        void lockForAWhile(int timeout);
    }
}
