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
            System.out.println("3.Exit System");
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
                    System.out.println("Exiting the system...");
                    scan.close();
                    return;
                default:
                    System.out.println("Invalid Choice. Please try again !");
            }
        }
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

        System.out.println("User Registration Successfull!");
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
}