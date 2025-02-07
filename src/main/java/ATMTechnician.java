class ATMTechnician {
    private String password;
    private double atmBalance;
    private double cashInMachine;
    private double maxWithdrawLimit;
    private double inkLevel;
    private double paperLevel;

    public ATMTechnician(String password, double atmBalance, double cashInMachine, double maxWithdrawLimit, double inkLevel, double paperLevel) {
        this.password = password;
        this.atmBalance = atmBalance;
        this.cashInMachine = cashInMachine;
        this.maxWithdrawLimit = maxWithdrawLimit;
        this.inkLevel = inkLevel;
        this.paperLevel = paperLevel;
    }

    public boolean validatePassword(String enteredPassword) {
        return password.equals(enteredPassword);
    }

    public boolean withdrawCash(double amount) {
        if (amount <= cashInMachine) {
            cashInMachine -= amount;
            // Decrease ink and paper after a successful withdrawal
            decreaseInkLevel();
            decreasePaperLevel();
            return true;
        }
        return false;
    }

    public void addCash(double amount) {
        this.cashInMachine += amount;
        System.out.println("Cash added successfully. New cash level: " + cashInMachine);
    }

    public void addInk(double amount) {
        this.inkLevel += amount;
        System.out.println("Ink added successfully. New ink level: " + inkLevel);
    }

    public void addPaper(double amount) {
        this.paperLevel += amount;
        System.out.println("Paper added successfully. New paper level: " + paperLevel);
    }

    // Decrease ink level by 1 unit after each transaction
    public void decreaseInkLevel() {
        if (inkLevel > 0) {
            inkLevel -= 1.0;  // Assume 1 unit of ink is used per transaction
            System.out.println("Ink level decreased. Current ink level: " + inkLevel);
        } else {
            System.out.println("Ink level is too low for printing.");
        }
    }

    // Decrease paper level by 1 unit after each transaction
    public void decreasePaperLevel() {
        if (paperLevel > 0) {
            paperLevel -= 1.0;  // Assume 1 unit of paper is used per transaction
            System.out.println("Paper level decreased. Current paper level: " + paperLevel);
        } else {
            System.out.println("Paper level is too low for printing.");
        }
    }

    public void viewATMStatus() {
        System.out.println("ATM Status:");
        System.out.println("Cash in machine: " + cashInMachine);
        System.out.println("Ink level: " + inkLevel);
        System.out.println("Paper level: " + paperLevel);
    }
}
