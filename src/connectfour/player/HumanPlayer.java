package connectfour.player;

import connectfour.utils.GameState;
import connectfour.interfaces.Player;
import connectfour.interfaces.UI;

/**
 *
 * @author Juuso
 */
public class HumanPlayer implements Player {

    private UI ui;
    private GameState currentGameState;

    public HumanPlayer(UI ui) {
        this.ui = ui;
        this.currentGameState = new GameState();
    }

    @Override
    public int getMove() {
        ui.showGameState(currentGameState);
        int move = ui.getUserMove(currentGameState);
        currentGameState.dropColor(false, move);
        ui.showGameState(currentGameState);
        return move;

    }

    @Override
    public void setEnemyMove(int column) {
        currentGameState.dropColor(true, column);
    }

}
