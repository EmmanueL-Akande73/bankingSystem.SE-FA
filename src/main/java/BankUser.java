import java.io.*;

class BankUser {
    private String accountID;
    private String securityCode;
    private double balance;

    public BankUser(String accountID, String securityCode, double balance) {
        this.accountID = accountID;
        this.securityCode = securityCode;
        this.balance = balance;
    }

    // Retrieve the user by account ID and security code
    public static BankUser retrieveUser(String accountID, String securityCode) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("user_data.txt"));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] userData = line.split(",");
                if (userData.length == 3 && userData[0].equals(accountID) && userData[1].equals(securityCode)) {
                    reader.close();
                    return new BankUser(userData[0], userData[1], Double.parseDouble(userData[2]));
                }
            }
            reader.close();
        } catch (IOException e) {
            System.out.println("Error reading user data: " + e.getMessage());
        }
        System.out.println("User not found with provided credentials.");
        return null;
    }

    // Save the user's data to user_data.txt
    public static void saveUser(BankUser user) {
        try {
            File file = new File("user_data.txt");
            if (!file.exists()) {
                file.createNewFile();  // Create the file if it doesn't exist
            }

            // Read the file, and store all users in a list, replacing the updated user
            BufferedReader reader = new BufferedReader(new FileReader(file));
            StringBuilder content = new StringBuilder();
            String line;
            boolean userFound = false;
            while ((line = reader.readLine()) != null) {
                String[] userData = line.split(",");
                if (userData[0].equals(user.accountID)) {
                    content.append(user.accountID).append(",").append(user.securityCode).append(",").append(user.balance).append("\n");
                    userFound = true;
                } else {
                    content.append(line).append("\n");
                }
            }
            reader.close();

            // If user is not found, append new user data
            if (!userFound) {
                content.append(user.accountID).append(",").append(user.securityCode).append(",").append(user.balance).append("\n");
            }

            // Write back the updated content to the file
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            writer.write(content.toString());
            writer.close();
            System.out.println("User data saved.");
        } catch (IOException e) {
            System.out.println("Error saving user data: " + e.getMessage());
        }
    }

    // Create new user and save their data
    public static void createNewUser(String accountID, String securityCode, double initialBalance) {
        BankUser newUser = new BankUser(accountID, securityCode, initialBalance);
        saveUser(newUser);
        System.out.println("New user created with account ID: " + accountID);
    }

    // Getter for balance
    public double getBalance() {
        return balance;
    }

    // Method to withdraw funds
    public void withdrawFunds(double amount) throws Exception {
        if (amount > balance) {
            throw new Exception("Insufficient balance.");
        }
        balance -= amount;
        System.out.println("Remaining balance: " + balance);
    }

    // Method to deposit funds
    public void depositFunds(double amount) {
        balance += amount;
        System.out.println("Deposit successful. New balance: " + balance);
    }

    // Method to transfer funds to another user
    public void transferFunds(double amount, BankUser recipient) throws Exception {
        if (amount > balance) {
            throw new Exception("Insufficient balance.");
        }
        balance -= amount;
        recipient.balance += amount;
        System.out.println("Transferred " + amount + " to " + recipient.accountID);
        saveUser(this);  // Save sender's data
        saveUser(recipient);  // Save recipient's data
    }
}
