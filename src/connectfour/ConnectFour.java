
package connectfour;

import connectfour.game.Game;
import connectfour.ui.TextUI;

public class ConnectFour {

    public static void main(String[] args) {
        Game game = new Game(TextUI.getInstance());
        game.play();
    }
    
}
