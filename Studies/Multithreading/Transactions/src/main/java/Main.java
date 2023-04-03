import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Main {
    static Map<String,Account> accounts = new HashMap<>();
    static int lowerBound = 20_000;
    static int upperBound = 99_999;
    static Account accountOne;
    static Account accountTwo;
    static Account accountThree;
    static Account accountFour;

    public static void main(String[] args) {
        generateAccounts();
        Bank bank = new Bank();
        bank.setAccounts(accounts);

        new Thread(() -> bank.transfer(accountOne.getAccNumber(), accountTwo.getAccNumber(), 51000)).start();
        new Thread(() -> bank.transfer(accountThree.getAccNumber(),accountOne.getAccNumber(),5000)).start();
        new Thread(() -> bank.transfer(accountTwo.getAccNumber(),accountFour.getAccNumber(),30000)).start();
        new Thread(() -> bank.transfer(accountFour.getAccNumber(),accountThree.getAccNumber(),51000)).start();
    }

    private static void generateAccounts() {
        for (int i = 0; i < 100; i++){
            Random random = new Random();
            int randomInt = random.nextInt(upperBound - lowerBound + 1) + lowerBound;

            StringBuilder randomStringBilder = new StringBuilder();

            for (int j = 0; j < 10; j++) {
                String alphabet = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
                Random r = new Random();
                char c = alphabet.charAt(r.nextInt(alphabet.length()));
                randomStringBilder.append(c);
            }

            Account generateAccount = new Account();
            String randomString = randomStringBilder.toString();

            generateAccount.setMoney(randomInt);
            generateAccount.setAccNumber(randomString);

            switch (i){
                case 2 -> accountOne = generateAccount;
                case 15 -> accountTwo = generateAccount;
                case 40 -> accountThree = generateAccount;
                case 97 -> accountFour = generateAccount;
            }
            accounts.put(randomString, generateAccount);
        }
    }
}