package main;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class BankingSystem {
    private static final List<User> users = new ArrayList<>();
    private static User loggedInUser;

    //Main Method
    public static void startBankingSystem(){
        loadUsers();
        loadTransactions();
        Scanner scan = new Scanner(System.in);

        printHeader("Welcome to the Banking Information System");
        while(true){
            if(loggedInUser == null){
                printFooter();
                System.out.println("| 1 | Register User");
                System.out.println("| 2 | Login User");
                System.out.println("| 3 | Exit");
            }else{
                printFooter();
                System.out.println("| 1 | View My Details");
                System.out.println("| 2 | Deposit Money");
                System.out.println("| 3 | Withdraw Money");
                System.out.println("| 4 | Transfer Funds");
                System.out.println("| 5 | View Account Statement");
                System.out.println("| 6 | Logout User");
            }
            printFooter();
            System.out.println("Select an option : ");
            int ch = scan.nextInt();
            scan.nextLine();

            if(loggedInUser == null) {
                switch (ch) {
                    case 1 -> registerUser(scan);
                    case 2 -> login(scan);
                    case 3 -> {
                        System.out.println("Exiting the system...");
                        saveUsers();
                        saveTransactions();
                        scan.close();
                        return;
                    }
                    default -> System.out.println("Invalid Choice. Please try again!");
                }
            }else{
                switch(ch){
                    case 1 -> displayUser();
                    case 2 -> depositMoney(scan);
                    case 3 -> withdrawMoney(scan);
                    case 4 -> transferFunds(scan);
                    case 5 -> viewAccountStatement(scan);
                    case 6 -> {
                        loggedInUser = null;
                        System.out.println("Logged out successfully!");
                    }
                    default -> System.out.println("Invalid Choice. Please try again!");
                }
            }
        }
    }

    //Method for finding the user
    private static User findUser(int accountNumber){
        for(User user : users){
            if(user.getAccountNumber() == accountNumber){
                return user;
            }
        }
        return null;
    }

    //Method for new user registration
    private static void registerUser(Scanner scan){
        System.out.println("Enter Name : ");
        String name = scan.nextLine();

        System.out.println("Enter Address : ");
        String address = scan.nextLine();

        System.out.println("Enter Contact : ");
        String contact = scan.nextLine();

        System.out.println("Enter Password : ");
        String password = scan.nextLine();

        System.out.println("Enter Initial Deposit : ");
        double initialDeposit = scan.nextDouble();

        User user = new User(name, address, contact, password, initialDeposit);
        users.add(user);

        System.out.println("User Registration Successful!");
        System.out.println(user);
    }

    //Method for user login
    private static void login(Scanner scan){
        System.out.println("Enter Account Number : ");
        int accountNumber = scan.nextInt();
        scan.nextLine();

        User user = findUser(accountNumber);
        if(user == null){
            System.out.println("Account Not Found!");
            return;
        }

        System.out.println("Enter Password : ");
        String password = scan.nextLine();

        if(user.validatePassword(password)){
            loggedInUser = user;
            System.out.println("Login Successful! Welcome "+user.getName());
        }else{
            System.out.println("Invalid Password! Login Failed.");
        }
    }

    //Method for displaying user details
    private static void displayUser(){
        if(loggedInUser != null){
            System.out.println("Your Account Details : ");
            System.out.println(loggedInUser);
        }else{
            System.out.println("No user logged in.");
        }
    }

    //Method for money deposit
    private static void depositMoney(Scanner scan){
        System.out.println("Enter Account Number : ");
        int accountNumber = scan.nextInt();

        User user = findUser(accountNumber);
        if(user == null){
            System.out.println("Account not found.");
            return;
        }
        System.out.println("Enter amount to deposit : ");
        double amount = scan.nextDouble();

        if(amount < 0){
            System.out.println("Invalid amount. Please enter the positive value.");
            return;
        }
        user.deposit(amount);
        System.out.println("Amount deposited successfully.\nBalance : "+user.getBalance());
    }

    //Method for money withdrawal
    private static void withdrawMoney(Scanner scan){
        System.out.println("Enter Account Number : ");
        int accountNumber = scan.nextInt();

        User user = findUser(accountNumber);
        if(user == null){
            System.out.println("Account not found.");
            return;
        }
        System.out.println("Enter amount to withdraw : ");
        double amount = scan.nextDouble();

        if(amount <= 0){
            System.out.println("Invalid amount. Please enter the positive value.");
            return;
        }

        if(user.withdraw(amount)){
            System.out.println("Amount withdrawal successfully.\nBalance : "+user.getBalance());
        }else{
            System.out.println("Insufficient Balance. Withdraw failed.");
        }
    }

    //Method for funds transfer
    private static void transferFunds(Scanner scan){
        System.out.println("Enter senders account number : ");
        int senderAccountNumber = scan.nextInt();

        User sender = findUser(senderAccountNumber);
        if(sender == null){
            System.out.println("Sender account not found!");
            return;
        }

        System.out.println("Enter receivers account number : ");
        int receiverAccountNumber = scan.nextInt();

        User receiver = findUser(receiverAccountNumber);
        if(receiver == null){
            System.out.println("Receiver account not found!");
            return;
        }

        System.out.println("Enter amount to transfer : ");
        double amount = scan.nextDouble();

        if(amount <= 0){
            System.out.println("Invalid amount. Please enter positive value.");
            return;
        }

        if(sender.withdraw(amount)){
            receiver.deposit(amount);
            System.out.println("Funds Transfer Successful!");
            System.out.println("Senders updated balance : "+sender.getBalance());
            System.out.println("Receivers updated balance : "+receiver.getBalance());
        }else{
            System.out.println("Funds transfer failed! Insufficient balance in senders account.");
        }
    }

    //Method for viewing account statement
    private static void viewAccountStatement(Scanner scan){
        System.out.println("Enter account number : ");
        int accountNumber = scan.nextInt();

        User user = findUser(accountNumber);
        if(user == null){
            System.out.println("Account not found!");
            return;
        }

        printHeader("Transaction History for Account Number : "+accountNumber);
        List<String> history = user.getTransactionHistory();
        if(history.isEmpty()) {
            System.out.println("No transactions found.");
        }else{
            for(String transaction : history){
                System.out.println("| "+transaction);
                printSeparator();
            }
        }
        printFooter();
    }

    //Method for loading user data in text file
    private static void loadUsers(){
        try(BufferedReader br = new BufferedReader(new FileReader("src/data/users.txt"))){
            String line;
            while((line = br.readLine()) != null){
                String[] parts = line.split(",");
                int accountNumber = Integer.parseInt(parts[0]);
                String name = parts[1];
                String address = parts[2];
                String contact = parts[3];
                String hashedPassword = parts[4];
                double balance = Double.parseDouble(parts[5]);

                User user = new User(name, address, contact, hashedPassword, balance);
                users.add(user);
            }
        }catch(IOException e){
            System.out.println("No existing data found.");
        }
    }

    //Method for loading transactions data in text file
    private static void loadTransactions(){
        try(BufferedReader br = new BufferedReader(new FileReader("src/data/transactions.txt"))){
            String line;
            while((line = br.readLine()) != null){
                String[] parts = line.split(",",2);
                int accountNumber = Integer.parseInt(parts[0]);
                String transaction = parts[1];

                User user = findUser(accountNumber);
                if(user != null){
                    user.addTransaction(transaction);
                }
            }
        }catch(IOException e){
            System.out.println("No existing transaction data found.");
        }
    }

    //Method for writing user data in text file
    private static void saveUsers(){
        try(PrintWriter pw = new PrintWriter(new FileWriter("src/data/users.txt"))){
            for(User user : users){
                pw.println(user.getAccountNumber() + "," +
                        user.getName() + "," +
                        user.getAddress() + "," +
                        user.getContact() + "," +
                        user.getHashedPassword() + "," +
                        user.getBalance());
            }
        }catch(IOException e){
            System.out.println("Error saving user data."+e.getMessage());
        }
    }

    //Method for writing transaction history in text file
    private static void saveTransactions(){
        try(PrintWriter pw = new PrintWriter(new FileWriter("src/data/transactions.txt"))){
            for(User user : users){
                for(String transaction : user.getTransactionHistory()){
                    pw.println(user.getAccountNumber()+ "," +transaction);
                }
            }
        }catch(IOException e){
            System.out.println("Error saving transaction data "+e.getMessage());
        }
    }

    //Methods for enhanced CLI
    private static void printHeader(String title) {
        System.out.println("\n*******************************************");
        System.out.println(title);
        System.out.println("*******************************************");
    }

    private static void printFooter() {
        System.out.println("*******************************************");
    }

    private static void printSeparator() {
        System.out.println("-------------------------------------------");
    }
}