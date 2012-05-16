package com.lightdev.app.samples.regedit;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.image.BufferedImage;

import javax.swing.JTree;
import javax.swing.tree.TreePath;

/**
 * <p>A drop target for class <code>JTree</code> that implements autoscrolling, automatic 
 * tree node expansion and a custom drag image during the drag part of a drag and drop 
 * operation.</p> 
 * 
 * @author Ulrich Hilger
 * @author Light Development
 * @author <a href="http://www.lightdev.com">http://www.lightdev.com</a>
 * @author <a href="mailto:info@lightdev.com">info@lightdev.com</a>
 * @author published under the terms and conditions of the
 *      GNU General Public License,
 *      for details see file  in the distribution
 *      package of this software
 *
 * @version 1, 03.08.2005
 */
public class TreeDropTarget extends DropTarget {

	/**
	 * constructor
	 * @param the transfer handler that provides the drag image for the currently dragged node
	 */
  public TreeDropTarget(NodeMoveTransferHandler h) {
    super();
    this.handler = h;
  }

  /* -------------- DropTargetListener start ----------------- */

  /**
   * use method dragOver to constantly update the drag mark and darg image as 
   * well as to support automatic scrolling durng a drag operation
   */
  public void dragOver(DropTargetDragEvent dtde) {
    JTree tree = (JTree) dtde.getDropTargetContext().getComponent();
    Point loc = dtde.getLocation();
    updateDragMark(tree, loc);
    paintImage(tree, loc);
    autoscroll(tree, loc);
    super.dragOver(dtde);
  }

  /**
   * clear the drawings on exit
   */
  public void dragExit(DropTargetDragEvent dtde) {
    clearImage((JTree) dtde.getDropTargetContext().getComponent());
    super.dragExit(dtde);
  }

  /**
   * clear the drawings on drop
   */
  public void drop(DropTargetDropEvent dtde) {
    clearImage((JTree) dtde.getDropTargetContext().getComponent());
    super.drop(dtde);
  }

  /* ----------------- DropTartgetListener end ------------------ */

  /* ----------------- drag image painting start ------------------ */

  /**
   * paint the dragged node
   */
  private final void paintImage(JTree tree, Point pt) {
  	BufferedImage image = handler.getDragImage(tree);
  	if(image != null) {
  		tree.paintImmediately(rect2D.getBounds());
  		rect2D.setRect((int) pt.getX()-15,(int) pt.getY()-15,image.getWidth(),image.getHeight());
  		tree.getGraphics().drawImage(image,(int) pt.getX()-15,(int) pt.getY()-15,tree);
  	}
  }
  
  /**
   * clear drawings
   */
  private final void clearImage(JTree tree) {
    tree.paintImmediately(rect2D.getBounds());
  }

  /* ----------------- drag image painting end ------------------ */

  /* ----------------- autoscroll implementation start ------------------ */

  private Insets getAutoscrollInsets() {
    return autoscrollInsets;
  }

  /**
   * scroll visible tree parts when user drags outside an 'inner part' of 
   * the visible region
   */
  private void autoscroll(JTree tree, Point cursorLocation) {
    Insets insets = getAutoscrollInsets();
    Rectangle outer = tree.getVisibleRect();
    Rectangle inner = new Rectangle(
        outer.x+insets.left,
        outer.y+insets.top,
        outer.width-(insets.left+insets.right),
        outer.height-(insets.top+insets.bottom));
    if (!inner.contains(cursorLocation))  {
      Rectangle scrollRect = new Rectangle(
          cursorLocation.x-insets.left,
          cursorLocation.y-insets.top,
          insets.left+insets.right,
          insets.top+insets.bottom);
      tree.scrollRectToVisible(scrollRect);
    }
  }

  /* ----------------- autoscroll implementation end ------------------ */

  /* ----------------- insertion mark painting start ------------------ */

  /**
   * manage display of a drag mark either highlighting a node or drawing an
   * insertion mark
   */
  public void updateDragMark(JTree tree, Point location) {
  	mostRecentLocation = location;
    int row = tree.getRowForPath(tree.getClosestPathForLocation(location.x, location.y));
    TreePath path = tree.getPathForRow(row);
    if(path != null) {
      Rectangle rowBounds = tree.getPathBounds(path);
      /*
       * find out if we have to mark a tree node or if we 
       * have to draw an insertion marker
       */ 
      int rby = rowBounds.y;
      int topBottomDist = insertAreaHeight / 2;
      // x = top, y = bottom of insert area
      Point topBottom = new Point(rby - topBottomDist, rby + topBottomDist);
      if(topBottom.x <= location.y && topBottom.y >= location.y) {
        // we are inside an insertArea
        paintInsertMarker(tree, location);
      }
      else {
        // we are inside a node
        markNode(tree, location);
      }
    }
  }
  
  /**
   * get the most recent mouse location, i.e. the drop location when called upon drop
   * @return the mouse location recorded most recently during a drag operation
   */
  public Point getMostRecentDragLocation() {
  	return mostRecentLocation;
  }
  
  /**
   * mark the node that is closest to the current mouse location 
   */
  private void markNode(JTree tree, Point location) {
    TreePath path = tree.getClosestPathForLocation(location.x, location.y);
    if(path != null) {
      if(lastRowBounds != null) {
        Graphics g = tree.getGraphics();
        g.setColor(Color.white);
        g.drawLine(lastRowBounds.x, lastRowBounds.y, 
        		lastRowBounds.x + lastRowBounds.width, lastRowBounds.y);
      }
      tree.setSelectionPath(path);
      tree.expandPath(path);
    }
  }

  /**
   * paint an insert marker between the nodes closest to the current mouse location
   */
  private void paintInsertMarker(JTree tree, Point location) {
    Graphics g = tree.getGraphics();
    tree.clearSelection();
    int row = tree.getRowForPath(tree.getClosestPathForLocation(location.x, location.y));
    TreePath path = tree.getPathForRow(row);
    if(path != null) {
      Rectangle rowBounds = tree.getPathBounds(path);
      if(lastRowBounds != null) {
        g.setColor(Color.white);
        g.drawLine(lastRowBounds.x, lastRowBounds.y, 
        		lastRowBounds.x + lastRowBounds.width, lastRowBounds.y);
      }
      if(rowBounds != null) {
        g.setColor(Color.black);
        g.drawLine(rowBounds.x, rowBounds.y, rowBounds.x + rowBounds.width, rowBounds.y);
      }
      lastRowBounds = rowBounds;
    }
  }

  /* ----------------- insertion mark painting end ------------------ */

  /* ----------------- class fields ------------------ */

  /** bounding rectangle of the last row a dragOver was recorded for */
  private Rectangle lastRowBounds;

  /** height of the gap between any two node rows to treat as an area for inserts */
  private int insertAreaHeight = 8;
  
  /** insets for autoscroll */
  private Insets autoscrollInsets = new Insets(20, 20, 20, 20);
  
  /** rectangle to clear (where the last image was drawn) */
  private Rectangle rect2D = new Rectangle();
  
  /** the transfer handler that provides the image for the currently dragged node */
  private NodeMoveTransferHandler handler;
  
  private Point mostRecentLocation;

}
