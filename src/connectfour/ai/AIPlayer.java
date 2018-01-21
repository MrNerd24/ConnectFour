package connectfour.ai;

import connectfour.utils.GameState;
import connectfour.interfaces.Player;
import connectfour.utils.GameStateInfoCalculator;
import connectfour.utils.GameStatus;

/**
 *
 * @author Juuso
 */
public class AIPlayer implements Player {

    private StateNode root;
    private static final int DEPTH = 8;

    public AIPlayer() {
        root = new StateNode();
        root.state = new GameState();
        for (int i = 0; i < root.children.length; i++) {
            GameState newState = new GameState(root.state);
            newState.dropColor(false, i);
            StateNode node = new StateNode();
            node.state = newState;
            root.children[i] = node;
        }
    }

    @Override
    public int getMove() {
        int move = getMaxMove(root, DEPTH, Long.MIN_VALUE, Long.MAX_VALUE);
        root = root.children[move];
        return move;
    }

    @Override
    public void setEnemyMove(int column) {
        root = root.children[column];
    }

    private int getMaxMove(StateNode node, int depth, long alpha, long beta) {

        long v = Long.MIN_VALUE;
        int move = 0;
        for (int i = 0; i < node.children.length; i++) {
            if (node.state.getHeightOfColumn(i) >= 6) {
                continue;
            }
            if (node.children[i] == null) {
                node.children[i] = new StateNode(node.state);
                node.children[i].state.dropColor(false, i);
            }

            long minVal = getMinVal(node.children[i], depth - 1, alpha, beta);
            if (minVal > v) {
                v = minVal;
                move = i;
            }
            alpha = Math.max(v, alpha);
//            if (beta < alpha) {
//                break;
//            }
        }
        return move;
    }

    private long getMaxVal(StateNode node, int depth, long alpha, long beta) {
        if (depth <= 0 || terminalNode(node)) {
            return GameStateInfoCalculator.getHeuristic(node.state);
        }
        long v = Long.MIN_VALUE;
        for (int i = 0; i < node.children.length; i++) {
            if (node.state.getHeightOfColumn(i) >= 6) {
                continue;
            }
            if (node.children[i] == null) {
                node.children[i] = new StateNode(node.state);
                node.children[i].state.dropColor(false, i);
            }
            v = Math.max(v, getMinVal(node.children[i], depth - 1, alpha, beta));
            alpha = Math.max(v, alpha);
//            if (beta < alpha) {
//                break;
//            }
        }

        return v;
    }

    private long getMinVal(StateNode node, int depth, long alpha, long beta) {
        if (depth <= 0 || terminalNode(node)) {
            return GameStateInfoCalculator.getHeuristic(node.state);
        }
        long v = Long.MAX_VALUE;
        for (int i = 0; i < node.children.length; i++) {
            if (node.state.getHeightOfColumn(i) >= 6) {
                continue;
            }
            if (node.children[i] == null) {
                node.children[i] = new StateNode(node.state);
                node.children[i].state.dropColor(true, i);
            }
            v = Math.min(v, getMaxVal(node.children[i], depth - 1, alpha, beta));
            beta = Math.min(v, beta);
//            if (beta < alpha) {
//                break;
//            }
        }

        return v;
    }

    private boolean terminalNode(StateNode node) {
        return GameStateInfoCalculator.getGameStatus(node.state) != GameStatus.ON_GOING;
    }

}
