package com.w_angler.calculator.backend.ast.leaf;

import java.util.ArrayList;
import java.util.List;

import javax.swing.tree.DefaultMutableTreeNode;

import com.w_angler.calculator.backend.Context;
import com.w_angler.calculator.backend.Environment;
import com.w_angler.calculator.backend.ast.AST;
import com.w_angler.calculator.backend.stack_base.Opcode;
import com.w_angler.calculator.frontend.Token;
import static com.w_angler.calculator.backend.stack_base.Opcode.*;

/**
 * identifier
 * @author w-angler
 *
 */
public class Identifier extends AST {
	private Token token;
	
	public Identifier(Token token) {
		this.token=token;
	}
	public Token getToken(){
		return token;
	}
	public String name() {
		return token.getValue();
	}
	@Override
	public Object eval(Environment env) {
		if(env.contains(token.getValue())){
			return env.get(token.getValue());
		}
		System.out.println(token+":variable does not exist!");
		return null;
	}

	@Override
	public DefaultMutableTreeNode buildJTree() {
		return new DefaultMutableTreeNode(token.getValue());
	}
	@Override
	public List<Opcode> generate(Context context) {
		List<Opcode> codes=new ArrayList<>();
		if (!context.exists(token.getValue())) {
            System.err.println((token + ":variable does not exist!"));
            codes.add(push(String.valueOf(Double.NaN)));
        }
		else{
	        codes.add(load(token.getValue()));
		}
        return codes;
	}

}
