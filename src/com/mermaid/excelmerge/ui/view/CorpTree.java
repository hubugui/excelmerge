package com.mermaid.excelmerge.ui.view;

import java.util.Enumeration;

import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

import com.lightdev.app.samples.regedit.NodeMoveTransferHandler;
import com.lightdev.app.samples.regedit.Region;
import com.lightdev.app.samples.regedit.TreeDropTarget;
import com.lightdev.app.samples.regedit.UserTreeCellEditor;
import com.mermaid.excelmerge.ui.model.Corp;

public class CorpTree extends JTree {
	private int id;
	private final static String default_node_name = "新公司";

	public CorpTree() {
		super();

		this.setShowsRootHandles(true);
		this.setEditable(true);
		this.setCellEditor(new UserTreeCellEditor(this, (DefaultTreeCellRenderer) this.getCellRenderer()));
		NodeMoveTransferHandler handler = new NodeMoveTransferHandler();
		this.setTransferHandler(handler);
		this.setDropTarget(new TreeDropTarget(handler));
		this.setDragEnabled(true);
		this.setModel(new DefaultTreeModel(getSampleTreeRoot(), false));
	}

	public void expandTree(JTree tree) {
		DefaultMutableTreeNode root = (DefaultMutableTreeNode) tree.getModel().getRoot();

		Enumeration e = root.breadthFirstEnumeration();
		while (e.hasMoreElements()) {
			DefaultMutableTreeNode node = (DefaultMutableTreeNode) e.nextElement();			
			if (node.isLeaf())
				continue;			
			int row = tree.getRowForPath(new TreePath(node.getPath()));
			tree.expandRow(row);
		}

		if (this.getRowCount() == 1)
			this.setSelectionRow(0);
		else
			this.setSelectionRow(1);
	}

	/**
	 * build some tree nodes suitable for our example
	 * 
	 * @return the root node to add to our tree
	 */
	private TreeNode getSampleTreeRoot() {
		DefaultMutableTreeNode root = new DefaultMutableTreeNode(new Corp(id++, "根"));
		// addNodes(root, new String[] {default_node_name + " " + id});
		return root;
	}

	/**
	 * add child nodes with the given strings as names to a given parent node
	 * 
	 * @param parent
	 *            the parent node to add children to
	 * @param children
	 *            the child names to add nodes for
	 */
	private void addNodes(TreeNode parent, String[] children) {
		for (int i = 0; i < children.length; i++) {			
			Region parentRegion = (Region) ((DefaultMutableTreeNode) parent).getUserObject();

			Region region = new Corp(id++, children[i]);
			region.setParentId(parentRegion.getId());

			((DefaultMutableTreeNode) parent).add(new DefaultMutableTreeNode(region));
		}
	}

	/**
	 * add a region to the currently selected tree node (if any)
	 */
	public boolean addRegion() {
		boolean rc = false;

		TreePath selectedPath = this.getSelectionPath();
		if (selectedPath != null) {
			Object o = selectedPath.getLastPathComponent();
			if (o != null) {
				DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) this.getSelectionPath().getLastPathComponent();
				Region newItem = new Corp(id, default_node_name + " " + id++);				
				
				Region parentRegion = (Region) selectedNode.getUserObject();
				newItem.setParentId(parentRegion.getId());

				DefaultMutableTreeNode newChild = new DefaultMutableTreeNode(newItem);
				((DefaultTreeModel) this.getModel()).insertNodeInto(newChild, selectedNode, selectedNode.getChildCount());
				TreePath newPath = selectedPath.pathByAddingChild(newChild);
				this.setSelectionPath(newPath);
				this.startEditingAtPath(newPath);
				
				rc = true;
			}
		}

		return rc;
	}

	/**
	 * remove the currently selected tree node (if any)
	 */
	public boolean removeRegion() {
		boolean rc = false;

		TreePath selectedPath = this.getSelectionPath();
		if (selectedPath != null) {
			if (selectedPath.getParentPath() != null) {
				Object o = selectedPath.getLastPathComponent();
				if (o != null) {				
					DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) this.getSelectionPath().getLastPathComponent();
					((DefaultTreeModel) this.getModel()).removeNodeFromParent(selectedNode);
					
					rc = true;
				}
			}
		}

		return rc;
	}	
}