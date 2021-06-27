package Lexico;

import java.io.*;
import java.util.*;

public class Lexer {
    public int line = 1;
    private char charReaded = ' ';
    private FileReader file;
    private Hashtable<String, Word> words = new Hashtable<String, Word>();

    public Lexer(String fileName) throws FileNotFoundException{
        try{
            file = new FileReader(fileName);
        }
        catch(FileNotFoundException e){
            System.out.println("Arquivo não encontrado");
            throw e;
        }

        //Insere palavras reservadas na HashTable
        this.insertReserveWords();
    }

    public Token scan() throws IOException{

        //Desconsidera delimitadores na entrada
        for (;; this.readchar())
        {
            if (this.charReaded == ' ' || this.charReaded == '\t' || this.charReaded == '\r' || this.charReaded == '\b')
            {
                continue;
            }
            else if (this.charReaded == '\n')
            {
                this.line++;
            }
            else {
                break;
            }
        }

        switch(this.charReaded)
        {
            //Operadores
            case '&':
                if (this.readchar('&')){
                    return new Word("&&", Tag.AND);
                }
                else
                {
                    return new Word("&", Tag.INVALID_TOKEN);
                }
            case '|':
                if (this.readchar('|')){
                    return new Word("||", Tag.OR);
                }
                else
                {
                    return new Word("|", Tag.INVALID_TOKEN);
                }
            case '=':
                if (this.readchar('='))
                {
                    return new Word("==", Tag.EQUAL);
                }
                else
                {
                    return new Word("=", Tag.ASSIGN);
                }
            case '<':
                if (this.readchar('='))
                {
                    return new Word("<=", Tag.LOWER_EQ);
                }
                else
                {
                    return new Word("<", Tag.LOWER);
                }
            case '>':
                if (this.readchar('='))
                {
                    return new Word(">=", Tag.GREATER_EQ);
                }
                else
                {
                    return new Word("<", Tag.GREATER);
                }
            case '-':
                this.charReaded = ' ';
                return new Word("-", Tag.MINS);
            case '+':
                this.charReaded = ' ';
                return new Word("+", Tag.PLUS);
            case '(':
                this.charReaded = ' ';
                return new Word("(", Tag.OPEN_PAR);
            case ')':
                this.charReaded = ' ';
                return new Word(")", Tag.CLOSE_PAR);
            case '{':
                this.charReaded = ' ';
                return new Word("{", Tag.OPEN_BRA);
            case '}':
                this.charReaded = ' ';
                return new Word("}", Tag.CLOSE_BRA);
            case '\"':
                var text = String.valueOf(this.charReaded);

                do
                {
                    this.readchar();

                    if (this.charReaded == (char)-1){
                        return new Token(Tag.UNEXPECTED_EOF);
                    }

                    text += this.charReaded;

                } while(this.charReaded != '\"');

                this.charReaded = ' ';
                return new Word(text, Tag.STRING);
            case '/':
                if (this.readchar('/')){
                    var comment = "//";

                    do
                    {
                        this.readchar();

                        comment += this.charReaded;

                    } while(this.charReaded != '\n' && this.charReaded != (char)-1);

                    this.charReaded = ' ';
                    return new Word(comment, Tag.COMMENT_LINE);
                }
                else if(this.charReaded == '*'){
                    var multipleComment = "/*";

                    do
                    {
                        this.readchar();

                        if (this.charReaded == (char)-1){
                            return new Token(Tag.UNEXPECTED_EOF);
                        }

                        multipleComment += this.charReaded;

                    } while(!multipleComment.contains("*/"));

                    this.charReaded = ' ';
                    return new Word(multipleComment, Tag.MULTI_COMMENT_LINE);
                }
                else
                {
                    return new Word("/", Tag.DIV);
                }
            case '*':
                this.charReaded = ' ';
                return new Word("*", Tag.TIMES);
            case ',':
                this.charReaded = ' ';
                return new Word(",", Tag.COMMA);
            case ';':
                this.charReaded = ' ';
                return new Word(";", Tag.DOT_COMMA);
            case (char)-1:
                return new Token(Tag.END_OF_FILE);
        }

        if (Character.isDigit(this.charReaded)){
            var value = 0;

            do
            {
                value = 10 * value + Character.digit(this.charReaded, 10);
                this.readchar();
            } while(Character.isDigit(this.charReaded));

            return new Num(value);
        }

        if (Character.isLetter(this.charReaded)){
            var sb = new StringBuffer();

            do
            {
                sb.append(this.charReaded);
                this.readchar();
            } while(Character.isLetterOrDigit(this.charReaded));

            var string = sb.toString();
            var word = (Word)this.words.get(string);

            if (word != null)
            {
                return word;
            }

            word = new Word(string, Tag.ID);
            this.words.put(string, word);

            return word;
        }

        var token = new Word(String.valueOf(this.charReaded), Tag.INVALID_TOKEN);
        this.charReaded = ' ';
        return token;
    }

    private void insertReserveWords()
    {
        reserve(new Word ("Class", Tag.CLASS));
        reserve(new Word ("int", Tag.INT));
        reserve(new Word ("string", Tag.STRING));
        reserve(new Word ("float", Tag.FLOAT));
        reserve(new Word ("init", Tag.INIT));
        reserve(new Word ("stop", Tag.STOP));
        reserve(new Word ("if", Tag.IF));
        reserve(new Word ("else", Tag.ELSE));
        reserve(new Word ("do", Tag.DO));
        reserve(new Word ("while", Tag.WHILE));
        reserve(new Word ("read", Tag.READ));
        reserve(new Word ("write", Tag.WRITE));
    }

    private void reserve(Word word)
    {
        this.words.put(word.toString(), word);
    }

    private void readchar() throws IOException
    {
        this.charReaded = (char)this.file.read();
    }

    private boolean readchar(char charReaded) throws IOException
    {
        this.readchar();

        if (this.charReaded != charReaded)
        {
            return false;
        }

        this.charReaded = ' ';

        return true;
    }
}
