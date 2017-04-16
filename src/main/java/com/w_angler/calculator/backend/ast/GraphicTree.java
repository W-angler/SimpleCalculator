package com.w_angler.calculator.backend.ast;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JDialog;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.WindowConstants;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeNode;

/**
 * A graphic tree
 * @author w-angler
 *
 */
public class GraphicTree extends JTree {
	private static final long serialVersionUID = 1L;
	private JPopupMenu menu;
	private JMenuItem item;
	
	public GraphicTree(TreeNode root){
		super(root);
		menu=new JPopupMenu();
		item=new JMenuItem("Show");
		menu.add(item);
		this.addMouseListener(new MouseAdapter(){
			@Override
			public void mousePressed(MouseEvent e) {
				if (e.getButton() == MouseEvent.BUTTON3) {
					if (getLastSelectedPathComponent()!=null) {
						menu.show(GraphicTree.this, e.getX(),e.getY());
					}
				}
			}
		});
		item.addActionListener((e)->{
			DefaultMutableTreeNode node=(DefaultMutableTreeNode) getLastSelectedPathComponent();
			JTree tree=new JTree(node);
			JDialog dialog=new JDialog();
			Object userObject=node.getUserObject();
			dialog.setTitle(userObject==null?"":userObject.toString());
			dialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			Dimension screen=Toolkit.getDefaultToolkit().getScreenSize();
			int width=(int) screen.getWidth();
			int height=(int)screen.getHeight();
			dialog.setBounds(width*3/8, height/4, width/4, height/2);
			for(int i=0;i<tree.getRowCount();i++){
				tree.expandRow(i);
			}
			JScrollPane showTree=new JScrollPane();
			showTree.setViewportView(tree);
			dialog.add(showTree,BorderLayout.CENTER);
			dialog.setModal(true);
			dialog.setVisible(true);
		});
		for(int i=0;i<getRowCount();i++){
			expandRow(i);
		}
	}

}
