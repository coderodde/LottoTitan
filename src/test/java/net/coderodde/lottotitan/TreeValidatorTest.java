package net.coderodde.lottotitan;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;

public class TreeValidatorTest {

    private TreeValidator validator;
    private final List<NumberPair> candidate = new ArrayList<>();
    
    @Test
    public void testIsValidTree() {
        NumberPair pair1 = new NumberPair(1, 2);
        NumberPair pair2 = new NumberPair(3, 2);
        NumberPair pair3 = new NumberPair(4, 3);
        NumberPair pair4 = new NumberPair(1, 4);
        
        pair1.addNeighborNumberPair(pair2);
        pair2.addNeighborNumberPair(pair3);
        pair3.addNeighborNumberPair(pair4);
        pair4.addNeighborNumberPair(pair1);
        
        candidate.clear();
        candidate.addAll(Arrays.asList(pair1, pair2, pair3, pair4));
        
        validator = new TreeValidator(4);
        assertFalse(validator.isValidTree(candidate));
        
        pair1 = new NumberPair(1, 2);
        pair2 = new NumberPair(1, 3);
        pair3 = new NumberPair(1, 4);
        pair4 = new NumberPair(4, 5);
        
        candidate.clear();
        candidate.addAll(Arrays.asList(pair1, pair2, pair3, pair4));
        
        assertFalse(validator.isValidTree(candidate));
        
        pair1.addNeighborNumberPair(pair2);
        pair1.addNeighborNumberPair(pair3);
        pair2.addNeighborNumberPair(pair3);
        pair3.addNeighborNumberPair(pair4);
        
        assertTrue(validator.isValidTree(candidate));
        
        // Cycle and disconnected.
        pair1 = new NumberPair(1, 2);
        pair2 = new NumberPair(2, 3);
        pair3 = new NumberPair(3, 1);
        pair4 = new NumberPair(4, 5);
        
        pair1.addNeighborNumberPair(pair2);
        pair2.addNeighborNumberPair(pair3);
        pair3.addNeighborNumberPair(pair1);
        
        candidate.clear();
        candidate.addAll(Arrays.asList(pair1, pair2, pair3, pair4));
        
        assertFalse(validator.isValidTree(candidate));
    }
}
