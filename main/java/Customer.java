public class Customer {
    private String firstName;
    private String lastName;
    private String dateOfBirth;
    private String email;
    private String pin;
    private boolean hasCurrentAccount;
    private boolean hasSavingsAccount;
    private CurrentAccount currentAccount;



    private SavingsAccount savingsAccount;

    public Customer(String firstName, String lastName, String dateOfBirth, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.email = email;
    }
    public Customer(String firstName, String lastName, String dateOfBirth, String email, String pin, boolean hasCurrentAccount, boolean hasSavingsAccount, CurrentAccount currentAccount, SavingsAccount savingsAccount) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.email = email;
        this.pin = pin;
        this.hasCurrentAccount = hasCurrentAccount;
        this.hasSavingsAccount = hasSavingsAccount;
        this.currentAccount = currentAccount;
        this.savingsAccount = savingsAccount;
    }


    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public CurrentAccount getCurrentAccount() {
        return currentAccount;
    }


    public SavingsAccount getSavingsAccount() {
        return savingsAccount;
    }

    public boolean hasCurrentAccount() {
        return hasCurrentAccount;
    }

    public void setHasCurrentAccount(boolean hasCurrentAccount) {
        this.hasCurrentAccount = hasCurrentAccount;
    }

    public boolean hasSavingsAccount() {
        return hasSavingsAccount;
    }

    public void setHasSavingsAccount(boolean hasSavingsAccount) {
        this.hasSavingsAccount = hasSavingsAccount;
    }

    public static String createPin() {
        int minRange = 1000;
        int maxRange = 9999;
        String pinValue = String.valueOf(generatePin(minRange, maxRange));

        return pinValue;
    }

    public static int generatePin(int min, int max) {
        return (int) (Math.random() * (max - min + 1)) + min;
    }
}
