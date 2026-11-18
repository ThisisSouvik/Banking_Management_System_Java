
package app;

import model.BankAccount;
import service.BankService;
import java.util.Scanner;

public class BankingApplication {

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        BankService bankService = new BankService();
        int choice;

        do {
            showMenu();

            choice = readInt("Enter your choice: ");

            switch (choice) {

                case 1:
                    createAccount(bankService);
                    break;

                case 2:
                    int depositAccount = readInt("Enter account number: ");
                    double depositAmount = readDouble("Enter deposit amount: ");

                    bankService.deposit(depositAccount, depositAmount);
                    break;

                case 3:
                    int withdrawAccount = readInt("Enter account number: ");
                    double withdrawAmount = readDouble("Enter withdrawal amount: ");

                    bankService.withdraw(withdrawAccount, withdrawAmount);
                    break;

                case 4:
                    int fromAccount = readInt("Enter sender account number: ");
                    int toAccount = readInt("Enter receiver account number: ");
                    double transferAmount = readDouble("Enter transfer amount: ");

                    bankService.transfer(fromAccount, toAccount, transferAmount);
                    break;

                case 5:
                    int accountNumber = readInt("Enter account number: ");
                    bankService.displayAccountDetails(accountNumber);
                    break;

                case 6:
                    int transactionAccount = readInt("Enter account number: ");
                    bankService.displayTransactionHistory(transactionAccount);
                    break;

                case 7:
                    bankService.displayAllAccounts();
                    break;

                case 8:
                    int savingsAccount = readInt("Enter savings account number: ");
                    bankService.addInterest(savingsAccount);
                    break;

                case 9:
                    System.out.println("\nThank you for using the Banking Management System. Goodbye!");
                    break;

                default:
                    System.out.println("Invalid choice. Please enter a number from 1 to 9.");
            }

        } while (choice != 9);

        scanner.close();
    }

    // Displays the main menu
    private static void showMenu() {

        System.out.println();
        System.out.println("========================================");
        System.out.println("       BANKING MANAGEMENT SYSTEM");
        System.out.println("========================================");

        System.out.println("1. Create Account");
        System.out.println("2. Deposit Money");
        System.out.println("3. Withdraw Money");
        System.out.println("4. Transfer Money");
        System.out.println("5. View Account Details");
        System.out.println("6. View Transaction History");
        System.out.println("7. View All Accounts");
        System.out.println("8. Add Interest to Savings Account");
        System.out.println("9. Exit");

        System.out.println("========================================");
    }

    // Creates a new bank account
    private static void createAccount(BankService bankService) {

        System.out.println("\n----- CREATE NEW ACCOUNT -----");

        System.out.println("Account Type:");
        System.out.println("1. Savings Account");
        System.out.println("2. Current Account");

        int accountType = readInt("Enter account type: ");

        System.out.print("Enter customer name: ");
        String name = scanner.nextLine().trim();

        System.out.print("Enter phone number (10 digits): ");
        String phoneNumber = scanner.nextLine().trim();

        double openingBalance = readDouble("Enter opening balance: ");

        BankAccount account = bankService.createAccount(
                accountType,
                name,
                phoneNumber,
                openingBalance
        );

        if (account != null) {
            System.out.println("\nAccount created successfully!");
            System.out.println("----------------------------------------");
            System.out.println(account.getDetails());
            System.out.println("----------------------------------------");
        }
    }

    // Reads an integer from the user
    private static int readInt(String message) {

        System.out.print(message);

        while (!scanner.hasNextInt()) {

            System.out.println("Invalid input! Please enter a whole number.");
            scanner.next();

            System.out.print(message);
        }

        int value = scanner.nextInt();

        scanner.nextLine(); // Clears the leftover newline

        return value;
    }

    // Reads a double value from the user
    private static double readDouble(String message) {

        System.out.print(message);

        while (!scanner.hasNextDouble()) {

            System.out.println("Invalid input! Please enter a valid number.");
            scanner.next();

            System.out.print(message);
        }

        double value = scanner.nextDouble();

        scanner.nextLine(); // Clears the leftover newline

        return value;
    }
}
