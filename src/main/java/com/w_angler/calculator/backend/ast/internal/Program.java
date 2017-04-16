package com.w_angler.calculator.backend.ast.internal;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.swing.tree.DefaultMutableTreeNode;

import com.w_angler.calculator.backend.Context;
import com.w_angler.calculator.backend.Environment;
import com.w_angler.calculator.backend.ast.AST;
import com.w_angler.calculator.backend.stack_base.Opcode;
import com.w_angler.calculator.backend.stack_base.OpcodeType;

/**
 * the whole program
 * @author w-angler
 *
 */
public class Program extends AST {
	List<AST> sentences;
	public Program(List<AST> sentences) {
		this.sentences=sentences;
	}

	@Override
	public Object eval(Environment env) {
		for(AST sentence:sentences){
			sentence.eval(env);
		}
		return null;
	}

	@Override
	public DefaultMutableTreeNode buildJTree() {
		DefaultMutableTreeNode node=new DefaultMutableTreeNode("program");
		for(AST sentence:sentences){
			node.add(sentence.buildJTree());
		}
		return node;
	}

	@Override
	public List<Opcode> generate(Context context) {
		List<Opcode> codes=new ArrayList<>();
		codes.add(Opcode.slot(""));
		for(AST sentence:sentences){
           codes.addAll(sentence.generate(context));
        }
		//A so-called backfill
		codes.get(0).arg = String.valueOf(context.getVariableNumber());
		codes.forEach(e -> {
			if (Objects.equals(e.type, OpcodeType.store) || Objects.equals(e.type, OpcodeType.load)) {
				e.arg = String.valueOf(context.slot(e.arg));
			}
		});
		return codes;
	}

}
