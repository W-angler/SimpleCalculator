package com.w_angler.calculator.jsr223;

import com.w_angler.calculator.ast.AST;
import com.w_angler.calculator.exception.LexemeException;
import com.w_angler.calculator.exception.SyntaxException;
import com.w_angler.calculator.frontend.Lexer;
import com.w_angler.calculator.frontend.Parser;
import com.w_angler.calculator.frontend.Source;

import javax.script.*;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

public class CalculatorScriptEngine extends AbstractScriptEngine {
    @Override
    public Object eval(String script, ScriptContext context) throws ScriptException {
        return eval(new StringReader(script), context);
    }

    @Override
    public Object eval(Reader reader, ScriptContext context) throws ScriptException {
        try (Source source = new Source(reader)) {
            Lexer lexer = new Lexer(source);
            Parser parser = new Parser(lexer);
            AST ast = parser.parse();
            return ast.eval(context);
        } catch (SyntaxException | IllegalArgumentException | LexemeException | IOException e) {
            throw new ScriptException(e);
        }
    }

    @Override
    public Bindings createBindings() {
        //不支持
        return null;
    }

    @Override
    public ScriptEngineFactory getFactory() {
        return new CalculatorScriptEngineFactory();
    }
}
