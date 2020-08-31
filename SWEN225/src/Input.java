import java.awt.GridLayout;
import java.util.List;
import javax.swing.*;

public class Input{

	private static JTextField textField ;
	private static JLabel jLabel1;

    //Allows access to the Board.
    Board myBoard;

    public Input(Board myBoard) {
        this.myBoard = myBoard;
    }

    public static String askID(int i) {
    	String text = JOptionPane.showInputDialog("Player " + i + " please enter your name");    	
    	return text;
	}

	public static String askPlayer(String[] optionArray) {
		String text = radioButtonInput(optionArray, "Choose your character!");
		return text;
	}

	public static List<String> getAccusation() {
		// TODO Auto-generated method stub
		return null;
	}

	public static List<String> getSuggestion() {
		// TODO Auto-generated method stub
		return null;
	}
	
	//Creates 
    public static String radioButtonInput(String[] optionArray, String optionType) {
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
