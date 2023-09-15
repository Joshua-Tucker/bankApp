import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.IOException;

public class CurrentAccount extends Accounts{


    public CurrentAccount(int accountNumber, String accountType, double balance, double withdraw, double deposit, double transfer) {
        super(accountNumber, accountType, balance, withdraw, deposit, transfer);
    }



}
