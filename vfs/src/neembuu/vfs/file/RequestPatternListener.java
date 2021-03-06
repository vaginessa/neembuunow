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

package neembuu.vfs.file;

import java.util.EventListener;
import jpfm.annotations.NonBlocking;

/**
 *
 * @author Shashank Tulsyan
 */
public interface RequestPatternListener extends EventListener{
    /**
     * Do not block as this is in the Read dispatch thread.
     * If you block this thread, the user's operating system applications
     * might become non-responsive
     * @param requestStarting
     * @param requestEnding 
     */
    @NonBlocking()
    void requested(long requestStarting,long requestEnding);
}
