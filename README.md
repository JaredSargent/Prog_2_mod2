Main Program Flow
BEGIN
    // Prompt user for initial bank balance
    PROMPT "Enter initial bank balance:"
    READ userInput

    // Validate and set the initial balance
    IF userInput is a valid number THEN
        SET initialBalance = parsed number
    ELSE
        DISPLAY "Invalid input. Starting balance set to $0."
        SET initialBalance = 0
    ENDIF

    // Create a BankAccount object with the initial balance
    CREATE account = new BankAccount(initialBalance)

    // Initialize and display the GUI
    CREATE new BankGUI(account)
END


BankGUI Class

CLASS BankGUI EXTENDS JFrame IMPLEMENTS ActionListener

    // Instance variables
    VARIABLE account       // BankAccount object
    VARIABLE balanceLabel  // Label to display the current balance
    VARIABLE depositButton // Button for deposits
    VARIABLE withdrawButton// Button for withdrawals
    VARIABLE exitButton    // Button to exit the application

    CONSTRUCTOR BankGUI(account):
        SET this.account = account

        // Setup JFrame properties
        SET title = "Bank Balance Application"
        SET size = (400, 200)
        SET default close operation to EXIT_ON_CLOSE
        CENTER the frame on screen

        // Create a JPanel with a simple layout
        CREATE panel with FlowLayout

        // Initialize and add the balance label
        SET balanceLabel = "Current Balance: $" + account.getBalance()
        ADD balanceLabel to panel

        // Initialize buttons and add action listeners
        CREATE depositButton labeled "Deposit"
        CREATE withdrawButton labeled "Withdraw"
        CREATE exitButton labeled "Exit"
        ADD this (as ActionListener) to each button
        ADD depositButton, withdrawButton, exitButton to panel

        // Add panel to frame and make frame visible
        ADD panel to JFrame
        DISPLAY the JFrame

    METHOD actionPerformed(event):
        IF event source is depositButton THEN
            PROMPT "Enter amount to deposit:"
            READ depositInput
            IF depositInput is valid THEN
                PARSE deposit amount as double
                CALL account.deposit(deposit amount)
                CALL updateBalance()
            ELSE
                DISPLAY error "Invalid amount entered."
            ENDIF

        ELSE IF event source is withdrawButton THEN
            PROMPT "Enter amount to withdraw:"
            READ withdrawInput
            IF withdrawInput is valid THEN
                PARSE withdrawal amount as double
                IF withdrawal amount > account.getBalance() THEN
                    DISPLAY error "Insufficient funds."
                ELSE
                    CALL account.withdraw(withdrawal amount)
                    CALL updateBalance()
                ENDIF
            ELSE
                DISPLAY error "Invalid amount entered."
            ENDIF

        ELSE IF event source is exitButton THEN
            DISPLAY "Final Balance: $" + account.getBalance()
            EXIT the application
        ENDIF

    METHOD updateBalance():
        // Refresh the label to show current balance
        SET balanceLabel text to "Current Balance: $" + account.getBalance()
END CLASS


BankAccount Class

CLASS BankAccount
    VARIABLE balance as double

    CONSTRUCTOR BankAccount(initialBalance):
        IF initialBalance < 0 THEN
            SET balance = 0
        ELSE
            SET balance = initialBalance
        ENDIF

    METHOD deposit(amount):
        IF amount > 0 THEN
            INCREASE balance by amount
        ENDIF

    METHOD withdraw(amount):
        IF amount > 0 AND amount <= balance THEN
            DECREASE balance by amount
        ENDIF

    METHOD getBalance():
        RETURN balance
END CLASS
