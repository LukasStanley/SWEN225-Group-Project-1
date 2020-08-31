
import java.awt.BasicStroke;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;

public class GameDisplay extends JFrame implements KeyListener
{
    //------------------------
    // MEMBER VARIABLES
    //------------------------
    static JMenuBar mb;
    static JMenuItem m1, m2, m3;
    static JMenu jm;
    static Canvas canvas;
    static final int windowLength = 1200;
    static final int windowHeight = 900;
    static final int canvasHeight = (int) (windowHeight*0.9);
    static final int canvasWidth = (int) (windowLength*0.9);
    
    static float textRatio = (float)canvasWidth/(float)960;
    
    static final int boardDisplayHeight = (int) (canvasHeight*0.75);
    static final int boardDisplayWidth = (int) (canvasWidth*0.6);

    private static int dice1 = 1;
    private static int dice2 = 1;
    static boolean shown = false;

    private Board myBoard;
    
    //------------------------
    // CONSTRUCTOR
    //------------------------

    public GameDisplay(Board myBoard)
    {
     this.myBoard = myBoard;
     rootPane.setFocusable(true);
     rootPane.addKeyListener(this);
    }
    public void redraw() {
        repaint();
    }
    public static void ChangeOccured() {
  	  //Notification for change state
    }
    

    public static void updateDie(int dice, int dicetwo){
        dice1 = dice;
        dice2 = dicetwo;
    }

    public void setUpGameBoard() {
        mb = new JMenuBar();
        jm = new JMenu("Menu");
        canvas = new Canvas() { 

            // paint the canvas 
            public void paint(Graphics g) { 
                Graphics2D g2 = (Graphics2D) g;
                Font currentFont;
                Font newFont;
                int boardHeight = Board.locations.length;
                int boardWidth = Board.locations[0].length;
                int cellHeight = boardDisplayHeight/boardWidth;
                int cellWidth = boardDisplayWidth/boardHeight;
                boolean[] roomLabelled = new boolean[Board.roomNames.length];
                for(int i = 0; i < roomLabelled.length; i++) {
                    roomLabelled[i] = false;
                }
                for( int j = 0; j<boardWidth; j++) {
                    for( int i = 0; i<boardHeight; i++) {
                        //Draw player picture

                        if(Board.locations[i][j].getPlayerOn() != null) {
                            //	  					 if(Board.locations[i][j].getPlayerOn().getPlayerName().getName() == null) {
                            //	  						 System.out.println("WOAH");
                            //	  					 }
                            String imageFilePath = "./src/MUSTARD.jpg";//"./src/" + Board.locations[i][j].getPlayerOn().getPlayerName().getName() + ".jpeg";
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
                            String imageFilePath = "./src/" + Board.locations[i][j].getWeaponOn().getName() + ".jpg";
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
                currentFont = g2.getFont();
                newFont = currentFont.deriveFont(currentFont.getSize() * 1.2F * textRatio);
                g2.setFont(newFont);
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
                                        g2.drawString(Board.locations[i][j].getRoomIn().getName(), (int) (((float) i+ 0.2 )*cellWidth), (j+2)*cellHeight);
                                    }

                                }
                        }
                    } 

                }
                
                //DISPLAY THE GAME TITLE
                currentFont = g2.getFont();
                newFont = currentFont.deriveFont(currentFont.getSize() * 2F  * textRatio);
                g2.setFont(newFont);
                g2.setColor(new Color(255, 30, 30));
                g2.drawString("CLUEDO", boardDisplayWidth + cellWidth, cellHeight*2);
                
                //DISPLAY THE DIE
                String diceImage = "./src/ONE.jpg";
                if(dice1 == 2) {
                    diceImage = "./src/TWO.jpg";
                }
                else if(dice1 == 3) {
                    diceImage = "./src/THREE.jpg";
                }
                else if(dice1 == 4) {
                    diceImage = "./src/FOUR.jpg";
                }
                else if(dice1 == 5) {
                    diceImage = "./src/FIVE.jpg";
                }
                else if(dice1 == 6) {
                    diceImage = "./src/SIX.jpg";
                }
                BufferedImage img = null;
                try {
                    img = ImageIO.read(new File(diceImage));
                } catch (IOException e) {
                    System.out.println(diceImage + " could not be found");
                }
                g2.drawImage(img, boardDisplayWidth + cellWidth, cellHeight*3, null);
                
                diceImage = "./src/ONE.jpg";
                if(dice2 == 2) {
                    diceImage = "./src/TWO.jpg";
                }
                else if(dice2 == 3) {
                    diceImage = "./src/THREE.jpg";
                }
                else if(dice2 == 4) {
                    diceImage = "./src/FOUR.jpg";
                }
                else if(dice2 == 5) {
                    diceImage = "./src/FIVE.jpg";
                }
                else if(dice2 == 6) {
                    diceImage = "./src/SIX.jpg";
                }
                img = null;
                try {
                    img = ImageIO.read(new File(diceImage));
                } catch (IOException e) {
                    System.out.println(diceImage + " could not be found");
                }
                g2.drawImage(img, boardDisplayWidth + cellWidth*2 + img.getWidth(), cellHeight*3, null);
                
                
                int diceHeight = img.getHeight();
                
                //DISPLAY PLAYER INFO
                
                //set current player
                Player currentPlayer = Board.players[Board.currentPlayer];
                
                
                
                
                //Prepare info font
                currentFont = g2.getFont();
                newFont = currentFont.deriveFont(currentFont.getSize() * 0.5F  * textRatio);
                g2.setFont(newFont);
                g2.setColor(new Color(255, 255, 255));
                
                
                if(currentPlayer != null) {
                	//Get thumbnail of current player
                    //String imageFilePath = "./src/" + currentPlayer.getPlayerName().getName() + ".jpg";
                    String imageFilePath = "./src/MUSTARD.jpg";
                    
                    img = null;
                    try {
                        img = ImageIO.read(new File(imageFilePath));
                    } catch (IOException e) {
                        System.out.println(imageFilePath + " could not be found");
                    }
                    //Draw the thumbnail
                    g2.drawImage(img, boardDisplayWidth + cellWidth, diceHeight + cellHeight*4, boardDisplayWidth + cellWidth*3,  diceHeight + cellHeight*6, 0, 0, img.getWidth(), img.getHeight(), null);
                	
                	//Print info font
                    g2.drawString("You are MUSTARD" , boardDisplayWidth + cellWidth*4, diceHeight + cellHeight*5);
                    //g2.drawString("You are " + currentPlayer.getPlayerName().getName() , boardDisplayWidth + cellWidth, cellHeight);
                    
                    g2.drawString("You have " + currentPlayer.getSteps() + " steps remaining." , boardDisplayWidth + cellWidth*4, diceHeight + cellHeight*6);
                    //g2.drawString("You have yet to roll." , boardDisplayWidth + cellWidth, img.getHeight() + cellHeight*5);
                    g2.drawString("Your hand:" , cellWidth*1, (int)(boardDisplayHeight + cellHeight*0.5));
                    for(int i = 0; i < currentPlayer.getHand().size(); i++) {
                    	imageFilePath = "./src/" + currentPlayer.getHand().get(i).getName() + ".jpg";
                        img = null;
                        try {
                            img = ImageIO.read(new File(imageFilePath));
                        } catch (IOException e) {
                            System.out.println(imageFilePath + " could not be found");
                        }
                        g2.drawImage(img, cellWidth + i*4*cellWidth, boardDisplayHeight + cellHeight, i*cellWidth*4 + 4*cellWidth,  boardDisplayHeight + cellHeight*4, 0, 0, img.getWidth(), img.getHeight(), null);
                        g2.setColor(new Color(255, 0, 0));
                        newFont = currentFont.deriveFont(currentFont.getSize() * 0.25F  * textRatio);
                        g2.setFont(newFont);
                        g2.drawString(currentPlayer.getHand().get(i).getName() , cellWidth + i*4*cellWidth, boardDisplayHeight + cellHeight*4);
                    }
                }
                
                
            }
        }; 

        m1 = new JMenuItem("Test1");
        m2 = new JMenuItem("Test2");
        m3 = new JMenuItem("Test3");

        m1.addKeyListener(this);
        m2.addKeyListener(this);
        m3.addKeyListener(this);
        jm.addKeyListener(this);
        canvas.addKeyListener(this);

        jm.add(m1);
        jm.add(m2);
        jm.add(m3);

        mb.add(jm);

        setJMenuBar(mb);
        canvas.setSize(canvasWidth, canvasHeight);
        canvas.setBackground(new Color(100, 200, 250));
        add(canvas);

        canvas.setVisible(true);
    } 
   
    public int displayPlayerPick() {
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

    public void keyPressed(KeyEvent keyEvent){
        switch(keyEvent.getKeyCode()){
            //Left Key
            case 37:
                break;

            //Up Key
            case 38:
                break;

            //Right Key
            case 39:
                break;

            //Down Key
            case 40:
                break;

            //Enter
            case 10:
                break;

            //SpaceBar
            case 32:
                break;

            default:
        }
        System.out.print("Key Pressed:");
        System.out.println(keyEvent.getExtendedKeyCode());
    }

    public void keyReleased(KeyEvent keyEvent){
        //Unused
    }
    public void keyTyped(KeyEvent keyEvent){
        //Unused
    }


}
