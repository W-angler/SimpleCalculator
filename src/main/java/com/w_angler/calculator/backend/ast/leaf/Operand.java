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
 * Operand
 * @author w-angler
 *
 */
public class Operand extends AST {
	private Token token;
	
	public Operand(Token token) {
		this.token=token;
	}
	@Override
	public Object eval(Environment env) {
		String value = token.getValue();
        try {
            if (value.startsWith("0x") || value.startsWith("0X") || (value.contains("_") && !value.contains("."))) {
                return Long.decode(value.replaceAll("_", ""));
            }
            return Double.parseDouble(value.replaceAll("_", ""));
        }
        catch (Throwable e) {
            System.out.println("Number Format Exception:" + this.token.getValue());
            return null;
        }
	}

	@Override
	public DefaultMutableTreeNode buildJTree() {
		return new DefaultMutableTreeNode(token.getValue());
	}
	
	@Override
	public List<Opcode> generate(Context context) {
		List<Opcode> codes=new ArrayList<>();
		codes.add(push(token.getValue()));
		return codes;
	}

}
