package com.w_angler.calculator.ast.internal;

import com.w_angler.calculator.ast.AST;
import com.w_angler.calculator.ast.leaf.Identifier;

import javax.script.ScriptContext;
import javax.script.ScriptException;
import java.util.Objects;

/**
 * Assignment
 *
 * @author w-angler
 */
public class Assign extends AST {
    private Identifier target;
    private AST value;

    public void setTarget(AST target) {
        this.target = (Identifier) target;
    }

    public void setValue(AST value) {
        this.value = value;
    }

    @Override
    public Object eval(ScriptContext env) throws ScriptException {
        Object result = value.eval(env);
        env.setAttribute(target.name(), Objects.isNull(result) ? Double.NaN : (double) result, ScriptContext.ENGINE_SCOPE);
        return result;
    }
}
