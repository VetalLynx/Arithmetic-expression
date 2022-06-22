package extratask;

import java.util.Scanner;

public class Solution {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String str = scanner.next();
        int countNumbers = 0;
        for (int i = 0; i < str.length(); i++) {
            if (Character.isDigit(str.charAt(i))) {
                countNumbers++;
            }
        }
        System.out.println("Total numbers in string: " + countNumbers);
    }
}
