package Lexico;

public class Token
{
    public Tag tag;

    public Token (Tag tag)
    {
        this.tag = tag;
    }

    public String toString()
    {
        return String.valueOf(this.tag.value);
    }
}
