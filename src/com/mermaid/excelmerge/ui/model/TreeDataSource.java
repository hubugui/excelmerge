package com.mermaid.excelmerge.ui.model;

import java.io.File;

import javax.swing.tree.TreeModel;
import javax.swing.tree.TreeNode;

import com.mermaid.excelmerge.ui.view.CorpTree;

public class TreeDataSource {
	public final static String storage_dir = "private/";
	private final static String storage_path = storage_dir + "excelmerge.xml";
	private CorpTree corpTree;

	public TreeDataSource(CorpTree corpTree) {
		this.corpTree = corpTree;

		File file = new File(storage_dir);
		if (file.exists() == false)
			file.mkdir();
	}

	public boolean save() {
		boolean rc = true;

		if (corpTree != null) {
			try {
				TreeWriter writer = new TreeWriter(storage_path, (TreeNode) corpTree.getModel().getRoot());
				writer.save();
			} catch(Exception ex) {
				ex.printStackTrace();
				rc = false;
			}
		}

		return rc;
	}

	public boolean load() {
		boolean rc = true;

		if (corpTree != null) {
			try {
				TreeReader reader = new TreeReader(storage_path, (TreeNode) corpTree.getModel().getRoot());
				reader.load();
			} catch(Exception ex) {
				ex.printStackTrace();
				rc = false;
			}
		}

		return rc;
	}
}