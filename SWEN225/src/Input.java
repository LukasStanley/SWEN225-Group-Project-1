import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.swing.*;

public class Input{

	private  JTextField textField ;
	private  JLabel jLabel1;

    //Allows access to the Board.
    Board myBoard;

    public Input(Board myBoard) {
        this.myBoard = myBoard;
    }

    public  String askID(int i) {
    	String text = JOptionPane.showInputDialog("Player " + i + " please enter your name");    	
    	return text;
	}

	public  String askPlayer(String[] optionArray) {
		String text = radioButtonInput(optionArray, "Choose your character!");
		return text;
	}

	public  List<String> getAccusation() {
        String person = radioButtonInput(myBoard.characterNames, "Pick a Character");
        String weapon = radioButtonInput(myBoard.weaponNames, "Pick a Weapon");
        String room = radioButtonInput(myBoard.roomNames, "Pick a Room");

        if(Arrays.asList(myBoard.characterNames).contains(person)
                && Arrays.asList(myBoard.weaponNames).contains(weapon)
                && Arrays.asList(myBoard.roomNames).contains(room)
                ){
            if(JOptionPane.showConfirmDialog(null, "Confirm Accusation:\n"+person+"\nWith the "+weapon+"\nIn the "+room)==0){
                return new ArrayList<>(Arrays.asList(person, weapon, room));
            }
            System.out.println("Accusation Failed (CANCELLED CHOICE)");
            return null;
        }
        System.out.println("Accusation Failed (INVALID CHOICES)");
        return null;
    }

	public  List<String> getSuggestion() {
        String person = radioButtonInput(myBoard.characterNames, "Pick a Character");
        String weapon = radioButtonInput(myBoard.weaponNames, "Pick a Weapon");
        Room currentRoom = myBoard.players[myBoard.currentPlayer].getCurrentRoom();
        String room;
        if(currentRoom != null){
            room = myBoard.players[myBoard.currentPlayer].getCurrentRoom().getName();

            if(Arrays.asList(myBoard.characterNames).contains(person)
                    && Arrays.asList(myBoard.weaponNames).contains(weapon)
                    && Arrays.asList(myBoard.roomNames).contains(room)
                    ){
                if(JOptionPane.showConfirmDialog(null, "Confirm Suggestion:\n"+person+"\nWith the "+weapon+"\nIn the "+room)==0){
                    return new ArrayList<>(Arrays.asList(person, weapon, room));
                }
                System.out.println("Suggestion Failed (CANCELLED CHOICE)");
                return null;
            }
            System.out.println("Suggestion Failed (INVALID CHOICES)");
            return null;
        }
        System.out.println("Suggestion Failed (NOT IN A ROOM)");
        return null;
	}

	//Creates 
    public  String radioButtonInput(String[] optionArray, String optionType) {
        String returnString = null;
        JPanel panel = new JPanel();
        JRadioButton allButtons[] = new JRadioButton[optionArray.length];
        panel.setLayout(new GridLayout(optionArray.length, 1));
        ButtonGroup group = new ButtonGroup();
        for(int i = 0; i < optionArray.length; i++){
            JRadioButton newButton = new JRadioButton(optionArray[i]);
            newButton.setActionCommand(optionArray[i]);
            panel.add(newButton);
            group.add(newButton);
            allButtons[i] = newButton;
            if(i == 0) {
                newButton.setSelected(true);;
            }
        }
        JOptionPane.showMessageDialog(panel, panel, optionType, 1);
        for(JRadioButton jrb : allButtons) {
            if(jrb.isSelected()) {
                returnString = jrb.getActionCommand();
            }
        }
        return returnString;
    }
}
