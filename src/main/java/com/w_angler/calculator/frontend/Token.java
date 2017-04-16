package com.w_angler.calculator.frontend;

/**
 * Token
 * @author w-angler
 *
 */
public class Token{
	/**
	 * token's type, declared in {@link TokenType}
	 */
	private TokenType type;
	/**
	 * token's value
	 */
	private String value;
	/**
	 * token's line number
	 */
	private int line;
	/**
	 * token's start position
	 */
	private int position;
	
	public Token(){}
	public Token(TokenType type,String value,int line,int position){
		this.type=type;
		this.value=value;
		this.line=line;
		this.position=position;
	}
	public TokenType getType() {
		return type;
	}
	public String getValue() {
		return value;
	}
	public int getLine() {
		return line;
	}
	public int getPosition() {
		return position;
	}
	public boolean is(TokenType tokenType){
		return type.equals(tokenType);
	}
	public boolean isOp() {
		return TokenType.OP.contains(this.getType());
	}
	@Override
    public String toString() {
        return "['" + this.value + "'=" + this.type.name() + ",line:" + this.line + ",position:" + this.position + "]";
    }
}
