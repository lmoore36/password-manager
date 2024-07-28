package assn07;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Set;

public class PasswordManagerApp extends JFrame {
    private Map<String, String> passwordManager;
    private JTextArea displayArea;
    private JTextField websiteField;
    private JTextField passwordField;
    private JTextField masterPasswordField;

    public PasswordManagerApp() {
        passwordManager = new PasswordManager<>();
        initializeGUI();
    }

    private void initializeGUI() {
        setTitle("Password Manager");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(6, 2));

        JLabel masterPasswordLabel = new JLabel("Master Password:");
        masterPasswordField = new JPasswordField();
        panel.add(masterPasswordLabel);
        panel.add(masterPasswordField);

        JButton loginButton = new JButton("Login");
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String enteredPassword = masterPasswordField.getText();
                if (passwordManager.checkMasterPassword(enteredPassword)) {
                    JOptionPane.showMessageDialog(null, "Login successful!");
                    showMainUI();
                } else {
                    JOptionPane.showMessageDialog(null, "Incorrect master password!");
                }
            }
        });
        panel.add(loginButton);

        displayArea = new JTextArea();
        displayArea.setEditable(false);

        add(panel, BorderLayout.NORTH);
        add(new JScrollPane(displayArea), BorderLayout.CENTER);
    }

    private void showMainUI() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(6, 2));

        JLabel websiteLabel = new JLabel("Website:");
        websiteField = new JTextField();
        panel.add(websiteLabel);
        panel.add(websiteField);

        JLabel passwordLabel = new JLabel("Password:");
        passwordField = new JTextField();
        panel.add(passwordLabel);
        panel.add(passwordField);

        JButton addButton = new JButton("Add Password");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String website = websiteField.getText();
                String password = passwordField.getText();
                passwordManager.put(website, password);
                displayArea.append("Added password for " + website + "\n");
                websiteField.setText("");
                passwordField.setText("");
            }
        });
        panel.add(addButton);

        JButton getButton = new JButton("Get Password");
        getButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String website = websiteField.getText();
                String password = passwordManager.get(website);
                if (password != null) {
                    displayArea.append("Password for " + website + ": " + password + "\n");
                } else {
                    displayArea.append("No account found for " + website + "\n");
                }
                websiteField.setText("");
            }
        });
        panel.add(getButton);

        JButton deleteButton = new JButton("Delete Account");
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String website = websiteField.getText();
                String removedPassword = passwordManager.remove(website);
                if (removedPassword != null) {
                    displayArea.append("Deleted account for " + website + "\n");
                } else {
                    displayArea.append("No account found for " + website + "\n");
                }
                websiteField.setText("");
            }
        });
        panel.add(deleteButton);

        JButton showAllButton = new JButton("Show All Accounts");
        showAllButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Set<String> websites = passwordManager.keySet();
                displayArea.append("All accounts:\n");
                for (String site : websites) {
                    displayArea.append(site + "\n");
                }
            }
        });
        panel.add(showAllButton);

        JButton generateButton = new JButton("Generate Random Password");
        generateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String lengthStr = JOptionPane.showInputDialog("Enter length of password:");
                int length = Integer.parseInt(lengthStr);
                String randomPassword = passwordManager.generateRandomPassword(length);
                displayArea.append("Generated password: " + randomPassword + "\n");
            }
        });
        panel.add(generateButton);

        add(panel, BorderLayout.SOUTH);
        revalidate();
        repaint();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new PasswordManagerApp().setVisible(true);
            }
        });
    }
}