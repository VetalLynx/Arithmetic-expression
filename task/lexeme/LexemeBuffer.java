package task.lexeme;

import java.util.List;

public class LexemeBuffer {
    private int index;
    public List<Lexeme> lexemes;

    public LexemeBuffer(List<Lexeme> lexemes) {
        this.lexemes = lexemes;
    }

    public Lexeme next() {
        return lexemes.get(index++);
    }

    public void back() {
        index--;
    }

    public int getIndex() {
        return index;
    }
}
