# ATM Interface — Java Console Application

**Developer:** Megha Kardam  
**GitHub:** [Megha-kardam99](https://github.com/Megha-kardam99)  
**Language:** Java 17  
**Type:** Personal Project  

---

## About

A fully functional console-based ATM simulation built in Core Java.  
Demonstrates OOP concepts — Classes, Encapsulation, HashMap, ArrayList.

---

## Features

- Secure Login with User ID + PIN (3 attempt limit)
- Transaction History — all credits, debits, transfers
- Withdraw Cash — multiples of Rs.100, max Rs.50,000
- Deposit Cash — max Rs.2,00,000
- Fund Transfer between accounts — max Rs.1,00,000
- Session logout

---

## Project Structure

The project has 3 Java files inside src/atm/

- Account.java — Account model storing balance and transaction history
- BankDatabase.java — In-memory database using HashMap to store all accounts
- ATMInterface.java — Main class with all ATM menus and operations (entry point)

---

## How to Run in IntelliJ

1. Open IntelliJ IDEA
2. File → Open → select this project folder
3. Right-click the src folder → Mark Directory As → Sources Root
4. Open ATMInterface.java → click the green Run button next to main method

---

## Demo Accounts

| User ID | PIN  | Name          | Balance       |
|---------|------|---------------|---------------|
| MK001   | 1234 | Megha Kardam  | Rs.50,000     |
| RK002   | 5678 | Rahul Kumar   | Rs.75,000     |
| PS003   | 9012 | Priya Sharma  | Rs.1,20,000   |

---

## OOP Concepts Used

| Concept | Where used |
|---|---|
| Classes and Objects | Account, BankDatabase, ATMInterface |
| Encapsulation | All fields are private, accessed via getters |
| Constructor | Account() initializes balance and history |
| Collections | HashMap for accounts, ArrayList for history |

---

## Tech Stack

- Java 17
- IntelliJ IDEA Community Edition
- Core Java — no external libraries needed
