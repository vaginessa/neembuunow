/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package neembuu.release1.defaultImpl.linkgroup;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import neembuu.release1.api.linkhandler.TrialLinkHandler;
import neembuu.release1.api.linkgroup.LinkGroup;

/**
 *
 * @author Shashank Tulsyan
 */
public class SplitsLinkGroup implements LinkGroup {

    String name;
    final List<SplitPart> splitParts = new LinkedList<SplitPart>();

    @Override
    public boolean absorb(TrialLinkHandler other) {
        SplitsLinkGroup otherLP = SplitsLinkGroupMaker.tryHandle(other);

        if (otherLP == null) {
            return false;
        }
        SplitPart otherSplit = otherLP.splitParts.get(0);

        if (name.equalsIgnoreCase(otherLP.name)) {
            for (SplitPart s1 : splitParts) {
                if (s1.index == otherSplit.index) {
                    //already exists
                    return false;
                }
            }
            splitParts.add(otherSplit);
            return true;
        }
        return false;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        String ret = name + "{\n";
        for (SplitPart splitPart : splitParts) {
            ret += "\t" + splitPart.splitName + " index=" + splitPart.index + "\n";
        }
        ret += "}\n";
        return ret;

    }
    
    final static class SplitPart {
        int index;
        String splitName;
        TrialLinkHandler th;

        public int getIndex() {
            return index;
        }

        public String getName() {
            return splitName;
        }

        public TrialLinkHandler getTrialLinkHandler() {
            return th;
        }
    }

    @Override
    public List<TrialLinkHandler> getLinks() {
        if(complete()){
            return Collections.emptyList();
        }
        
        final List<TrialLinkHandler> failedLinks = new LinkedList<TrialLinkHandler>();
        for(SplitPart sp : splitParts ){
            failedLinks.add(sp.getTrialLinkHandler());
        }
        return failedLinks;
    }
    
    @Override
    public boolean complete() {
        Collections.sort(splitParts, new Comparator<SplitPart>() {
            @Override
            public int compare(SplitPart o1, SplitPart o2) {
                return o1.index - o2.index;
            }
        });
        
        int i = 1;
        boolean complete = false;
        
        
        for(SplitPart sp : splitParts){
            if(sp.index!=i){
                break;
            }
            i++;
        }
        
        if(i==splitParts.size()+1){
            complete = true;
        }
        
        return complete;
    }
}