import java.util.Scanner;

public class Main {
    static boolean trigger;
    static int[][] array;
    static int x;
    static int y;

    public static void main(String[] args) {
        newGame();
        while (true) {
            if (trigger) {
                System.out.println("Ход Х");
                action();
                array[x][y] = 1;
            } else {
                System.out.println("Ход О");
                action();
                array[x][y] = 2;
            }
            trigger = !trigger;
            examinationArray(array);
        }
    }

    private static void newGame() {
        array = new int[3][3];
        trigger = true;
        System.out.println("Введите координаты первой клетки через пробел, например:");
        System.out.println("2 2");
    }

    private static void action() {
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        try {
            x = Integer.parseInt(input.split(" ")[0]) - 1;
            y = Integer.parseInt(input.split(" ")[1]) - 1;
        } catch (Exception e) {
            System.out.println("Неверный формат ввода, попробуйте еще раз");
            action();
        }
    }

    private static void examinationArray(int[][] array) {
        int count = 0;
        int diagonalOne = 0;
        int diagonalTwo = 0;
        for (int i = 0; i < array.length; i++) {
            int line = 0;
            int column = 0;
            for (int j = 0; j < array[i].length; j++) {
                switch (array[i][j]) {
                    case 0 -> System.out.print("- ");
                    case 1 -> {
                        System.out.print("X ");
                        line++;
                    }
                    case 2 -> {
                        System.out.print("0 ");
                        line--;
                    }
                }
                switch (array[j][i]) {
                    case 1 -> column++;
                    case 2 -> column--;
                }
                if (Math.abs(line) == 3 || Math.abs(column) == 3) {
                    System.out.println("Gave over");
                    newGame();
                }
                if (array[i][j] == 0) {
                    count++;
                }
            }
            System.out.println();
            switch (array[i][i]) {
                case 1 -> diagonalOne++;
                case 2 -> diagonalOne--;
            }
            switch (array[i][array.length - 1 - i]) {
                case 1 -> diagonalTwo++;
                case 2 -> diagonalTwo--;
            }
        }
        if (Math.abs(diagonalOne) == 3 || Math.abs(diagonalTwo) == 3) {
            System.out.println("Gave over");
            newGame();
            return;
        }
        if (count == 0) {
            System.out.println("Ничья все клетки заполнены");
            newGame();
        }
    }
}
