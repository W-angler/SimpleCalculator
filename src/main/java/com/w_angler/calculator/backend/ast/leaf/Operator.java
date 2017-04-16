package com.w_angler.calculator.backend.ast.leaf;

import java.util.List;

import javax.swing.tree.DefaultMutableTreeNode;

import com.w_angler.calculator.backend.Context;
import com.w_angler.calculator.backend.Environment;
import com.w_angler.calculator.backend.ast.AST;
import com.w_angler.calculator.backend.stack_base.Opcode;
import com.w_angler.calculator.frontend.Token;

/**
 * Operator
 * @author w-angler
 *
 */
public class Operator extends AST {
    private Token op;
	public Operator(Token op) {
		this.op=op;
	}
	public Token getToken(){
		return op;
	}
	
	@Override
	public Object eval(Environment env) {
		return op.getValue();
	}

	@Override
	public DefaultMutableTreeNode buildJTree() {
		return new DefaultMutableTreeNode(op.getValue());
	}

	@Override
	public List<Opcode> generate(Context context){
		//do nothing
		return null;
	}

}
