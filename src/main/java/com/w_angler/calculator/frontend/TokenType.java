package com.w_angler.calculator.frontend;

import java.util.EnumSet;


/**
 * Token's type
 * @author w-angler
 * 
 */
public enum TokenType{
	/**
	 *  identifier, likes {@code abc}
	 */
	IDENTIFIER("identifier"),
	/**
	 * assignment, {@code =}
	 */
	ASSIGNMENT("="),
	/**
	 * operand, likes {@code 123,1.0}
	 */
	OPRAND("operand"),
	/**
	 * end of sentence, {@code ;}
	 */
	SEMICOLON(";"),
	/**
	 * add operator, {@code +}
	 */
	ADD("+"),
	/**
	 * subtract operator, {@code -}
	 */
	SUB("-"),
	/**
	 * multiply operator, {@code *}
	 */
	MUL("*"),
	/**
	 * divide operator, {@code /}
	 */
	DIV("/"),
	/**
	 * modulus operator, {@code %}
	 */
	MOD("%"),
	/**
	 * power operator, {@code **}
	 */
	POW("**"),
	/**
	 * left bracket, {@code (}
	 */
	LEFT_BRACKET("("),
	/**
	 * right bracket, {@code )}
	 */
	RIGHT_BRACKET(")"),
	/**
	 * print, {@code print}
	 */
	PRINT("print"),
	/**
	 * error
	 */
	ERROR("error");
	/**
	 * range of operators' type
	 */
	public static EnumSet<TokenType> OP=EnumSet.range(ADD, POW);
	public String literal;
	private TokenType(String literal) {
		this.literal=literal;
	}
}
