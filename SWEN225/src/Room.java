/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.30.0.5105.3f8cbd5a3 modeling language!*/


import java.util.*;

// line 48 "model.ump"
// line 115 "model.ump"
public class Room
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Room Associations
  private List<Location> locations;
  
  private Location topExit;
  private Location bottomExit;
  private Location leftExit;
  private Location rightExit;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Room(Location top, Location bottom, Location left, Location right)
  {
    locations = new ArrayList<Location>();
    if(top != null) {
    	topExit = top;
    }
    if(bottom != null) {
    	bottomExit = bottom;
    }
    if(left != null) {
    	leftExit = left;
    }
    if(top != null) {
    	rightExit = right;
    }
  }

  //------------------------
  // INTERFACE
  //------------------------
  
  public Location getTop() {
	  return topExit;
  }
  
  public Location getLeft() {
	  return leftExit;
  }
  
  public Location getRight() {
	  return rightExit;
  }
  
  public Location getBottom() {
	  return bottomExit;
  }
  
  /* Code from template association_GetMany */
  public Location getLocation(int index)
  {
    Location aLocation = locations.get(index);
    return aLocation;
  }

  public List<Location> getLocations()
  {
    List<Location> newLocations = Collections.unmodifiableList(locations);
    return newLocations;
  }

  public int numberOfLocations()
  {
    int number = locations.size();
    return number;
  }

  public boolean hasLocations()
  {
    boolean has = locations.size() > 0;
    return has;
  }

  public int indexOfLocation(Location aLocation)
  {
    int index = locations.indexOf(aLocation);
    return index;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfLocations()
  {
    return 0;
  }
  /* Code from template association_AddUnidirectionalMany */
  public boolean addLocation(Location aLocation)
  {
    boolean wasAdded = false;
    if (locations.contains(aLocation)) { return false; }
    locations.add(aLocation);
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeLocation(Location aLocation)
  {
    boolean wasRemoved = false;
    if (locations.contains(aLocation))
    {
      locations.remove(aLocation);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addLocationAt(Location aLocation, int index)
  {  
    boolean wasAdded = false;
    if(addLocation(aLocation))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfLocations()) { index = numberOfLocations() - 1; }
      locations.remove(aLocation);
      locations.add(index, aLocation);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveLocationAt(Location aLocation, int index)
  {
    boolean wasAdded = false;
    if(locations.contains(aLocation))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfLocations()) { index = numberOfLocations() - 1; }
      locations.remove(aLocation);
      locations.add(index, aLocation);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addLocationAt(aLocation, index);
    }
    return wasAdded;
  }

  public void delete()
  {
    locations.clear();
  }

}