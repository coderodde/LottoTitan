package net.coderodde.lottotitan;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.Set;

/**
 * This class implements an edge type in the history graph.
 * 
 * @author Rodion "rodde" Efremov
 * @version 1.6 (Feb 1, 2016)
 */
class NumberPair {
   
    private static final int BIT_SHIFT = 16;
    
    private final int numberA; 
    private final int numberB;
    
    private final List<NumberPair> numberANeighborList = new ArrayList<>();
    private final List<NumberPair> numberBNeighborList = new ArrayList<>();
    
    private final List<NumberPair> numberANeighborListWrapper = 
            Collections.<NumberPair>unmodifiableList(numberANeighborList);
    
    private final List<NumberPair> numberBNeighborListWrapper = 
            Collections.<NumberPair>unmodifiableList(numberBNeighborList);
    
    NumberPair(int numberA, int numberB) {
        if (numberA == numberB) {
            throw new IllegalArgumentException(
                    "Requesting a self-loop: received equal numbers.");
        }
        
        this.numberA = Math.min(numberA, numberB);
        this.numberB = Math.max(numberA, numberB);
    }
    
    int getNumberA() {
        return numberA;
    }
    
    int getNumberB() {
        return numberB;
    }
    
    void addNeighborNumberPair(NumberPair pair) {
        Objects.requireNonNull(pair, "The input neighbor pair is null.");
        
        if (numberA == pair.numberA || numberA == pair.numberB) {
            numberANeighborList.add(pair);
        } else if (numberB == pair.numberA || numberB == pair.numberB) {
            numberBNeighborList.add(pair);
        } else {
            throw new IllegalArgumentException(
                    "The input pair is not a neighbor pair.");
        }
    }
    
    List<NumberPair> getANeighbors() {
        return numberANeighborListWrapper;
    }
    
    List<NumberPair> getBNeighbors() {
        return numberBNeighborListWrapper;
    }
    
    @Override
    public int hashCode() {
        // We are assuming here that the total number of lottery numbers is
        // reasonable. We don't expect it to be as large as 32000 or so.
        return numberB << BIT_SHIFT | numberA;
    }
    
    public boolean equals(Object o) {
        NumberPair other = (NumberPair) o;
        return numberA == other.numberA && numberB == other.numberB;
    }
    
//    public static void main(String[] args) {
//        benchmark();
//    }
    
    public static void benchmark() {
        int numbers = 39;
        int treeSize = 6;
        List<NumberPair> graph = new ArrayList<>();
        List<NumberPair>[] map = new List[numbers];
        
        for (int i = 0; i < numbers; ++i) {
            map[i] = new ArrayList<>();
        }
        
        for (int a = 0; a < numbers; ++a) {
            for (int b = a + 1; b < numbers; ++b) {
                NumberPair pair = new NumberPair(a, b);
                map[a].add(pair);
                map[b].add(pair);
                graph.add(pair);
            }
        }
        
        for (int a = 0; a < numbers; ++a) {
            List<NumberPair> list = map[a];
            
            for (int i = 0; i < list.size() - 1; ++i) {
                for (int j = i + 1; j < list.size(); ++j) {
                    list.get(i).addNeighborNumberPair(list.get(j));
                }
            }
        }
        
        Random random = new Random();
        TreeValidator validator = new TreeValidator(treeSize);
        int iterations = 850_000;
        
        long startTime = System.nanoTime();
        
        for (int i = 0; i < iterations; ++i) {
            List<NumberPair> candidate = sample(graph, treeSize, random);
            validator.isValidTree(candidate);
        }
        
        long endTime = System.nanoTime();
        
        System.out.printf("Iterated %d iterations in %.2f milliseconds.\n",
                          iterations, (endTime - startTime) / 1e6);
    }
    
    private static List<NumberPair> sample(List<NumberPair> graph, 
                                           int many, 
                                           Random random) {
        Set<NumberPair> set = new HashSet<>();
        
        while (set.size() < many) {
            set.add(choose(graph, random));
        }
        
        return new ArrayList<>(set);
    }
    
    private static <T> T choose(List<T> list, Random random) {
        return list.get(random.nextInt(list.size()));
    }
}
