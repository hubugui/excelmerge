package com.mermaid.excelmerge.ui.controller;

import javax.swing.tree.TreeNode;

public class TreeNodeAccess {
	public static void depthFirst(TreeNode root, TreeNodeAccessCB cb, int depth, Object userData) {
		TreeNode node = root;

		if (node != null) {
			Object newUserData = cb.access(node, depth, userData);

			int childCount = node.getChildCount();
			for (int i = 0; i < childCount; i++) {
				TreeNode child = (TreeNode) node.getChildAt(i);

				depthFirst(child, cb, depth + 1, newUserData);
			}
		}
	}
}

interface TreeNodeAccessCB {
	public Object access(TreeNode node, int depth, Object userData);
}