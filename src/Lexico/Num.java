package Lexico;

public class Num extends Token
{
    public int value;

    public Num(int value)
    {
        super(Tag.NUMBER);
        this.value = value;
    }

    public String toString()
    {
        return String.valueOf(value);
    }
}
