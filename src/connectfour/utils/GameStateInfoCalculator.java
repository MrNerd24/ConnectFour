package connectfour.utils;

import connectfour.interfaces.UI;
import connectfour.ui.TextUI;

public class GameStateInfoCalculator {

    private static LRUCache<Long, Long> heuristicCache = new LRUCache(1000000);
    private static LRUCache<Long, GameStatus> gameStatusCache = new LRUCache(1000000);

    public static long getHeuristic(GameState state) {
        if (heuristicCache.containsKey(state.getState())) {
            return heuristicCache.get(state.getState());
        }
        long heuristic = 0;
        for (int i = 0; i < 7; i++) {
            int height = state.getHeightOfColumn(i) - 1;
            for (int j = height; j >= 0; j--) {
                heuristic += getHeuristicOfSpot(i, j, state);
            }

        }
        heuristicCache.put(state.getState(), heuristic);

//        UI ui = TextUI.getInstance();
//        System.out.println("heuristic: " + heuristic);
//        ui.showGameState(state);
        
        return heuristic;
    }

    private static long getHeuristicOfSpot(int column, int row, GameState state) {
        long heuristic = 0;

        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (j == 0 && i == 0) {
                    continue;
                }
                heuristic += countHeuristicFromDirection(column, i, row, j, state);
            }
        }

        return heuristic;
    }

    private static long countHeuristicFromDirection(int column, int columnChange, int row, int rowChange, GameState state) {
        boolean spotColor = state.getColor(column, row);
        long heuristic = 0;
        Boolean[] colors = new Boolean[4];

        for (int i = 0; i < 4; i++) {
            Boolean color = null;
            if (column < 0 || column > 6 || row < 0 || row > 5) {
                break;
            }

            if (state.getHeightOfColumn(column) > row) {
                color = state.getColor(column, row);
            }
            colors[i] = color;

            column += columnChange;
            row += rowChange;
        }

        int yellows = 0;
        int reds = 0;

        for (int i = 0; i < colors.length; i++) {
            if (colors[i] == null) {
                continue;
            }
            if (colors[i]) {
                reds++;
            } else {
                yellows++;
            }
        }

        if (reds == 0) {
            heuristic -= 1L << (yellows * 10);
        } else if (yellows == 0) {
            heuristic += 1L << (reds * 10);
        }

        return heuristic;

    }

    public static GameStatus getGameStatus(GameState state) {
        if (gameStatusCache.containsKey(state.getState())) {
            return gameStatusCache.get(state.getState());
        }
        GameStatus ans = null;
        int minHeight = 6;
        for (int i = 0; i < 7; i++) {

            int height = state.getHeightOfColumn(i) - 1;
            minHeight = Math.min(minHeight, height);

            for (int j = height; j >= 0; j--) {
                GameStatus statusOfSpot = getStatusOfSpot(i, j, state);
                if (statusOfSpot == GameStatus.RED_WON) {
                    ans = GameStatus.RED_WON;
                    break;
                }
                if (statusOfSpot == GameStatus.YELLOW_WON) {
                    ans = GameStatus.YELLOW_WON;
                    break;
                }
            }
                
            
        }
        if (ans == null && minHeight >= 6) {
            ans = GameStatus.TIE;
        }
        if (ans == null) {
            ans = GameStatus.ON_GOING;
        }
        gameStatusCache.put(state.getState(), ans);
        return ans;
    }

    private static GameStatus getStatusOfSpot(int column, int row, GameState state) {
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (j == 0 && i == 0) {
                    continue;
                }
                long heuristic = countHeuristicFromDirection(column, i, row, j, state);
                if (heuristic >= 1L << (10 * 4)) {
                    return GameStatus.RED_WON;
                }
                if (heuristic <= -(1L << (10 * 4))) {
                    return GameStatus.YELLOW_WON;
                }
            }
        }
        return GameStatus.ON_GOING;
    }

}
