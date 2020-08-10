
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;
import java.io.IOException;
import java.io.InputStreamReader;

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
  private static Location[][] locations = new Location[25][24];
  private static Player[] players = new Player[6];
  private static int playersPlaying;
  static Room[] rooms;
  static String[] roomNames = {"KITCHEN", "BALLROOM", "CONSERVATORY", "BILLIARD", "LIBRARY", "STUDY", "HALL", "LOUNGE", "BALLROOM"};
  static String[] commands = {"ACCUSE", "SUGGEST", "MOVE", "CARDS", "MAP", "END"};
  static int[][] startingPoints = {{7,24}, {9,0}, {14,0}, {23,6}, {23,19}, {0,17}};
  static int currentPlayer = 0;


  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Board()
  {
    cards = new ArrayList<Card>();
    distributionCards = new ArrayList<Card>();
    rooms = new Room[roomNames.length];
 
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
  	  for(int i = 0; i<players.length; i++) {
  		  players[i] = new Player((PersonCard) cards.get(i), locations[startingPoints[i][1]][startingPoints[i][0]], true, locations);
  		  locations[startingPoints[i][1]][startingPoints[i][0]].setPlayerOn(players[i]);
	  }
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


  private static void movePlayerToLocation(Player p, Location l){
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

  private static void moveWeaponToLocation(WeaponCard w, Location l){
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
  
 

  public static int randomGeneration(int low, int high){
			Random r = new Random();
			int result = r.nextInt(high-low) + low;
			return result;
		}

	
   public static void displayInfo(Player playerTurn) {
	  //System.out.println("DEBUG: COORDS " + playerTurn.getCurrentLocation().getX() + " , " + playerTurn.getCurrentLocation().getY());
	  System.out.println("You are " + playerTurn.getPlayerName().getName());
	  System.out.println("The other players are:");
	  for(int i = 0; i < playersPlaying; i++) {
		  Player p = players[i];
		  if(p!= playerTurn) {
			  System.out.print("	"+p.getPlayerName().getName());
		  }
	  }
	  System.out.println();
	  System.out.println("You have "+ playerTurn.getSteps()+" moves remaining. You can move infinitely within a room.");
	  System.out.println("use MOVE followed by any number of u(up) d(down) l(left) r(right) to move. Move once at a time, or using a long combination. Non u/d/r/l are ignored.");
	  System.out.println("use END to finish your turn without doing anything else");
	  System.out.println("use SUGGEST followed by a name, weapon and location to suggest a certain sequence of events. Seperate these with a space, not a comma.");
	  System.out.println("use ACCUSE followed by a name, weapon and location to accuse a certain person. Seperate these with a space, not a comma. Fail = game over. Succeed = win!");
	  System.out.println("use MAP to redisplay the map");
	  System.out.println("use CARDS to display your hand");
  }
  
  public static void displayCards(Player playerTurn){
	System.out.flush();
	for(Card c :playerTurn.getHand()) {
		if(c instanceof PersonCard) {
			System.out.print("Person: ");
		}
		else if(c instanceof WeaponCard) {
			System.out.print("Weapon: ");
		}
		else{
			System.out.print("Room: ");
		}
			System.out.println(c.getName());
		
	}
}

private static boolean executeTurn(Player p) {
	String inputLine = takeStringInput();
	int turnType = findTurn(inputLine);
	//For storing the weapons etc of a accusation or suggestion.
	String[] parameters;
	
	//MOVE
	if(turnType == 2) {
        if(p.getSteps()==0) {
        	System.out.println("You are out of moves!");
            return false;
        }
        //Raw list of inputs, needs to be cut down/checked for size
        ArrayList<Integer> movementArray = movementInputs(inputLine.substring(commands[2].length()));
        //Let the player simulate how far they can reach
        Location playerNewLoc = p.movePlayer(movementArray);
        //Move them this distance
        if(playerNewLoc != null) {
            movePlayerToLocation(p, playerNewLoc); 	displayMap(); displayInfo(p);
        }
        return false;
        
    }
	//ACCUSE
	else if(turnType == 0) {
		parameters = inputLine.substring(commands[0].length()).split(" ");
		if(parameters.length < 4){
			System.out.println("Too few parameters! You need a WEAPON, PERSON and LOCATION");
			return false;
		}
		Card personCard = getCard(parameters[1]);
		Card weaponCard = getCard(parameters[2]);
		Card roomCard = getCard(parameters[3]);
		if(personCard instanceof PersonCard) {
			if(weaponCard instanceof WeaponCard) {
				if(roomCard instanceof RoomCard) {
					Accugestion a = new Accugestion(weaponCard, personCard, roomCard, p);
					makeAccusation(a);
				}
				else {
					System.out.println("The third item must be a valid room!");
					return false;
				}
			}
			else {
				System.out.println("The second item must be a valid weapon!");
				return false;
			}
		}
		else {
			System.out.println("The first item must be a valid person!");
			return false;
		}
		
	}
	
	
	//SUGGEST
	else if(turnType == 1) {
		parameters = inputLine.substring(commands[1].length()).split(" ");
		if(parameters.length < 3){
			System.out.println("Too few parameters! You need a WEAPON and PERSON");
			return false;
		}
		if(p.getCurrentRoom() != null) {
			System.out.println(parameters.toString());
			Card personCard = getCard(parameters[1]);
			Card weaponCard = getCard(parameters[2]);
			Card roomCard = getCard(p.getCurrentRoom().getName());
			if(personCard instanceof PersonCard) {
				if(weaponCard instanceof WeaponCard) {
					Accugestion a = new Accugestion(weaponCard, personCard, roomCard, p);
					makeAccusation(a);
				}
				else {
					System.out.println("The first item must be a valid person!");
					return false;
				}
			}
			else {
				System.out.println("The second item must be a valid weapon!");
				return false;
			}
		}
		else {System.out.println("You must be in a room to make a suggestion"); return false;}
		
	}
	//NO COMMAND FOUND
	else if(turnType == -1) {
		return false;
	}
	//Go to next turn
	else if(turnType == 6) {
		return true;
	}
	//Cards
	else if(turnType == 3) {
		displayCards(p);
		return false;
	}
	//Map
	else if(turnType == 3) {
		displayMap();
		displayInfo(p);
		return false;
	}
	return true;
}

private static int findTurn(String inputLine) {
	boolean commandFound = true;
	int commandIndex = -1;
	for(String command : commands) {
		commandIndex ++;
		commandFound = true;
		if(command.length() <= inputLine.length()) {
			for (int i=0; i < command.length(); i++) {	
				if(inputLine.charAt(i) != command.charAt(i)) {
					commandFound = false;
				}
			}
		}
		else {
			commandFound = false;
		}
		
		if(commandFound == true) {
			break;
		}
	}
	
	if(commandFound) {
		return commandIndex;
	}
	else {
		return -1;
	}	
}

private static ArrayList<Integer> movementInputs(String movementString) {
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

private static Player nextPlayer() {
	if(currentPlayer < playersPlaying) { int i = currentPlayer + 1; return players[i]; }
	else if( currentPlayer == playersPlaying){return players[0];}
	return null;
	}

private static String takeStringInput() {
	  InputStreamReader isr = new InputStreamReader(System.in);
	  BufferedReader br = new BufferedReader(isr);
	  String line = "woah";
	  try {
		line = br.readLine();
	  } catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	  }
	  return line;
}
  																			//Actual Board Stuff


   public static Card getCard(String cardName){
	for(Card c : cards) {
		if(c.getName().equalsIgnoreCase(cardName)) {return c;}
	}
	System.out.println("Couldn't find the right card for " + cardName);
	return null;
    
  }
   public static int rollDice(){
	int dice1 = (int)(Math.random()*6) + 1;
	int dice2 = (int)(Math.random()*6) + 1;
	   
	   return (dice1 + dice2);
    
  }
   
   public static void displayPassToPlayer(Player playerTurn) {
	      System.out.flush();
	      for(int i = 0; i<300; i++) {
	          System.out.println();
	      }
	      System.out.println("It is now the turn of " + playerTurn.getPlayerName().getName());
	      System.out.println("If you are "+  playerTurn.getPlayerName().getName() + ", please press the enter key to continue...");
	      String inputLine = takeStringInput();
	  }


 

  public static void makeSuggestion(Accugestion suggestion) {

		for(Player p : players){
		
			if( p.handContains(suggestion.getPerson().getName()) || p.handContains(suggestion.getRoom().getName()) || p.handContains(suggestion.getWeapon().getName()) ) {
				Card c = p.checkHand(suggestion.getPerson(), suggestion.getRoom(), suggestion.getWeapon());
				System.out.println(c + " has been refuted");
			}
			displayPassToPlayer(nextPlayer());
			
		}
		System.out.println("Your suggestion has not been refuted by any other player");
	}
  
  public static void makeAccusation(Accugestion accusation) {
	  
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

private static void displayMap(){
 System.out.flush();
  for(int i=0; i<25; i++) {
    for(int j=0; j<24; j++) {

      System.out.print("+");

      if (locations[i][j].getWallUp()) {
          System.out.print("----");
      }else{
        System.out.print("    ");
      }
    }
    System.out.println("+");

    for (int j=0; j<24; j++) {
      if(locations[i][j].getWallLeft()){
        System.out.print("|");
      }else {
        System.out.print(" ");
      }
      if(locations[i][j].getPlayerOn()!=null) {
        String name = locations[i][j].getPlayerOn().getPlayerName().getName();
        switch (name){
          case "WHITE":
            System.out.print(" W  ");
            break;

          case "GREEN":
            System.out.print(" G  ");
            break;

          case "MUSTARD":
            System.out.print(" M  ");
            break;

          case "PEACOCK":
            System.out.print(" Pe ");
            break;

          case "PLUM":
            System.out.print(" Pl ");
            break;

          case "SCARLETT":
            System.out.print(" S  ");
            break;

        }
      }else if(locations[i][j].getWeaponOn()!=null) {
          String name = locations[i][j].getWeaponOn().getName();
          switch (name){
              case "GUN":
                  System.out.print("gun ");
                  break;

              case "KNIFE":
                  System.out.print("knif");
                  break;

              case "PIPE":
                  System.out.print("pipe");
                  break;

              case "ROPE":
                  System.out.print("rope");
                  break;

              case "CANDLESTICK":
                  System.out.print("cand");
                  break;

              case "SPANNER":
                  System.out.print("span");
                  break;
          }
      }else{
          System.out.print("    ");
      }
    }
    System.out.println("|");

  }
  for (int j=0; j<24; j++) {
    System.out.print("+");
    if (locations[24][j].getWallDown()) {
      System.out.print("----");
    } else {
      System.out.print("    ");
    }
  }
  System.out.println("+");

}

  private void playGame() {
	boolean currentTurnActive = true;
	while(isRunning) {
		for(int i = 0; i<playersPlaying; i++) {
			Player player = players[i];
			currentPlayer = i;
			currentTurnActive = true;
			player.setSteps(Board.rollDice());
			displayMap();
			displayInfo(player);
			while(currentTurnActive) {
				if(player.getIsPlaying()) {
					currentTurnActive = !executeTurn(player);
					
				}
			}
			displayPassToPlayer(nextPlayer());
		}
		
	}
}

  public static void main(String[] args) {
    Board myBoard = new Board();
    myBoard.generateCards();
    myBoard.generateRooms();
    myBoard.chooseMurder();
    myBoard.loadMapFromCSV();
    myBoard.randomizeWeapons();
    
    playersPlaying = 7;
    while(playersPlaying > 6 || playersPlaying <2) {
    	System.out.println("Pick the number of players:");
    	playersPlaying = Character.getNumericValue(takeStringInput().charAt(0));
    }
    System.out.println(playersPlaying + " Players selected.");
    myBoard.generatePlayers();

    isRunning = true;
    myBoard.distributeCards();
    myBoard.playGame();
  }
}
