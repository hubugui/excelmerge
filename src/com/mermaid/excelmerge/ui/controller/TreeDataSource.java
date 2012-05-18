package com.mermaid.excelmerge.ui.controller;

import javax.swing.tree.TreeModel;
import javax.swing.tree.TreeNode;

import com.mermaid.excelmerge.ui.view.CorpTree;

public class TreeDataSource {
	private final static String storage_path = "db/excelmerge.xml";
	private CorpTree corpTree;

	public TreeDataSource(CorpTree corpTree) {
		this.corpTree = corpTree;
	}

	public boolean save() {
		boolean rc = true;

		if (corpTree != null) {
			TreeModel tm = corpTree.getModel();
			try {
				TreeWriter tw = new TreeWriter("", storage_path, (TreeNode) tm.getRoot());
				tw.save();
			} catch(Exception ex) {
				ex.printStackTrace();
				rc = false;
			}
		}

		return rc;
	}

	public boolean load() {
		boolean rc = true;

		return rc;
	}
}