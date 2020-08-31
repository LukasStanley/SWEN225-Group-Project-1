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

	public static String askPlayer() {
		String text = JOptionPane.showInputDialog("DEBUG PLACEHOLDER: Enter Character name in all caps");
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
}
