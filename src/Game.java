import java.util.Scanner;

public class Game {
    public static void main(String[] arguments) {
        Scanner sc = new Scanner(System.in);
        Board board = new Board();
        int currentPlayer = 1;

        // keep playing until there is a winner or draw
        while (true) {
            // show the state of the board before the next move
            System.out.println(board);

            // keep prompting the player until a valid move is entered
            while (true) {
                System.out.print(board.getToken(currentPlayer) + " Player " + currentPlayer + " – enter slot 1-8: ");
                if (!sc.hasNextInt()) {
                    System.out.println("⚠️ Please enter an integer. ");
                    sc.next();
                    continue;
                }
                int column = sc.nextInt();
                if (column < 1 || column > board.getSize()) {
                    System.out.println("⚠️ The column must be between 1 and " + board.getSize());
                    continue;
                }
                if (board.isColumnFull(column - 1)) {
                    System.out.println("⚠️ Sorry that column is full. Please choose another one.");
                    continue;
                }

                // valid move - drop token
                board.dropToken(currentPlayer, column - 1);
                break;
            }

            // check if we have a winner
            int winningPlayer = board.getWinningPlayer();
            if (winningPlayer != 0) {
                System.out.println(board);
                System.out.println(board.getToken(winningPlayer) + " Congratulations player " + winningPlayer + "! You won!");
                break;
            }

            // check if we have a draw
            if (board.isFull()) {
                System.out.println(board);
                System.out.println("☯️ There are no more moves left, it's a draw!");
                break;
            }

            // switch to the next player
            currentPlayer = 3 - currentPlayer;
        }
    }
}