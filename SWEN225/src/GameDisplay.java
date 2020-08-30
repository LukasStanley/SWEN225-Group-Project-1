
import java.awt.BasicStroke;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.lang.reflect.Array;
import java.util.*;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.imageio.ImageIO;
import javax.swing.*;

public class GameDisplay
{
  //------------------------
  // MEMBER VARIABLES
  //------------------------
	static JOptionPane numPlayersOptionPane = new JOptionPane();
	static JFrame f;
	static JMenuBar mb;
	static JMenuItem m1, m2, m3;
	static JMenu jm;
	static Canvas canvas;
	
	static int windowLength = 1200;
	static int windowHeight = 800;
	static int canvasHeight = 800;
	static int canvasWidth = 800;
	
	static boolean shown = false;

	static JComboBox playerCountCombo;
  //------------------------
  // CONSTRUCTOR
  //------------------------

  public GameDisplay()
  {

  }
  
  public static void redraw() {
	  f.repaint();
  }
  

  public static void setUpGameBoard() {
	  f = new JFrame("Cluedo");
	  f.setSize(windowLength,windowHeight);
	  f.setLayout(null);//no layout manager
	  f.setVisible(true);
	  
	  mb = new JMenuBar();
	  jm = new JMenu("Menu");
	  canvas = new Canvas() { 
		  
          // paint the canvas 
          public void paint(Graphics g) { 
        	  Graphics2D g2 = (Graphics2D) g;
	          int boardHeight = Board.locations.length;
	  		  int boardWidth = Board.locations[0].length;
	  		  int cellHeight = GameDisplay.canvasHeight/boardWidth;
	  		  int cellWidth = GameDisplay.canvasWidth/boardHeight;
	  		  boolean[] roomLabelled = new boolean[Board.roomNames.length];
	  		  for(int i = 0; i < roomLabelled.length; i++) {
	  			  roomLabelled[i] = false;
	  		  }
	  		  for( int j = 0; j<boardWidth; j++) {
	  			  for( int i = 0; i<boardHeight; i++) {
	  				  //Draw player picture

	  				  if(Board.locations[i][j].getPlayerOn() != null) {
		  				String imageFilePath = "./src/" + Board.locations[i][j].getPlayerOn().getPlayerName().getName() + ".jpeg";
	  					BufferedImage img = null;
	  					try {
	  					    img = ImageIO.read(new File(imageFilePath));
	  					} catch (IOException e) {
	  						System.out.println(imageFilePath + " could not be found");
	  					}
	  					  g2.drawImage(img, (i)*cellWidth, (j)*cellHeight, (i+1)*cellWidth, (j+1)*cellHeight, 0, 0, img.getWidth(), img.getHeight(), null);
	  				  }
	  				  //Draw weapon picture
	  				  else if(Board.locations[i][j].getWeaponOn() != null) {
			  				String imageFilePath = "./src/" + Board.locations[i][j].getWeaponOn().getName() + ".jpeg";
		  					BufferedImage img = null;
		  					try {
		  					    img = ImageIO.read(new File(imageFilePath));
		  					} catch (IOException e) {
		  						System.out.println(imageFilePath + " could not be found");
		  					}
		  					g2.drawImage(img, (i)*cellWidth, (j)*cellHeight, (i+1)*cellWidth, (j+1)*cellHeight, 0, 0, img.getWidth(), img.getHeight(), null);
	  				  }
	  				  //Draw coloured tile
	  				  else {
		  				  if(Board.locations[i][j].getRoomIn() != null) {
		  					  //Coloure room tiles a set of colours
			  				  if(Board.locations[i][j].getRoomIn().getName() == Board.roomNames[0]) {
			  					  g2.setColor(new Color(255, 30, 30));
			  				  }
			  				  else if(Board.locations[i][j].getRoomIn().getName() == Board.roomNames[1]) {
			  					  g2.setColor(new Color(30, 255, 30));
			  				  }
			  				  else if(Board.locations[i][j].getRoomIn().getName() == Board.roomNames[2]) {
			  					  g2.setColor(new Color(30, 30, 255));
			  				  }
			  				  else if(Board.locations[i][j].getRoomIn().getName() == Board.roomNames[3]) {
			  					  g2.setColor(new Color(255, 220, 30));
			  				  }
			  				  else if(Board.locations[i][j].getRoomIn().getName() == Board.roomNames[4]) {
			  					  g2.setColor(new Color(30, 255, 255));
			  				  }
			  				  else if(Board.locations[i][j].getRoomIn().getName() == Board.roomNames[5]) {
			  					  g2.setColor(new Color(255, 30, 255));
			  				  }
			  				  else if(Board.locations[i][j].getRoomIn().getName() == Board.roomNames[6]) {
			  					  g2.setColor(new Color(255, 150, 30));
			  				  }
			  				  else if(Board.locations[i][j].getRoomIn().getName() == Board.roomNames[7]) {
			  					  g2.setColor(new Color(255, 30, 150));
			  				  }
			  				  else if(Board.locations[i][j].getRoomIn().getName() == Board.roomNames[8]) {
			  					  g2.setColor(new Color(30, 100, 30));
			  				  }
		  				  }
		  				  //Hallway/non room tiles get coloured beige
		  				  else {
		  					  g2.setColor(new Color(249, 228, 183));
		  				  }
		  				  //Draw a coloured rect for the tile.
		  				  g2.fillRect((i)*cellWidth, (j)*cellHeight, cellWidth, cellHeight);
	  				  }
	  				  
	  				  //Draw walls/dividing lines. Check for walls upwards, left right and down, and draw a thick line if there is one, thin if there is not.
	  				  if(Board.locations[i][j].getWallRight()) {
	  					  g2.setColor(new Color(0, 0, 0));
	  					  g2.setStroke(new BasicStroke(2));
	  				  }
	  				  else {
	  					g2.setColor(new Color(90, 90, 90));
		  				g2.setStroke(new BasicStroke(0.5F));
	  				  }
	  				  g2.drawLine((i)*cellWidth, (j+1)*cellHeight, (i+1)*cellWidth, (j+1)*cellHeight);
	  				  
	  				  if(Board.locations[i][j].getWallLeft()) {
	  					  g2.setColor(new Color(0, 0, 0));	
	  					  g2.setStroke(new BasicStroke(2));
	  				  }
	  				  else {
		  				g2.setColor(new Color(90, 90, 90));
	  					g2.setStroke(new BasicStroke(0.5F));
	  				  }
	  				  g2.drawLine((i)*cellWidth, (j)*cellHeight, (i+1)*cellWidth, (j)*cellHeight);
	  				  
	  				  if(Board.locations[i][j].getWallUp()) {
	  					  g2.setColor(new Color(0, 0, 0));
	  					  g2.setStroke(new BasicStroke(2));
	  				  }
	  				  else {
		  				g2.setColor(new Color(90, 90, 90));
		  				g2.setStroke(new BasicStroke(0.5F));
	  				  }
	  				  g2.drawLine((i)*cellWidth, (j+1)*cellHeight, (i)*cellWidth, (j)*cellHeight);
	  				
	  				  if(Board.locations[i][j].getWallDown()) {
	  					  g2.setColor(new Color(0, 0, 0));
	  					  g2.setStroke(new BasicStroke(2));
	  				  }
	  				  else {
		  				g2.setColor(new Color(90, 90, 90));
		  				g2.setStroke(new BasicStroke(0.5F));
	  				  }
	  				  g2.drawLine((i+1)*cellWidth, (j+1)*cellHeight, (i+1)*cellWidth, (j)*cellHeight);
	  			  }
	  		  }
	  		  //Add the room labels.
	  		  for( int j = 0; j<boardWidth; j++) {
	  			  for( int i = 0; i<boardHeight; i++) {
		  				if(Board.locations[i][j].getRoomIn() != null) {
		  					for(int k = 0; k < Board.roomNames.length; k++)
		  					if(Board.locations[i][j].getRoomIn().getName() == Board.roomNames[k]) {
								  if(roomLabelled[k] == false) {
									  roomLabelled[k] = true;
									  g2.setColor(new Color(255, 255, 255));
									  g2.setStroke(new BasicStroke(2));
									  g2.drawString(Board.locations[i][j].getRoomIn().getName(), (i+1)*cellWidth, (j+2)*cellHeight);
								  }
			  			  
		  					}
		  				}
	  			  } 
	  		  
	  		  }
          }
      }; 
	  
	  m1 = new JMenuItem("Test1");
	  m2 = new JMenuItem("Test2");
	  m3 = new JMenuItem("Test3");
	  
	  jm.add(m1);
	  jm.add(m2);
	  jm.add(m3);
	  
	  mb.add(jm);
	  
	  f.setJMenuBar(mb);
	  canvas.setSize(canvasWidth, canvasHeight);
	  canvas.setBackground(new Color(100, 200, 250));
	  f.add(canvas);
	  

	  canvas.setVisible(true);
	  
  } 

  public static int displayPlayerPick() {
	  if(!shown){
		  setUpGameBoard();
		  shown = true;
	  }
	 
	  Integer playerCountOptions[] = {2,3,4,5,6};

      Object out = JOptionPane.showInputDialog(null,
              "How many players are playing?",
              "Pick Number of Players",
              JOptionPane.PLAIN_MESSAGE,
              null,
              playerCountOptions,
              3
      );

      if (out==null){
        return 7;
      }else{
          return (int)out;
      }
  }
}