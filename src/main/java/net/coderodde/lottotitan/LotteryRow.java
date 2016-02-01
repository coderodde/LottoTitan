package net.coderodde.lottotitan;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Objects;

/**
 * This class models a lottery row.
 * 
 * @author Rodion "rodde" Efremov
 * @version 1.6 (Feb 1, 2016)
 */
public class LotteryRow {
   
    private final int[] numbers;
    private final long timestamp;
    
    public LotteryRow(long timestamp, int... numbers) {
        Objects.requireNonNull(numbers, "The input numbers are null.");
        this.numbers = numbers.clone();
        Arrays.sort(this.numbers);
        this.timestamp = timestamp;
    }
    
    public int get(int i) {
        return numbers[i];
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd");
        sb.append("[")
          .append(sdf.format(new Date(timestamp)))
          .append(":");
        
        for (int i : numbers) {
            sb.append(" ").append(i);
        }
        
        return sb.append("]").toString();
    }
}
