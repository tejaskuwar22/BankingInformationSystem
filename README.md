# Banking Information System

## Overview
The **Banking Information System** is a Core Java-based prototype designed to simulate the core functionalities of a real-world banking system. It provides essential features such as user registration, account management, secure login, transactions (deposit, withdrawal, fund transfer), and account statement generation.

## Features
- **User Registration**: Register new users with personal details and an initial deposit.
- **Account Management**: View account details and balances.
- **Password Protection**: Secure login with hashed passwords using BCrypt.
- **Deposit and Withdrawal**: Update balances with proper validation.
- **Fund Transfer**: Securely transfer funds between accounts.
- **Account Statements**: Generate transaction history.
- **Data Persistence**: User and transaction data stored in `users.txt` and `transactions.txt`.
- **Enhanced CLI**: Command-line interface using symbols for better readability.

## Technical Stack
- **Programming Language**: Java (Core)
- **Development Environment**: IntelliJ IDEA
- **Build Tool**: Maven
- **Libraries**:
    - `org.mindrot.jbcrypt` for password hashing
- **Data Storage**: Plain text files (`users.txt`, `transactions.txt`)

## Project Structure
```
src/
├── main/
│   ├── java/
│       ├── backend/
│           ├── Main.java            // Entry point
│       ├── main/
│           ├── BankingSystem.java   // Core logic and menu handling
│           ├── User.java            // User data model
│   ├── data/
│       ├── users.txt                // Stores user details
│       ├── transactions.txt         // Stores transaction history
```

## Setup and Usage
1. Clone the repository:
   ```bash
   git clone https://github.com/tejaskuwar22/BankingInformationSystem.git
   ```
2. Open the project in IntelliJ IDEA.
3. Ensure Maven dependencies are installed.
4. Run the program from `src/main/java/backend/Main.java`.
5. Interact with the Banking Information System via the command-line interface.


## Author
Tejas Kuwar
