package com.w_angler.calculator.backend;

import java.util.HashMap;

/**
 * an environment for evaluation
 * @author w-angler
 *
 */
public class Environment {
	private HashMap<String, Double> variables=new HashMap<>();
	
	public double get(String key){
		return variables.get(key);
	}
	public Object add(String key,double value){
		return variables.put(key, value);
	}
	public boolean contains(String key){
		return variables.containsKey(key);
	}
}
