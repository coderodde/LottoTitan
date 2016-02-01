package net.coderodde.lottotitan;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * This class implements a simple lottery row parser.
 * 
 * @author Rodion "rodde" Efremov
 * @version 1.6 (Feb 1, 2016)
 */
public class LotteryRowParser {
    
    public static List<LotteryRow> parse(File file, int numbers) {
        List<LotteryRow> rowList = new ArrayList<>();
        int[] lotteryNumbers = new int[numbers];
        
        try (Scanner scanner = new Scanner(file);) {
            while (scanner.hasNextLong()) {
                long timestamp = scanner.nextLong();
                
                for (int i = 0; i < numbers; ++i) {
                    lotteryNumbers[i] = scanner.nextInt();
                }
                
                rowList.add(new LotteryRow(timestamp, lotteryNumbers));
            }
        } catch (FileNotFoundException ex) {
            System.out.println(ex);
            return null;
        }
        
        return rowList;
    }
    
    public static void main(String[] args) {
        List<LotteryRow> rowList = parse(new File("/Users/rodionefremov/Desktop/lottery_rows.txt"), 7);
        
        rowList.stream().forEach((i) -> {System.out.println(i);});
    }
}
