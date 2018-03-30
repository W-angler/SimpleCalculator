package com.w_angler.calculator.ast.leaf;

import com.w_angler.calculator.ast.AST;
import com.w_angler.calculator.frontend.Token;

import javax.script.ScriptContext;
import javax.script.ScriptException;

/**
 * Operator
 *
 * @author w-angler
 */
public class Operator extends AST {
    private Token op;

    public Operator(Token op) {
        this.op = op;
    }

    public Token getToken() {
        return op;
    }

    @Override
    public Object eval(ScriptContext env) throws ScriptException {
        return op.getValue();
    }
}
