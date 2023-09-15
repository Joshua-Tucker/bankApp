import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

public class Bank {

    private String bankName;
    private ArrayList<Branch> branches;
    private CustomerManager customerManager;


    public Bank() {
        this.bankName = "Coventry Building Society";
        this.branches = new ArrayList<>();
        this.customerManager = new CustomerManager(JacksonReader.jsonFile);

    }


    public void bankGreeting(Bank bank) throws IOException {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to Coventry Building Society. Can you confirm which branch you're in?");
        System.out.println("Type Bristol / Coventry / London / Edinburgh ");
        String location = scanner.nextLine();
        System.out.println("Thank you. You are at the " + location + " branch. Are you a new or existing customer?");
        System.out.println("Type NEW / EXISTING");
        String customerType = scanner.nextLine();

        Branch branch = getBranchByName(location);

        if (customerType.equals("NEW")) {
            newCustomer(branch, bank);
        } else {
            existingCustomer(branch);
        }

    }

    public void deposit(Customer customer) throws IOException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("To access your accounts, you must first make a deposit");

        System.out.println("PERFORMING IF STATEMENT");

        if (customer.hasCurrentAccount() && customer.hasSavingsAccount()) {
            System.out.println("You have both Current and Savings accounts. Which would you like to deposit into?");
            System.out.println("1 = Current, 2 = Savings");
            String answer = scanner.nextLine();
            if ("1".equals(answer)) {
                currentDeposit(customer);
            } else if ("2".equals(answer)) {
                savingsDeposit(customer);
            } else {
                System.out.println("Invalid choice.");
            }
        } else if (customer.hasCurrentAccount()) {
            System.out.println("You have a Current account.");
            currentDeposit(customer);
        } else if (customer.hasSavingsAccount()) {
            System.out.println("You have a Savings account.");
            savingsDeposit(customer);
        } else {
            System.out.println("You do not have any accounts with us as of yet. Would you like to create one?");
        }
    }


    public Customer newCustomer(Branch branch, Bank bank) throws IOException {

        JacksonReader.addCustomerToJson(branch, bank);
        Customer newCustomer = JacksonReader.createCustomer(bank);
        bank.customerManager.addCustomer(newCustomer);

        newCustomer = checkCustomerPin(newCustomer);


        deposit(newCustomer);


        return newCustomer;
    }

    //        bank.customerManager.saveDataToJson(); WRITE METHOD FOR THIS


    public Customer checkCustomerPin(Customer customer) throws IOException {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Please enter your first name");
        String firstName = scanner.nextLine();
        System.out.println("Please enter your last name");
        String lastName = scanner.nextLine();

        customer = customerManager.findCustomerByName(firstName, lastName);

        if (customer != null) {
            System.out.println("Customer found, Hello " + customer.getFirstName());
            System.out.println("Please type in your 4-digit PIN");

            int pinAttempts = 0;

            while (pinAttempts < 3) {
                String pin = scanner.nextLine();
                if (customer.getPin().equals(pin)) {
                    System.out.println("Thank you " + customer.getFirstName() + ", taking you through to your accounts now.");
                    // Takes you through to account
                    return customer;
                } else {
                    pinAttempts++;
                    System.out.println("Incorrect PIN. You have " + (3 - pinAttempts) + " attempts remaining.");
                }
            }

            System.out.println("Incorrect PIN. You have been locked out of your account, please contact the branch.");
            System.exit(1);
        } else {
            System.out.println("Customer not found in this branch.");
        }
        return null;
    }


    public Customer checkCustomerPin(Branch branch) {
        Scanner scanner = new Scanner(System.in);
        Customer isCustomer = null;

        System.out.println("Please enter your first name");
        String firstName = scanner.nextLine();
        System.out.println("Please enter your last name");
        String lastName = scanner.nextLine();

        for (Customer customer : branch.getCustomerList()
        ) {
            if (customer.getFirstName().equals(firstName) && customer.getLastName().equals(lastName)) {
                isCustomer = customer;
            }

        }


        if (isCustomer != null) {
            System.out.println("Customer found, Hello " + isCustomer.getFirstName());
            System.out.println("Please type in your 4-digit PIN");

            int pinAttempts = 0;

            while (pinAttempts < 3) {
                String pin = scanner.nextLine();
                if (isCustomer.getPin().equals(pin)) {
                    System.out.println("Thank you " + isCustomer.getFirstName() + ", taking you through to your accounts now.");
                    // Takes you through to account
                    return isCustomer;
                } else {
                    pinAttempts++;
                    System.out.println("Incorrect PIN. You have " + (3 - pinAttempts) + " attempts remaining.");
                }
            }

            System.out.println("Incorrect PIN. You have been locked out of your account, please contact the branch.");
            System.exit(1);
        } else {
            System.out.println("Customer not found in this branch.");
        }
        return null;
    }

    public void existingCustomer(Branch branch) {
        checkCustomerPin(branch);
    }


    public void savingsDeposit(Customer customer) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Please choose a deposit amount (minimum £100)");
        double deposit = Double.parseDouble(scanner.nextLine());
        customer.getSavingsAccount().setDeposit(deposit);

    }

    public void currentDeposit(Customer customer) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Please choose a deposit amount (minimum £100)");
        double deposit = Double.parseDouble(scanner.nextLine());
        customer.getCurrentAccount().setDeposit(deposit);

    }


    public CustomerManager getCustomerManager() {
        return this.customerManager;
    }


    public static void setNewPin(ObjectNode customerNode) {
        System.out.println("Finally your pin");
        String newPin = Customer.createPin();
        System.out.println("Your Pin Number is : " + newPin);
        System.out.println("Keep this safe and do not show anybody else");
        customerNode.put("pin", newPin);
    }

    public static void setAccounts(ObjectNode customerNode) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Excellent, nearly done! Would you like: 1. Current Account 2. Savings Account  3. Both.");
        System.out.println("Type 1,2 or 3");
        String accountType = scanner.nextLine();

        boolean hasCurrentAccount = false;
        boolean hasSavingsAccount = false;

        if (accountType.contains("1")) {
            hasCurrentAccount = true;
        } else if (accountType.contains("2")) {
            hasSavingsAccount = true;
        } else {
            hasCurrentAccount = true;
            hasSavingsAccount = true;
        }

        customerNode.put("currentAccount", hasCurrentAccount);
        customerNode.put("savingsAccount", hasSavingsAccount);
    }


    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public ArrayList<Branch> getBranches() {
        return branches;
    }

    public Branch getBranchByName(String branchName) {
        for (Branch branch : branches) {
            if (branch.getBranchName().equals(branchName)) {
                return branch;
            }
        }
        return null;
    }

    public void setBranches(ArrayList<Branch> branches) {
        this.branches = branches;
    }

    public void addBranches(Branch branch) {
        branches.add(branch);
    }

    public void displayCustomerInformation() {
        for (Branch branch : branches) {
            System.out.println("Branch: " + branch.getBranchName());

            for (Customer customer : branch.getCustomerList()) {
                System.out.println("Customer: " + customer.getFirstName() + " " + customer.getLastName());
                System.out.println("Date of Birth: " + customer.getDateOfBirth());
                System.out.println("Email: " + customer.getEmail());

                if (customer.getCurrentAccount() != null) {
                    System.out.println("Current Account:");
                    System.out.println("Account Number: " + customer.getCurrentAccount().getAccountNumber());
                    System.out.println("Balance: " + customer.getCurrentAccount().getBalance());
                }

                if (customer.getSavingsAccount() != null) {
                    System.out.println("Savings Account:");
                    System.out.println("Account Number: " + customer.getSavingsAccount().getAccountNumber());
                    System.out.println("Balance: " + customer.getSavingsAccount().getBalance());
                }

                System.out.println();
            }
        }
    }

}



