package com.lightdev.app.samples.regedit;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

/**
 * Region Editor - an example application to demonstrate usage of enhancements to JTree 
 *
 * <p>Use this sample code as a copanion to article 'Extending JTree capabilities' available 
 * at <a href="http://articles.lightdev.com/tree/tree_article.pdf" target="_blank">
 * http://articles.lightdev.com/tree/tree_article.pdf</a>.</p>
 * 
 * @author Ulrich Hilger
 * @author Light Development
 * @author <a href="http://www.lightdev.com">http://www.lightdev.com</a>
 * @author <a href="mailto:info@lightdev.com">info@lightdev.com</a>
 * @author published under the terms and conditions of the
 *      GNU General Public License,
 *      for details see file gpl.txt in the distribution
 *      package of this software
 *
 * @version 1, 5.8.2005
 */
public class RegionEditor implements ActionListener {
	
  /**
   * constructor
   */
  public RegionEditor() {
		buildUi();
	}
	
  /**
   * application entry point
   * @param args  command line arguments
   */
	public static void main(String[] args) {
    try {
      UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
    }
    catch(Exception e) {
      e.printStackTrace();
    }
		new RegionEditor();
	}

  /**
   * construct a tree that has all the enhancements described in the article this 
   * sample accompanies
   * @return an enhanced tree
   */
	private JTree getTree() {
    regTree = new JTree(getSampleTreeRoot());
    regTree.setShowsRootHandles(true);
    regTree.setEditable(true);
    regTree.setCellEditor(
    		new UserTreeCellEditor(regTree, (DefaultTreeCellRenderer) regTree.getCellRenderer()));
    NodeMoveTransferHandler handler = new NodeMoveTransferHandler();
    regTree.setTransferHandler(handler);
    regTree.setDropTarget(new TreeDropTarget(handler));
    regTree.setDragEnabled(true);
    return regTree;
	}
	
  /**
   * build some tree nodes suitable for our example
   * @return  the root node to add to our tree
   */
	private TreeNode getSampleTreeRoot() {
    DefaultMutableTreeNode root = new DefaultMutableTreeNode(new Region(id++, "World"));
    addNodes(root, new String[] {"Europe", "North America"});
    addNodes(root.getChildAt(0), new String[] {"France", "Germany", "UK"});
    addNodes(root.getChildAt(1), new String[] {"Canada", "USA"});
    addNodes(root.getChildAt(0).getChildAt(2), new String[] {"London", "Peterborough"});
    addNodes(root.getChildAt(0).getChildAt(1), new String[] {"Berlin", "Frankfurt"});
    addNodes(root.getChildAt(0).getChildAt(0), new String[] {"Paris", "Marseille"});
    addNodes(root.getChildAt(1).getChildAt(0), new String[] {"Ottawa", "Vancouver"});
    addNodes(root.getChildAt(1).getChildAt(1), new String[] {"Washington", "New York"});
		return root;
	}
  
  /**
   * add child nodes with the given strings as names to a given parent node
   * @param parent  the parent node to add children to
   * @param children  the child names to add nodes for
   */
  private void addNodes(TreeNode parent, String[] children) {
    for(int i = 0; i < children.length; i++) {
      ((DefaultMutableTreeNode) parent).add(
          new DefaultMutableTreeNode(new Region(id++, children[i])));
    }
  }
	
  /**
   * build the user interface of this application
   */
	private void buildUi() {
		// create tree panel
    JPanel treePanel = new JPanel(new BorderLayout());    
    treePanel.setBorder(new EmptyBorder(3, 3, 3, 3));
    treePanel.add(new JScrollPane(getTree()), BorderLayout.CENTER);
    
    // create button panel
    JPanel editBtnPanel = new JPanel(new GridLayout(2, 1));
    editBtnPanel.add(getButton(BTN_TXT_ADD));
    editBtnPanel.add(getButton(BTN_TXT_REMOVE));
    
    // helper panel to keep button panel in the upper right area
    JPanel rightPanel = new JPanel(new BorderLayout());
    rightPanel.setBorder(new EmptyBorder(3, 0, 3, 3));
    rightPanel.add(editBtnPanel, BorderLayout.NORTH);
    rightPanel.setPreferredSize(new Dimension(80,80));

    // create a frame
		JFrame frame = new JFrame();
    frame.setLayout(new BorderLayout());
    
    // add components to frame
    Container contentPane = frame.getContentPane();
    contentPane.add(treePanel, BorderLayout.CENTER);
    contentPane.add(rightPanel, BorderLayout.EAST);
    
    // set frame properties as approriate
    frame.setTitle(APP_TITLE);
    frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    frame.setSize(300, 300);
    frame.setLocationRelativeTo(null);
    frame.setVisible(true);
	}
	
  /**
   * create a button
   * @param name  the name the button shall display
   * @return  the created button
   */
	private JButton getButton(String name) {
    JButton b = new JButton(name);
    b.addActionListener(this);
    return b;
	}
	
  /**
   * add a region to the currently selected tree node (if any)
   */
  private void addRegion() {
    TreePath selectedPath = regTree.getSelectionPath();
    if(selectedPath != null) {
      Object o = selectedPath.getLastPathComponent();
      if(o != null) {
        DefaultMutableTreeNode selectedNode = 
          (DefaultMutableTreeNode) regTree.getSelectionPath().getLastPathComponent();
        Region newItem = new Region(id, "new region " + id++);
        DefaultMutableTreeNode newChild = new DefaultMutableTreeNode(newItem);
        ((DefaultTreeModel) regTree.getModel()).insertNodeInto(
            newChild, selectedNode, selectedNode.getChildCount());
        TreePath newPath = selectedPath.pathByAddingChild(newChild);
        regTree.setSelectionPath(newPath);
        regTree.startEditingAtPath(newPath);
      }
    }    
  }

  /**
   * remove the currently selected tree node (if any)
   */
  private void removeRegion() {
    TreePath selectedPath = regTree.getSelectionPath();
    if(selectedPath != null) {
      Object o = selectedPath.getLastPathComponent();
      if(o != null) {
        DefaultMutableTreeNode selectedNode = 
          (DefaultMutableTreeNode) regTree.getSelectionPath().getLastPathComponent();
        ((DefaultTreeModel) regTree.getModel()).removeNodeFromParent(selectedNode);
      }
    }
  }
  
	/* ------------------------- ActionListener implementation start ------------------- */

  /**
   * route button clicks appropriately
   */
	public void actionPerformed(ActionEvent e) {
		Object src = e.getSource();
		if(src instanceof JButton) {
			JButton b = (JButton) src;
			if(b.getText().equalsIgnoreCase(BTN_TXT_ADD)) {
				addRegion();
			}
			else if(b.getText().equalsIgnoreCase(BTN_TXT_REMOVE)) {
				removeRegion();
			}
		}
	}
	
	/* ------------------------- ActionListener implementation end ------------------- */
  
  /* ------------------------- class fields start ------------------- */
  
  /** our tree */
  private JTree regTree;
  /** an field to help simulate region ids */
  private int id = 0;
  
  /* ------------------------- class fields end ------------------- */
	
	/* ------------------------- class constants start ------------------- */

  /** the title of the application */
  private static final String APP_TITLE = "RegionEditor";
  /** add button text */
	private static final String BTN_TXT_ADD = "add";
  /** remove button text */
	private static final String BTN_TXT_REMOVE = "remove";
	
	/* ------------------------- class constants end ------------------- */

}
