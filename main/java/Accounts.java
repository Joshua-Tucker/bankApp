public class Accounts {
    private int accountNumber;
    private String accountType;
    private double balance;
    private double withdraw;
    private double deposit;
    private double transfer;


    public Accounts(int accountNumber, String accountType, double balance, double withdraw, double deposit, double transfer) {
        this.accountNumber = accountNumber;
        this.accountType = accountType;
        this.balance = balance;
        this.withdraw = withdraw;
        this.deposit = deposit;
        this.transfer = transfer;
    }



    public static int createAccountNumber(){
        int minRange = 10000000;
        int maxRange = 99999999;

        return generateNumber(minRange, maxRange);
    }

    public static double createBalance() {
        int wholePart = generateWholePart();
        int decimalPart = generateDecimalPart();
        return Double.parseDouble(wholePart + "." + decimalPart);
    }

    public static int generateWholePart() {
        int minWhole = -500;
        int maxWhole = 99999999;
        return (int) (Math.random() * (maxWhole - minWhole + 1)) + minWhole;
    }

    public static int generateDecimalPart() {
        int minDecimal = 0;
        int maxDecimal = 99;
        return (int) (Math.random() * (maxDecimal - minDecimal + 1)) + minDecimal;
    }


    public static int generateNumber(int min, int max) {
     return (int) (Math.random() * (max - min + 1)) + min;
 }


    public int getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(int accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public double getWithdraw() {
        return withdraw;
    }

    public void setWithdraw(double withdraw) {
        this.withdraw = withdraw;
    }

    public double getDeposit() {
        return deposit;
    }

    public void setDeposit(double deposit) {
        this.deposit = deposit;
    }

    public double getTransfer() {
        return transfer;
    }

    public void setTransfer(double transfer) {
        this.transfer = transfer;
    }

}
