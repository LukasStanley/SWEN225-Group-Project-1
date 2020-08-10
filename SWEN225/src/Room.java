import java.util.ArrayList;

public class Room
{

  private String name;
  private ArrayList<Location> locations;
  private ArrayList<Player> players;
  private ArrayList<WeaponCard> weapons;


  public Room(String name) {
    this.name = name;
    locations = new ArrayList<>();
    players = new ArrayList<>();
    weapons = new ArrayList<>();
  }

  public String getName() {
    return name;
  }

  public ArrayList<Location> getLocations() {
    return locations;
  }

  public ArrayList<Player> getPlayers() {
    return players;
  }

  public ArrayList<WeaponCard> getWeapons() {
    return weapons;
  }
  
}