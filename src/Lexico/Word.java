package Lexico;

public class Word extends Token
{
    private String lexeme;

    public Word (String lexeme, Tag tag)
    {
        super(tag);
        this.lexeme = lexeme;
    }

    public String toString()
    {
        return String.valueOf(this.lexeme);
    }
}
