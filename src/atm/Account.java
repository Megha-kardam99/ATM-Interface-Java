package atm;

import java.util.ArrayList;
import java.util.List;

/**
 * Account.java
 * Represents a bank account with all associated data and transaction history.
 * Author: Megha Kardam
 */
public class Account {

    private String userId;
    private String userPin;
    private String accountHolderName;
    private double balance;
    private List<String> transactionHistory;

    public Account(String userId, String userPin, String accountHolderName, double initialBalance) {
        this.userId = userId;
        this.userPin = userPin;
        this.accountHolderName = accountHolderName;
        this.balance = initialBalance;
        this.transactionHistory = new ArrayList<>();
        transactionHistory.add("Account opened with initial balance: ₹" + String.format("%.2f", initialBalance));
    }

    // Getters
    public String getUserId() { return userId; }
    public String getUserPin() { return userPin; }
    public String getAccountHolderName() { return accountHolderName; }
    public double getBalance() { return balance; }
    public List<String> getTransactionHistory() { return transactionHistory; }

    public void deposit(double amount) {
        balance += amount;
        transactionHistory.add("CREDIT  | ₹" + String.format("%.2f", amount)
                + " | Balance: ₹" + String.format("%.2f", balance));
    }

    public boolean withdraw(double amount) {
        if (amount > balance) return false;
        balance -= amount;
        transactionHistory.add("DEBIT   | ₹" + String.format("%.2f", amount)
                + " | Balance: ₹" + String.format("%.2f", balance));
        return true;
    }

    public boolean transfer(Account target, double amount) {
        if (amount > balance) return false;
        balance -= amount;
        target.deposit(amount);
        transactionHistory.add("TRANSFER OUT | ₹" + String.format("%.2f", amount)
                + " to [" + target.getUserId() + "] | Balance: ₹" + String.format("%.2f", balance));
        target.getTransactionHistory().remove(target.getTransactionHistory().size() - 1);
        target.getTransactionHistory().add("TRANSFER IN  | ₹" + String.format("%.2f", amount)
                + " from [" + this.userId + "] | Balance: ₹" + String.format("%.2f", target.getBalance()));
        return true;
    }
}
