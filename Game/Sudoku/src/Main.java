import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Main {
    static int[][] board = new int[9][9];
    public static void main(String[] args) {
        parseFile();
        action();
        for (int[] ints : board) {
            for (int anInt : ints) {
                System.out.print(anInt + " ");
            }
            System.out.println();
        }
    }

    private static boolean action() {
        int column = 0;
        int line = 0;
        boolean trigger = true;
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                if (board[i][j] == 0) {
                    line = i;
                    column = j;
                    trigger = false;
                    break;
                }
            }
            if (!trigger)
                break;
        }
        if (trigger)
            return true;

        for (int i = 1; i <= 9; i++) {
            if (examination(line, column, i)) {
                board[line][column] = i;
                if (action()) {
                    return true;
                }
                board[line][column] = 0;

            }
        }
        return false;
    }
    private static boolean examination(int line, int column, int num) {
        for (int i = 0; i < board.length; i++) {
            if (board[line][i] == num || board[i][column] == num) {
                return false;
            }
        }
        int tempIntOne = line - line % 3;
        int tempIntTwo = column - column % 3;
        for (int i = tempIntOne; i < tempIntOne + 3; i++) {
            for (int j = tempIntTwo; j < tempIntTwo + 3; j++) {
                if (board[i][j] == num) {
                    return false;
                }
            }
        }
        return true;
    }
    private static void parseFile() {
        String fileName = "lib/input.txt";
        String line = null;
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            while ((line = reader.readLine()) != null) {
                String[] tempArrayString = line.split(" ");
                String[] tempIndex = tempArrayString[0].split("");
                int x = Integer.parseInt(tempIndex[0]);
                int y = Integer.parseInt(tempIndex[1]);
                board[x][y] = Integer.parseInt(tempArrayString[1]);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
