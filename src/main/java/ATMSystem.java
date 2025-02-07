import java.io.*;
import java.util.*;

public class ATMSystem {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ATMTechnician technician = new ATMTechnician("admin123", 5000.0, 1000.0, 1000.0, 100.0, 100.0);

        while (true) {
            System.out.println("\nATM Service:");
            System.out.println("1. User Login");
            System.out.println("2. Technician Login");
            System.out.println("3. Create New User");
            System.out.println("4. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            if (choice == 1) {
                System.out.print("Enter Account ID: ");
                String accID = scanner.nextLine();
                System.out.print("Enter Security Code: ");
                String secCode = scanner.nextLine();
                try {
                    BankUser user = BankUser.retrieveUser(accID, secCode);
                    if (user != null) {
                        userSession(user, technician);
                    } else {
                        System.out.println("Invalid login credentials.");
                    }
                } catch (Exception e) {
                    System.out.println("Error during user login: " + e.getMessage());
                }
            } else if (choice == 2) {
                System.out.print("Enter Technician Password: ");
                String password = scanner.nextLine();
                if (technician.validatePassword(password)) {
                    technicianSession(technician);
                } else {
                    System.out.println("Incorrect technician password.");
                }
            } else if (choice == 3) {
                // Create New User
                System.out.print("Enter New Account ID: ");
                String newAccID = scanner.nextLine();
                System.out.print("Enter New Security Code: ");
                String newSecCode = scanner.nextLine();
                System.out.print("Enter Initial Balance: ");
                double initialBalance = scanner.nextDouble();
                BankUser.createNewUser(newAccID, newSecCode, initialBalance);
            } else {
                System.out.println("Goodbye!");
                break;
            }
        }
    }

    private static void technicianSession(ATMTechnician technician) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\nTechnician Session:");
            System.out.println("1. View ATM Status");
            System.out.println("2. Add Cash to ATM");
            System.out.println("3. Add Ink to ATM");
            System.out.println("4. Add Paper to ATM");
            System.out.println("5. Exit Technician Session");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    technician.viewATMStatus();
                    break;
                case 2:
                    System.out.print("Enter amount to add: ");
                    double cashAmount = scanner.nextDouble();
                    technician.addCash(cashAmount);
                    break;
                case 3:
                    System.out.print("Enter ink amount to add: ");
                    double inkAmount = scanner.nextDouble();
                    technician.addInk(inkAmount);
                    break;
                case 4:
                    System.out.print("Enter paper amount to add: ");
                    double paperAmount = scanner.nextDouble();
                    technician.addPaper(paperAmount);
                    break;
                case 5:
                    return; // Exit the technician session
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void userSession(BankUser user, ATMTechnician technician) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\nUser Session:");
            System.out.println("1. View Account Balance");
            System.out.println("2. Withdraw Funds");
            System.out.println("3. Deposit Funds");
            System.out.println("4. Transfer Funds");
            System.out.println("5. Exit User Session");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    System.out.println("Your balance is: " + user.getBalance());
                    break;
                case 2:
                    System.out.print("Enter amount to withdraw: ");
                    double withdrawAmount = scanner.nextDouble();
                    try {
                        user.withdrawFunds(withdrawAmount);
                        // Attempt withdrawal from the ATM
                        if (technician.withdrawCash(withdrawAmount)) {
                            System.out.println("Withdrawal successful.");
                            technician.viewATMStatus(); // View ATM status after withdrawal
                        } else {
                            System.out.println("ATM does not have enough cash.");
                        }
                    } catch (Exception e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                    break;
                case 3:
                    System.out.print("Enter amount to deposit: ");
                    double depositAmount = scanner.nextDouble();
                    user.depositFunds(depositAmount);
                    break;
                case 4:
                    // Transfer funds logic
                    System.out.print("Enter recipient Account ID: ");
                    String recipientID = scanner.nextLine();
                    System.out.print("Enter recipient Security Code: ");
                    String recipientSecCode = scanner.nextLine();

                    // Retrieve the recipient user
                    try {
                        BankUser recipient = BankUser.retrieveUser(recipientID, recipientSecCode);
                        if (recipient != null) {
                            System.out.print("Enter amount to transfer: ");
                            double transferAmount = scanner.nextDouble();
                            try {
                                user.transferFunds(transferAmount, recipient);
                            } catch (Exception e) {
                                System.out.println("Error transferring funds: " + e.getMessage());
                            }
                        } else {
                            System.out.println("Recipient not found.");
                        }
                    } catch (Exception e) {
                        System.out.println("Error retrieving recipient: " + e.getMessage());
                    }
                    break;
                case 5:
                    return; // Exit the user session
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

}
