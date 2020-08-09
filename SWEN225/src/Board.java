
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;

public class Board
{
  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Board Attributes
  private static PersonCard mPerson;
  private static WeaponCard mWeapon;
  private static RoomCard mRoom;
  private static boolean isRunning; 

  //Board Associations
  private static List<Card> cards;
  private static List<Card> distributionCards;
  private List<Room> rooms;
  private static List<Player> players;
  private static Location[][] locations = new Location[25][24];

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Board()
  {
    cards = new ArrayList<Card>();
    distributionCards = new ArrayList<Card>();
    rooms = new ArrayList<Room>();
    players = new ArrayList<Player>();
    generateCards();
    chooseMurder();
  }

  private void chooseMurder() {
	mPerson = (PersonCard) cards.get(randomGeneration(0,5));
	 mWeapon = (WeaponCard) cards.get(randomGeneration(6,11));
	 mRoom = (RoomCard) cards.get(randomGeneration(12,17));
	distributionCards.remove(mPerson); distributionCards.remove(mWeapon); distributionCards.remove(mRoom);
	
}

private void generateCards() {
	  List<Card> cardList = Arrays.asList(new PersonCard("PEACOCK"), new PersonCard("PLUM"),new PersonCard("MUSTARD"), new PersonCard("WHITE"), new PersonCard("GREEN"), new PersonCard("SCARLETT"), new WeaponCard("GUN"), new WeaponCard("KNIFE"), 
				new WeaponCard("PIPE"), new WeaponCard("ROPE"), new WeaponCard("CANDLESTICK"), new RoomCard("SPANNER"), new RoomCard("DINING"), new RoomCard("KITCHEN"),new RoomCard("BALLROOM"), new RoomCard("CONSERVATORY"),
					new RoomCard("BILLIARD"), new RoomCard("LIBRARY"), new RoomCard("STUDY"), new RoomCard("HALL"), new RoomCard("LOUNGE"));
cards.addAll(cardList);
distributionCards.addAll(cardList);
	
}


//------------------------
  // INTERFACE
  //------------------------


  private void movePlayerToLocation(Player p, Location l){
	  p.getCurrentLocation().setPlayerOn(null);
	  p.setCurrentLocation(l);
	  l.setPlayerOn(p);
  }
  
  private void movePlayerToRoom(Player p, Room r) {
	  for(Location l : r.getLocations()) {
		  if(l.getPlayerOn() == null) {
			  movePlayerToLocation(p, l);
			  break;
		  }
	  }
  }
  
 

  public static int randomGeneration(int low, int high){
			Random r = new Random();
			int result = r.nextInt(high-low) + low;
			return result;
		}
  


  //Card Stuff
  public Card getCard(int index)
  {
    Card aCard = cards.get(index);
    return aCard;
  }

  public List<Card> getCards()
  {
    List<Card> newCards = Collections.unmodifiableList(cards);
    return newCards;
  }

  public int numberOfCards()
  {
    int number = cards.size();
    return number;
  }

  public boolean hasCards()
  {
    boolean has = cards.size() > 0;
    return has;
  }

  public int indexOfCard(Card aCard)
  {
    int index = cards.indexOf(aCard);
    return index;
  }
  /* Code from template association_GetMany */
  public Room getRoom(int index)
  {
    Room aRoom = rooms.get(index);
    return aRoom;
  }

  // Room stuff
  public List<Room> getRooms()
  {
    List<Room> newRooms = Collections.unmodifiableList(rooms);
    return newRooms;
  }

  public int numberOfRooms()
  {
    int number = rooms.size();
    return number;
  }

  public boolean hasRooms()
  {
    boolean has = rooms.size() > 0;
    return has;
  }

  public int indexOfRoom(Room aRoom)
  {
    int index = rooms.indexOf(aRoom);
    return index;
  }
  /* Code from template association_GetMany */
  public Player getPlayer(int index)
  {
    Player aPlayer = players.get(index);
    return aPlayer;
  }

  // Player Stuff
  public List<Player> getPlayers()
  {
    List<Player> newPlayers = Collections.unmodifiableList(players);
    return newPlayers;
  }

  public int numberOfPlayers()
  {
    int number = players.size();
    return number;
  }

  public boolean hasPlayers()
  {
    boolean has = players.size() > 0;
    return has;
  }

  public int indexOfPlayer(Player aPlayer)
  {
    int index = players.indexOf(aPlayer);
    return index;
  }

  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfCards()
  {
    return 0;
  }
  /* Code from template association_AddUnidirectionalMany */
  public boolean addCard(Card aCard)
  {
    boolean wasAdded = false;
    if (cards.contains(aCard)) { return false; }
    cards.add(aCard);
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeCard(Card aCard)
  {
    boolean wasRemoved = false;
    if (cards.contains(aCard))
    {
      cards.remove(aCard);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addCardAt(Card aCard, int index)
  {  
    boolean wasAdded = false;
    if(addCard(aCard))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfCards()) { index = numberOfCards() - 1; }
      cards.remove(aCard);
      cards.add(index, aCard);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveCardAt(Card aCard, int index)
  {
    boolean wasAdded = false;
    if(cards.contains(aCard))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfCards()) { index = numberOfCards() - 1; }
      cards.remove(aCard);
      cards.add(index, aCard);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addCardAt(aCard, index);
    }
    return wasAdded;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfRooms()
  {
    return 0;
  }
  /* Code from template association_AddUnidirectionalMany */
  public boolean addRoom(Room aRoom)
  {
    boolean wasAdded = false;
    if (rooms.contains(aRoom)) { return false; }
    rooms.add(aRoom);
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeRoom(Room aRoom)
  {
    boolean wasRemoved = false;
    if (rooms.contains(aRoom))
    {
      rooms.remove(aRoom);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addRoomAt(Room aRoom, int index)
  {  
    boolean wasAdded = false;
    if(addRoom(aRoom))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfRooms()) { index = numberOfRooms() - 1; }
      rooms.remove(aRoom);
      rooms.add(index, aRoom);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveRoomAt(Room aRoom, int index)
  {
    boolean wasAdded = false;
    if(rooms.contains(aRoom))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfRooms()) { index = numberOfRooms() - 1; }
      rooms.remove(aRoom);
      rooms.add(index, aRoom);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addRoomAt(aRoom, index);
    }
    return wasAdded;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfPlayers()
  {
    return 0;
  }
  /* Code from template association_AddUnidirectionalMany */
  public boolean addPlayer(Player aPlayer)
  {
    boolean wasAdded = false;
    if (players.contains(aPlayer)) { return false; }
    players.add(aPlayer);
    wasAdded = true;
    return wasAdded;
  }

  public boolean removePlayer(Player aPlayer)
  {
    boolean wasRemoved = false;
    if (players.contains(aPlayer))
    {
      players.remove(aPlayer);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addPlayerAt(Player aPlayer, int index)
  {  
    boolean wasAdded = false;
    if(addPlayer(aPlayer))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfPlayers()) { index = numberOfPlayers() - 1; }
      players.remove(aPlayer);
      players.add(index, aPlayer);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMovePlayerAt(Player aPlayer, int index)
  {
    boolean wasAdded = false;
    if(players.contains(aPlayer))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfPlayers()) { index = numberOfPlayers() - 1; }
      players.remove(aPlayer);
      players.add(index, aPlayer);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addPlayerAt(aPlayer, index);
    }
    return wasAdded;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfLocations()
  {
    return 0;
  }


  public void delete()
  {
    cards.clear();
    rooms.clear();
    players.clear();
  }
  
  
  																			//Actual Board Stuff


   public static Card getCard(String cardName){
	return null;
    
  }
   public static int rollDice(){
	int dice1 = (int)(Math.random()*6) + 1;
	int dice2 = (int)(Math.random()*6) + 1;
	   
	   return (dice1 + dice2);
    
  }


 

  public static void makeSuggestion(Accugestion suggestion) {

		for(Player p : players){
		
			if( p.handContains(suggestion.getPerson().getName()) || p.handContains(suggestion.getRoom().getName()) || p.handContains(suggestion.getWeapon().getName()) ) {
				Card c = p.checkHand(suggestion.getPerson(), suggestion.getRoom(), suggestion.getWeapon());
				System.out.println(c + " has been refuted");
			}
			else{System.out.println("Your suggestion has not been refuted by any other player");}
		}
	}
  
  public static void makeAccusation(Accugestion accusation) {
	  
	  if(accusation.getPerson() == mPerson && accusation.getRoom() == mRoom && accusation.getWeapon() == mWeapon) {System.out.println("yay you won"); isRunning = false;}
	  else {System.out.println("Your guess was incorrect, you are now out of the game"); accusation.getOwner().setIsPlaying(false);}
	
	}

private void loadMapFromCSV(){
  //Populate blank array of locations
  for(int i=0;i<locations.length;i++) {
    for(int j=0; j<locations[i].length;j++){
      locations[i][j] = new Location(i,j,false,false,false,false,null,null);
    }
  }

  String wallFile = "./src/walls.csv";
  String roomFile = "./src/rooms.csv";
  String line = "";
  String csvDelimiter = ",";

  String[][] rows = new String[25][];

  try{
    BufferedReader br = new BufferedReader(new FileReader(wallFile));
    int index = 0;
    while((line = br.readLine()) != null){
      String[] row = line.split(csvDelimiter);
      rows[index] = row;
      index++;
    }
    br.close();
  }catch(Exception e){
    System.out.println(e);
  }

  for(int i=0;i<25;i++){
    for(int j=0;j<24;j++){
      System.out.println(i+","+j);
      String current = rows[i][j];
      if(current.equals("")){
        continue;
      }
      switch(current.charAt(0)){
        case 'U':
          locations[i][j].setWallUp(true);
          break;
        case 'D':
          locations[i][j].setWallDown(true);
          break;
        case 'B':
          locations[i][j].setWallUp(true);
          locations[i][j].setWallDown(true);
          break;
        case 'N':
          break;
      }

      switch(current.charAt(1)){
        case 'L':
          locations[i][j].setWallLeft(true);
          break;
        case 'R':
          locations[i][j].setWallRight(true);
          break;
        case 'B':
          locations[i][j].setWallLeft(true);
          locations[i][j].setWallRight(true);
          break;
        case 'N':
          break;
      }
    }
  }

  //Load Room Data
//  rows = new String[25][];
//  line = "";
//  try{
//    BufferedReader br = new BufferedReader(new FileReader(roomFile));
//    int index = 0;
//    while((line = br.readLine()) != null){
//      String[] row = line.split(csvDelimiter);
//      rows[index] = row;
//      index++;
//    }
//  }catch(Exception e){
////    System.out.println(e);
//  }
//
//  for(int i=0;i<25;i++){
//    for(int j=0;j<24;j++) {
//      String current = rows[i][j];
//      if(current.equals("")){
//        continue;
//      }else{
//        switch (Integer.parseInt(current)){
//          case 1:
//            //placeholder room
//            locations[i][j].setRoomIn(new Room(new Location(0,0,false,false,false,false,null,null),new Location(0,0,false,false,false,false,null,null),new Location(0,0,false,false,false,false,null,null),new Location(0,0,false,false,false,false,null,null)));
//            break;
//          default:
//            //placeholder room
//            locations[i][j].setRoomIn(new Room(new Location(0,0,false,false,false,false,null,null),new Location(0,0,false,false,false,false,null,null),new Location(0,0,false,false,false,false,null,null),new Location(0,0,false,false,false,false,null,null)));
//            break;
//        }
//      }
//    }
//  }

}

private void displayMap(){

  for(int i=0; i<25; i++) {
    for(int j=0; j<24; j++) {

      if(i>0 && j>0 && locations[i][j].getRoomIn()!=null && locations[i-1][j-1].getRoomIn()!=null){
        System.out.println(" ");
      }else {
        System.out.print("+");
      }

      if (locations[i][j].getWallUp()) {
        System.out.print("----");
      }else if (i > 0 && locations[i - 1][j].getRoomIn() != null) {
        System.out.print("   ");
      } else {
        System.out.print("    ");
      }
    }
    System.out.println("+");

    for (int j=0; j<24; j++) {
      if(locations[i][j].getWallLeft()){
        System.out.print("| ");
      }else {
        System.out.print("  ");
      }
      if(locations[i][j].getPlayerOn()!=null) {
        String name = locations[i][j].getPlayerOn().getPlayerName().getName();
        switch (name){
          case "White":
            System.out.print("W");
            break;

          case "Green":
            System.out.print("G");
            break;

          case "Mustard":
            System.out.print("M");
            break;

          case "Peacock":
            System.out.print("Pe");
            break;

          case "Plum":
            System.out.print("Pl");
            break;

          case "Scarlett":
            System.out.print("S");
            break;

        }
      }else {
        System.out.print("  ");
      }
      if(j<23){
        System.out.print(" ");
      }
    }
    System.out.println(" |");

  }
  for (int j=0; j<24; j++) {
    System.out.print("+");
    if (locations[24][j].getWallDown()) {
      System.out.print("----");
    }else if (locations[24][j].getRoomIn() != null) {
      System.out.print("   ");
    } else {
      System.out.print("    ");
    }
  }
  System.out.println("+");

}

  private void playGame() {
	while(isRunning) {
		for(Player player : players) {
			if(player.getIsPlaying());
			player.setSteps(Board.rollDice());
			
		}
		
	}
}

  public static void main(String[] args) {
    Board myBoard = new Board();
    myBoard.generateCards();

    myBoard.loadMapFromCSV();
    myBoard.displayMap();

    isRunning = true;
    myBoard.playGame();
  }
}