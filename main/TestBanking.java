package main;

import model.Customer;
import model.Transaction;

public class TestBanking {

    public static void main(String[] args) {

        System.out.println("==================================");
        System.out.println("   BANKING MANAGEMENT SYSTEM");
        System.out.println("        TESTING MODULE");
        System.out.println("==================================");

        // ==================================
        // TEST 1: CUSTOMER
        // ==================================

        System.out.println("\n--- CUSTOMER TESTING ---");

        // Customer testing will be added after
        // introducing a customer creation method.

        System.out.println("Customer class compiled successfully.");
        System.out.println("Object creation test: Pending");

        // ==================================
        // TEST 2: TRANSACTION
        // ==================================

        System.out.println("\n--- TRANSACTION TESTING ---");

        Transaction t1 = new Transaction(
                Transaction.DEPOSIT, 5000, 5000);

        Transaction t2 = new Transaction(
                Transaction.WITHDRAWAL, 1000, 4000);

        // Test transaction IDs
        System.out.println("\nTransaction IDs:");

        System.out.println("Transaction 1: " + t1.getTransactionId());
        System.out.println("Transaction 2: " + t2.getTransactionId());

        // Test getters
        System.out.println("\nTransaction Details:");

        System.out.println(t1);
        System.out.println(t2);

        // Test equals()
        System.out.println("\nEquality Test:");

        System.out.println("Are t1 and t2 equal? " + t1.equals(t2));

        // Test hashCode()
        System.out.println("\nHashCode Test:");

        System.out.println("t1 HashCode: " + t1.hashCode());
        System.out.println("t2 HashCode: " + t2.hashCode());

        System.out.println("\n==================================");
        System.out.println("       TESTING COMPLETED");
        System.out.println("==================================");
    }
}
