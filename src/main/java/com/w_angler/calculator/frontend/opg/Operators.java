package com.w_angler.calculator.frontend.opg;

import java.util.HashMap;

import com.w_angler.calculator.frontend.opg.Operator.ARY;
import com.w_angler.calculator.frontend.opg.Precedence.Association;

import static com.w_angler.calculator.frontend.opg.Precedence.Association.*;
import static com.w_angler.calculator.frontend.opg.Operator.ARY.*;
/**
 * Operators
 * @author w-angler
 *
 */
public class Operators {
	private HashMap<Operator,Precedence> operators=new HashMap<>();
	
	/**
	 * basic operators
	 */
	public static final Operators BASIC=new Operators();
	static{
		BASIC.add("+",BINARY,2,LEFT);
		BASIC.add("-",BINARY,2,LEFT);
		BASIC.add("*",BINARY,4,LEFT);
		BASIC.add("/",BINARY,4,LEFT);
		BASIC.add("%",BINARY,4,LEFT);
		BASIC.add("**",BINARY,6,RIGHT);
	}
	/**
	 * add operator
	 * @param name operator's name
	 * @param ary operand's number
	 * @param value precedence value
	 * @param association operator's association
	 */
	public void add(String name,ARY ary, int value, Association association) {
		operators.put(new Operator(name, ary), new Precedence(value, association));
	}
	/**
	 * get operator's precedence
	 * @param name operator's name
	 * @param ary operand's number
	 * @return
	 */
	public Precedence get(String name,ARY ary){
		return operators.get(new Operator(name, ary));
	}
	@Override
	public String toString(){
		return operators.keySet().toString();
	}
}
