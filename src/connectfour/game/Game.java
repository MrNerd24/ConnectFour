package connectfour.game;

import connectfour.ai.AIPlayer;
import connectfour.interfaces.Player;
import connectfour.interfaces.UI;
import connectfour.player.HumanPlayer;
import connectfour.utils.GameState;
import connectfour.utils.GameStateInfoCalculator;
import connectfour.utils.GameStatus;

/**
 *
 * @author Juuso
 */
public class Game {
    
    private Player human;
    private Player AI;
    private GameState state;
    private UI ui;
    
    public Game(UI ui) {
        this.ui = ui;
        human = new HumanPlayer(ui);
        AI = new AIPlayer();
        state = new GameState();
    }
    
    public void play() {
        ui.showInitialScene();
        int moves = 0;
        
        while (true) {
            if(moves % 2 == 0) {
                int move = human.getMove();
                state.dropColor(false, move);
                AI.setEnemyMove(move);
            } else {
                int move = AI.getMove();
                state.dropColor(true, move);
                human.setEnemyMove(move);
            }
            moves++;
            
            GameStatus status = GameStateInfoCalculator.getGameStatus(state);
            switch (status) {
                case RED_WON:
                    ui.redWon();
                    return;
                case YELLOW_WON:
                    ui.yellowWon();
                    return;
                case TIE:
                    ui.tie();
                    return;
            }
        }
    }
    
}
