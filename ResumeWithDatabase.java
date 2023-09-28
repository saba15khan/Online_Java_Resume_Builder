package saba;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ResumeWithDatabase extends JFrame {
    private JTextField nameField;
    private JTextField emailField;
    private JTextArea addressArea;
    private JTextArea educationArea;
    private JTextArea experienceArea;
    private JTextArea skillsArea;
    private JTextArea achievementsArea;
    private JTextArea certificatesArea;

    private static final String DB_URL = "jdbc:mysql://localhost:3307/saba";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "password";

    public ResumeWithDatabase() {
        setTitle("Resume Builder with Database");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(9, 2, 10, 10));

        mainPanel.add(new JLabel("Name:"));
        nameField = new JTextField();
        mainPanel.add(nameField);

        mainPanel.add(new JLabel("Email:"));
        emailField = new JTextField();
        mainPanel.add(emailField);

        mainPanel.add(new JLabel("Address:"));
        addressArea = new JTextArea(3, 20);
        mainPanel.add(new JScrollPane(addressArea));

        mainPanel.add(new JLabel("Education:"));
        educationArea = new JTextArea(5, 20);
        mainPanel.add(new JScrollPane(educationArea));

        mainPanel.add(new JLabel("Experience:"));
        experienceArea = new JTextArea(5, 20);
        mainPanel.add(new JScrollPane(experienceArea));

        mainPanel.add(new JLabel("Skills:"));
        skillsArea = new JTextArea(5, 20);
        mainPanel.add(new JScrollPane(skillsArea));

        mainPanel.add(new JLabel("Achievements:"));
        achievementsArea = new JTextArea(3, 20);
        mainPanel.add(new JScrollPane(achievementsArea));

        mainPanel.add(new JLabel("Certificates:"));
        certificatesArea = new JTextArea(3, 20);
        mainPanel.add(new JScrollPane(certificatesArea));

        JButton generateButton = new JButton("Generate Resume");
        generateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                generateResume();
            }
        });
        mainPanel.add(generateButton);

        JButton saveButton = new JButton("Save Resume");
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveResume();
            }
        });
        mainPanel.add(saveButton);

        JButton loadButton = new JButton("Load Resume");
        loadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadResume();
            }
        });
        mainPanel.add(loadButton);

        JButton clearButton = new JButton("Clear Fields");
        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearFields();
            }
        });
        mainPanel.add(clearButton);

        add(mainPanel);
    }

    private void generateResume() {
        String name = nameField.getText();
        String email = emailField.getText();
        String address = addressArea.getText();
        String education = educationArea.getText();
        String experience = experienceArea.getText();
        String skills = skillsArea.getText();
        String achievements = achievementsArea.getText();
        String certificates = certificatesArea.getText();

        String resume = "<html><head><style>";
        resume += "body { font-family: Arial, sans-serif; }";
        resume += "h1 { color: #007acc; }";
        resume += "h2 { color: #333; }";
        resume += "p { margin: 0; }";
        resume += "</style></head><body>";
        resume += "<h1>" + name + "</h1>";
        resume += "<p><strong>Email:</strong> " + email + "</p>";
        resume += "<p><strong>Address:</strong><br>" + address + "</p>";
        resume += "<h2>Education</h2>";
        resume += "<p>" + education + "</p>";
        resume += "<h2>Experience</h2>";
        resume += "<p>" + experience + "</p>";
        resume += "<h2>Skills</h2>";
        resume += "<p>" + skills + "</p>";
        resume += "<h2>Achievements</h2>";
        resume += "<p>" + achievements + "</p>";
        resume += "<h2>Certificates</h2>";
        resume += "<p>" + certificates + "</p>";
        resume += "</body></html>";

        
        JFrame resumeWindow = new JFrame("Generated Resume");
        JTextPane textPane = new JTextPane();
        textPane.setContentType("text/html");
        textPane.setText(resume);
        textPane.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textPane);
        resumeWindow.add(scrollPane);
        resumeWindow.setSize(800, 600);
        resumeWindow.setVisible(true);
    }

    private void saveResume() {
        
        String name = nameField.getText();
        String email = emailField.getText();
        String address = addressArea.getText();
        String education = educationArea.getText();
        String experience = experienceArea.getText();
        String skills = skillsArea.getText();
        String achievements = achievementsArea.getText();
        String certificates = certificatesArea.getText();

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String query = "INSERT INTO resumes (name, email, address, education, experience, skills, achievements, certificates) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, email);
            preparedStatement.setString(3, address);
            preparedStatement.setString(4, education);
            preparedStatement.setString(5, experience);
            preparedStatement.setString(6, skills);
            preparedStatement.setString(7, achievements);
            preparedStatement.setString(8, certificates);

            preparedStatement.executeUpdate();
            JOptionPane.showMessageDialog(this, "Resume saved to database.", "Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error saving resume to the database.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }


    private void clearFields() {
        nameField.setText("");
        emailField.setText("");
        addressArea.setText("");
        educationArea.setText("");
        experienceArea.setText("");
        skillsArea.setText("");
        achievementsArea.setText("");
        certificatesArea.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new ResumeWithDatabase().setVisible(true);
            }
        });
    }
}
