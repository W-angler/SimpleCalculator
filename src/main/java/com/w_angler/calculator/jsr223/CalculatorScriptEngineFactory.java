package com.w_angler.calculator.jsr223;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineFactory;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class CalculatorScriptEngineFactory implements ScriptEngineFactory {
    @Override
    public String getEngineName() {
        return "Calculator Script Engine";
    }

    @Override
    public String getEngineVersion() {
        return "1.0";
    }

    @Override
    public List<String> getExtensions() {
        return Arrays.asList("in", "calc");
    }

    @Override
    public List<String> getMimeTypes() {
        return Arrays.asList("text/calculatorscript","application/calculatorscript");
    }

    @Override
    public List<String> getNames() {
        return Arrays.asList("CalculatorScript", "Calculator", "calculator", "Calc", "calc");
    }

    @Override
    public String getLanguageName() {
        return "Calculator Script";
    }

    @Override
    public String getLanguageVersion() {
        return "1.0";
    }

    @Override
    public Object getParameter(String key) {
        if (Objects.equals(key, ScriptEngine.ENGINE))
            return this.getEngineName();
        else if (Objects.equals(key, ScriptEngine.ENGINE_VERSION))
            return this.getEngineVersion();
        else if (Objects.equals(key, ScriptEngine.NAME))
            return this.getNames();
        else if (Objects.equals(key, ScriptEngine.LANGUAGE))
            return this.getLanguageName();
        else if (Objects.equals(key, ScriptEngine.LANGUAGE_VERSION))
            return this.getLanguageVersion();
        else
            return null;
    }

    @Override
    public String getMethodCallSyntax(String obj, String m, String... args) {
        //不支持
        return "";
    }

    @Override
    public String getOutputStatement(String toDisplay) {
        return "print("+toDisplay+");";
    }

    @Override
    public String getProgram(String... statements) {
        //不支持
        return "";
    }

    @Override
    public ScriptEngine getScriptEngine() {
        return new CalculatorScriptEngine();
    }
}
