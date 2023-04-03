import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class Loader {
    static final char[] letters = {'У', 'К', 'Е', 'Н', 'Х', 'В', 'А', 'Р', 'О', 'С', 'М', 'Т'};
    static final String path = "res/";
    static PrintWriter printWriter;

    public static void main(String[] args) {
        long start = System.currentTimeMillis();

        List<Thread> list = new ArrayList<>();

        for (int regionCode = 1; regionCode <= 50; regionCode++) {
            RunnableTest runnableTest = new RunnableTest(regionCode);
            list.add(new Thread(runnableTest));
        }

        for (Thread thread : list){
            long start1 = System.currentTimeMillis();
            thread.start();
            try {
                thread.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println(System.currentTimeMillis() - start1);
        }

        printWriter.flush();
        printWriter.close();
        System.out.println((System.currentTimeMillis() - start) + " ms");
    }


    static class RunnableTest implements Runnable{
        int regionCode;
        public RunnableTest(int regionCode) {
            this.regionCode = regionCode;
        }

        @Override
        public void run() {
            try {
                numberGenerator(regionCode);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        private static void numberGenerator(int regionCode)
                throws IOException {
            printWriter = new PrintWriter(path + "numbers_reg" + regionCode + ".txt");
            StringBuilder carNumber = new StringBuilder();
            for (int number = 1; number < 1000; number++) {
                for (char firstLetter : letters) {
                    for (char secondLetter : letters) {
                        for (char thirdLetter : letters) {
                            carNumber
                                    .append(firstLetter)
                                    .append(padNumber(number, 3))
                                    .append(secondLetter)
                                    .append(thirdLetter)
                                    .append(padNumber(regionCode, 2))
                                    .append("\t\n");
                        }
                    }
                }
            }
            printWriter.write(String.valueOf(carNumber));

        }

        private static String padNumber(int number, int numberLength) {
            StringBuilder numberStr = new StringBuilder(Integer.toString(number));
            int padSize = numberLength - numberStr.length();

            for (int i = 0; i < padSize; i++) {
                numberStr.insert(0, '0');
            }
            return numberStr.toString();
        }
    }
}
