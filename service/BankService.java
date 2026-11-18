
package service;

import model.BankAccount;
import model.CurrentAccount;
import model.Customer;
import model.SavingsAccount;

public class BankService {

    // Array of objects: stores both SavingsAccount and CurrentAccount
    // using parent-class reference (Polymorphism)
    private BankAccount[] accounts = new BankAccount[100];

    private int accountCount = 0;

    // accountType: 1 = Savings, 2 = Current
    // Returns the new account, or null if creation fails
    public BankAccount createAccount(int accountType, String name,
            String phoneNumber, double openingBalance) {

        if (accountCount == accounts.length) {
            System.out.println("Error: Maximum number of accounts reached.");
            return null;
        }

        if (!isValidName(name) || !isValidPhone(phoneNumber)) {
            System.out.println("Error: Invalid name or phone number.");
            return null;
        }

        if (!Double.isFinite(openingBalance) || openingBalance < 0) {
            System.out.println("Error: Opening balance cannot be negative or non-finite.");
            return null;
        }

        if (accountType != 1 && accountType != 2) {
            System.out.println("Error: Invalid account type.");
            return null;
        }

        if (accountType == 1 && openingBalance < SavingsAccount.MINIMUM_BALANCE) {
            System.out.println("Error: Minimum balance for Savings Account is "
                    + SavingsAccount.MINIMUM_BALANCE);
            return null;
        }

        

        // Create Customer using the static factory method
        Customer customer = Customer.create(name.trim(), phoneNumber);

        // Upcasting: child object stored in parent reference
        BankAccount newAccount;

        if (accountType == 1) {
            newAccount = new SavingsAccount(customer, openingBalance);
        } else {
            newAccount = new CurrentAccount(customer, openingBalance);
        }

        accounts[accountCount] = newAccount;
        accountCount++;

        System.out.println("Account created successfully!");
        System.out.println("Account Number: " + newAccount.getAccountNumber());

        return newAccount;
    }

    // Returns the account with this number, or null if not found
    public BankAccount findAccount(int accountNumber) {

        for (int i = 0; i < accountCount; i++) {

            if (accounts[i].getAccountNumber() == accountNumber) {
                return accounts[i];
            }
        }

        return null;
    }

    // Deposits money into an account
    public void deposit(int accountNumber, double amount) {

        BankAccount account = findAccount(accountNumber);

        if (account == null) {
            System.out.println("Error: Account not found.");
            return;
        }

        if (account.deposit(amount)) {
            System.out.printf("Deposit successful. New balance: %.2f%n",
                    account.getBalance());
        }
    }

    // Withdraws money from an account
    public void withdraw(int accountNumber, double amount) {

        BankAccount account = findAccount(accountNumber);

        if (account == null) {
            System.out.println("Error: Account not found.");
            return;
        }

        // Dynamic Method Dispatch:
        // Java executes SavingsAccount.withdraw() or CurrentAccount.withdraw()
        // depending on the actual object
        if (account.withdraw(amount)) {
            System.out.printf("Withdrawal successful. New balance: %.2f%n",
                    account.getBalance());
        }
    }

    // Transfers money between two accounts
    public void transfer(int fromAccountNumber, int toAccountNumber, double amount) {

        if (fromAccountNumber == toAccountNumber) {
            System.out.println("Error: Sender and receiver cannot be the same account.");
            return;
        }

        BankAccount sender = findAccount(fromAccountNumber);
        BankAccount receiver = findAccount(toAccountNumber);

        if (sender == null) {
            System.out.println("Error: Sender account not found.");
            return;
        }

        if (receiver == null) {
            System.out.println("Error: Receiver account not found.");
            return;
        }

        if (!Double.isFinite(amount) || amount <= 0) {
            System.out.println("Error: Transfer amount must be greater than zero.");
            return;
        }

        // Sender's withdrawal follows its own account rules
        if (sender.withdraw(amount)) {

            if (receiver.deposit(amount)) {
                System.out.printf(
                        "Transfer successful. Sender balance: %.2f | Receiver balance: %.2f%n",
                        sender.getBalance(), receiver.getBalance());
            } else {
                // Refund sender if receiver deposit fails
                sender.deposit(amount);
                System.out.println("Transfer failed. Amount refunded to sender.");
            }
        }
    }

    // Displays account details
    public void displayAccountDetails(int accountNumber) {

        BankAccount account = findAccount(accountNumber);

        if (account == null) {
            System.out.println("Error: Account not found.");
            return;
        }

        // Runs the child class's getDetails() through polymorphism
        System.out.println(account.getDetails());
    }

    // Displays all accounts
    public void displayAllAccounts() {

        if (accountCount == 0) {
            System.out.println("No accounts available.");
            return;
        }

        // Enhanced for loop
        for (int i = 0; i < accountCount; i++) {
            System.out.println(accounts[i]);
        }
    }

    // Displays transaction history
    public void displayTransactionHistory(int accountNumber) {

        BankAccount account = findAccount(accountNumber);

        if (account == null) {
            System.out.println("Error: Account not found.");
            return;
        }

        System.out.println("Transaction history for account " + accountNumber + ":");

        account.displayTransactions();
    }

    // Adds interest to Savings Account only
    public void addInterest(int accountNumber) {

        BankAccount account = findAccount(accountNumber);

        if (account == null) {
            System.out.println("Error: Account not found.");
            return;
        }

        // Downcasting: checks actual object type before casting
        if (account instanceof SavingsAccount) {

            SavingsAccount savingsAccount = (SavingsAccount) account;

            double interest = savingsAccount.addInterest();

            System.out.printf("Interest credited: %.2f%n", interest);
            System.out.printf("New balance: %.2f%n", savingsAccount.getBalance());

        } else {
            System.out.println("Error: Interest can only be added to a Savings Account.");
        }
    }

    // Validates customer name
    private boolean isValidName(String name) {
        return name != null && !name.trim().isEmpty();
    }

    // Validates 10-digit phone number
    private boolean isValidPhone(String phoneNumber) {

        if (phoneNumber == null || phoneNumber.length() != 10) {
            return false;
        }

        for (int i = 0; i < phoneNumber.length(); i++) {

            if (!Character.isDigit(phoneNumber.charAt(i))) {
                return false;
            }
        }

        return true;
    }
}
