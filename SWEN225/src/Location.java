/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.30.0.5105.3f8cbd5a3 modeling language!*/



// line 53 "model.ump"
// line 120 "model.ump"
public class Location
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Location Attributes
  private int x;
  private int y;
  private boolean wallLeft;
  private boolean wallRight;
  private boolean wallUp;
  private boolean wallDown;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Location(int aX, int aY, boolean aWallLeft, boolean aWallRight, boolean aWallUp, boolean aWallDown)
  {
    x = aX;
    y = aY;
    wallLeft = aWallLeft;
    wallRight = aWallRight;
    wallUp = aWallUp;
    wallDown = aWallDown;
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setX(int aX)
  {
    boolean wasSet = false;
    x = aX;
    wasSet = true;
    return wasSet;
  }

  public boolean setY(int aY)
  {
    boolean wasSet = false;
    y = aY;
    wasSet = true;
    return wasSet;
  }

  public boolean setWallLeft(boolean aWallLeft)
  {
    boolean wasSet = false;
    wallLeft = aWallLeft;
    wasSet = true;
    return wasSet;
  }

  public boolean setWallRight(boolean aWallRight)
  {
    boolean wasSet = false;
    wallRight = aWallRight;
    wasSet = true;
    return wasSet;
  }

  public boolean setWallUp(boolean aWallUp)
  {
    boolean wasSet = false;
    wallUp = aWallUp;
    wasSet = true;
    return wasSet;
  }

  public boolean setWallDown(boolean aWallDown)
  {
    boolean wasSet = false;
    wallDown = aWallDown;
    wasSet = true;
    return wasSet;
  }

  public int getX()
  {
    return x;
  }

  public int getY()
  {
    return y;
  }

  public boolean getWallLeft()
  {
    return wallLeft;
  }

  public boolean getWallRight()
  {
    return wallRight;
  }

  public boolean getWallUp()
  {
    return wallUp;
  }

  public boolean getWallDown()
  {
    return wallDown;
  }

  public void delete()
  {}


  public String toString()
  {
    return super.toString() + "["+
            "x" + ":" + getX()+ "," +
            "y" + ":" + getY()+ "," +
            "wallLeft" + ":" + getWallLeft()+ "," +
            "wallRight" + ":" + getWallRight()+ "," +
            "wallUp" + ":" + getWallUp()+ "," +
            "wallDown" + ":" + getWallDown()+ "]";
  }
}