import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Input implements KeyListener {

    //Allows access to the Board.
    Board myBoard;

    public Input(Board myBoard) {
        this.myBoard = myBoard;
    }

    public static String askID() {
		// TODO Auto-generated method stub
		return null;
	}

	public static String askPlayer() {
		// TODO Auto-generated method stub
		return null;
	}

	public void keyPressed(KeyEvent keyEvent){
//	    switch(){
//
//        }
    }
	public void keyReleased(KeyEvent keyEvent){}
	public void keyTyped(KeyEvent keyEvent){}
}
