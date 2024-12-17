package com.example.banking;

import java.util.HashMap;
import java.util.Scanner;

public class SimpleBankingApp {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        HashMap<String, BankAccount> accounts = new HashMap<>();

        System.out.println("Welcome to the Simple Banking Application!");
        while (true) {
            System.out.println("\nChoose an option:");
            System.out.println("1. Create Account");
            System.out.println("2. Deposit");
            System.out.println("3. Withdraw");
            System.out.println("4. Check Balance");
            System.out.println("5. Exit");
            System.out.print("Your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> {
                    System.out.print("Enter account number: ");
                    String accountNumber = scanner.nextLine();
                    if (accounts.containsKey(accountNumber)) {
                        System.out.println("Account number already exists.");
                    } else {
                        System.out.print("Enter account holder name: ");
                        String accountHolderName = scanner.nextLine();
                        accounts.put(accountNumber, new BankAccount(accountNumber, accountHolderName));
                        System.out.println("Account created successfully.");
                    }
                }

                case 2 -> {
                    System.out.print("Enter account number: ");
                    String accountNumber = scanner.nextLine();
                    BankAccount account = accounts.get(accountNumber);
                    if (account != null) {
                        System.out.print("Enter deposit amount: ");
                        double depositAmount = scanner.nextDouble();
                        account.deposit(depositAmount);
                    } else {
                        System.out.println("Account not found.");
                    }
                }

                case 3 -> {
                    System.out.print("Enter account number: ");
                    String accountNumber = scanner.nextLine();
                    BankAccount account = accounts.get(accountNumber);
                    if (account != null) {
                        System.out.print("Enter withdrawal amount: ");
                        double withdrawalAmount = scanner.nextDouble();
                        account.withdraw(withdrawalAmount);
                    } else {
                        System.out.println("Account not found.");
                    }
                }

                case 4 -> {
                    System.out.print("Enter account number: ");
                    String accountNumber = scanner.nextLine();
                    BankAccount account = accounts.get(accountNumber);
                    if (account != null) {
                        account.displayDetails();
                    } else {
                        System.out.println("Account not found.");
                    }
                }

                case 5 -> {
                    System.out.println("Thank you for using the Simple Banking Application. Goodbye!");
                    scanner.close();
                    return;
                }

                default -> System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}