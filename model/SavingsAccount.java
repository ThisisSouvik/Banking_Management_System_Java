package model;

public class SavingsAccount extends BankAccount {

    public static final double MINIMUM_BALANCE = 1000;
    public static final double INTEREST_RATE = 4.0;

    public SavingsAccount(Customer customer, double initialBalance) {
        super(customer, validateInitialBalance(initialBalance));
    }

    // Validates the minimum balance required to open a savings account
    private static double validateInitialBalance(double initialBalance) {

        if (initialBalance < MINIMUM_BALANCE) {
            throw new IllegalArgumentException(
                    "Minimum balance required to open a Savings Account is "
                            + MINIMUM_BALANCE);
        }

        return initialBalance;
    }

    @Override
    public String getAccountType() {
        return "Savings";
    }

    // Method overriding: enforces the minimum balance rule
    // Reuses the parent's validation through super
    @Override
    public boolean withdraw(double amount) {

        // Let the parent handle invalid amounts and insufficient funds
        if (!Double.isFinite(amount) || amount <= 0 || amount > getBalance()) {
            return super.withdraw(amount);
        }

        if (getBalance() - amount < MINIMUM_BALANCE) {
            System.out.println(
                    "Error: Savings account must maintain a minimum balance of "
                            + MINIMUM_BALANCE);
            return false;
        }

        return super.withdraw(amount);
    }

    // Savings-only feature: calculates and credits interest
    // Returns the interest credited
    public double addInterest() {

        double interest = getBalance() * INTEREST_RATE / 100;

        if (interest <= 0) {
            return 0;
        }

        applyCredit(interest, Transaction.INTEREST_CREDITED);

        return interest;
    }

    @Override
    public String getDetails() {

        return super.getDetails() + "\n"
                + "Minimum Balance: " + MINIMUM_BALANCE + "\n"
                + "Interest Rate: " + INTEREST_RATE + "%";
    }
}