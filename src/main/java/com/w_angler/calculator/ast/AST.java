package com.w_angler.calculator.ast;

import javax.script.ScriptContext;
import javax.script.ScriptException;

/**
 * Abstract Syntax Tree
 *
 * @author w-angler
 */
public abstract class AST {
    /**
     * evaluate this abstract syntax tree in specified environment
     *
     * @param env environment
     * @return eval result
     */
    public abstract Object eval(ScriptContext env) throws ScriptException;
}
