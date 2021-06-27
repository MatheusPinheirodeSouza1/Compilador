package Lexico;

public enum Tag
{
    // special tokens
    INVALID_TOKEN(-2),
    UNEXPECTED_EOF(-1),
    END_OF_FILE(0),

    // Palavras reservadas
    CLASS(1),
    INT(2),
    STRING(3),
    FLOAT(4),
    INIT(5),
    STOP(6),
    IF(7),
    ELSE(8),
    DO(9),
    WHILE(10),
    READ(11),
    WRITE(12),

    //Operadores e pontuação
    ASSIGN(13),
    EQUAL(14),
    DIFF(15),
    LOWER(16),
    GREATER(17),
    LOWER_EQ(18),
    GREATER_EQ(19),
    AND(20),
    OR(21),
    PLUS(22),
    MINS(23),
    TIMES(24),
    DIV(25),
    OPEN_PAR(26),
    CLOSE_PAR(27),
    OPEN_BRA(28),
    CLOSE_BRA(29),
    COMMENT_LINE(30),
    MULTI_COMMENT_LINE(31),
    DOT(32),
    DOT_COMMA(33),
    COMMA(34),

    //Outros tokens
    WORD(35),
    NUMBER(36),
    ID(37);

    public int value;

    Tag(int valor)
    {
        value = valor;
    }
}
