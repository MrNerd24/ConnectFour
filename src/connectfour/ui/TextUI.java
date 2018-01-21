package connectfour.ui;

import connectfour.utils.GameState;
import connectfour.interfaces.UI;
import connectfour.utils.GameStateInfoCalculator;
import java.util.Scanner;

public class TextUI implements UI {

    private static TextUI instance = null;
    private Scanner scanner = new Scanner(System.in);

    public static TextUI getInstance() {
        if (TextUI.instance == null) {
            TextUI.instance = new TextUI();
        }
        return TextUI.instance;
    }

    private TextUI() {

    }

    @Override
    public void showGameState(GameState state) {
        System.out.println(" 1 2 3 4 5 6 7");
        for (int y = 5; y >= 0; y--) {
            for (int x = 0; x < 7; x++) {
                if (state.getHeightOfColumn(x) <= y) {
                    System.out.print("| ");
                } else {
                    if (state.getColor(x, y)) {
                        System.out.print("|R");
                    } else {
                        System.out.print("|Y");
                    }
                }
            }
            System.out.println("|");
            System.out.println("---------------");
        }
        System.out.println("Heuristic: " + GameStateInfoCalculator.getHeuristic(state));
        System.out.println("");
    }

    @Override
    public int getUserMove(GameState state) {
        while (true) {
            System.out.println("");
            System.out.print("Give your move (1-7): ");
            try {
                int column = Integer.parseInt(scanner.nextLine());
                System.out.println("");
                
                column--;
                
                if (column < 0 || column > 6) {
                    throw new IllegalArgumentException("Column out of bounds.");
                }

                if (state.getHeightOfColumn(column) > 5) {
                    throw new IllegalArgumentException("Column is filled already.");
                }

                return column;
            } catch (Exception e) {
                System.out.println("");
                System.out.println("Invalid input!");
                System.out.println(e.getMessage());
                System.out.println("");
                System.out.println("try again:");
            }
            

        }

    }

    @Override
    public void showInitialScene() {
        System.out.println("CONNECT FOUR");
        System.out.println("");
        System.out.println("");
    }

    @Override
    public void redWon() {
        System.out.println("");
        System.out.println("Red won!");
    }

    @Override
    public void yellowWon() {
        System.out.println("");
        System.out.println("Yellow won!");
    }

    @Override
    public void tie() {
        System.out.println("");
        System.out.println("Tie!");
    }

}
