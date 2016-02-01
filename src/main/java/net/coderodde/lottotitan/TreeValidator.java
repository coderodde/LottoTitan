package net.coderodde.lottotitan;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 *
 * @author rodionefremov
 */
public class TreeValidator {

    private final int numberOfDistinctNodes;
    private final Set<Integer> numberSet = new HashSet<>();
    private final Set<NumberPair> visitedSet = new HashSet<>();
    private final Set<NumberPair> filterSet = new HashSet<>();
    
    public TreeValidator(int numberPairs) {
        if (numberPairs < 1) {
            throw new IllegalArgumentException("Are you fu*king kidding me?");
        }
        
        this.numberOfDistinctNodes = numberPairs + 1;
    }
    
    public boolean isValidTree(List<NumberPair> candidate) {
        numberSet.clear();
        
        for (NumberPair pair : candidate) {
            numberSet.add(pair.getNumberA());
            numberSet.add(pair.getNumberB());
            
            if (numberSet.size() > numberOfDistinctNodes) {
                return false;
            }
        }
        
        if (numberSet.size() < numberOfDistinctNodes) {
            return false;
        }
        
        return isAcyclic(candidate);
    }
    
    private boolean isAcyclic(List<NumberPair> candidate) {
        visitedSet.clear();
        visitedSet.add(candidate.get(0));

        filterSet.clear();
        filterSet.addAll(candidate);
        
        return isAcyclicImpl(candidate.get(0)) 
                && visitedSet.size() == candidate.size();
    }
    
    private boolean isAcyclicImpl(NumberPair pair) {
        for (NumberPair neighbor : pair.getANeighbors()) {
            if (!filterSet.contains(neighbor)) {
                // Don't go outside the candidate pairs/edges.
                continue;
            }
            
            if (visitedSet.contains(neighbor)) {
                return false;
            }
            
            visitedSet.add(neighbor);
            return isAcyclicImpl(neighbor);
        }
        
        for (NumberPair neighbor : pair.getBNeighbors()) {
            if (!filterSet.contains(neighbor)) {
                // Don't go outside the candidate pairs/edges.
                continue;
            }
            
            if (visitedSet.contains(neighbor)) {
                return false;
            }
            
            visitedSet.add(neighbor);
            return isAcyclicImpl(neighbor);
        }
        
        return true;
    }
}
