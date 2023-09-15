import java.util.ArrayList;
import java.util.List;

public class Branch {


    private String branchName;
    private ArrayList <Customer> customerList;

    public Branch(String branchName, ArrayList<Customer> customerList) {
        this.branchName = branchName;
        this.customerList = customerList;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public ArrayList<Customer> getCustomerList() {
        return customerList;
    }
    public void addCustomer(Customer customer) {
        customerList.add(customer);
    }



    public void setCustomerList(ArrayList<Customer> customerList) {
        this.customerList = customerList;
    }
}
