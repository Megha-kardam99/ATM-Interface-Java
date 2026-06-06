package atm;

import java.util.HashMap;
import java.util.Map;

/**
 * BankDatabase.java
 * Simulates a bank database with pre-loaded user accounts.
 * In a production system this would connect to a real database via JDBC.
 *
 * @author  Megha Kardam
 * @version 1.0
 */
public class BankDatabase {

    private Map<String, Account> accounts;

    public BankDatabase() {
        accounts = new HashMap<>();
        accounts.put("MK001", new Account("MK001", "1234", "Megha Kardam",   50000.00));
        accounts.put("RK002", new Account("RK002", "5678", "Rahul Kumar",    75000.00));
        accounts.put("PS003", new Account("PS003", "9012", "Priya Sharma",  120000.00));
    }

    public Account getAccount(String userId) {
        return accounts.get(userId);
    }

    public boolean validateUser(String userId, String pin) {
        Account acc = accounts.get(userId);
        return acc != null && acc.getUserPin().equals(pin);
    }

    public Map<String, Account> getAllAccounts() {
        return accounts;
    }
}
