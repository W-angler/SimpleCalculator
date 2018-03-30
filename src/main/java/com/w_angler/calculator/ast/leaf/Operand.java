package com.w_angler.calculator.ast.leaf;

import com.w_angler.calculator.ast.AST;
import com.w_angler.calculator.frontend.Token;

import javax.script.ScriptContext;
import javax.script.ScriptException;

/**
 * Operand
 *
 * @author w-angler
 */
public class Operand extends AST {
    private Token token;

    public Operand(Token token) {
        this.token = token;
    }

    @Override
    public Object eval(ScriptContext env) throws ScriptException {
        String value = token.getValue();
        try {
            if (value.startsWith("0x") || value.startsWith("0X") || (value.contains("_") && !value.contains("."))) {
                return Long.decode(value.replaceAll("_", ""));
            }
            return Double.parseDouble(value.replaceAll("_", ""));
        } catch (Throwable e) {
            throw new ScriptException("Number Format Exception:" + this.token.getValue());
        }
    }

}
