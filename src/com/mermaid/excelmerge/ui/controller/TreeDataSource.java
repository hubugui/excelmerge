package com.mermaid.excelmerge.ui.controller;

import java.util.Enumeration;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;

import com.lightdev.app.samples.regedit.Region;
import com.mermaid.excelmerge.ui.view.CorpTree;

public class TreeDataSource {
	private CorpTree corpTree;

	public TreeDataSource(CorpTree corpTree) {
		this.corpTree = corpTree;
	}
	
	public boolean save() {
		boolean rc = true;

		if (corpTree != null) {
			StringBuffer sb = new StringBuffer();

			DefaultMutableTreeNode root = (DefaultMutableTreeNode) corpTree.getModel().getRoot();
			Enumeration e = root.breadthFirstEnumeration();
			while (e.hasMoreElements()) {
				DefaultMutableTreeNode node = (DefaultMutableTreeNode) e.nextElement();
				saveTreeNode(node, sb);				
			}
/*
			TreeModel tm = corpTree.getModel();
			DefaultMutableTreeNode node = (DefaultMutableTreeNode) tm.getRoot();
			
			while (node != null) {
				saveTreeNode(node, sb);				
			}
			*/
		}
		
		return rc;
	}

	private boolean saveTreeNode(DefaultMutableTreeNode node, StringBuffer sb) {
		boolean rc = true;

		Region region = (Region) node.getUserObject();

		System.out.println(region.getName());
		
		return rc;
	}

	public boolean load() {
		boolean rc = true;
		
		return rc;
	}	
}
