package com.w_angler.calculator.backend.ast.internal;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.swing.tree.DefaultMutableTreeNode;

import com.w_angler.calculator.backend.Context;
import com.w_angler.calculator.backend.Environment;
import com.w_angler.calculator.backend.ast.AST;
import com.w_angler.calculator.backend.ast.leaf.Identifier;
import com.w_angler.calculator.backend.stack_base.Opcode;
import static com.w_angler.calculator.backend.stack_base.Opcode.*;

/**
 * Assignment
 * @author w-angler
 *
 */
public class Assign extends AST {
	private Identifier target;
	private AST value;

	public void setTarget(AST target) {
		this.target =(Identifier)target;
	}

	public void setValue(AST value) {
		this.value = value;
	}
	@Override
	public Object eval(Environment env) {
//		String key=target.name();
//		if(env.contains(key)){
//			System.out.println(target+":variable already exists!");
//			return null;
//		}
		Object result=value.eval(env);
		return env.add(target.name(),Objects.isNull(result)?Double.NaN:(double)result);
	}

	@Override
	public DefaultMutableTreeNode buildJTree() {
		DefaultMutableTreeNode node=new DefaultMutableTreeNode("=");
		node.add(target.buildJTree());
		node.add(value.buildJTree());
		return node;
	}

	@Override
	public List<Opcode> generate(Context context) {
		List<Opcode> codes=new ArrayList<>(value.generate(context));
        context.addVariable(target.name());
        codes.add(store(target.name()));
        codes.add(pop());
        return codes;
	}
}
