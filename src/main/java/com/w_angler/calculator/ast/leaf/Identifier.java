package com.w_angler.calculator.ast.leaf;

import com.w_angler.calculator.ast.AST;
import com.w_angler.calculator.frontend.Token;

import javax.script.ScriptContext;
import javax.script.ScriptException;

import static java.util.Objects.nonNull;

/**
 * identifier
 *
 * @author w-angler
 */
public class Identifier extends AST {
    private Token token;

    public Identifier(Token token) {
        this.token = token;
    }

    public String name() {
        return token.getValue();
    }

    @Override
    public Object eval(ScriptContext env) throws ScriptException {
        Object value = env.getAttribute(token.getValue());
        if (nonNull(value)) {
            return value;
        }
        throw new ScriptException(token + ":variable does not exist!");
    }
}
