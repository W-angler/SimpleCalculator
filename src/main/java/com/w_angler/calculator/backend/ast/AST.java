package com.w_angler.calculator.backend.ast;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.tree.DefaultMutableTreeNode;

import com.w_angler.calculator.backend.Context;
import com.w_angler.calculator.backend.Environment;
import com.w_angler.calculator.backend.stack_base.Opcode;

/**
 * Abstract Syntax Tree
 * @author w-angler
 * 
 */
public abstract class AST{
    /**
     * evaluate this abstract syntax tree in specified environment
     * @param env environment
     * @return
     */
    public abstract Object eval(Environment env);
    /**
     * generate opcodes
     * @param codes
     * @param context
     */
    public abstract List<Opcode> generate(Context context);
    /**
     * build a JTree node
     * @return
     */
    public abstract DefaultMutableTreeNode buildJTree();
    /**
     * show this abstract syntax tree graphically
     */
    public void show(String title){
		GraphicTree tree=graphicTree();
		JFrame frame=new JFrame(title);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Dimension screen=Toolkit.getDefaultToolkit().getScreenSize();
		int width=(int) screen.getWidth();
		int height=(int)screen.getHeight();
		frame.setBounds(width/3, height/12, width/3, height*5/6);
		JScrollPane showTree=new JScrollPane();
		showTree.setViewportView(tree);
		frame.add(showTree,BorderLayout.CENTER);
		frame.setVisible(true);
    }
    public void show(){
    	show("");
    }
    /**
     * 
     * @return
     */
    public GraphicTree graphicTree(){
    	return new GraphicTree(buildJTree());
    }
}
