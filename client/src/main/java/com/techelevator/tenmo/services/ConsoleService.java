package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.Transaction;

import java.sql.SQLOutput;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class ConsoleService {

    private final Scanner scanner = new Scanner(System.in);


    public void printLoginMenu(){
        System.out.println();
        System.out.println("----Welcome to Tenmo---");
        System.out.println("1: Login");
        System.out.println("2: Register");
        System.out.println("0: Exit");
        System.out.println();
    }

    public void printMainMenu(String name, double balance){
        System.out.println();
        System.out.println("----Welcome " + name + " !----");
        System.out.println("Your current balance is: $" + balance);
        System.out.println();
        System.out.println("--What would you like to do?--");
        System.out.println("1. Send money to other user");
        System.out.println("2. View previous transfers");
        System.out.println("3. Request money from other user");
        System.out.println("4. View pending requests");
        System.out.println("0. Exit");
    }

    public String getUsername(){
        System.out.println();
        System.out.print("Please enter username: ");
        return scanner.nextLine();
    }

    public String getPassword(){
        System.out.println();
        System.out.print("Please enter password: ");
        return scanner.nextLine();
    }

    public String sendMoney(List<String> users){
        System.out.println();
        System.out.println("-----------------------");
        System.out.println("Users");
        System.out.println(String.format("%-15s", "User #") + "Username");
        System.out.println("-----------------------");
        int count = 1;
       for (String username : users){
           System.out.println(String.format("%-15s", count) + username);
           count += 1;
       }
        System.out.println();
        System.out.print("Please choose a User # from the above list (Enter 0 to exit)");
        return promptForUserId(users);
    }
    public double getAmountToSend(){
        System.out.println("Please enter an amount to send this user: ");
        return promptForAmountOfMoney();
    }

    public void showTransactions(List<Transaction> transactions, String username){
        System.out.println();
        System.out.println("-----------------------");
        System.out.println("Transactions");
        System.out.println(String.format("%-10s", "ID") + String.format("%-25s", "From/To") + "Amount");
        System.out.println("-----------------------");
        for (Transaction transaction : transactions){
            if (username.equalsIgnoreCase(transaction.getReceiverUsername())) {
                System.out.println(String.format("%-10s", transaction.getTransactionId()) + String.format("%-25s", "From: " + transaction.getSenderUsername() )
                        + "$" + transaction.getMoneySent());
            }
            if (username.equalsIgnoreCase(transaction.getSenderUsername())) {
                System.out.println(String.format("%-10s", transaction.getTransactionId()) + String.format("%-25s", "To: " + transaction.getReceiverUsername() )
                        + "$" + transaction.getMoneySent());
            }
        }
        System.out.println("------");
        System.out.print("Please enter a transaction ID to see details (Enter 0 to cancel): ");
        int transactionId = promptForTransactionId();
        for (Transaction transaction : transactions){
            if(transaction.getTransactionId() == transactionId){
                showTransactionDetails(transaction);
            }
        }

    }

    public String requestMoney(List<String> users){
        System.out.println();
        System.out.println("-----------------------");
        System.out.println("Users");
        System.out.println(String.format("%-15s", "User #") + "Username");
        System.out.println("-----------------------");
        int count = 1;
        for (String username : users){
            System.out.println(String.format("%-15s", count) + username);
            count += 1;
        }
        System.out.println();
        System.out.print("Please choose a User ID from the above list to request money from (Enter 0 to exit)");
        return promptForUserId(users);
    }

    public double getAmountToRequest(){
        System.out.println("Please enter an amount to request from this user: ");
        return promptForAmountOfMoney();
    }

    public Transaction viewPendingRequest(List<Transaction> transactions, String username){
        System.out.println();
        System.out.println("-----------------------");
        System.out.println("Pending Transactions");
        System.out.println(String.format("%-10s", "ID") + String.format("%-25s", "From/To") + "Amount");
        System.out.println("-----------------------");
        for (Transaction transaction : transactions) {
            if (transaction.getStatus().equalsIgnoreCase("Pending")) {
                if (username.equalsIgnoreCase(transaction.getReceiverUsername())) {
                    System.out.println(String.format("%-10s", transaction.getTransactionId()) + String.format("%-25s", "From: " + transaction.getSenderUsername())
                            + "$" + transaction.getMoneySent());
                }
                if (username.equalsIgnoreCase(transaction.getSenderUsername())) {
                    System.out.println(String.format("%-10s", transaction.getTransactionId()) + String.format("%-25s", "To: " + transaction.getReceiverUsername())
                            + "$" + transaction.getMoneySent());
                }
            }
        }
        System.out.println("------");
        System.out.print("Please enter a transaction ID to Approve or Reject (Enter 0 to cancel): ");
        int transactionId = promptForTransactionId();
        for (Transaction transaction : transactions){
            if(transaction.getTransactionId() == transactionId){
                return transaction;
            }
        }
        return null;
    }


    public void approveRejectTransaction(int transactionId){
        System.out.println("-----------");
        System.out.println("1. Approve");
        System.out.println("2. Reject");
        System.out.println("0. Exit");
        System.out.println("-----------");
        System.out.print("Please choose an option: ");
    }

    public void showTransactionDetails(Transaction transaction){
        System.out.println("-----------------------");
        System.out.println("Transaction Details");
        System.out.println("-----------------------");
        System.out.println("ID: " + transaction.getTransactionId());
        System.out.println("From: " + transaction.getSenderUsername());
        System.out.println("TO: " + transaction.getReceiverUsername());
        System.out.println("Status: " + transaction.getStatus());
        System.out.println("Amount: " + transaction.getMoneySent());
        System.out.println("------------");
    }

    public int promptForMenuSelection(){
        int menuSelection;
        try {
            menuSelection = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            menuSelection = -1;
        }
        return menuSelection;
    }

    private String promptForUserId(List<String> users){
        try {
            return users.get(Integer.parseInt(scanner.nextLine()));
        } catch (NumberFormatException e){
            System.out.println("Invalid User #. Please try again.");
            promptForUserId(users);
        }
        return null;
    }

    private double promptForAmountOfMoney(){
        try {
            return Double.parseDouble(scanner.nextLine());
        } catch (NumberFormatException e){
            System.out.println("Invalid amount entered. Please try again");
            promptForAmountOfMoney();
        }
        return 0.0;
    }

    private int promptForTransactionId(){
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e){
            System.out.println("Not a valid ID. Please Try again");
            promptForTransactionId();
        }
        return 0;
    }



}
