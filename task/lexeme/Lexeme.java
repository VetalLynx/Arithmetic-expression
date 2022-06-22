package task.lexeme;

import task.exception.LexemeException;

import java.util.ArrayList;
import java.util.List;

public class Lexeme {
    private LexemeType type;
    private String value;

    public Lexeme(LexemeType type, String value) {
        this.type = type;
        this.value = value;
    }
    public Lexeme(LexemeType type, Character value) {
        this.type = type;
        this.value = value.toString();
    }

    public LexemeType getType() {
        return type;
    }

    public String getValue() {
        return value;
    }

    public static List<Lexeme> lexAnalyze(String expression) {
        List<Lexeme> lexemes = new ArrayList<>();
        int pos = 0;
        while (pos < expression.length()) {
            char c = expression.charAt(pos);
            switch (c) {
                case '(':
                    lexemes.add(new Lexeme(LexemeType.LEFT_BRACKET, c));
                    pos++;
                    continue;
                case ')':
                    lexemes.add(new Lexeme(LexemeType.RIGHT_BRACKET, c));
                    pos++;
                    continue;
                case '+':
                    lexemes.add(new Lexeme(LexemeType.PLUS, c));
                    pos++;
                    continue;
                case '-':
                    lexemes.add(new Lexeme(LexemeType.MINUS, c));
                    pos++;
                    continue;
                case '*':
                    lexemes.add(new Lexeme(LexemeType.MULTI, c));
                    pos++;
                    continue;
                case '/':
                    lexemes.add(new Lexeme(LexemeType.DIVIDE, c));
                    pos++;
                    continue;
                default:
                    if (Character.isDigit(c) || c == '.') {
                        StringBuilder sb = new StringBuilder();
                        do {
                            sb.append(c);
                            pos++;
                            if (pos >= expression.length()) {
                                break;
                            }
                            c = expression.charAt(pos);
                        } while (Character.isDigit(c)|| c == '.');
                        lexemes.add(new Lexeme(LexemeType.NUMBER, sb.toString()));
                    } else {
                        if (c != ' ') {
                            throw new LexemeException("Unexpected symbol: " + c);
                        }
                    }
            }
        }
        lexemes.add(new Lexeme(LexemeType.EOF, ""));
        return lexemes;
    }

    public static double getExpressionValue(LexemeBuffer lexemes) {
        double result;
        Lexeme lexeme = lexemes.next();
        if (lexeme.getType() == LexemeType.EOF) {
            result = 0;
        } else {
            lexemes.back();
            result = operationPlusAndMinus(lexemes);
        }
        return result;
    }

    private static double operationPlusAndMinus(LexemeBuffer lexemes) {
        double value = operationMultiAndDivide(lexemes);
        while (true) {
            Lexeme lexeme = lexemes.next();
            switch (lexeme.getType()) {
                case PLUS:
                    value += operationMultiAndDivide(lexemes);
                    break;
                case MINUS:
                    value -= operationMultiAndDivide(lexemes);
                    break;
                default:
                    lexemes.back();
                    return value;
            }
        }
    }

    private static double operationMultiAndDivide(LexemeBuffer lexemes) {
        double value = factor(lexemes);
        while (true) {
            Lexeme lexeme = lexemes.next();
            switch (lexeme.getType()) {
                case MULTI:
                    value *= factor(lexemes);
                    break;
                case DIVIDE:
                    value /= factor(lexemes);
                    break;
                default:
                    lexemes.back();
                    return value;
            }
        }
    }

    private static double factor(LexemeBuffer lexemes) {
        double result;
        Lexeme lexeme = lexemes.next();
        switch (lexeme.getType()) {
            case NUMBER:
                result = Double.parseDouble(lexeme.getValue());
                break;
            case LEFT_BRACKET:
                double value = getExpressionValue(lexemes);
                lexeme = lexemes.next();
                if (lexeme.getType() != LexemeType.RIGHT_BRACKET) {
                    throw new LexemeException("Unexpected token: " + lexeme.getValue() + " in the position: " + lexemes.getIndex());
                }
                result = value;
                break;
            default:
                throw new LexemeException("Unexpected token: " + lexeme.getValue() + " in the position: " + lexemes.getIndex());
        }
        return result;
    }
}
