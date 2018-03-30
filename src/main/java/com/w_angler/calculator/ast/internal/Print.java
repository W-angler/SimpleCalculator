package com.w_angler.calculator.ast.internal;

import com.w_angler.calculator.ast.AST;

import javax.script.ScriptContext;
import javax.script.ScriptException;
import java.io.IOException;

/**
 * write value to standard output stream
 *
 * @author w-angler
 */
public class Print extends AST {
    private AST value;

    public Print(AST value) {
        this.value = value;
    }

    @Override
    public Object eval(ScriptContext env) throws ScriptException {
        try {
            Object result = value.eval(env).toString();
            env.getWriter().write(result.toString() + "\n");
            return result;
        } catch (IOException e) {
            return null;
        }
    }
}
