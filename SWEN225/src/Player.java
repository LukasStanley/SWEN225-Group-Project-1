

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
  private Location[][] locationArray;
  private int stepsRemaining;

  //Player Associations
  private List<Card> hand;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Player(PersonCard aPlayerName, Room aCurrentRoom, Location aCurrentLocation, boolean aIsPlaying, Location[][] locations)
  {
    playerName = aPlayerName;
    currentRoom = aCurrentRoom;
    currentLocation = aCurrentLocation;
    isPlaying = aIsPlaying;
    hand = new ArrayList<Card>();
    locationArray = locations;
  }

  //------------------------
  // INTERFACE
  //------------------------
  
  public void setSteps(int stepCount) {
	  stepsRemaining = stepCount;
  }
  
  public int getSteps() {
	  return stepsRemaining;
  }

  //direction is 0 = up 1 = right 2 = down 3 = left
  public Location movePlayer(ArrayList<Integer> movements) {
	  Location currentLocationStep = currentLocation;
	  Location nextLocation = currentLocation;
	  boolean stillMovin = true;
	  for(Integer current : movements) {
		  
		 //The current location of the player as they are trying to perform their inputs. nextLocation will be initialized to the previous (valid) move.
		 currentLocationStep = nextLocation;
		  
		  //If not inside a room
		 if(currentLocationStep.getRoomIn() == null) {
			  //up
			 if(current == 0) {
				 if(currentLocationStep.getY()==0) {
					 stillMovin = false;
					 break;
				 }
				 nextLocation = locationArray[currentLocationStep.getY()-1][currentLocationStep.getX()];
				 if(nextLocation.getWallDown()){
					 stillMovin = false;
					 break;
				 }
			 }
			 //right
			 else if(current == 1) {
				 if(currentLocationStep.getX()==23) {
					 stillMovin = false;
					 break;
				 }
				 nextLocation = locationArray[currentLocationStep.getY()][currentLocationStep.getX()+1];
				 if(nextLocation.getWallLeft()){
					 stillMovin = false;
					 break;
				 }
			 }
			 //down
			 else if(current == 2) {
				 if(currentLocationStep.getY()==24) {
					 stillMovin = false;
					 break;
				 }
				 nextLocation = locationArray[currentLocationStep.getY()+1][currentLocationStep.getX()];
				 if(nextLocation.getWallUp()){
					 stillMovin = false;
					 break;
				 }
			 }
			 //left or invalid (defaults to left)
			 else{
				 if(currentLocationStep.getX()==0) {
					 stillMovin = false;
					 break;
				 }
				 nextLocation = locationArray[currentLocationStep.getY()][currentLocationStep.getX()-1];
				 if(nextLocation.getWallRight()){
					 stillMovin = false;
					 break;
				 }
			 }
			 
			 //Trying to land on a tile with a player
			 if(nextLocation.getPlayerOn() != null) {
				 stillMovin = false;
				 break;
			 }
		 }
		 //Inside a room
		 else {
			  //up
			 if(current == 0) {
				 if(currentLocationStep.getRoomIn().getTop() == null) {
					 stillMovin = false;
					 break;
				 }
				 else {
					 nextLocation = currentLocationStep.getRoomIn().getTop();
				 }
			 }
			 //right
			 else if(current == 1) {
				 if(currentLocationStep.getRoomIn().getRight() == null) {
					 stillMovin = false;
					 break;
				 }
				 else {
					 nextLocation = currentLocationStep.getRoomIn().getRight();
				 }
			 }
			 //down
			 else if(current == 2) {
				 if(currentLocationStep.getRoomIn().getBottom() == null) {
					 stillMovin = false;
					 break;
				 }
				 else {
					 nextLocation = currentLocationStep.getRoomIn().getBottom();
				 }
			 }
			 //left or invalid (defaults to left)
			 else{
				 if(currentLocationStep.getRoomIn().getLeft() == null) {
					 stillMovin = false;
					 break;
				 }
				 else {
					 nextLocation = currentLocationStep.getRoomIn().getLeft();
				 }
			 }
		 }
		 
		 if(stillMovin) {
			 try {
				 stepsRemaining--;
			 }
			 catch (Exception e) {
		            e.printStackTrace();
		     }
		 }
		 
		 
	  }
	  if(currentLocationStep == currentLocation) {
		  return null;
	  }
	  
	  else {
		  return currentLocationStep;
	  }
  }
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

  public boolean isIsPlaying()
  {
    return isPlaying;
  }

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
  
  public boolean handContains(String s) {
	  for(int i = 0; i < hand.size(); i++) {
		  if(hand.get(i).getName() == s) { return true;}
	  }
	  return false;
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
   
   public void makeAccusation(String person, String weapon, String room){
	    Card weaponCard =  Board.getCard(weapon);
	    Card personCard =  Board.getCard(person);
	    Card roomCard =  Board.getCard(room);
	    //Verify they exist and are of correct type
	    Accugestion accusation = new Accugestion(weaponCard, personCard, roomCard, this);
	    Board.makeAccusation(accusation);
	  }


  public String toString()
  {
    return super.toString() + "["+
            "isPlaying" + ":" + getIsPlaying()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "playerName" + "=" + (getPlayerName() != null ? !getPlayerName().equals(this)  ? getPlayerName().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "currentRoom" + "=" + (getCurrentRoom() != null ? !getCurrentRoom().equals(this)  ? getCurrentRoom().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "currentLocation" + "=" + (getCurrentLocation() != null ? !getCurrentLocation().equals(this)  ? getCurrentLocation().toString().replaceAll("  ","    ") : "this" : "null");
  }
  
  public Card checkHand(PersonCard person, RoomCard room, WeaponCard weapon) {
	List<Card> potential = new ArrayList<Card>();
	Scanner sc = new Scanner(System.in);
	if(hand.contains(person)) { potential.add(person);}
	if(hand.contains(weapon)) { potential.add(weapon);}
	if(hand.contains(room)) { potential.add(room);}
	if(potential.size() == 1) {return potential.get(0);}
	if(potential.size() == 2) {System.out.println("The suggestion involves two of your cards, pick which one to show the current player. Press 0 for " + potential.get(0) + " or press 1 for " + potential.get(1)); int choice = sc.nextInt(); return potential.get(choice);}
	if(potential.size() == 3) {
			System.out.println("The suggestion involves three of your cards, pick which one to show the current player. Press 0 for " + potential.get(0) + ", press 1 for " + potential.get(1) + " or press 2 for " + potential.get(2)); 
				int choice = sc.nextInt(); return potential.get(choice);}
	
	return weapon;
	
	
}
}