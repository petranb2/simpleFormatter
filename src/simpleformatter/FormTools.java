/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package simpleformatter;

/**
 *
 * @author george
 */
public class FormTools {

    private static int tabs = 0;
    private static StringBuffer beautiful = new StringBuffer(""); 

    public static void addTab(){
        tabs++;
    }

    public static void deTab(){
        tabs--;
    }

    public static void produceTabs(){
        for (int i=0;i<tabs;i++)
            beautiful.append(new StringBuffer("    "));
    }

    public static void produceToken(String s)
    {
        beautiful.append(new StringBuffer(s));
    }

    public static void produceNewLine()
    {
        beautiful.append(new StringBuffer("\n"));
    }

    public static void produceSpace()
    {
        beautiful.append(new StringBuffer(" "));
    }
    
    public static StringBuffer getProduced()
    {
        return beautiful;
    }

    public static void resetProduced()
    {
        beautiful.setLength(0);
        tabs = 0;
    }

    
    
}
