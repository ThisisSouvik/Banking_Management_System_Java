
package model;

public class BankAccount {

    private static final int MAX_TRANSACTIONS = 100;

    // Static variable: generates a unique account number
    private static int nextAccountNumber = 1001;

    private final int accountNumber;
    private Customer customer;
    private double balance;

    private Transaction[] transactions = new Transaction[MAX_TRANSACTIONS];
    private int transactionCount = 0;

    public BankAccount(Customer customer, double initialBalance) {

        if (customer == null) {
            throw new IllegalArgumentException("Customer cannot be null.");
        }

        if (!Double.isFinite(initialBalance) || initialBalance < 0) {
            throw new IllegalArgumentException("Initial balance cannot be negative or non-finite.");
        }

        this.accountNumber = nextAccountNumber++;
        this.customer = customer;
        this.balance = initialBalance;

        // Record the initial account opening transaction
        addTransaction(Transaction.ACCOUNT_OPENED, initialBalance);
    }

    public int getAccountNumber() {
        return accountNumber;
    }

    public Customer getCustomer() {
        return customer;
    }

    public double getBalance() {
        return balance;
    }

    public int getTransactionCount() {
        return transactionCount;
    }

    // Returns "Savings" / "Current"; overridden in child classes
    public String getAccountType() {
        return "Bank";
    }

    // Returns true if the deposit succeeded
    public boolean deposit(double amount) {

        if (!Double.isFinite(amount) || amount <= 0) {
            System.out.println("Error: Deposit amount must be greater than 0.");
            return false;
        }

        applyCredit(amount, Transaction.DEPOSIT);
        return true;
    }

    // Child classes override this method with their own rules
    // Returns true if the withdrawal succeeded
    public boolean withdraw(double amount) {

        if (!Double.isFinite(amount) || amount <= 0) {
            System.out.println("Error: Withdrawal amount must be greater than 0.");
            return false;
        }

        if (amount > balance) {
            System.out.println("Error: Insufficient funds for withdrawal.");
            return false;
        }

        applyDebit(amount, Transaction.WITHDRAWAL);
        return true;
    }

    // Protected: usable by this class and its child classes
    // Children use these methods instead of accessing balance directly

    protected void applyCredit(double amount, String transactionType) {
        balance += amount;
        addTransaction(transactionType, amount);
    }

    protected void applyDebit(double amount, String transactionType) {
        balance -= amount;
        addTransaction(transactionType, amount);
    }

    // Stores a transaction; if the array is full, the oldest is dropped
    private void addTransaction(String transactionType, double amount) {

        if (transactionCount == MAX_TRANSACTIONS) {

            for (int i = 1; i < MAX_TRANSACTIONS; i++) {
                transactions[i - 1] = transactions[i];
            }

            transactionCount--;
        }

        transactions[transactionCount++] =
                new Transaction(transactionType, amount, balance);
    }

    public String getDetails() {

        return "Account Number : " + accountNumber + "\n"
                + "Account Type   : " + getAccountType() + "\n"
                + "Customer Name  : " + customer.getName() + "\n"
                + "Phone Number   : " + customer.getPhoneNumber() + "\n"
                + String.format("Balance        : %.2f\n", balance);
    }

    public void displayTransactions() {
        displayTransactions(transactionCount);
    }

    // Method overloading: displays the last 'count' transactions
    public void displayTransactions(int count) {

        if (transactionCount == 0) {
            System.out.println("No transactions to display.");
            return;
        }

        if (count <= 0) {
            System.out.println("Please enter a valid transaction count.");
            return;
        }

        if (count > transactionCount) {
            count = transactionCount;
        }

        for (int i = transactionCount - count; i < transactionCount; i++) {
            System.out.println(transactions[i]);
        }
    }

    // One-line summary, used when listing all accounts
    @Override
    public String toString() {

        return String.format("Account %d | %-8s | %-15s | Balance: %10.2f",
                accountNumber, getAccountType(), customer.getName(), balance);
    }

    // Two accounts are considered equal if they have the same account number
    @Override
    public boolean equals(Object obj) {

        if (this == obj)
            return true;

        if (obj == null || getClass() != obj.getClass())
            return false;

        BankAccount account = (BankAccount) obj;
        return accountNumber == account.accountNumber;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(accountNumber);
    }
}
