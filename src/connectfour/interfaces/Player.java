package connectfour.interfaces;

import connectfour.utils.GameState;

public interface Player {
    
    public int getMove();
    public void setEnemyMove(int column);
    
}
