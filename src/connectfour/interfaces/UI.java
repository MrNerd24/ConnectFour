package connectfour.interfaces;

import connectfour.utils.GameState;

public interface UI {
    public void showInitialScene();
    public void showGameState(GameState state);
    public int getUserMove(GameState state);

    public void redWon();

    public void yellowWon();

    public void tie();
}
