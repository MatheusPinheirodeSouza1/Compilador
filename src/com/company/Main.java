package com.company;

import java.text.MessageFormat;
import Lexico.Lexer;
import Lexico.Tag;
import Lexico.Token;

public class Main {

    public static void main(String[] args) throws Exception {
        if (args.length != 1) {
            System.out.println("Usage: java mlab [MiniLab File]");
            return;
        }

        var lexer = new Lexer(args[0]);
        Token token;
        do
        {
            token = lexer.scan();
            System.out.println(MessageFormat.format("Token: {0} -> Type: {1}", token.toString(), token.tag));
        } while(stop(token.tag));

        if (token.tag == Tag.UNEXPECTED_EOF || token.tag == Tag.INVALID_TOKEN)
        {
            System.out.println(MessageFormat.format("Erro at Line {0}: {1}", lexer.line, token.tag));
        }
    }

    private static boolean stop (Tag tag){
        return tag != Tag.END_OF_FILE && tag != Tag.UNEXPECTED_EOF && tag != Tag.INVALID_TOKEN;
    }
}
