
package main;

import model.Transaction;

public class TestBanking {

    public static void main(String[] args) {

        Transaction t1 = new Transaction(
                Transaction.DEPOSIT, 5000, 5000);

        Transaction t2 = new Transaction(
                Transaction.WITHDRAWAL, 1000, 4000);

        System.out.println("Banking Transaction Test");
        System.out.println("------------------------");

        System.out.println(t1);
        System.out.println(t2);

        System.out.println("\nTransaction IDs:");
        System.out.println(t1.getTransactionId());
        System.out.println(t2.getTransactionId());

        System.out.println("\nEquality Check:");
        System.out.println(t1.equals(t2));
    }
}
