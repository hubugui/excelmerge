package com.lightdev.app.samples.regedit;

/**
 * <p>Region.java - A class that models a geographic region.</p>
 * 
 * <p>A special characteristic of a region is that it may be 
 * any geographic area such as a continent, a country, a state, a city or county, etc. 
 * Regions are modeled hierarchical in the way that each region links to one superior 
 * region, e.g. Paris links to France, France links to Europe, Europe links to World.</p>
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
 * @version 1, April 3, 2005
 */

public class Region implements HierarchicalItem {

  /**
   * constructor
   */
  public Region() {
  }

  /**
   * construct a region object with a given id and name
   * @param id int  the id of the region
   * @param rName String  the name of the region
   */
  public Region(int id, String rName) {
    this();
    setId(id);
    setName(rName);
  }
  
  /**
   * get a string representation of this object
   */
  public String toString() {
    return getName();
  }

  /**
   * determine whether or not a given object is equal to this object 
   */
  public boolean equals(Object obj) {
    boolean isEqual = false;
    if (obj instanceof Region) {
      Region compareTo = (Region) obj;
      Object thisId = getId();
      Object otherId = compareTo.getId();
      if (thisId != null && otherId != null && thisId.equals(otherId)) {
        isEqual = true;
      }
      else {
        String thisName = getName();
        String otherName = compareTo.getName();
        if (thisName != null && otherName != null && thisName.equalsIgnoreCase(otherName)) {
          isEqual = true;
        }
      }
    }
    return isEqual;
  }

  /**
   * set the unique id of this region
   * @param id int  the region id
   */
  public void setId(int id) {
    this.regionId = id;
  }

  /**
   * get the id of this region
   * @return int  the region id
   */
  public Object getId() {
    return new Integer(regionId);
  }

  /**
   * set the id of the region superior to this region
   * @param superiorId int  the superior region id
   */
  public void setSuperiorId(int superiorId) {
    this.superId = superiorId;
  }

  /**
   * get the id of the region superior to this region
   * @return int  the superior region id or -1 if not set
   */
  public int getSuperiorId() {
    return superId;
  }

  /**
   * set the name of this region
   * @param name String  the region name to set
   */
  public void setName(String name) {
    this.regionName = name;
  }

  /**
   * get the name of this region
   * @return String  the region name
   */
  public String getName() {
    return regionName;
  }
  
  /* ----------------------- HierarchicalItem implementation start ------------------ */

  public void setData(Object data) {
    setName(data.toString());
  }

  public Object getData() {
    return getName();
  }

  public void setId(Object id) {
    setId(((Integer) id).intValue());
  }

  public Object getParentId() {
    return new Integer(superId);
  }

  public void setParentId(Object parentId) {
    setSuperiorId(((Integer) parentId).intValue());
  }
  public boolean isRoot() {
    return (superId == -1);
  }
  
  /* ----------------------- HierarchicalItem implementation end ------------------ */
  
  /* ----------------------- class fields ------------------ */

  /** the region id */
  private int regionId;
  /** the region name */
  private String regionName;
  /** superior region id */
  private int superId = -1;

}
