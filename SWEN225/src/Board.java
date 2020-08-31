
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;
import java.io.IOException;
import java.io.InputStreamReader;
import javax.swing.*;

public class Board
{
  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Board Attributes

  private  PersonCard mPerson;
  private  WeaponCard mWeapon;
  private  RoomCard mRoom;
  private  boolean isRunning; 

  //Board Associations
  private  List<Card> cards;
  private  List<Card> distributionCards;
   Location[][] locations = new Location[25][24];
   Player[] players = new Player[6];
  private  int playersPlaying;
   Room[] rooms;
   String[] characterNames =  {"SCARLETT", "WHITE", "GREEN", "PEACOCK", "PLUM", "MUSTARD"};
   String[] roomNames = {"KITCHEN", "BALLROOM", "CONSERVATORY", "BILLIARD", "LIBRARY", "STUDY", "HALL", "LOUNGE", "DINING"};
   String[] commands = {"ACCUSE", "SUGGEST", "MOVE", "CARDS", "MAP", "END"};
   int[][] startingPoints = {{7,24}, {9,0}, {14,0}, {23,6}, {23,19}, {0,17}};
   int currentPlayer = 0;
   boolean hasRolled;

  Input myInput;
  GameDisplay myGameDisplay;


  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Board()
  {
    cards = new ArrayList<Card>();
    distributionCards = new ArrayList<Card>();
    rooms = new Room[roomNames.length];

//    Associates the model and the Input with each other
      myInput = new Input(this);

//    Associates the model and the display with each other
      myGameDisplay = new GameDisplay(this);

//    setup JFrame display
      myGameDisplay.setTitle("Cluedo");
      myGameDisplay.setSize(myGameDisplay.windowLength, myGameDisplay.windowHeight);
      myGameDisplay.setLayout(null);//no layout manager
      myGameDisplay.setVisible(true);
  }

  private void distributeCards() {
	  Collections.shuffle(distributionCards);
	
	  for(int i = 0; i < distributionCards.size(); i++) {
		  for(int j = 0; j<playersPlaying; j++) {
			  Player p = players[j];
			  if( i < 18) {p.addHand(distributionCards.get(i)); i++;}
			  else {break;}
		  }
		  
	  }
		  
	
  }

  private void chooseMurder() {
	mPerson = (PersonCard) cards.get(randomGeneration(0,5));
	 mWeapon = (WeaponCard) cards.get(randomGeneration(6,11));
	 mRoom = (RoomCard) cards.get(randomGeneration(12,17));
	distributionCards.remove(mPerson); distributionCards.remove(mWeapon); distributionCards.remove(mRoom);
	
  }

  private  void StateChange() {
	  myGameDisplay.ChangeOccured();

  }
  
  private void generateCards() {
		  List<Card> cardList = Arrays.asList(
                  new PersonCard("SCARLETT"),
                  new PersonCard("WHITE"),
                  new PersonCard("GREEN"),
                  new PersonCard("PEACOCK"),
                  new PersonCard("PLUM"),
                  new PersonCard("MUSTARD"),

                  new WeaponCard("GUN"),
                  new WeaponCard("KNIFE"),
                  new WeaponCard("PIPE"),
                  new WeaponCard("ROPE"),
                  new WeaponCard("CANDLESTICK"),
                  new WeaponCard("SPANNER"),

                  new RoomCard("DINING"),
                  new RoomCard("KITCHEN"),
                  new RoomCard("BALLROOM"),
                  new RoomCard("CONSERVATORY"),
                  new RoomCard("BILLIARD"),
                  new RoomCard("LIBRARY"),
                  new RoomCard("STUDY"),
                  new RoomCard("HALL"),
                  new RoomCard("LOUNGE"));
	cards.addAll(cardList);
	distributionCards.addAll(cardList); 

		
  }
  
  private void generatePlayers() {
  	  for(int i = 0; i < playersPlaying; i++) {
  		  List<String> untakenCharacters = new ArrayList(Arrays.asList(characterNames));
  		  //Make sure the player picks a unique and existing name.
  		  boolean isUnique = false;
  		  String id = null;
  		  while(isUnique != true) {
  			//Get the player name from a text field input
  			id = myInput.askID(i);
  			  if(id != null && id != "") {
  				  boolean foundMismatch = false;
  				  for(Player p : players) {
  					  if(p != null) {
  						  if(p.getPlayerId().equals(id)) {
  							  foundMismatch = true;
  						  }
  					  }
  					  
  				  }
  				  if(foundMismatch == false) {
  					  isUnique = true;
  				  }
  			  }
  		  }
  		  
  		  //Get the player choice from a radio button popup
  		  String player = myInput.askPlayer(untakenCharacters.toArray(new String[0]));
  		  //Remove the choice from available options
  		  for(int j = 0; j < untakenCharacters.size(); j++) {
  			  if(untakenCharacters.get(j) == player) {
  				  untakenCharacters.remove(j);
  			  }
  		  }
  		  players[i] = new Player((PersonCard) getCard(player), locations[startingPoints[i][1]][startingPoints[i][0]], true, locations, id, this);
  		  locations[startingPoints[i][1]][startingPoints[i][0]].setPlayerOn(players[i]);
	  }
  	  //GameDisplayer.players(players);
  }
  
  private void generateRooms() {
	  for(int i = 0; i<roomNames.length; i++) {
		  rooms[i] = new Room(roomNames[i]);
	  }
  }
  private void randomizeWeapons() {
    int index = 0;
    List<Integer> possibleRoom = Arrays.asList(0, 1, 2, 3 ,4, 5, 6, 7, 8);
    Collections.shuffle(possibleRoom);
    for(Card w : cards) {
        if(w instanceof WeaponCard) {
            moveWeaponToRoom((WeaponCard) w, rooms[possibleRoom.get(index)]);
            index++;
        }
  }
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
  
  private void moveWeaponToLocation(WeaponCard w, Location l){

      if(w.getLocation()!=null){
		  w.getLocation().setWeaponOn(null);
	  }
      w.setLocation(l);
      l.setWeaponOn(w);
  }

  private void moveWeaponToRoom(WeaponCard w, Room r) {
      for(Location l : r.getLocations()) {
          if(l.getWeaponOn() == null) {
              moveWeaponToLocation(w, l);
              break;
            }
        }
    }
  

 
  public int randomGeneration(int low, int high){

			Random r = new Random();
			int result = r.nextInt(high-low) + low;
			return result;
		}

 


private boolean executeTurn(Player p) {
	//Method to send current player information to View
	//Await input
	
	return false;
	
}
    
	
public boolean Accuse() {
	Player p = players[currentPlayer];
		List<String> parameters = myInput.getAccusation();
		if(parameters.size() < 4){
			System.out.println("Too few parameters! You need a WEAPON, PERSON and LOCATION");
			return false;
		}
		Card personCard = getCard(parameters.get(1));
		Card weaponCard = getCard(parameters.get(2));
		Card roomCard = getCard(parameters.get(3))	;
		if(personCard instanceof PersonCard) {
			if(weaponCard instanceof WeaponCard) {
				if(roomCard instanceof RoomCard) {
					Accugestion a = new Accugestion(weaponCard, personCard, roomCard, p);
					makeAccusation(a);
				}
				else {
					JOptionPane.showMessageDialog(null, "You need a valid room");
					return false;
				}
			}
			else {
				JOptionPane.showMessageDialog(null, "You need a valid weapon");
				return false;
			}
		}
		else {
			JOptionPane.showMessageDialog(null, "You need a valid person");
			return false;
		}
		return true;
		
	}

	
public boolean Suggest() {
	Player p = players[currentPlayer];
	List<String> parameters = myInput.getSuggestion();
		if(parameters.size() < 3){
			JOptionPane.showMessageDialog(null, "You must select both a weapon and a person");
			return false;
		}
		if(p.getCurrentRoom() != null) {
			System.out.println(parameters.toString());
			Card personCard = getCard(parameters.get(1));
			Card weaponCard = getCard(parameters.get(2));
			Card roomCard = getCard(p.getCurrentRoom().getName());
			if(personCard instanceof PersonCard) {
				if(weaponCard instanceof WeaponCard) {
					Accugestion a = new Accugestion(weaponCard, personCard, roomCard, p);
					makeAccusation(a);
				}
				else {
					JOptionPane.showMessageDialog(null, "You need a valid person");
					return false;
				}
			}
			else {
				JOptionPane.showMessageDialog(null, "You need a valid weapon");
				return false;
			}
		}
		else {JOptionPane.showMessageDialog(null, "You must be in a room"); return false;}
		return true;
		
	}
	

private ArrayList<Integer> movementInputs(String movementString) {
	ArrayList<Integer> movements = new ArrayList<Integer>();
	for (int i=0; i < movementString.length(); i++) {
		
	    if(movementString.charAt(i) == 'd' || movementString.charAt(i) == 'D') {
	    	movements.add(2);
	    }
	    else if(movementString.charAt(i) == 'u' || movementString.charAt(i) == 'U') {
	    	movements.add(0);
	    }
	    else if(movementString.charAt(i) == 'l' || movementString.charAt(i) == 'L') {
	    	movements.add(3);
	    }
	    else if(movementString.charAt(i) == 'r' || movementString.charAt(i) == 'R') {
	    	movements.add(1);
	    }
	}
	
	System.out.println(movements);
	return movements;
}

private Player nextPlayer() {
	if(currentPlayer < playersPlaying) { int i = currentPlayer + 1; return players[i]; }
	else if( currentPlayer == playersPlaying){return players[0];}
	return null;
	}
  																			//Actual Board Stuff



 public Card getCard(String cardName){
	for(Card c : cards) {
		if(c.getName().equalsIgnoreCase(cardName)) {return c;}
	}
	System.out.println("Couldn't find the right card for " + cardName);
	return null;
    
  }
   public void rollDice(){

	int dice1 = (int)(Math.random()*6) + 1;
	int dice2 = (int)(Math.random()*6) + 1;
	   
	   players[currentPlayer].setSteps((dice1 + dice2));
	   hasRolled = true;
    myGameDisplay.updateDie(dice1, dice2);
    StateChange();
  }



 


  public void makeSuggestion(Accugestion suggestion) {
	  Player accused = players[0];
	  Room crimeScene = rooms[0];;
	  for(Player p : players){	
		  if(p.getPlayerName() == suggestion.getPerson()) {
			  accused = p;
			  break;
		  }
	  }
	  for(Room r : rooms){	
		  if(r.getName() == suggestion.getRoom().getName()) {
			  crimeScene = r;
			  break;
		  }
	  }
	  movePlayerToRoom(accused, crimeScene);
	  moveWeaponToRoom(suggestion.getWeapon(), crimeScene);
		for(Player p : players){
		
			if( p.handContains(suggestion.getPerson().getName()) || p.handContains(suggestion.getRoom().getName()) || p.handContains(suggestion.getWeapon().getName()) ) {
				Card c = p.checkHand(suggestion.getPerson(), suggestion.getRoom(), suggestion.getWeapon());
				JOptionPane.showMessageDialog(null, c + "has been refuted");
			}
			
		}
		JOptionPane.showMessageDialog(null, "Your suggestion has not been refuted by any other player");
	
	}
  
  public void makeAccusation(Accugestion accusation) {
	  Player accused = players[0];
	  Room crimeScene = rooms[0];;
	  for(Player p : players){	
		  if(p.getPlayerName() == accusation.getPerson()) {
			  accused = p;
			  break;
		  }
	  }
	  for(Room r : rooms){	
		  if(r.getName() == accusation.getRoom().getName()) {
			  crimeScene = r;
			  break;
		  }
	  }
	  movePlayerToRoom(accused, crimeScene);
	  moveWeaponToRoom(accusation.getWeapon(), crimeScene);
	  if(accusation.getPerson() == mPerson && accusation.getRoom() == mRoom && accusation.getWeapon() == mWeapon) {
		  System.out.flush();
	      for(int i = 0; i<300; i++) {
	          System.out.println();
	      }	
		  System.out.println("Player " + players[currentPlayer].getPlayerName().getName() + " has won the game!");
		  System.out.println("The correct solution was " + mPerson.getName() + " in the " + mRoom.getName() + " with the " + mWeapon.getName());
		  System.exit(100);
		  isRunning = false;
		  }
	  else {JOptionPane.showMessageDialog(null, "Your guess was incorrect, you have been removed from play"); accusation.getOwner().setIsPlaying(false); StateChange();}
	
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
  rows = new String[25][];
  line = "";
  try{
    BufferedReader br = new BufferedReader(new FileReader(roomFile));
    int index = 0;
    while((line = br.readLine()) != null){
      String[] row = line.split(csvDelimiter);
      rows[index] = row;
      index++;
    }
  }catch(Exception e){
//    System.out.println(e);
  }

  for(int i=0;i<25;i++){
    for(int j=0;j<24;j++) {
      String current = "";
      try{current = rows[i][j];}catch (Exception e){System.out.println(i+","+j);}
      if(!current.equals("0")){

//      	int roomInt = Integer.parseInt(current);
//      	locations[i][j].setRoomIn(rooms[roomInt-1]);
//        rooms[roomInt-1].addLocation(locations[i][j]);

    	  if(current.equals("1")) {
              locations[i][j].setRoomIn(rooms[0]);
              rooms[0].addLocation(locations[i][j]);
    	  }
    	  else if(current.equals("2")) {
              locations[i][j].setRoomIn(rooms[1]);
			  rooms[1].addLocation(locations[i][j]);
    	  }
    	  else if(current.equals("3")) {
              locations[i][j].setRoomIn(rooms[2]);
			  rooms[2].addLocation(locations[i][j]);
    	  }
    	  else if(current.equals("4")) {
              locations[i][j].setRoomIn(rooms[3]);
			  rooms[3].addLocation(locations[i][j]);
    	  }
    	  else if(current.equals("5")) {
              locations[i][j].setRoomIn(rooms[4]);
			  rooms[4].addLocation(locations[i][j]);
    	  }
    	  else if(current.equals("6")) {
              locations[i][j].setRoomIn(rooms[5]);
			  rooms[5].addLocation(locations[i][j]);
    	  }
    	  else if(current.equals("7")) {
              locations[i][j].setRoomIn(rooms[6]);
			  rooms[6].addLocation(locations[i][j]);
    	  }
    	  else if(current.equals("8")) {
              locations[i][j].setRoomIn(rooms[7]);
			  rooms[7].addLocation(locations[i][j]);
    	  }
    	  else if(current.equals("9")) {
              locations[i][j].setRoomIn(rooms[8]);
			  rooms[8].addLocation(locations[i][j]);
    	  }

      }
    }
  }

}



  private void playGame() {
	boolean currentTurnActive = true;
	while(isRunning) {
		for(int i = 0; i<playersPlaying; i++) {
			Player player = players[i];
			currentPlayer = i;
			currentTurnActive = true;
			hasRolled = false;
			while(!hasRolled) {}
			while(currentTurnActive) {
				if(player.getIsPlaying()) {
					currentTurnActive = !executeTurn(player);
					
				}
			}
	
		}
		
	}
}
  
  private void startGame() {
//		JOptionPane numPlayersOptionPane = new JOptionPane();
		playersPlaying = myGameDisplay.displayPlayerPick();
	    while(playersPlaying > 6 || playersPlaying <2) {
	    	System.out.println("Pick the number of players:");
	    	playersPlaying = myGameDisplay.displayPlayerPick();
	    }
	    System.out.println(playersPlaying + " Players selected.");

	    generatePlayers();

	    isRunning = true;
	    distributeCards();
	    playGame();
  }

  public void main(String[] args) {
    Board myBoard = new Board();
    myBoard.generateCards();
    myBoard.generateRooms();
    myBoard.chooseMurder();
    myBoard.loadMapFromCSV();
    myBoard.randomizeWeapons();
    myBoard.startGame();

  }
}
