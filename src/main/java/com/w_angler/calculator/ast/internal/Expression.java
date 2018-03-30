package com.w_angler.calculator.ast.internal;

import com.w_angler.calculator.ast.AST;
import com.w_angler.calculator.ast.leaf.Operator;

import javax.script.ScriptContext;
import javax.script.ScriptException;
import java.util.Objects;

/**
 * arithmetic expression
 *
 * @author w-angler
 */
public class Expression extends AST {
    private Operator op;
    private AST left;
    private AST right;

    public void setOp(AST op) {
        this.op = (Operator) op;
    }

    public void setLeft(AST left) {
        this.left = left;
    }

    public void setRight(AST right) {
        this.right = right;
    }

    @Override
    public Object eval(ScriptContext env) throws ScriptException {
        Object lResult = left.eval(env);
        Object rResult = right.eval(env);
        if (Objects.isNull(rResult) || Objects.isNull(lResult)) {
            return null;
        }
        double leftValue = ((Number) lResult).doubleValue();
        double rightValue = ((Number) rResult).doubleValue();
        String opString = (String) op.eval(env);
        switch (opString) {
            case "+":
                return leftValue + rightValue;
            case "-":
                return leftValue - rightValue;
            case "*":
                return leftValue * rightValue;
            case "/":
                if (rightValue == 0) {
                    throw new ScriptException(op.getToken() + ":divided by zero");
                }
                return leftValue / rightValue;
            case "%":
                return leftValue % rightValue;
            case "**":
                return Math.pow(leftValue, rightValue);
            default:
                break;
        }
        return 0;
    }
}
