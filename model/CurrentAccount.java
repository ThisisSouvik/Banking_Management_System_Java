
package model;

public class CurrentAccount extends BankAccount {

    public static final double OVERDRAFT_LIMIT = 5000;

    public CurrentAccount(Customer customer, double initialBalance) {
        super(customer, initialBalance);
    }

    @Override
    public String getAccountType() {
        return "Current";
    }

    // Method overriding: allows overdraft up to the specified limit
    @Override
    public boolean withdraw(double amount) {

        if (!Double.isFinite(amount) || amount <= 0) {
            System.out.println("Error: Withdrawal amount must be greater than 0.");
            return false;
        }

        if (amount > getBalance() + OVERDRAFT_LIMIT) {
            System.out.println("Error: Withdrawal exceeds overdraft limit of "
                    + OVERDRAFT_LIMIT);
            return false;
        }

        applyDebit(amount, Transaction.WITHDRAWAL);
        return true;
    }

    @Override
    public String getDetails() {

        return super.getDetails() + "\n"
                + "Overdraft Limit: " + OVERDRAFT_LIMIT + "\n"
                + String.format("Available Funds: %.2f",
                        getBalance() + OVERDRAFT_LIMIT);
    }
}
