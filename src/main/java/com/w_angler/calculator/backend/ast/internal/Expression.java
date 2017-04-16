package com.w_angler.calculator.backend.ast.internal;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.swing.tree.DefaultMutableTreeNode;

import com.w_angler.calculator.backend.Context;
import com.w_angler.calculator.backend.Environment;
import com.w_angler.calculator.backend.ast.AST;
import com.w_angler.calculator.backend.ast.leaf.Operator;
import com.w_angler.calculator.backend.stack_base.Opcode;
import static com.w_angler.calculator.backend.stack_base.Opcode.*;

/**
 * arithmetic expression
 * @author w-angler
 *
 */
public class Expression extends AST {
	private Operator op;
	private AST left;
	private AST right;
	public void setOp(AST op) {
		this.op = (Operator)op;
	}
	public void setLeft(AST left) {
		this.left = left;
	}
	public void setRight(AST right) {
		this.right = right;
	}
	@Override
	public Object eval(Environment env) {
		Object lResult=left.eval(env);
		Object rResult=right.eval(env);
		if(Objects.isNull(rResult)||Objects.isNull(lResult)){
			return null;
		}
		double left=((Number)lResult).doubleValue();
		double right=((Number)rResult).doubleValue();
		String opString=(String)op.eval(env);
		switch(opString) {
		case "+":
			return left+right;
		case "-":
			return left-right;
		case "*":
			return left*right;
		case "/":
            if (right==0){
                System.out.println(op.getToken()+":divided by zero");
            }
			return left/right;
		case "%":
			return left%right;
		case "**":
			return Math.pow(left,right);
		}
		return 0;
	}
	
	@Override
	public DefaultMutableTreeNode buildJTree(){
		DefaultMutableTreeNode root=op.buildJTree();
		root.add(left.buildJTree());
		root.add(right.buildJTree());
		return root;
	}
	@Override
	public List<Opcode> generate(Context context) {
		List<Opcode> codes=new ArrayList<>();
		codes.addAll(left.generate(context));
		codes.addAll(right.generate(context));
        switch (op.getToken().getValue()) {
            case "%": {
                codes.add(mod());
                break;
            }
            case "*": {
                codes.add(mul());
                break;
            }
            case "+": {
                codes.add(add());
                break;
            }
            case "-": {
                codes.add(sub());
                break;
            }
            case "/": {
                codes.add(div());
                break;
            }
            case "**": {
                codes.add(pow());
                break;
            }
            default:
                break;
        }
        return codes;
	}
}
