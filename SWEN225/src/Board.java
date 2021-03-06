
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
   String[] weaponNames = {"GUN", "KNIFE", "PIPE", "ROPE", "CANDLESTICK", "SPANNER"};
   String[] commands = {"ACCUSE", "SUGGEST", "MOVE", "CARDS", "MAP", "END"};
   int[][] startingPoints = {{7,24}, {9,0}, {14,0}, {23,6}, {23,19}, {0,17}};
   int currentPlayer = 0;
   boolean hasRolled = false;
   boolean currentTurnActive = false;

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
	//System.out.println("DEBUG: MURDER SOLUTION: " + mPerson.getName() + " " + mWeapon.getName() + " " + mRoom.getName());
	
  }

  private void StateChange() {
	  myGameDisplay.ChangeOccured();

  }
  
  public void endTurn(){
	if(hasRolled) {
		currentTurnActive = false; 
	}
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
  
  public void stepCurrentPlayer(Integer direction){
      Player p = players[currentPlayer];
      //Raw list of inputs, needs to be cut down/checked for size
      ArrayList<Integer> movementArray = new ArrayList<>(Arrays.asList(direction));
      //Let the player simulate how far they can reach
      Location playerNewLoc = p.movePlayer(movementArray);
      //Move them this distance
      if(playerNewLoc != null) {
          movePlayerToLocation(p, playerNewLoc);
          myGameDisplay.redraw();
      }
	}
  
  private void generatePlayers() {
	  List<String> untakenCharacters = new ArrayList<String>(Arrays.asList(characterNames));
	  //Make player characters
  	  for(int i = 0; i < playersPlaying; i++) {
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
  	  
  	  //Make NPC characters
  	  for(int i = 0; i < untakenCharacters.size(); i++) {
  		  players[i+playersPlaying] = new Player( (PersonCard) getCard(untakenCharacters.get(i)), locations[startingPoints[i+playersPlaying][1]][startingPoints[i+playersPlaying][0]], true, locations, untakenCharacters.get(i), this);
  		  locations[startingPoints[i+playersPlaying][1]][startingPoints[i+playersPlaying][0]].setPlayerOn(players[i+playersPlaying]);
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

 

    
	
public boolean Accuse() {
	Player p = players[currentPlayer];
	if(hasRolled == false) {return false;}
		List<String> parameters = myInput.getAccusation();
		Card personCard = getCard(parameters.get(0));
		Card weaponCard = getCard(parameters.get(1));
		Card roomCard = getCard(parameters.get(2));
		Accugestion a = new Accugestion(weaponCard, personCard, roomCard, p);
		makeAccusation(a);
		return true;	
	}

	public boolean Suggest() {
		Player p = players[currentPlayer];
		if(p.getCurrentRoom() == null || hasRolled == false) {return false;}
		List<String> parameters = myInput.getSuggestion();
		Card personCard = getCard(parameters.get(0));
		Card weaponCard = getCard(parameters.get(1));
		Card roomCard = getCard(parameters.get(2));
		Accugestion a = new Accugestion(weaponCard, personCard, roomCard, p);
		makeSuggestion(a);
		return true;
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
	   if(!hasRolled) {
	int dice1 = (int)(Math.random()*6) + 1;
	int dice2 = (int)(Math.random()*6) + 1;
	   
	   players[currentPlayer].setSteps((dice1 + dice2));
	   hasRolled = true;
    myGameDisplay.updateDie(dice1, dice2);
    StateChange();}
	   else {JOptionPane.showMessageDialog(null, "You can only roll the dice once per turn!");}
  }



 


  public void makeSuggestion(Accugestion suggestion) {
	  Player accused = players[0];
	  Room crimeScene = rooms[0];
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
				JOptionPane.showMessageDialog(null, "Your suggestion has been refuted, " + p.getPlayerId() + " has the card " +c.getName() + ".");
				endTurn();
				return;
			}
			
		}
		JOptionPane.showMessageDialog(null, "Your suggestion has not been refuted by any other player");
		endTurn();
		return;
	}
  
  public void makeAccusation(Accugestion accusation) {
	  Player accused = players[0];
	  Room crimeScene = rooms[0];
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
		  myGameDisplay.gameWon(accusation.getOwner());
		  System.exit(100);
		  isRunning = false;
		  }
	  else {JOptionPane.showMessageDialog(null, "Your guess was incorrect, you have been removed from play"); accusation.getOwner().setIsPlaying(false); StateChange();}
		endTurn();
		return;
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
	currentTurnActive = true;
	while(isRunning) {
		for(int i = 0; i<playersPlaying; i++) {
			Player player = players[i];
			player.setSteps(0);
            myGameDisplay.updateDie(0, 0);
			currentPlayer = i;
			this.currentTurnActive = true;
			this.hasRolled = false;
			boolean playingCurrentTurn = true;
			boolean hasDiceRolled = false;
			if(player.getIsPlaying()) {
				myGameDisplay.redraw();
				while(playingCurrentTurn == true) {
					//Make sure the player has rolled first
					if(this.hasRolled == true) {
						if(this.currentTurnActive == false) {
							playingCurrentTurn = false;
						}
						else {
							//Needed to receive latest events from myGameDisplay
							myGameDisplay.getFont();
						}
					}
					else {
						//Needed to receive latest events from myGameDisplay
						myGameDisplay.getFont();
					}
				}
			}
		}
	}
}
  
  private void startGame() {
//		JOptionPane numPlayersOptionPane = new JOptionPane();
		playersPlaying = myGameDisplay.displayPlayerPick();
	    while(playersPlaying > 6 || playersPlaying <2) {
	    	playersPlaying = myGameDisplay.displayPlayerPick();
	    }
	    generatePlayers();

	    isRunning = true;
	    distributeCards();
	    myGameDisplay.redraw();
	    playGame();
  }

  public static void main(String[] args) {
    Board myBoard = new Board();
    myBoard.generateCards();
    myBoard.generateRooms();
    myBoard.chooseMurder();
    myBoard.loadMapFromCSV();
    myBoard.randomizeWeapons();
    myBoard.startGame();

  }
}
