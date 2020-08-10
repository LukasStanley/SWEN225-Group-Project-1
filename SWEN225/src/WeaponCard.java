public class WeaponCard extends Card
{

  Location location;


  public WeaponCard(String aName)
  {
    super(aName);
  }

  public Location getLocation() {
    return location;
  }

  public void setLocation(Location location) {
    this.location = location;
  }
}