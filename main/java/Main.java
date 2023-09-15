import com.fasterxml.jackson.databind.JsonNode;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        File jsonFile = new File("/Users/version2.0/nologyWork/Java course/CbsBankingProject/src/main/java/MockData.json");

        Bank bank = new Bank();
        JacksonReader.createBranches(bank, jsonFile);
        JacksonReader.createCustomer(bank);
        CustomerManager customerManager = bank.getCustomerManager();
        bank.bankGreeting(bank);







    }



}


