package com.w_angler.calculator.backend.ast.internal;

import java.util.ArrayList;
import java.util.List;

import javax.swing.tree.DefaultMutableTreeNode;

import com.w_angler.calculator.backend.Context;
import com.w_angler.calculator.backend.Environment;
import com.w_angler.calculator.backend.ast.AST;
import com.w_angler.calculator.backend.stack_base.Opcode;
import static com.w_angler.calculator.backend.stack_base.Opcode.*;

/**
 * write value to standard output stream
 * @author w-angler
 *
 */
public class Print extends AST {
	private AST value;
	public Print(AST value) {
		this.value=value;
	}
	@Override
	public Object eval(Environment env) {
		System.out.println(value.eval(env));
		return null;
	}

	@Override
	public DefaultMutableTreeNode buildJTree() {
		DefaultMutableTreeNode node=new DefaultMutableTreeNode("print");
		node.add(value.buildJTree());
		return node;
	}
	@Override
	public List<Opcode> generate(Context context) {
		 List<Opcode> codes=new ArrayList<>(value.generate(context));
	     codes.add(print());
	     return codes;
	}

}
