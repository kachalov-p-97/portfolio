public class TrucksContainersAndBoxes {
    private final int MAX_CONTAINER = 12;
    private final int MAX_BOX = 27;

    private TrucksContainersAndBoxes(int box) {
        if (box <= 0) {
            System.out.println("Пустоту не возим, ящики обратно не принемаем.\nВведите положительное число!");
            return;
        }

        int track = 1;  // Переменная для вывода и счетчик грузовиков
        int container = 0;  //Переменная для вывода контейнеров
        int floatingBoxes = 0;  //переменная для вывода ящиков

        int totalContainers = 1;  // Счетчик контейнеров

        System.out.println("\t\tГрузовик: " + track);
        System.out.println("\tКонтейнер:" + (container + 1));

        do {
            floatingBoxes++;
            System.out.println("Ящик: " + floatingBoxes);
            if (floatingBoxes == MAX_BOX) {
                container++;
                floatingBoxes = 0;
                box -= MAX_BOX;

                if (container + 1 != 13 && box > 0) {
                    totalContainers++;
                    System.out.println("\tКонтейнер: " + (container + 1));
                }

                if (container == MAX_CONTAINER && box > 0) {
                    track++;
                    container = 0;
                    System.out.println("\t\tГрузовик: " + track);
                    System.out.println("\tКонтейнер: " + (container + 1));
                    totalContainers = 1;
                }
            }
        } while (floatingBoxes < box);

        System.out.println("\nГрузовиков: " + track);
        System.out.println("Контейнеров: " + (track == 1 ? totalContainers : (track - 1) * 12 + totalContainers));
    }

    public static void main(String[] args) {
        new TrucksContainersAndBoxes(00);
    }
}
