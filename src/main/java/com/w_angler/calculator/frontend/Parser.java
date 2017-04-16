package com.w_angler.calculator.frontend;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import com.w_angler.calculator.backend.ast.AST;
import com.w_angler.calculator.backend.ast.internal.Assign;
import com.w_angler.calculator.backend.ast.internal.Expression;
import com.w_angler.calculator.backend.ast.internal.Program;
import com.w_angler.calculator.backend.ast.internal.Print;
import com.w_angler.calculator.backend.ast.leaf.Operand;
import com.w_angler.calculator.backend.ast.leaf.Operator;
import com.w_angler.calculator.backend.ast.leaf.Identifier;
import com.w_angler.calculator.exception.LexemeException;
import com.w_angler.calculator.exception.SyntaxException;
import com.w_angler.calculator.frontend.opg.Operator.ARY;
import com.w_angler.calculator.frontend.opg.Operators;
import com.w_angler.calculator.frontend.opg.Precedence;
import com.w_angler.calculator.frontend.opg.Precedence.Association;

import static com.w_angler.calculator.frontend.TokenType.*;

/**
 * Syntax Analysis, and its arithmetic expression use Operator Precedence Analysis
 * @author w-angler
 *
 */
public class Parser{
	/**
	 * cursor point to current token
	 */
	private Token cursor;
	/**
	 * tokenss
	 */
	private LinkedList<Token> tokens=new LinkedList<>();
	/**
	 * operators
	 */
	private Operators operators;
	private Lexer lexer;

	public Parser(Lexer lexer) {
		this.tokens = new LinkedList<Token>();
		this.operators = Operators.BASIC;
		this.lexer = lexer;
	}
	/**
	 * parsing
	 * @return
	 * @throws SyntaxException 
	 */
	public AST parse() throws SyntaxException, LexemeException {
		while (lexer.hasNext()) {
			tokens.add(lexer.next());
		}
		cursor = tokens.poll();
		return program();
	}
	/**
	 * has errors
	 * @throws SyntaxException 
	 */
	private void error(TokenType... type)throws SyntaxException {
		throw new SyntaxException(cursor+":Syntax Error!Excepted "+Arrays.stream(type).map(e->e.literal).collect(Collectors.toSet()));
	}
	/**
	 * has same token type?
	 * @param type token'type
	 * @return
	 * @throws SyntaxException 
	 */
	private void match(TokenType type) throws SyntaxException{
		if(!cursor.getType().equals(type)){
			error(type);
		}
		else{
			cursor=tokens.poll();
		}
	}
	/**
	 * <program>::=(<assign>|<print>)*
	 * @throws SyntaxException
	 */
	private AST program() throws SyntaxException{
		List<AST> sentences = new ArrayList<AST>();
		while (!tokens.isEmpty()) {
			switch (cursor.getType()) {
			case IDENTIFIER: {
				sentences.add(assign());
				continue;
			}
			case PRINT: {
				sentences.add(print());
				continue;
			}
			default: {
				error(IDENTIFIER,PRINT);
				continue;
			}
			}
		}
		return new Program(sentences);
	}
	/**
	 * <assign>::= VARIABLE "=" <expression> ";"
	 * @return
	 * @throws SyntaxException
	 */
	private AST assign() throws SyntaxException{
		Assign assign = new Assign();
		assign.setTarget(new Identifier(cursor));
		match(IDENTIFIER);
		match(ASSIGNMENT);
		AST value = expression();
		assign.setValue(value);
		match(SEMICOLON);
		return assign;
	}
	/**
	 * <print>::= PRINT "(" <expression> ")" ";"
	 * @throws SyntaxException
	 */
	private AST print() throws SyntaxException{
		match(PRINT);
		match(LEFT_BRACKET);
		AST expression=expression();
		match(RIGHT_BRACKET);
		match(SEMICOLON);
		return new Print(expression);
	}
	/**
	 * <factor>::= OPRAND | "(" <expression> ")" | VARIABLE
	 * @return
	 * @throws SyntaxException
	 */
	private AST factor() throws SyntaxException{
		switch (cursor.getType()) {
		case OPRAND: {
			AST operand = new Operand(cursor);
			match(OPRAND);
			return operand;
		}
		case LEFT_BRACKET: {
			match(LEFT_BRACKET);
			AST expr = expression();
			match(RIGHT_BRACKET);
			return expr;
		}
		case IDENTIFIER: {
			AST var = new Identifier(cursor);
			match(IDENTIFIER);
			return var;
		}
		default: {
			error(OPRAND,RIGHT_BRACKET,IDENTIFIER);
			return null;
		}
		}
	}

	/**
	 * @return
	 * @throws SyntaxException
	 */
	private AST expression() throws SyntaxException{
		AST expression=factor();
		Precedence precedence;
		while ((precedence = nextOperator()) != null){
			expression = shift(expression, precedence.value);
		}
		return expression;
	}
	/******************************************************************/
	private AST shift(AST left, int precedence) throws SyntaxException{
		Expression expression=new Expression();
		expression.setLeft(left);
		expression.setOp(new Operator(cursor));
		cursor=tokens.poll();
		AST right=factor();
		Precedence next;
		while ((next = nextOperator()) != null&& shouldContinue(precedence, next)){
			right = shift(right, next.value);
		}
		expression.setRight(right);
		return expression;
	}
	private Precedence nextOperator(){
		if (cursor!=null&&cursor.isOp()){
			return operators.get(cursor.getValue(),ARY.BINARY);
		}
		else{
			return null;
		}
	}
	private static boolean shouldContinue(int precedence, Precedence nextPrecedence) {
		if (nextPrecedence.association.equals(Association.LEFT)){
			return precedence<nextPrecedence.value;
		}
		else{
			return precedence<=nextPrecedence.value;
		}
	}
	/******************************************************************/
}
