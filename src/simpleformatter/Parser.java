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
public class Parser {

    Token token;
    Lex lex;
    Boolean error = false;

    public Parser(String buffer) {
        lex = new Lex(buffer);
        if (lex.getError()) {
            error = true;
        } else {
            token = lex.nextToken();
            program();
        }
    }

    private void error() {
        if (!error) {
            System.out.format("Syntax error in line %d\n", token.getLine());
        }
        error = true;
    }

    public Boolean getError() {
        return error;
    }

    private void program() {
        if (token.getType().name().equals("programTK")) {
            FormTools.produceToken(token.getData());
            FormTools.produceSpace();
            token = lex.nextToken();
            if (token.getType().name().equals("variableTK")) {
                FormTools.produceToken(token.getData());
                FormTools.produceNewLine();
                FormTools.addTab();
                FormTools.produceTabs();
                token = lex.nextToken();
                statements();
                FormTools.produceNewLine();

                if (error) {
                    System.out.println("*********************");
                    System.out.println("Compiled code with errors");
                    System.out.println("*********************");
                } else {
                    System.out.println("*********************");
                    System.out.println("Success Compiled code");
                    System.out.println("*********************");
                }
            } else {
                error();
            }
        } else {
            error();
        }
    }

    private void statements() {
        statement();
        while (token.type.name().equals("semicolTK")) {
            FormTools.produceToken(token.getData());
            FormTools.produceNewLine();
            FormTools.produceTabs();
            token = lex.nextToken();
            statement();
        }
    }

    private void statement() {
        if (token.type.name().equals("ifTK")) {
            FormTools.produceToken(token.getData());
            FormTools.produceSpace();
            if_stat();
        } else if (token.type.name().equals("whileTK")) {
            FormTools.produceToken(token.getData());
            while_stat();
        } else if (token.type.name().equals("printTK")) {
            FormTools.produceToken(token.getData());
            print_stat();
        } else if (token.type.name().equals("variableTK")) {
            FormTools.produceToken(token.getData());
            FormTools.produceSpace();
            assign_stat();
        } else {
            error();
        }
    }

    private void print_stat() {
        token = lex.nextToken();
        if (!token.type.name().equals("leftpTK")) {
            error();
        }

        // add left parenthesis
        FormTools.produceToken(token.getData());
        FormTools.produceSpace();

        token = lex.nextToken();
        expression();

        if (!token.type.name().equals("rightpTK")) {
            error();
        }

        // add right parenthesis
        FormTools.produceSpace();
        FormTools.produceToken(token.getData());
    }

    private void assign_stat() {
        token = lex.nextToken();
        if (token.type.name().equals("assignTK")) {
            FormTools.produceToken(token.getData());
            FormTools.produceSpace();
            token = lex.nextToken();
            FormTools.produceSpace();
            expression();
        } else {
            error();
        }
    }

    private void while_stat() {

        token = lex.nextToken();
        if (!token.type.name().equals("leftpTK")) {
            error();
        }
        // add left parenthesis
        FormTools.produceToken(token.getData());
        FormTools.produceSpace();

        token = lex.nextToken();
        expression();
        relOperator();
        expression();

        if (!token.type.name().equals("rightpTK")) {
            error();
        }
        // add right parenthesis
        FormTools.produceSpace();
        FormTools.produceToken(token.getData());

        token = lex.nextToken();
        if (!token.type.name().equals("leftbTK")) {
            error();
        }
        // add left curly brace
        FormTools.produceNewLine();
        FormTools.produceTabs();
        FormTools.produceToken(token.getData());

        // new line plus one tab for statements
        FormTools.produceNewLine();
        FormTools.addTab();
        FormTools.produceTabs();

        token = lex.nextToken();
        statements();

        // Remove the extra tab 
        FormTools.deTab();

        if (!token.type.name().equals("rightbTK")) {
            error();
        }
        // add right curly brace
        FormTools.produceNewLine();
        FormTools.produceTabs();
        FormTools.produceToken(token.getData());

        token = lex.nextToken();
    }

    private void if_stat() {

        token = lex.nextToken();
        if (!token.type.name().equals("leftpTK")) {
            error();
        }
        // add left parenthesis
        FormTools.produceToken(token.getData());
        FormTools.produceSpace();

        token = lex.nextToken();

        expression();
        relOperator();
        expression();

        if (!token.type.name().equals("rightpTK")) {
            error();
        }

        // add right parenthesis
        FormTools.produceSpace();
        FormTools.produceToken(token.getData());

        token = lex.nextToken();
        if (!token.type.name().equals("leftbTK")) {
            error();
        }

        // add left curly brace
        FormTools.produceNewLine();
        FormTools.produceTabs();
        FormTools.produceToken(token.getData());

        // new line plus one tab for statements
        FormTools.produceNewLine();
        FormTools.addTab();
        FormTools.produceTabs();

        token = lex.nextToken();
        statements();

        // Remove the extra tab 
        FormTools.deTab();

        if (!token.type.name().equals("rightbTK")) {
            error();
        }
        // add right curly brace
        FormTools.produceNewLine();
        FormTools.produceTabs();
        FormTools.produceToken(token.getData());

        token = lex.nextToken();
        else_part();
    }

    private void else_part() {

        if (token.type.name().equals("elseTK")) {
            // add else keyword in new line with tabs
            FormTools.produceNewLine();
            FormTools.produceTabs();
            FormTools.produceToken(token.getData());

            token = lex.nextToken();
            if (!token.type.name().equals("leftbTK")) {
                error();
            }
            // add left curly brace
            FormTools.produceNewLine();
            FormTools.produceTabs();
            FormTools.produceToken(token.getData());

            // new line plus one tab for statements
            FormTools.produceNewLine();
            FormTools.addTab();
            FormTools.produceTabs();

            token = lex.nextToken();
            statements();

            // Remove a tab 
            FormTools.deTab();

            if (!token.type.name().equals("rightbTK")) {
                error();
            }
            // add right curly brace
            FormTools.produceNewLine();
            FormTools.produceTabs();
            FormTools.produceToken(token.getData());

            token = lex.nextToken();
        }

    }

    private void expression() {
        optional_sign();
        term();
        while (token.type.name().equals("plusTK") || token.type.name().equals("minusTK")) {
            add_oper();
            term();
        }
    }

    private void term() {
        factor();
        while (token.type.name().equals("multTK") || token.type.name().equals("divTK")) {
            mult_oper();
            factor();
        }
    }

    private void factor() {
        if (token.type.name().equals("integerTK")) {
            FormTools.produceToken(token.getData());
            token = lex.nextToken();
        } else if (token.type.name().equals("variableTK")) {
            FormTools.produceToken(token.getData());
            token = lex.nextToken();
        } else if (token.type.name().equals("leftpTK")) {
            FormTools.produceToken(token.getData());
            token = lex.nextToken();
            expression();
            if (token.type.name().equals("rightpTK")) {
                FormTools.produceToken(token.getData());
                token = lex.nextToken();
            } else {
                error();
            }
        } else {
            error();
        }
    }

    private void relOperator() {
        if (token.type.name().equals("equalTK")) {
            FormTools.produceToken(token.getData());
            token = lex.nextToken();
        } else if (token.type.name().equals("smallerTK")) {
            FormTools.produceToken(token.getData());
            token = lex.nextToken();
        } else if (token.type.name().equals("largerTK")) {
            FormTools.produceToken(token.getData());
            token = lex.nextToken();
        } else if (token.type.name().equals("smallerEqualTK")) {
            FormTools.produceToken(token.getData());
            token = lex.nextToken();
        } else if (token.type.name().equals("largerEqualTK")) {
            FormTools.produceToken(token.getData());
            token = lex.nextToken();
        } else if (token.type.name().equals("notEqualTK")) {
            FormTools.produceToken(token.getData());
            token = lex.nextToken();
        } else {
            error();
        }
    }

    private void add_oper() {
        if (token.type.name().equals("plusTK")) {
            FormTools.produceToken(token.getData());
            token = lex.nextToken();
        } else if (token.type.name().equals("minusTK")) {
            FormTools.produceToken(token.getData());
            token = lex.nextToken();
        } else {
            error();
        }
    }

    private void mult_oper() {
        if (token.type.name().equals("multTK")) {
            FormTools.produceToken(token.getData());
            token = lex.nextToken();
        } else if (token.type.name().equals("divTK")) {
            FormTools.produceToken(token.getData());
            token = lex.nextToken();
        } else {
            error();
        }
    }

    private void optional_sign() {
        if (token.type.name().equals("plusTK") || token.type.name().equals("minusTK")) {
            add_oper();
        }
    }

}
