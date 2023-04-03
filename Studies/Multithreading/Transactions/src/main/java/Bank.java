import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

import static java.lang.Thread.sleep;

public class Bank {

    private Map<String, Account> accounts;
    private final Random RANDOM = new Random();

    private final Map<String, Boolean> BLOCK_ACCOUNT = new HashMap<>();

    public synchronized boolean isFraud()
            throws InterruptedException {
        sleep(1000);
        return RANDOM.nextBoolean();
    }

    /**
     * TODO: реализовать метод. Метод переводит деньги между счетами. Если сумма транзакции > 50000,
     * то после совершения транзакции, она отправляется на проверку Службе Безопасности – вызывается
     * метод isFraud. Если возвращается true, то делается блокировка счетов (как – на ваше
     * усмотрение)
     */

    public void transfer(String fromAccountNum, String toAccountNum, long amount)  {
        if (BLOCK_ACCOUNT.containsKey(fromAccountNum)) {
            System.out.println("The recipient's account is blocked");
            return;
        }
        if (BLOCK_ACCOUNT.containsKey(toAccountNum)) {
            System.out.println("The sender's account is blocked");
            return;
        }

        long fromAccountBalance = getBalance(fromAccountNum);
        long toAccountBalance = getBalance(toAccountNum);

        if (fromAccountBalance < amount) {
            System.out.println("Insufficient funds on the sender's balance");
            return;
        }
        


        try {
            if (amount > 50_000 && isFraud()) {
                BLOCK_ACCOUNT.put(fromAccountNum, true);
                BLOCK_ACCOUNT.put(toAccountNum, true);
                System.out.println("For security purposes, the accounts of the sender and recipient are blocked");
                return;
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        Account fromAccount = accounts.get(fromAccountNum);
        Account toAccount = accounts.get(toAccountNum);
        boolean temporaryBoolean = fromAccount.hashCode() < toAccount.hashCode();

        synchronized (temporaryBoolean ? fromAccount : toAccount) {
            synchronized (!temporaryBoolean ? fromAccount : toAccount) {
                fromAccount.setMoney(fromAccountBalance - amount);
                toAccount.setMoney(toAccountBalance + amount);
            }
        }

        accounts.put(fromAccountNum, fromAccount);
        accounts.put(toAccountNum, toAccount);

        System.out.println("Transaction completed");
//        System.out.println("Money in the bank on all accounts: " + getSumAllAccounts());
    }

    /**
     * TODO: реализовать метод. Возвращает остаток на счёте.
     */
    public long getBalance(String accountNum) {
        return accounts.get(accountNum).getMoney();
    }

    public long getSumAllAccounts() {
        AtomicLong sumBalanceAllAccounts = new AtomicLong();
        for (Map.Entry<String, Account> account : accounts.entrySet()) {
            sumBalanceAllAccounts.addAndGet(getBalance(account.getKey()));
        }
        return sumBalanceAllAccounts.longValue();
    }

    public void setAccounts(Map<String, Account> accounts) {
        this.accounts = accounts;
    }
}
