package atm;

import java.util.Scanner;

/**
 * ATMInterface.java
 * Console-based ATM simulation with full banking operations.
 *
 * Features:
 *  - Secure login with User ID + PIN (max 3 attempts)
 *  - Transaction History
 *  - Withdraw Cash
 *  - Deposit Cash
 *  - Fund Transfer between accounts
 *  - Session logout
 *
 * @author  Megha Kardam
 * @version 1.0
 */
public class ATMInterface {

    private BankDatabase database;
    private Account      currentAccount;
    private Scanner      scanner;

    public ATMInterface() {
        database = new BankDatabase();
        scanner  = new Scanner(System.in);
    }

    private void printHeader(String title) {
        System.out.println();
        System.out.println("================================================");
        System.out.println("  " + title);
        System.out.println("================================================");
    }

    private void printLine() {
        System.out.println("------------------------------------------------");
    }

    private void ok(String msg)  { System.out.println("  [OK]    " + msg); }
    private void err(String msg) { System.out.println("  [ERROR] " + msg); }

    private void pause() {
        System.out.print("\n  Press ENTER to continue...");
        scanner.nextLine();
    }

    private void showWelcomeBanner() {
        System.out.println();
        System.out.println("================================================");
        System.out.println("           JAVA ATM INTERFACE                   ");
        System.out.println("              Version  1.0                      ");
        System.out.println("                                                 ");
        System.out.println("         Developed by : Megha Kardam             ");
        System.out.println("         GitHub : github.com/Megha-kardam99      ");
        System.out.println("================================================");
        System.out.println();
    }

    private boolean login() {
        printHeader("SECURE LOGIN");
        System.out.println();
        System.out.println("  Demo Accounts:");
        System.out.println("  User ID: MK001  PIN: 1234  (Megha Kardam)");
        System.out.println("  User ID: RK002  PIN: 5678  (Rahul Kumar)");
        System.out.println("  User ID: PS003  PIN: 9012  (Priya Sharma)");
        System.out.println();

        int attempts = 0;
        while (attempts < 3) {
            System.out.print("  Enter User ID : ");
            String userId = scanner.nextLine().trim().toUpperCase();
            System.out.print("  Enter PIN     : ");
            String pin = scanner.nextLine().trim();

            if (database.validateUser(userId, pin)) {
                currentAccount = database.getAccount(userId);
                System.out.println();
                ok("Welcome, " + currentAccount.getAccountHolderName() + "!");
                pause();
                return true;
            } else {
                attempts++;
                err("Invalid credentials. Attempts left: " + (3 - attempts));
                System.out.println();
            }
        }
        err("Too many failed attempts. Card blocked.");
        return false;
    }

    private void showMainMenu() {
        printHeader("MAIN MENU  -  " + currentAccount.getAccountHolderName());
        System.out.printf("  Available Balance : Rs.%.2f%n", currentAccount.getBalance());
        printLine();
        System.out.println("  [1]  Transaction History");
        System.out.println("  [2]  Withdraw Cash");
        System.out.println("  [3]  Deposit Cash");
        System.out.println("  [4]  Fund Transfer");
        System.out.println("  [5]  Quit / Logout");
        printLine();
        System.out.print("  Choose option : ");
    }

    private void showTransactionHistory() {
        printHeader("TRANSACTION HISTORY");
        System.out.println("  Account Holder : " + currentAccount.getAccountHolderName());
        System.out.println("  Account ID     : " + currentAccount.getUserId());
        printLine();
        var history = currentAccount.getTransactionHistory();
        if (history.isEmpty()) {
            System.out.println("  No transactions found.");
        } else {
            for (int i = 0; i < history.size(); i++)
                System.out.println("  " + (i + 1) + ".  " + history.get(i));
        }
        printLine();
        System.out.printf("  Current Balance : Rs.%.2f%n", currentAccount.getBalance());
        pause();
    }

    private void withdraw() {
        printHeader("WITHDRAW CASH");
        System.out.printf("  Available Balance : Rs.%.2f%n", currentAccount.getBalance());
        printLine();
        System.out.print("  Enter amount to withdraw (Rs.) : ");
        try {
            double amount = Double.parseDouble(scanner.nextLine().trim());
            if (amount <= 0)            { err("Amount must be greater than zero."); }
            else if (amount % 100 != 0) { err("ATM dispenses in multiples of Rs.100 only."); }
            else if (amount > 50000)    { err("Single transaction limit is Rs.50,000."); }
            else if (currentAccount.withdraw(amount)) {
                ok("Rs." + String.format("%.2f", amount) + " dispensed successfully.");
                System.out.printf("  Remaining Balance : Rs.%.2f%n", currentAccount.getBalance());
            } else { err("Insufficient balance."); }
        } catch (NumberFormatException e) { err("Invalid amount entered."); }
        pause();
    }

    private void deposit() {
        printHeader("DEPOSIT CASH");
        System.out.printf("  Current Balance : Rs.%.2f%n", currentAccount.getBalance());
        printLine();
        System.out.print("  Enter amount to deposit (Rs.) : ");
        try {
            double amount = Double.parseDouble(scanner.nextLine().trim());
            if (amount <= 0)         { err("Amount must be greater than zero."); }
            else if (amount > 200000) { err("Single deposit limit is Rs.2,00,000."); }
            else {
                currentAccount.deposit(amount);
                ok("Rs." + String.format("%.2f", amount) + " deposited successfully.");
                System.out.printf("  New Balance : Rs.%.2f%n", currentAccount.getBalance());
            }
        } catch (NumberFormatException e) { err("Invalid amount entered."); }
        pause();
    }

    private void transfer() {
        printHeader("FUND TRANSFER");
        System.out.printf("  Your Balance : Rs.%.2f%n", currentAccount.getBalance());
        printLine();
        System.out.print("  Enter Beneficiary Account ID : ");
        String targetId = scanner.nextLine().trim().toUpperCase();

        if (targetId.equals(currentAccount.getUserId())) {
            err("Cannot transfer to your own account."); pause(); return;
        }
        Account targetAccount = database.getAccount(targetId);
        if (targetAccount == null) {
            err("Account [" + targetId + "] not found."); pause(); return;
        }
        System.out.println("  Beneficiary : " + targetAccount.getAccountHolderName());
        System.out.print("  Enter amount to transfer (Rs.) : ");
        try {
            double amount = Double.parseDouble(scanner.nextLine().trim());
            if (amount <= 0)          { err("Amount must be greater than zero."); }
            else if (amount > 100000) { err("Single transfer limit is Rs.1,00,000."); }
            else if (currentAccount.transfer(targetAccount, amount)) {
                ok("Rs." + String.format("%.2f", amount) + " transferred to "
                        + targetAccount.getAccountHolderName() + ".");
                System.out.printf("  Your New Balance : Rs.%.2f%n", currentAccount.getBalance());
            } else { err("Insufficient balance."); }
        } catch (NumberFormatException e) { err("Invalid amount entered."); }
        pause();
    }

    private void logout() {
        printHeader("SESSION ENDED");
        System.out.println("  Thank you, " + currentAccount.getAccountHolderName() + "!");
        System.out.println("  Please collect your card. Have a great day!");
        printLine();
        System.out.println("  Developed by : Megha Kardam");
        System.out.println("  GitHub       : github.com/Megha-kardam99");
        printLine();
    }

    public void run() {
        showWelcomeBanner();
        if (!login()) { System.out.println("\n  Exiting. Goodbye!"); scanner.close(); return; }
        boolean running = true;
        while (running) {
            showMainMenu();
            String choice = scanner.nextLine().trim();
            switch (choice) {
                case "1" -> showTransactionHistory();
                case "2" -> withdraw();
                case "3" -> deposit();
                case "4" -> transfer();
                case "5" -> { logout(); running = false; }
                default  -> { err("Invalid option. Choose 1-5."); pause(); }
            }
        }
        scanner.close();
    }

    public static void main(String[] args) {
        new ATMInterface().run();
    }
}
