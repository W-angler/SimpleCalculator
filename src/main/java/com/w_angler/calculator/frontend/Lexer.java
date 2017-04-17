package com.w_angler.calculator.frontend;

import com.w_angler.calculator.exception.LexemeException;

/**
 * Lexical Analysis
 * @author w-angler
 *
 */
public class Lexer{
    private static final char[] HEX="abcdefABCDEF".toCharArray();
	private Source source;
	
	public Lexer(Source source){
		this.source=source;
	}
	
	public boolean hasNext(){
		return source.peekChar()!=Source.EOF;
	}
	public Token next() throws LexemeException{
		char ch=source.nextChar();
		//reads next char if lexer meets white space
		while(Character.isWhitespace(ch)){
			ch=source.nextChar();
		}
		//isn't digit
		if (!Character.isDigit(ch)) {
            if (ch == '(') {
                return new Token(TokenType.LEFT_BRACKET, "(",
                		source.getLineNum(), source.getCurrentPosition());
            }
            if (ch == ')') {
                return new Token(TokenType.RIGHT_BRACKET, ")",
                		source.getLineNum(), source.getCurrentPosition());
            }
            if (ch == '=') {
                return new Token(TokenType.ASSIGNMENT, "=", 
                		source.getLineNum(), source.getCurrentPosition());
            }
            if (ch == ';') {
                return new Token(TokenType.SEMICOLON, ";", 
                		source.getLineNum(), source.getCurrentPosition());
            }
            if (ch == '+') {
                return new Token(TokenType.ADD, "+", 
                		source.getLineNum(), source.getCurrentPosition());
            }
            if (ch == '-') {
                return new Token(TokenType.SUB, "-", 
                		source.getLineNum(), source.getCurrentPosition());
            }
            if (ch == '/') {
                return new Token(TokenType.DIV, "/", 
                		source.getLineNum(), source.getCurrentPosition());
            }
            if (ch == '%') {
                return new Token(TokenType.MOD, "%",
                		source.getLineNum(), source.getCurrentPosition());
            }
            if (ch == '*') {
                if (source.peekChar() == '*') {
                    ch = source.nextChar();
                    return new Token(TokenType.POW, "**", 
                    		source.getLineNum(), source.getCurrentPosition() - 1);
                }
                return new Token(TokenType.MUL, "*", 
                		source.getLineNum(), source.getCurrentPosition());
            }
            else {
                StringBuilder strToken = new StringBuilder();
                strToken.append(ch);
                int offset = 0;
                while(Character.isAlphabetic(source.peekChar()) 
                		||Character.isDigit(source.peekChar())) {
                    ch = source.nextChar();
                    ++offset;
                    if(ch==Source.EOF) {
                        break;
                    }
                    strToken.append(ch);
                }
                String tokenValue=strToken.toString();
                if ("print".equals(tokenValue)) {
                    return new Token(TokenType.PRINT, "print", 
                    		source.getLineNum(), source.getCurrentPosition() - offset);
                }
                return new Token(TokenType.IDENTIFIER, tokenValue, 
                		source.getLineNum(), source.getCurrentPosition() - offset);
            }
        }
		//is digit
        else {
            if (ch=='0'&&(source.peekChar()=='x'||source.peekChar()=='X')){
                StringBuilder hex = new StringBuilder();
                hex.append(ch);
                hex.append(source.nextChar());
                int offset = 1;
                if (isHex(source.peekChar())) {
                    while (isHex(source.peekChar())) {
                        ch = source.nextChar();
                        ++offset;
                        if (ch == Source.EOF) {
                            break;
                        }
                        hex.append(ch);
                    }
                    if (hex.length()!= 2) {
                        return new Token(TokenType.OPRAND, hex.toString(), 
                        		source.getLineNum(), source.getCurrentPosition() - offset);
                    }
                }
                final Token error = new Token(TokenType.ERROR, hex.toString(), 
                		source.getLineNum(), source.getCurrentPosition() - offset);
                throw new LexemeException(error + ":invalid hexadecimal number");
            }
            final StringBuilder number=new StringBuilder();
            number.append(ch);
            int offset = 0;
            while (Character.isDigit(source.peekChar())
            		||(source.peekChar() == '_' && Character.isDigit(source.peekChar(2)))) {
                ch = source.nextChar();
                ++offset;
                if (ch == Source.EOF) {
                    break;
                }
                number.append(ch);
            }
            if (source.peekChar() != '.') {
                return new Token(TokenType.OPRAND, number.toString(), 
                		source.getLineNum(), source.getCurrentPosition() - offset);
            }
            number.append(source.nextChar());
            while (Character.isDigit(source.peekChar()) 
            		||(source.peekChar() == '_' && Character.isDigit(source.peekChar(2)))) {
                ch = source.nextChar();
                ++offset;
                if (ch == Source.EOF) {
                    break;
                }
                number.append(ch);
            }
            return new Token(TokenType.OPRAND, number.toString(), 
            		source.getLineNum(), source.getCurrentPosition() - offset);
        }
	}
	/**
	 * is a hexadecimal number?
	 * @param ch
	 * @return
	 */
    private static final boolean isHex(final char ch) {
        for (int i=0;i<HEX.length;i++) {
            if (HEX[i]==ch) {
                return true;
            }
        }
        return Character.isDigit(ch);
    }
}
