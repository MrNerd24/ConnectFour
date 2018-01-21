package connectfour.ai;

import connectfour.utils.GameState;

/**
 *
 * @author Juuso
 */
public class StateNode {
    
    public GameState state;
    public StateNode[] children = new StateNode[7];
    
    public StateNode() {
        
    }
    
    public StateNode(GameState state) {
        this.state = new GameState(state);
    }
}
