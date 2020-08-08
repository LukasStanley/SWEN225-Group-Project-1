

import java.util.*;

// line 62 "model.ump"
// line 125 "model.ump"
public class Player
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Player Attributes
  private PersonCard playerName;
  private Room currentRoom;
  private Location currentLocation;
  private boolean isPlaying;

  //Player Associations
  private List<Card> hand;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Player(PersonCard aPlayerName, Room aCurrentRoom, Location aCurrentLocation, boolean aIsPlaying)
  {
    playerName = aPlayerName;
    currentRoom = aCurrentRoom;
    currentLocation = aCurrentLocation;
    isPlaying = aIsPlaying;
    hand = new ArrayList<Card>();
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setPlayerName(PersonCard aPlayerName)
  {
    boolean wasSet = false;
    playerName = aPlayerName;
    wasSet = true;
    return wasSet;
  }

  public boolean setCurrentRoom(Room aCurrentRoom)
  {
    boolean wasSet = false;
    currentRoom = aCurrentRoom;
    wasSet = true;
    return wasSet;
  }

  public boolean setCurrentLocation(Location aCurrentLocation)
  {
    boolean wasSet = false;
    currentLocation = aCurrentLocation;
    wasSet = true;
    return wasSet;
  }

  public boolean setIsPlaying(boolean aIsPlaying)
  {
    boolean wasSet = false;
    isPlaying = aIsPlaying;
    wasSet = true;
    return wasSet;
  }

  public PersonCard getPlayerName()
  {
    return playerName;
  }

  public Room getCurrentRoom()
  {
    return currentRoom;
  }

  public Location getCurrentLocation()
  {
    return currentLocation;
  }

  public boolean getIsPlaying()
  {
    return isPlaying;
  }
  /* Code from template attribute_IsBoolean */
  public boolean isIsPlaying()
  {
    return isPlaying;
  }
  /* Code from template association_GetMany */
  public Card getHand(int index)
  {
    Card aHand = hand.get(index);
    return aHand;
  }

  public List<Card> getHand()
  {
    List<Card> newHand = Collections.unmodifiableList(hand);
    return newHand;
  }

  public int numberOfHand()
  {
    int number = hand.size();
    return number;
  }

  public boolean hasHand()
  {
    boolean has = hand.size() > 0;
    return has;
  }

  public int indexOfHand(Card aHand)
  {
    int index = hand.indexOf(aHand);
    return index;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfHand()
  {
    return 0;
  }
  /* Code from template association_AddUnidirectionalMany */
  public boolean addHand(Card aHand)
  {
    boolean wasAdded = false;
    if (hand.contains(aHand)) { return false; }
    hand.add(aHand);
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeHand(Card aHand)
  {
    boolean wasRemoved = false;
    if (hand.contains(aHand))
    {
      hand.remove(aHand);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addHandAt(Card aHand, int index)
  {  
    boolean wasAdded = false;
    if(addHand(aHand))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfHand()) { index = numberOfHand() - 1; }
      hand.remove(aHand);
      hand.add(index, aHand);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveHandAt(Card aHand, int index)
  {
    boolean wasAdded = false;
    if(hand.contains(aHand))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfHand()) { index = numberOfHand() - 1; }
      hand.remove(aHand);
      hand.add(index, aHand);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addHandAt(aHand, index);
    }
    return wasAdded;
  }

  public void delete()
  {
    hand.clear();
  }

  // line 69 "model.ump"
   public void makeSuggestion(String person, String weapon, String room){
    Card weaponCard =  Board.getCard(weapon);
    Card personCard =  Board.getCard(person);
    Card roomCard =  Board.getCard(room);
    //Verify they exist and are of correct type
    Accugestion suggestion = new Accugestion(weaponCard, personCard, roomCard, this);
    Board.makeSuggestion(suggestion);
  }


  public String toString()
  {
    return super.toString() + "["+
            "isPlaying" + ":" + getIsPlaying()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "playerName" + "=" + (getPlayerName() != null ? !getPlayerName().equals(this)  ? getPlayerName().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "currentRoom" + "=" + (getCurrentRoom() != null ? !getCurrentRoom().equals(this)  ? getCurrentRoom().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "currentLocation" + "=" + (getCurrentLocation() != null ? !getCurrentLocation().equals(this)  ? getCurrentLocation().toString().replaceAll("  ","    ") : "this" : "null");
  }
}