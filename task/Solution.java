package task;

import task.db.DataBase;
import java.util.Scanner;

public class Solution {
    private static boolean stop;

    public static void main(String[] args) {
        while (!stop) {
            System.out.println("Select one of the current commands: \n" +
                    "a - add an arithmetic expression into DB;\n" +
                    "u - update an arithmetic expression,\n" +
                    "s - show all arithmetic expressions,\n" +
                    "f - find the certain arithmetic expressions,\n" +
                    "e - exit");
            Scanner scanner = new Scanner(System.in);
            String command = scanner.next();
            switch (command) {
                case "a":
                    System.out.println("Enter an arithmetic expression for writing into DB: ");
                    String arithmeticExpr = scanner.next();
                    DataBase.writeIntoDataBase(arithmeticExpr);
                    break;
                case "s":
                    System.out.println("All arithmetic expressions which contains in DB: ");
                    DataBase.showAll();
                    break;
                case "e":
                    stop = true;
                    break;
                case "u":
                    System.out.println("Enter a searching arithmetic expression: ");
                    String currentExpr = scanner.next();
                    System.out.println("Enter a new arithmetic expression: ");
                    String newExpr = scanner.next();
                    DataBase.updateExpression(currentExpr, newExpr);
                    break;
                case "f":
                    System.out.println("Enter any sing from these examples:\n" +
                            "==, <, <=, >, >=");
                    String sing = scanner.next();
                    System.out.println("Enter certain value:");
                    double value = scanner.nextDouble();
                    DataBase.showCertainExpression(sing, value);
                    break;
                default:
                    System.out.println("It was entered a wrong command! Please enter a correct command from a list below");
            }
        }
        //String arithmeticExpression = "22+3.5-2*(2*5+2)*4";
    }
}
