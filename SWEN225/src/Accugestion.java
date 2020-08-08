/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.30.0.5105.3f8cbd5a3 modeling language!*/



// line 40 "model.ump"
// line 110 "model.ump"
public class Accugestion
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Accugestion Attributes
  private WeaponCard weapon;
  private RoomCard room;
  private PersonCard person;
  private Player owner;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Accugestion(Card weaponCard, Card personCard, Card roomCard, Player aOwner)
  {
    weapon = (WeaponCard) weaponCard;
    room = (RoomCard) roomCard;
    person = (PersonCard) personCard;
    owner = aOwner;
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setWeapon(WeaponCard aWeapon)
  {
    boolean wasSet = false;
    weapon = aWeapon;
    wasSet = true;
    return wasSet;
  }

  public boolean setRoom(RoomCard aRoom)
  {
    boolean wasSet = false;
    room = aRoom;
    wasSet = true;
    return wasSet;
  }

  public boolean setPerson(PersonCard aPerson)
  {
    boolean wasSet = false;
    person = aPerson;
    wasSet = true;
    return wasSet;
  }

  public boolean setOwner(Player aOwner)
  {
    boolean wasSet = false;
    owner = aOwner;
    wasSet = true;
    return wasSet;
  }

  public WeaponCard getWeapon()
  {
    return weapon;
  }

  public RoomCard getRoom()
  {
    return room;
  }

  public PersonCard getPerson()
  {
    return person;
  }

  public Player getOwner()
  {
    return owner;
  }

  public void delete()
  {}


  public String toString()
  {
    return super.toString() + "["+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "weapon" + "=" + (getWeapon() != null ? !getWeapon().equals(this)  ? getWeapon().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "room" + "=" + (getRoom() != null ? !getRoom().equals(this)  ? getRoom().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "person" + "=" + (getPerson() != null ? !getPerson().equals(this)  ? getPerson().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "owner" + "=" + (getOwner() != null ? !getOwner().equals(this)  ? getOwner().toString().replaceAll("  ","    ") : "this" : "null");
  }
}