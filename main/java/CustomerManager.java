import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CustomerManager {
    private List<Customer> inMemoryCustomerData;
    private File jsonFile;

    public CustomerManager(File jsonFile) {
        this.inMemoryCustomerData = new ArrayList<>();
        this.jsonFile = jsonFile;
    }

    public void loadInitialData() throws IOException {
        // Read JSON data and populate inMemoryCustomerData
    }

    // Add a new customer to in-memory data structure
    public void addCustomer(Customer customer) {
        inMemoryCustomerData.add(customer);

    }

    // Update customer data in in-memory data structure
    public void updateCustomer(Customer customer) {
        // Find and update the customer in inMemoryCustomerData
    }

    // Remove a customer from in-memory data structure
    public void removeCustomer(Customer customer) {
        inMemoryCustomerData.remove(customer);
    }

    // Save in-memory data to JSON file
    public void saveDataToJson() throws IOException {
        // Write inMemoryCustomerData to the JSON file
    }
    public Customer findCustomerByName(String firstName, String lastName) {
        for (Customer customer : inMemoryCustomerData) {
            if (customer.getFirstName().equals(firstName) && customer.getLastName().equals(lastName)) {
                return customer;
            }
        }
        return null; // Customer not found
    }



    // Retrieve customer data from in-memory data structure
    public List<Customer> getCustomers() {
        return inMemoryCustomerData;
    }
}
