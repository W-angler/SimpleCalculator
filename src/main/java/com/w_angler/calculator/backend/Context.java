package com.w_angler.calculator.backend;

import java.util.HashMap;
import java.util.Map;

/**
 * A context for code generation
 * @author w-angler
 *
 */
public class Context
{
    private Map<String, Integer> variables= new HashMap<String, Integer>();
    
    public int getVariableNumber() {
        return variables.size();
    }
    
    public void addVariable(String name) {
        variables.put(name, variables.size());
    }
    
    public boolean exists(String name) {
        return variables.containsKey(name);
    }
    
    public int slot(String name) {
        return variables.get(name);
    }
}
