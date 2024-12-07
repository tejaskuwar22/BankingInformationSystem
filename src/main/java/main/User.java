package main;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import org.mindrot.jbcrypt.BCrypt;

public class User{
    private static final AtomicInteger accountNumberGenerator = new AtomicInteger(1000);
    private final int accountNumber;
    private String name;
    private String address;
    private String contact;
    private String hashedPassword;
    private double balance;
    private final List<String> transactionHistory;

    public User(String name, String address, String contact, String password, double initialDeposit){
        this.accountNumber = accountNumberGenerator.getAndIncrement();
        this.name = name;
        this.address = address;
        this.contact = contact;
        this.hashedPassword = hashPassword(password);
        this.balance = initialDeposit;
        this.transactionHistory = new ArrayList<>();
        addTransaction("Accounted created with initial deposit : "+initialDeposit);
    }
    //Password hashing method
    private String hashPassword(String password){
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }
    //Password validation method
    public boolean validatePassword(String password){
        return BCrypt.checkpw(password, this.hashedPassword);
    }
    public int getAccountNumber(){
        return accountNumber;
    }
    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name = name;
    }
    public String getAddress(){
        return address;
    }
    public void setAddress(String address){
        this.address = address;
    }
    public String getContact(){
        return contact;
    }
    public void setContact(String contact){
        this.contact = contact;
    }
    public double getBalance(){
        return balance;
    }
    //Method for deposit money
    public void deposit(double amount){
        if(amount > 0){
            this.balance += amount;
            addTransaction("Deposited : "+amount+" | Balance : "+balance);
        }
    }

    //Method to withdraw money
    public boolean withdraw(double amount){
        if(amount > 0 && amount <= balance){
            this.balance -= amount;
            addTransaction("Withdrew : "+amount+" | Balance : "+balance);
            return true;
        }
        return false; //Less balance
    }

    public void addTransaction(String transactionDetails){
        transactionHistory.add(transactionDetails);
    }

    public List<String> getTransactionHistory(){
        return transactionHistory;
    }

    @Override
    public String toString(){
        return "Account Number : "+accountNumber+
                "\nName : "+name+
                "\nAddress : "+address+
                "\nContact : "+contact+
                "\nBalance : "+balance;
    }
}