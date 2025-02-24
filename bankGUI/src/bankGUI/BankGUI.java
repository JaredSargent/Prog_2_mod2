package bankGUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class BankGUI extends JFrame implements ActionListener {
    private BankAccount account;
    private JLabel balanceLabel;
    private JButton depositButton, withdrawButton, exitButton;
    
    public BankGUI(BankAccount account) {
        this.account = account;
        
        // Set up the frame
        setTitle("Bank Balance Application");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        // Create a JPanel and set its layout
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());
        
        // Create and add a label to display the current balance
        balanceLabel = new JLabel("Current Balance: $" + account.getBalance());
        panel.add(balanceLabel);
        
        // Create buttons for deposit, withdraw, and exit
        depositButton = new JButton("Deposit");
        withdrawButton = new JButton("Withdraw");
        exitButton = new JButton("Exit");
        
        // Attach ActionListeners to the buttons
        depositButton.addActionListener(this);
        withdrawButton.addActionListener(this);
        exitButton.addActionListener(this);
        
        // Add the buttons to the panel
        panel.add(depositButton);
        panel.add(withdrawButton);
        panel.add(exitButton);
        
        // Add the panel to the frame and make it visible
        add(panel);
        setVisible(true);
    }
    
    // Handle button clicks
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == depositButton) {
            String input = JOptionPane.showInputDialog("Enter amount to deposit:");
            if (input != null && !input.isEmpty()){
                try {
                    double amount = Double.parseDouble(input);
                    account.deposit(amount);
                    updateBalance();
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "Invalid amount entered.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } else if (e.getSource() == withdrawButton) {
            String input = JOptionPane.showInputDialog("Enter amount to withdraw:");
            if (input != null && !input.isEmpty()){
                try {
                    double amount = Double.parseDouble(input);
                    if (amount > account.getBalance()){
                        JOptionPane.showMessageDialog(this, "Insufficient funds.", "Error", JOptionPane.ERROR_MESSAGE);
                    } else {
                        account.withdraw(amount);
                        updateBalance();
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "Invalid amount entered.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } else if (e.getSource() == exitButton) {
            JOptionPane.showMessageDialog(this, "Final Balance: $" + account.getBalance());
            System.exit(0);
        }
    }
    
    // Update the displayed balance
    private void updateBalance() {
        balanceLabel.setText("Current Balance: $" + account.getBalance());
    }
    
    public static void main(String[] args) {
        // Obtain the initial balance from the user
        String input = JOptionPane.showInputDialog("Enter initial bank balance:");
        double initialBalance = 0;
        try {
            initialBalance = Double.parseDouble(input);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Invalid input. Starting balance set to $0.");
        }
        
        // Create a BankAccount and start the GUI
        BankAccount account = new BankAccount(initialBalance);
        new BankGUI(account);
    }
}

// Simple BankAccount class with deposit and withdrawal methods
class BankAccount {
    private double balance;
    
    public BankAccount(double initialBalance) {
        this.balance = (initialBalance < 0) ? 0 : initialBalance;
    }
    
    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
        }
    }
    
    public void withdraw(double amount) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
        }
    }
    
    public double getBalance() {
        return balance;
    }
}
