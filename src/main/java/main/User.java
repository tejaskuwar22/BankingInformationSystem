package main;
import java.util.concurrent.atomic.AtomicInteger;

public class User{
    private static final AtomicInteger accountNumberGenerator = new AtomicInteger(1000);
    private final int accountNumber;
    private String name;
    private String address;
    private String contact;
    private double balance;

    public User(String name, String address, String contact, double initialDeposit){
        this.accountNumber = accountNumberGenerator.getAndIncrement();
        this.name = name;
        this.address = address;
        this.contact = contact;
        this.balance = initialDeposit;
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
        }
    }

    //Method to withdraw money
    public boolean withdraw(double amount){
        if(amount > 0 && amount <= balance){
            this.balance -= amount;
            return true;
        }
        return false; //Less balance
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