
package simpleformatter;

public class Token 
{
    public TokenType type;
    public String data;
    public int line;

    public Token(TokenType type, String data, int line) 
    {
        this.type = type;
        this.data = data;
        this.line = line;
    }

    
    public TokenType getType() 
    {
        return type;
    }

    public void setType(TokenType type) 
    {
        this.type = type;
    }

    public String getData() 
    {
        return data;
    }

    public void setData(String data) 
    {
        this.data = data;
    }

    public int getLine()    
    {
        return line;
    }

    public void setLine(int line) 
    {
        this.line = line;
    }

    
    @Override
    public String toString() 
    {
        return String.format("(%s %s %d)", type.name(), data, line);
    }    
}
