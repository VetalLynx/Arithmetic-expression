package task.db;

import task.exception.LexemeException;
import task.lexeme.Lexeme;
import task.lexeme.LexemeBuffer;

import java.io.*;
import java.nio.file.Files;
import java.util.*;

public class DataBase {
    private static Map<String, Double> data = new HashMap<>();
    private static File dataBase = new File("C:\\Users\\User\\Documents\\Java\\project\\GeeksForLess\\src\\task\\db\\db.txt");
    private static BufferedReader reader;
    private static FileOutputStream writer;

    public static void writeIntoDataBase(String expression) {
        try {
            writer = new FileOutputStream(dataBase, true);
            List<Lexeme> lexemes = Lexeme.lexAnalyze(expression);
            double value = Lexeme.getExpressionValue(new LexemeBuffer(lexemes));
            StringBuilder sb = new StringBuilder(4);
            sb.append(expression).append(' ').append(value).append("\n");
            writer.write(sb.toString().getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (LexemeException e) {
            System.out.println("This expression is not correct!!!");
        }
    }

    private static void readAllData() throws IOException {
            reader = new BufferedReader(new FileReader(dataBase));
            String line;
            while ((line = reader.readLine()) != null && !line.isEmpty()) {
                String[] array = line.split(" ");
                data.put(array[0], Double.parseDouble(array[1]));
            }
            reader.close();
    }

    public static void showAll() {
        try {
            readAllData();
            if (data.isEmpty()) {
                System.out.println("Data base doesn't have any expression!");
            } else {
                for (Map.Entry<String, Double> pair : data.entrySet()) {
                    System.out.println(pair.getKey() + "  " + pair.getValue());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
}
    public static void showCertainExpression(String sing, double value) {
        try {
            readAllData();
            List<String> certainExpressions = new LinkedList<>();
            switch (sing) {
                case "==":
                    for (String expr : data.keySet()) {
                        if (data.get(expr) == value)
                            certainExpressions.add(expr);
                    }
                    break;
                case ">":
                    for (String expr : data.keySet()) {
                        if (data.get(expr) > value)
                            certainExpressions.add(expr);
                    }
                    break;
                case ">=":
                    for (String expr : data.keySet()) {
                        if (data.get(expr) >= value)
                            certainExpressions.add(expr);
                    }
                    break;
                case "<":
                    for (String expr : data.keySet()) {
                        if (data.get(expr) < value)
                            certainExpressions.add(expr);
                    }
                    break;
                case "<=":
                    for (String expr : data.keySet()) {
                        if (data.get(expr) <= value)
                            certainExpressions.add(expr);
                    }
                    break;
                default:
                    System.out.println("Wrong sing!!!");
                    return;
            }
            if (certainExpressions.isEmpty()) {
                System.out.println("There is no such expressions");
            } else {
                System.out.println("Certain expressions: ");
                for (String expr : certainExpressions) {
                    System.out.println(expr);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void updateExpression(String currentExpression, String editExpression) {
        try {
            readAllData();
            Set<String> expressions = data.keySet();
            if (data.isEmpty()) {
                System.out.println("Data base is empty! Please fill DB with data.");
            } else if (expressions.contains(currentExpression)) {
                List<Lexeme> lexemes = Lexeme.lexAnalyze(editExpression);
                double value = Lexeme.getExpressionValue(new LexemeBuffer(lexemes));
                data.remove(currentExpression);
                data.put(editExpression, value);
                BufferedWriter bw = Files.newBufferedWriter(dataBase.toPath());
                for (Map.Entry<String, Double> pair : data.entrySet()) {
                    bw.write(pair.getKey() + " " + pair.getValue() + "\n");
                }
                bw.flush();
                bw.close();
            } else {
                System.out.println("Data base doesn't contain expression like: " + currentExpression);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (LexemeException e) {
            System.out.println("Edit expression is not correct!!!");
        }
    }
}
