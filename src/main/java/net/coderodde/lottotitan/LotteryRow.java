package net.coderodde.lottotitan;

import java.util.Objects;

/**
 * This class models a lottery row.
 * 
 * @author Rodion "rodde" Efremov
 * @version 1.6 (Feb 1, 2016)
 */
public class LotteryRow {
   
    private int[] numbers;
    
    public LotteryRow(int... numbers) {
        Objects.requireNonNull(numbers, "The input numbers are null.");
        this.numbers = numbers.clone();
    }
}
