
import java.io.BufferedReader;
import java.io.FileReader;
import java.lang.reflect.Array;
import java.util.*;
import java.io.IOException;
import java.io.InputStreamReader;
import javax.swing.*;

public class GameDisplay
{
  //------------------------
  // MEMBER VARIABLES
  //------------------------
	static JOptionPane numPlayersOptionPane = new JOptionPane();
	static JFrame f = new JFrame();
  //------------------------
  // CONSTRUCTOR
  //------------------------

  public GameDisplay()
  {

  }



  public static int displayPlayerPick() {
	 int playersPlaying = 7;
     f.setSize(1200,800);
	 f.setLayout(null);//no layout manager
	 f.setVisible(true);
	 System.out.println("Pick the number of players:");
	 playersPlaying = Integer.parseInt(JOptionPane.showInputDialog("Choose number of players (2-6)"));
	 System.out.println(playersPlaying + " Players selected.");
	 return playersPlaying;
  }
}
