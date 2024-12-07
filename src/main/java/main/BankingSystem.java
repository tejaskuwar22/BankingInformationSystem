package main;
import java.net.SocketOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class BankingSystem {
    private static final List<User> users = new ArrayList<>();

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);

        System.out.println("Welcome to the Banking Information System");
        while(true){
            System.out.println("\n1.Register User");
            System.out.println("2.View All Users");
            System.out.println("3.Deposit Money");
            System.out.println("4.Withdraw Money");
            System.out.println("5.Transfer Funds");
            System.out.println("6.View Account Statement");
            System.out.println("7.Exit System");
            System.out.println("Select an option : ");

            int ch = scan.nextInt();
            scan.nextLine();

            switch(ch){
                case 1:
                    registerUser(scan);
                    break;
                case 2:
                    displayUser();
                    break;
                case 3:
                    depositMoney(scan);
                    break;
                case 4:
                    withdrawMoney(scan);
                    break;
                case 5:
                    transferFunds(scan);
                    break;
                case 6:
                    viewAccountStatement(scan);
                    break;
                case 7:
                    System.out.println("Exiting the system...");
                    scan.close();
                    return;
                default:
                    System.out.println("Invalid Choice. Please try again !");
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

        System.out.println("Enter Initial Deposit : ");
        double initialDeposit = scan.nextDouble();

        User user = new User(name, address, contact, initialDeposit);
        users.add(user);

        System.out.println("User Registration Successful!");
        System.out.println(user);
    }

    //Method for displaying all users
    private static void displayUser(){
        if(users.isEmpty()){
            System.out.println("No registered user found.");
            return;
        }
        for(User user : users){
            System.out.println("\n"+user);
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

        System.out.println("Transaction History for Account Number : "+accountNumber);
        List<String> history = user.getTransactionHistory();
        if(history.isEmpty()) {
            System.out.println("No transactions found.");
        }else{
            for(String transaction : history){
                System.out.println(transaction);
            }
        }
    }
}