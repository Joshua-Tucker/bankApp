import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class JacksonReader {
    private static final ObjectMapper objectMapper = new ObjectMapper();
    static final File jsonFile = new File("/Users/version2.0/nologyWork/Java course/CbsBankingProject/src/main/java/MockData.json");

    public static Customer addCustomerToJson(Branch location, Bank bank) throws IOException {
        Scanner scanner = new Scanner(System.in);

        String branchName = location.getBranchName();

        System.out.print("Enter first name: ");
        String firstName = scanner.nextLine();

        System.out.print("Enter last name: ");
        String lastName = scanner.nextLine();

        System.out.print("Enter date of birth (YYYY/MM/DD): ");
        String dateOfBirth = scanner.nextLine();

        System.out.print("Enter email: ");
        String email = scanner.nextLine();

        // Create a customer node with the new JSON format
        ObjectNode customerNode = objectMapper.createObjectNode();
        customerNode.put("firstName", firstName);
        customerNode.put("lastName", lastName);
        customerNode.put("dateOfBirth", dateOfBirth);
        customerNode.put("email", email);

        System.out.print("Do you want a current account? (Yes/No): ");
        boolean hasCurrentAccount = scanner.nextLine().equalsIgnoreCase("Yes");

        System.out.print("Do you want a savings account? (Yes/No): ");
        boolean hasSavingsAccount = scanner.nextLine().equalsIgnoreCase("Yes");

        customerNode.put("hasCurrentAccount", hasCurrentAccount);
        customerNode.put("hasSavingsAccount", hasSavingsAccount);

        CurrentAccount currentAccount = null;
        if (hasCurrentAccount) {
            ObjectNode currentAccountNode = objectMapper.createObjectNode();
            currentAccountNode.put("accountNumber", Accounts.createAccountNumber());
            currentAccountNode.put("accountType", "Current Account");
            currentAccountNode.put("balance", 0.0);
            currentAccountNode.put("withdraw", true);
            currentAccountNode.put("deposit", true);
            currentAccountNode.put("transfer", true);
            customerNode.set("currentAccount", currentAccountNode);

            // Create an instance of Current Account
            currentAccount = new CurrentAccount(
                    currentAccountNode.get("accountNumber").asInt(),
                    currentAccountNode.get("accountType").asText(),
                    currentAccountNode.get("balance").asDouble(),
                    currentAccountNode.get("withdraw").asDouble(),
                    currentAccountNode.get("deposit").asDouble(),
                    currentAccountNode.get("transfer").asDouble()
            );
        }

        SavingsAccount savingsAccount = null;
        if (hasSavingsAccount) {
            ObjectNode savingsAccountNode = objectMapper.createObjectNode();
            savingsAccountNode.put("accountNumber", Accounts.createAccountNumber());
            savingsAccountNode.put("accountType", "Savings Account");
            savingsAccountNode.put("balance", 0.0);
            savingsAccountNode.put("withdraw", true);
            savingsAccountNode.put("deposit", true);
            savingsAccountNode.put("transfer", true);
            customerNode.set("savingsAccount", savingsAccountNode);

            // Create an instance of Savings Account
            savingsAccount = new SavingsAccount(
                    savingsAccountNode.get("accountNumber").asInt(),
                    savingsAccountNode.get("accountType").asText(),
                    savingsAccountNode.get("balance").asDouble(),
                    savingsAccountNode.get("withdraw").asDouble(),
                    savingsAccountNode.get("deposit").asDouble(),
                    savingsAccountNode.get("transfer").asDouble()
            );
        }

        // Generate the PIN
        String generatedPin = Customer.createPin();

        // Set the PIN to the customerNode
        customerNode.put("pin", generatedPin);

        System.out.println("Your Pin Number is : " + generatedPin);
        System.out.println("Keep this safe and do not show anybody else");

        ArrayNode rootNode = (ArrayNode) objectMapper.readTree(jsonFile);

        boolean foundBranch = false;
        for (JsonNode branchNode : rootNode) {
            if (branchNode.get("branchName").asText().equals(branchName)) {
                ArrayNode customersArray = (ArrayNode) branchNode.get("customers");
                customersArray.add(customerNode);
                foundBranch = true;
                break;
            }
        }

        if (!foundBranch) {
            ObjectNode newBranchNode = objectMapper.createObjectNode();
            newBranchNode.put("branchName", branchName);

            ArrayNode customersArray = objectMapper.createArrayNode();
            customersArray.add(customerNode);

            newBranchNode.set("customers", customersArray);
            rootNode.add(newBranchNode);
        }

        objectMapper.writeValue(jsonFile, rootNode);

        Customer customer = new Customer(
                firstName,
                lastName,
                dateOfBirth,
                email,
                generatedPin,
                hasCurrentAccount,
                hasSavingsAccount,
                currentAccount,
                savingsAccount
        );

        bank.getCustomerManager().addCustomer(customer);

        System.out.println("Customer added successfully.");
        return customer;
    }

    public static void createBranches(Bank bank, File jsonFile) throws IOException {
        JsonNode rootNode = objectMapper.readTree(jsonFile);
        for (JsonNode branchNode : rootNode) {
            String branchName = branchNode.get("branchName").asText();
            Branch branch = new Branch(branchName, new ArrayList<>());
            bank.addBranches(branch);
        }
    }

    public static Customer createCustomer(Bank bank) throws IOException {
        JsonNode rootNode = objectMapper.readTree(jsonFile);
        Customer customer = null; // Declare a Customer variable

        for (JsonNode branchNode : rootNode) {
            String branchName = branchNode.get("branchName").asText();
            Branch branch = bank.getBranchByName(branchName);
            if (branch == null) {
                branch = new Branch(branchName, new ArrayList<>());
                bank.addBranches(branch);
            }

            JsonNode customersNode = branchNode.get("customers");

            for (JsonNode customerNode : customersNode) {
                String firstName = customerNode.get("firstName").asText();
                String lastName = customerNode.get("lastName").asText();
                String dateOfBirth = customerNode.get("dateOfBirth").asText();
                String email = customerNode.get("email").asText();
                String pin = customerNode.get("pin").asText();
                boolean hasCurrentAccount = customerNode.get("hasCurrentAccount").asBoolean();
                boolean hasSavingsAccount = customerNode.get("hasSavingsAccount").asBoolean();

                // Initialize CurrentAccount and SavingsAccount as null
                CurrentAccount currentAccount = null;
                SavingsAccount savingsAccount = null;

                // Check if hasCurrentAccount is true, and create CurrentAccount if so
                if (hasCurrentAccount) {
                    JsonNode currentAccountNode = customerNode.get("currentAccount");
                    if (currentAccountNode != null) {
                        int accountNumber = currentAccountNode.get("accountNumber").asInt();
                        String accountType = currentAccountNode.get("accountType").asText();
                        double balance = currentAccountNode.get("balance").asDouble();
                        double withdraw = currentAccountNode.get("withdraw").asDouble();
                        double deposit = currentAccountNode.get("deposit").asDouble();
                        double transfer = currentAccountNode.get("transfer").asDouble();

                        currentAccount = new CurrentAccount(accountNumber, accountType, balance, withdraw, deposit, transfer);
                    }
                }

                // Check if hasSavingsAccount is true, and create SavingsAccount if so
                if (hasSavingsAccount) {
                    JsonNode savingsAccountNode = customerNode.get("savingsAccount");
                    if (savingsAccountNode != null) {
                        int accountNumber = savingsAccountNode.get("accountNumber").asInt();
                        String accountType = savingsAccountNode.get("accountType").asText();
                        double balance = savingsAccountNode.get("balance").asDouble();
                        double withdraw = savingsAccountNode.get("withdraw").asDouble();
                        double deposit = savingsAccountNode.get("deposit").asDouble();
                        double transfer = savingsAccountNode.get("transfer").asDouble();

                        savingsAccount = new SavingsAccount(accountNumber, accountType, balance, withdraw, deposit, transfer);
                    }
                }

                // Create the customer with CurrentAccount, SavingsAccount, and the generated PIN
                customer = new Customer(firstName, lastName, dateOfBirth, email, pin, hasCurrentAccount, hasSavingsAccount, currentAccount, savingsAccount);
                branch.addCustomer(customer);
            }
        }

        return customer; // Return the created customer
    }



}







