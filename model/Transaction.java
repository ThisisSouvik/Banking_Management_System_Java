package model;

public class Transaction {

    // final constants: the allowed transaction types (Values can never change)
    public static final String ACCOUNT_OPENED = "ACCOUNT_OPENED";
    public static final String DEPOSIT = "DEPOSIT";
    public static final String WITHDRAWAL = "WITHDRAWAL";
    public static final String INTEREST_CREDITED = "INTEREST_CREDITED";

    //static variable: shared by all Transaction objects, used to generate unique IDs
    private static int nextTransactionId = 1;

    private final int transactionId;
    private final String transactionType;
    private final double amount;
    private final double balanceAfterTransaction;

    public Transaction(String transactionType, double amount, double balanceAfterTransaction) {
        this.transactionId = nextTransactionId++;
        this.transactionType = transactionType;
        this.amount = amount;
        this.balanceAfterTransaction = balanceAfterTransaction;
    }

    public int getTransactionId() {
        return transactionId;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public double getAmount() {
        return amount;
    }

    public double getBalanceAfterTransaction() {
        return balanceAfterTransaction;
    }

    @Override
    public String toString(){
        return String.format("ID: %-4d | %-18s | Amount: %10.2f | Balance: %10.2f",
                transactionId, transactionType, amount, balanceAfterTransaction);
        
    }
    @Override 
    public boolean equals(Object obj){
        if (this == obj)
            return true;

        if (obj == null || getClass() != obj.getClass())
            return false;

        Transaction transaction = (Transaction) obj;
        return transactionId == transaction.transactionId;
    }

    @Override 
    public int hashCode(){
        return Integer.hashCode(transactionId);
    }
}
