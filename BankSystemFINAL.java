package BankAccountManagementSystem.FINAL;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Random;

//Code for creating, changing and retrieving values in the account objects
class Account {

    //Defines the variables for account
    private int accessCode;
    private int pinNumber;

    //Used to implement other methods that may require the logged in variable
    private boolean loggedIn = false;
    
    
    //Creates the object account and initializes the variables for account
    //Allows you to set the variables for the account when creating a new account object
    public Account(int accessCode, int pinNumber) {
        this.accessCode = accessCode;
        this.pinNumber = pinNumber;
    }

    //Getter methods
    //Allows you to retrieve the value of the variables for the instances
    public int getAccessCode() {
        return accessCode;
    }

    public int getPinNumber() {
        return pinNumber;
    }

    public boolean getLoggedIn() {
        return loggedIn;
    }
}

class BankAccount extends Account {
        private int accountNumber;
        private String accountHolder;
        private double balance;

        public BankAccount(int accessCode, int pinNumber, int accountNumber, String accountHolder, double balance) {
        super(accessCode, pinNumber);
        this.accountNumber = accountNumber;
        this.accountHolder = accountHolder;
        this.balance = balance;
    }

    //Getters for accountNumber, accountHolder, and balance
    public int getAccountNumber() {
        return accountNumber;
    }

    public String getAccountHolder() {
        return accountHolder;
    }

    public double getBalance() {
        return balance;
    }

    //Deposit method
    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
        }
    }

    //Withdraw method
    public void withdraw(double amount) {
        if (amount > 0 && balance >= amount) {
            balance -= amount;
        }
    }

    //Returns this string when account is called    
    public String getAccountDetails() {
        return "Account Number: " + accountNumber + "\nAccount Holder: " + accountHolder + "\nBalance: $" + balance;
    }
}   

//Has the collection of accounts
//Provides methods related to the accounts eg creating accounts
class Bank {
    //List that holds the account objects
    private List<Account> accounts;
    private List<BankAccount> bankAccounts; 

    public Bank() {
        accounts = new ArrayList<>();
        bankAccounts = new ArrayList<>();
    }

    //Getter for bank account
    public List<BankAccount> getBankAccounts() {
        return bankAccounts;
    }

    //Create account method
    //Allows creation of new accounts
    public void createAccountWithRandomAccessCode(int pinNumber) {
        Random random = new Random();
        int accessCode;

        // Generate a random access code and ensure it doesn't already exist
        do {
            accessCode = 100_000 + random.nextInt(900_000); 
        } while (isAccessCodeInUse(accessCode));

        // Create the account
        Account account = new Account(accessCode, pinNumber);
        accounts.add(account);

        System.out.println("Account created successfully with access code: " + accessCode);
    }

    //To check if access code already exists
    private boolean isAccessCodeInUse(int accessCode) {
        for (Account existingAccount : accounts) {
            if (existingAccount.getAccessCode() == accessCode) {
                return true;
            }
        }
        return false;
    }

    //Creates accounts for popluating data
    public void createAccount(int accessCode, int pinNumber) {
        //Validator for duplicate access codes
        for (Account existingAccount : accounts) {
            if (existingAccount.getAccessCode() == accessCode) {
                System.out.println("An account with the same access code already exists. Cannot create a duplicate account.");
                return;
            }
        }

        Account account = new Account(accessCode, pinNumber);
        
        //If account was created during data poluation, nothing is printed
        if (accessCode == 111111 || accessCode == 222222 || accessCode == 333333) {
            accounts.add(account);
        } else {
            accounts.add(account);
            System.out.println("Account created successfully.");
        }
    }

    //Creates bank account for popluating data
    public void createBankAccount(int accessCode, int pinNumber, int accountNumber, String accountHolder, double balance) {
        BankAccount bankAccount = new BankAccount(accessCode, pinNumber, accountNumber, accountHolder, balance);
        bankAccounts.add(bankAccount);
    }

    //Creates bank account with random account number for users
    public BankAccount createBankAccountWithRandomAccountNumber(int accessCode, int pinNumber, String accountHolder, double balance) {
        Random random = new Random();
        int accountNumber;
        do {
            accountNumber = 10_000_000 + random.nextInt(90_000_000); 
        } while (isAccountNumberInUse(accountNumber));
        BankAccount bankAccount = new BankAccount(accessCode, pinNumber, accountNumber, accountHolder, balance);
        bankAccounts.add(bankAccount);
        return bankAccount; 
    }

        //To check if access code already exists
    private boolean isAccountNumberInUse(int accountNumber) {
        for (BankAccount existingAccount : bankAccounts) {
            if (existingAccount.getAccountNumber() == accountNumber) {
                return true;
            }
        }
        return false;
    }

    //Login method
    //Allows users to login, returning true if successful and false if not
    public boolean login(int accessCode, int pinNumber) {
        for (Account account : accounts) {
            if (account.getAccessCode() == accessCode && account.getPinNumber() == pinNumber) {
                return true;
            }
        }
        return false;
    }

    //Find account method
    //Returns matching accounts based on the acccess code and pin number
    public List<BankAccount> findAccounts(int accessCode, int pinNumber) {
        List<BankAccount> matchingAccounts = new ArrayList<>();

        //Adds matching accounts to the list
        for (BankAccount account : bankAccounts) {
            if (account.getAccessCode() == accessCode && account.getPinNumber() == pinNumber) {
                matchingAccounts.add(account);
            }
        }
        
        if (matchingAccounts.size() > 1) {
            System.out.println("Matching accounts:");
            for (int i = 0; i < matchingAccounts.size(); i++) {
                BankAccount account = matchingAccounts.get(i);
                System.out.println((i + 1) + ". " + account.getAccountNumber() + " " + account.getAccountHolder());
                }
        }
        return matchingAccounts;
    }
    
    //Finds acounts by account number
    public BankAccount findAccountByAccountNumber(List<BankAccount> accounts, int accountNumber) {
        for (BankAccount account : accounts) {
            if (account.getAccountNumber() == accountNumber) {
                return account;
            }
        }
        return null;
    }

    //Checks if account exists
    public boolean accountExists(int accountNumber) {
        for (BankAccount account : bankAccounts) {
            if (account.getAccountNumber() == accountNumber) {
                return true; 
            }
        }
        return false;
    }

    //Print acccount method
    //Allows printing of accounts
    public void printMatchingAccounts(int accountChoice, List<BankAccount> matchingAccounts) {

        if (accountChoice >= 1 && accountChoice <= matchingAccounts.size()) {
            BankAccount selectedAccount = matchingAccounts.get(accountChoice - 1);
            System.out.println(selectedAccount.getAccountDetails());
        } else {
            System.out.println("Invalid choice.");
        }
    }

    //Deposit accounts method
    public void depositMatchingAccount(int accountChoice, List<BankAccount> matchingAccounts, double amount) {
        if (accountChoice >= 1 && accountChoice <= matchingAccounts.size()) {
            BankAccount selectedAccount = matchingAccounts.get(accountChoice - 1);
            selectedAccount.deposit(amount);
            System.out.println("Deposit successful.");
        } else {
            System.out.println("Invalid choice.");
        }
    }

    //Withdraw accounts method
    public void withdrawMatchingAccount(int accountChoice, List<BankAccount> matchingAccounts, double amount) {
        if (accountChoice >= 1 && accountChoice <= matchingAccounts.size()) {
            BankAccount selectedAccount = matchingAccounts.get(accountChoice - 1);
            selectedAccount.withdraw(amount);
            System.out.println("Withdraw successful.");
        } else {
            System.out.println("Invalid choice.");
        }
    }

    //Transfer money method 
    //Allows user to transfer money from one of their accounts to any other
    public boolean transferMoney(List<BankAccount> matchingAccounts, int sourceAccountIndex, int targetAccountNumber, double amount) {
        // Find the source and target accounts using their account numbers
        BankAccount sourceAccount = matchingAccounts.get(sourceAccountIndex - 1);
        BankAccount targetAccount = findAccountByAccountNumber(bankAccounts, targetAccountNumber);

        //Exception handling so the same account cant be entered twice
        if (sourceAccount == targetAccount) {
        System.out.println("Source and target accounts cannot be the same.");
        return false;
        }

        //Exception handling
        if (sourceAccount != null && targetAccount != null) {
            if (sourceAccount.getBalance() >= amount) {
                //Excute the transfer
                sourceAccount.withdraw(amount);
                targetAccount.deposit(amount);
                System.out.println("Transfer successful.");
                return true;
            } else {
                System.out.println("Insufficient balance in the source account.");
            }
        } else {
            System.out.println("Invalid target account number.");
        }
        
        return false;
    }
}



//Serves as the main method
public class BankSystemFINAL {
    //Initializing variables
    private static int accessCode = 0;
    private static int pinNumber = 0;
    private static boolean loggedIn = false;

    //Creates new instance of class
    private static Bank bank = new Bank();

    //Have to call it in the class because it gives an error when called twice
    public static Scanner scanner = new Scanner(System.in);

    //Create sample accounts using the create account method
    public static void popluateData(Bank bank){
        bank.createAccount(111111, 123456);
        bank.createBankAccount(111111, 123456, 11111111, "User 1", 4_500);
        bank.createAccount(222222, 234567);
        bank.createBankAccount(222222, 234567, 22222222, "User 2", 70_000);
        bank.createBankAccount(222222, 234567, 22222223, "User 2 part 2", 5_000);
        bank.createAccount(333333, 345678);
    }


    public static void loginMenu(Bank bank) {

        //Login menu loop
        while (!loggedIn) {
            try{
                
                //Login menu
                System.out.println("Bank Account Management System");
                System.out.println("1. Login");
                System.out.println("2. Create new account");
                System.out.println("3. Exit");
                System.out.print("Enter your choice: ");
                int choice = scanner.nextInt();

                //Choices
                switch (choice) {
                    //Login
                    case 1:
                        //For the case 1 loop
                        boolean case1Successful = false;
    
                        while (!case1Successful) {
                            try {
                                System.out.println("\n");
                    
                                //User enters access code
                                System.out.print("Enter your access code: ");
                                accessCode = scanner.nextInt();
                
                                //Checks if access code is 6 digits
                                if (!(accessCode >= 100_000 && accessCode <= 999_999)) {
                                    System.out.println("\nInvalid number. Access code must be six digits.\n");
                                    continue;
                                }
                                
                                //User enters pin number
                                System.out.print("Enter your PIN number: ");
                                pinNumber = scanner.nextInt();
                
                                //Checks if pin number is 6 digits
                                if (!(pinNumber >= 100_000 && pinNumber <= 999_999)) {
                                    System.out.println("\nInvalid number. PIN number must be six digits.\n");
                                    continue;
                                }
                                
                                //Implements login and checks if it exists 
                                if (bank.login(accessCode, pinNumber)) {
                                    loggedIn = true;
                                    case1Successful = true;
                                    System.out.println("Login successful.");
                                } else {
                                    System.out.println("Login failed. Invalid access code or PIN number.");
                                }
                    
                                System.out.println("\n");

                            }   catch (java.util.InputMismatchException e) {
                                System.out.println("Invalid input. Please enter a valid number.\n");
                                scanner.nextLine();
                                }
                        }
                        break;
                    
                    //Create new account
                    case 2:
                        boolean case2Successful = false;

                        while (!case2Successful) {
                            try {
                                System.out.println("\n");

                                //User enters pin number
                                System.out.print("Create your PIN number(6 digits): ");
                                pinNumber = scanner.nextInt();
                
                                //Checks if pin number is 6 digits
                                if (!(pinNumber >= 100_000 && pinNumber <= 999_999)) {
                                    System.out.println("\nInvalid number. PIN number must be six digits.\n");
                                    continue;
                                }

                                //Calls method create account
                                bank.createAccountWithRandomAccessCode(pinNumber);
                                case2Successful = true;
                            
                                System.out.println("\n");

                            }   catch (java.util.InputMismatchException e) {
                                System.out.println("Invalid input. Please enter a valid number.\n");
                                scanner.nextLine();
                                }
                        }
                        break;

                    //Exit
                    case 3:
                        System.out.println("\n");
                        System.out.println("Exiting program now");
                        System.out.println("Goodbye");
                        scanner.close();
                        System.exit(0);
                    
                    //If anything else is entered 
                    default:
                        System.out.println("\n");
                        System.out.println("Invalid choice");
                        System.out.println("\n");
                        break;
                        
                }
            }   //Catches input mismatch exceptions and repeats the loop
                catch (java.util.InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid choice"); 
                scanner.nextLine();
                System.out.println("\n");
                loginMenu(bank);

            }
        }
    } 
        
    public static void menu(Bank bank) {   

        
        //Menu loop
        while (loggedIn) {
            
            //Invalid input exception handling
            try {

                //Menu
                System.out.println("Bank Account Management System");
                System.out.println("Logged into account " + accessCode);
                System.out.println("1. View account details");
                System.out.println("2. Deposit money");
                System.out.println("3. Withdraw money");
                System.out.println("4. Transfer money");
                System.out.println("5. Create new bank account");
                System.out.println("6. Log out");
                System.out.println("7. Exit");
                System.out.print("Enter your choice: ");
                int choice = scanner.nextInt();

            
                //Choices
                switch (choice){
                    //View account details
                    case 1:
                        boolean case1Successful = false;
                        boolean userHasSelectedAccount = false;

                        while (!case1Successful) {
                            try {
                                if (userHasSelectedAccount) {
                                    break;
                                }

                                System.out.println("\n");

                                // Calls find accounts method
                                List<BankAccount> matchingAccounts = bank.findAccounts(accessCode, pinNumber);
                                int accountChoice = 0;

                                // Error validation
                                
                                if (matchingAccounts.size() > 1) {
                                    // Print matching accounts and get the user's choice
                                    System.out.print("Enter the number of the account you want to view: ");
                                    accountChoice = scanner.nextInt();
                                    if (accountChoice < 1 || accountChoice > matchingAccounts.size()) {
                                        System.out.println("Invalid choice.");
                                        continue;
                                    }
                                    userHasSelectedAccount = true;
                                } if (matchingAccounts.isEmpty()) {
                                    System.out.println("No accounts found\n");
                                    break;
                                } if (matchingAccounts.size() == 1) {
                                    accountChoice = 1;
                                }   
                                bank.printMatchingAccounts(accountChoice, matchingAccounts);
                                case1Successful = true;

                                System.out.println("\n");

                            }   catch (java.util.InputMismatchException e) {
                                System.out.println("Invalid input. Please enter a valid number.\n");
                                scanner.nextLine();
                                }
                        }
                        break;

                    // Deposit money
                    case 2:
                        boolean case2Successful = false;
                        userHasSelectedAccount = false;

                        while (!case2Successful) {
                            try {
                                if (userHasSelectedAccount) {
                                    break;
                                }

                                System.out.println("\n");

                                List<BankAccount> matchingAccounts = bank.findAccounts(accessCode, pinNumber);
                                int accountChoice;

                                
                                if (matchingAccounts.size() > 1) {
                                    accountChoice = 0; // Initialize accountChoice
                                
                                    while (accountChoice < 1 || accountChoice > matchingAccounts.size()) {
                                        System.out.print("Enter the index of the account to deposit money into: ");
                                        accountChoice = scanner.nextInt();
                                
                                        if (accountChoice < 1 || accountChoice > matchingAccounts.size()) {
                                            System.out.println("Invalid choice.");
                                        }
                                    }
                                } else if (matchingAccounts.isEmpty()) {
                                    System.out.println("No accounts found\n");
                                    break; 
                                } else {
                                    //Only one account in the list, set accountChoice to 1
                                    accountChoice = 1;
                                }

                                System.out.print("Enter the amount to deposit: $");
                                double depositAmount = scanner.nextDouble();

                                if (depositAmount < 0) {
                                    System.out.println("Please enter a positive number\n");
                                    continue;
                                }

                                // Calls deposit matching account method
                                bank.depositMatchingAccount(accountChoice, matchingAccounts, depositAmount);
                                userHasSelectedAccount = true;

                                double newBalance = matchingAccounts.get(accountChoice - 1).getBalance();
                                System.out.println("New balance: $" + newBalance);
                                case2Successful = true;

                                System.out.println("\n");
                            }   catch (java.util.InputMismatchException e) {
                                System.out.println("Invalid input. Please enter a valid number.\n");
                                scanner.nextLine();
                                }
                        }
                        break;
                    
                    //Withdraw money
                    case 3:
                        boolean case3Successful = false;
                        userHasSelectedAccount = false;

                        while(!case3Successful) {  
                            try{
                                if (userHasSelectedAccount) {
                                    break;
                                }

                                System.out.println("\n");

                                List<BankAccount> matchingAccounts = bank.findAccounts(accessCode, pinNumber);
                                int accountChoice;

                                if (matchingAccounts.size() > 1) {
                                    accountChoice = 0; // Initialize accountChoice
                                
                                    while (accountChoice < 1 || accountChoice > matchingAccounts.size()) {
                                        System.out.print("Enter the index to withdraw money from: ");
                                        accountChoice = scanner.nextInt();
                                
                                        if (accountChoice < 1 || accountChoice > matchingAccounts.size()) {
                                            System.out.println("Invalid choice.");
                                        }
                                    }
                                } else if (matchingAccounts.isEmpty()) {
                                    System.out.println("No accounts found\n");
                                    break; 
                                } else {
                                    //Only one account in the list, set accountChoice to 1
                                    accountChoice = 1;
                                }

                                //Find original balance
                                double balance = matchingAccounts.get(accountChoice - 1).getBalance();

                                System.out.print("Enter the amount to withdraw: $");
                                double withdrawAmount = scanner.nextDouble();

                                if (withdrawAmount < 0) {
                                    System.out.println("Please enter a positive number");
                                    continue;
                                } if (withdrawAmount > balance) {
                                    System.out.println("Transfer failed. Insufficient balance in the source account.");
                                    continue;
                                }

                                //Calls withdraw matching acccount method
                                bank.withdrawMatchingAccount(accountChoice, matchingAccounts, withdrawAmount);
                                userHasSelectedAccount = true;

                                //Gets new balance and prints it
                                double newBalance = matchingAccounts.get(accountChoice - 1).getBalance();
                                System.out.println("New balance: $" + newBalance);
                                case3Successful = true;

                                System.out.println("\n");

                            }   catch (java.util.InputMismatchException e) {
                                System.out.println("Invalid input. Please enter a valid number.\n");
                                scanner.nextLine();
                                }
                        }
                        break;
                    
                    //Transfer money
                    case 4:
                        boolean case4Successful = false;
                        userHasSelectedAccount = false;
                        
                        while (!case4Successful) {
                            try {
                                System.out.println("\n");
                                List<BankAccount> matchingAccounts = bank.findAccounts(accessCode, pinNumber);
                                int sourceAccountIndex;

                                if (matchingAccounts.size() > 1) {
                                    sourceAccountIndex = 0; // Initialize accountChoice
                                
                                    while (sourceAccountIndex < 1 || sourceAccountIndex > matchingAccounts.size()) {
                                        System.out.print("Enter the index of the source account:");
                                        sourceAccountIndex = scanner.nextInt();
                                
                                        if (sourceAccountIndex < 1 ||sourceAccountIndex > matchingAccounts.size()) {
                                            System.out.println("Invalid choice.");
                                        }
                                    }
                                } else if (matchingAccounts.isEmpty()) {
                                    System.out.println("No accounts found\n");
                                    break; 
                                } else {
                                    //Only one account in the list, set accountChoice to 1
                                    sourceAccountIndex = 1;
                                }

                                System.out.print("Enter the account number of the target account: ");
                                int targetAccountNumber = scanner.nextInt();

                                System.out.print("Enter the amount to transfer: $");
                                double transferAmount = scanner.nextDouble();

                                // Call the transferMoney method
                                boolean transferResult = bank.transferMoney(matchingAccounts, sourceAccountIndex, targetAccountNumber, transferAmount);

                                if (transferResult) {
                                    case4Successful = true;
                                    userHasSelectedAccount = true;
                                } else {
                                    System.out.println("Transfer failed. Please try again.");
                                }

                                System.out.println("\n");

                            } catch (java.util.InputMismatchException e) {
                                System.out.println("Invalid input. Please enter a valid number.\n");
                                scanner.nextLine();
                            }
                        }
                        break;

                    //Create new bank account
                    case 5:
                        System.out.println("\n");

                        System.out.print("Enter the account holder's name: ");
                        String accountHolder = scanner.next();

                        double balance = 0;

                        //Calls method create account
                        BankAccount newAccount = bank.createBankAccountWithRandomAccountNumber(accessCode, pinNumber, accountHolder, balance);
                        System.out.println("Account created");
                        System.out.println("Your account number is " + newAccount.getAccountNumber());

                        System.out.println("\n");

                        break;


                    //Log out
                    case 6:
                        System.out.println("\n");
                        
                        System.out.println("Logging out");
                        
                        //Sets logged in to false
                        loggedIn = false;

                        System.out.println("\n");

                        break;

                    //Exit
                    case 7:
                        System.out.println("\n");
                        System.out.println("Exiting program now");
                        System.out.println("Goodbye");
                        scanner.close();
                        System.exit(0);

                    default:
                        System.out.println("\n");
                        System.out.println("Invalid choice");
                        System.out.println("\n");
                        break;
                }
            }   //Catches input mismatch exceptions and repeats the loop
                catch (java.util.InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid choice"); 
                scanner.nextLine();
                System.out.println("\n");
                menu(bank);

                }
        }
    }


    //Acts as the main function
    public static void main(String[] args) {

        //creates sample accounts
        popluateData(bank);

        while (true) {
            if (!loggedIn) {
                loginMenu(bank);
            } else if (loggedIn) {
                menu(bank);
            }
        }
    }
}
