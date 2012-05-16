package com.lightdev.app.samples.regedit;

/**
 * HierarchicalItem.java
 * 
 * <p>Classes wishing to e treated as part of a hierarchical data structure need to 
 * implement this interface</p>
 * 
 * @author Ulrich Hilger
 * @author Light Development
 * @author <a href="http://www.lightdev.com">http://www.lightdev.com</a>
 * @author <a href="mailto:info@lightdev.com">info@lightdev.com</a>
 * @author Redistribution and use are subject to terms and conditions
 *  of the Light Development Basic License agreement as explained at
 *   http://www.lightdev.com/page/95.htm
 *
 * @version 1, 01.08.2005
 */
public interface HierarchicalItem {

  /**
   * set the data of this item
   * @param data  the item's data 
   */
  public abstract void setData(Object data);

  /**
   * get the data of this item
   * @return  the data of this item
   */
  public abstract Object getData();

  /**
   * set the id of this item
   * @param id  the id of this item
   */
  public abstract void setId(Object id);

  /**
   * get the id of this item
   * @return  the id of this item
   */
  public abstract Object getId();

  /**
   * get the id of the parent of this item
   * @return  the id of the parent of this item
   */
  public abstract Object getParentId();

  /**
   * set the id of the parent of this item
   * @param parentId  the parent id of this item
   */
  public abstract void setParentId(Object parentId);

  /**
   * determine if this item is a root item, i.e. has no parent items
   * @return  true if this item is a root item, false otherwise
   */
  public abstract boolean isRoot();

}