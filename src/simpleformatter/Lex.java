
package simpleformatter;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Lex 
{
    
    private ArrayList<Token> tokens;
    Boolean error = false;
    
    public Lex(String input)
    {

        tokens = new ArrayList<>();
        int lineCount=1;

        StringBuilder tokenPatternsBuffer = new StringBuilder();
        for (TokenType tokenType : TokenType.values())
            tokenPatternsBuffer.append(String.format("|(?<%s>%s)", tokenType.name(), tokenType.pattern));
        Pattern tokenPatterns = Pattern.compile(tokenPatternsBuffer.substring(1));
        
        Matcher matcher = tokenPatterns.matcher(input);
       
        while (matcher.find())            
            for(TokenType token : TokenType.values())
                if(matcher.group(token.name()) != null){
                    if (null != token.name())
                        switch (token.name()) {
                            case "unknownTK":
                                System.out.format("Lexer error - Unknown Symbol at line %d: %s\n",
                                        lineCount,matcher.group(token.name()));
                                error = true;
                                break;
                            case "newlineTK":
                                lineCount++;
                                break;
                            case "whitespaceTK":
                                ;
                                break;
                            default:
                                tokens.add(new Token(token , matcher.group(token.name()), lineCount));
                                break;
                        }
                }        
    }


    public ArrayList<Token> getTokens() 
    {
        return tokens;
    }
    
    public Token nextToken()
    {
        Token next = tokens.remove(0);
//        System.out.println(next);
        return next;
    } 
    
    public Boolean getError(){
        return error;
    }
}
