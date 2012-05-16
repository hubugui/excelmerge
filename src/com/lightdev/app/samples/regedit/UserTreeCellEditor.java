package com.lightdev.app.samples.regedit;

import java.awt.Component;

import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellEditor;
import javax.swing.tree.DefaultTreeCellRenderer;

/**
 * UserTreeCellEditor.java
 * 
 * <p>This class overrides DefaultTreeCellEditor to allow for custom user object handling 
 * of a region tree of application DayMate. A region tree has user objects of class 
 * HierarchicalItem. To edit the name of one HierarchicalItem inside the tree requires 
 * to override the methods that return the default cell editor and the editor value.</p>
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
 * @version 1, 31.07.2005
 */
public class UserTreeCellEditor extends DefaultTreeCellEditor {

  /**
   * constructor
   * @param tree  the tree this editor is used for
   * @param renderer  the renderer to use
   */
  public UserTreeCellEditor(JTree tree, DefaultTreeCellRenderer renderer) {
    super(tree, renderer);
  }

  /**
   * overridden to return the HierarchicalItem as the user object after editing
   */
  public Object getCellEditorValue() {
    Object returnValue = null;
    Object value = super.getCellEditorValue();
    if(item == null) {
      returnValue = value;
    }
    else {
      item.setData(value);
      returnValue = item;
    }
    return returnValue;
  }

  /**
   * overridden to save the HierarchicalItem which is stored in the user object of a 
   * tree during editing
   */
  public Component getTreeCellEditorComponent(JTree tree, Object value, boolean isSelected, boolean expanded, boolean leaf, int row) {
    if(value instanceof DefaultMutableTreeNode) {
      DefaultMutableTreeNode node = (DefaultMutableTreeNode) value;
      Object userObject = node.getUserObject();
      if(userObject instanceof HierarchicalItem) {
        item = (HierarchicalItem) node.getUserObject();
      }
    }
    return super.getTreeCellEditorComponent(tree, value, isSelected, expanded, leaf, row);
  }
  
  /** reference to user object */
  private HierarchicalItem item;

}
