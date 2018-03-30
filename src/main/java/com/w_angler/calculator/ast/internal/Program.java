package com.w_angler.calculator.ast.internal;

import com.w_angler.calculator.ast.AST;

import javax.script.ScriptContext;
import javax.script.ScriptException;
import java.util.List;

/**
 * the whole program
 *
 * @author w-angler
 */
public class Program extends AST {
    private List<AST> sentences;

    public Program(List<AST> sentences) {
        this.sentences = sentences;
    }

    @Override
    public Object eval(ScriptContext env) throws ScriptException {
        for (AST sentence : sentences) {
            sentence.eval(env);
        }
        return null;
    }
}
