import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SGPAUI extends JFrame {

    private JTextField nameField;
    private JTextField usnField;
    private JTextField subnoField;
    private JTextField[] subFields;
    private JTextField[] ccFields;
    private JTextField[] marksFields;

    public SGPAUI() {
        // Frame setup
        setTitle("SGPA Calculator");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center the window

        // Main panel setup
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 2, 10, 10)); // GridLayout with padding

        // Name field setup
        JLabel nameLabel = new JLabel("Name:");
        nameField = new JTextField();
        panel.add(nameLabel);
        panel.add(nameField);

        // USN field setup
        JLabel usnLabel = new JLabel("USN:");
        usnField = new JTextField();
        panel.add(usnLabel);
        panel.add(usnField);

        // Number of subjects field setup
        JLabel subnoLabel = new JLabel("No. of subjects:");
        subnoField = new JTextField();
        panel.add(subnoLabel);
        panel.add(subnoField);

        // Calculate Button
        JButton calculateButton = new JButton("Enter Subject Details");
        calculateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Get number of subjects and show dynamic fields
                int subno = Integer.parseInt(subnoField.getText());
                displaySubjectInputFields(subno);
            }
        });

        // Add the panel and button to the frame
        add(panel, BorderLayout.NORTH);
        add(calculateButton, BorderLayout.SOUTH);
    }

    private void displaySubjectInputFields(int subno) {
        // Create a new frame to enter subject details
        JFrame subjectFrame = new JFrame("Enter Subject Details");
        subjectFrame.setSize(600, 400);
        subjectFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        subjectFrame.setLocationRelativeTo(null); // Center the window

        // Create the panel for subject input
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(subno + 1, 6, 10, 10)); // GridLayout for subjects

        subFields = new JTextField[subno];
        ccFields = new JTextField[subno];
        marksFields = new JTextField[subno];

        // Add subject input fields dynamically
        for (int i = 0; i < subno; i++) {
            panel.add(new JLabel("Subject " + (i + 1) + ":"));
            subFields[i] = new JTextField();
            panel.add(subFields[i]);

            panel.add(new JLabel("Credits:"));
            ccFields[i] = new JTextField();
            panel.add(ccFields[i]);

            panel.add(new JLabel("Marks:"));
            marksFields[i] = new JTextField();
            panel.add(marksFields[i]);
        }

        // Submit button to calculate SGPA
        JButton submitButton = new JButton("Submit");
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                submitSGPA(subno);
            }
        });

        // Add the submit button to the panel
        panel.add(submitButton);
        subjectFrame.add(panel);
        subjectFrame.setVisible(true);
    }

    private void submitSGPA(int subno) {
        double totalCredits = 0.0;
        double weightedMarks = 0.0;
        double sgpa = 0.0;
        String reaction = "";

        // Arrays to store Credits and Marks
        int[] cc = new int[subno];
        int[] marks = new int[subno];
        int[] grades = new int[subno];

        // Gather subject credits and marks, and calculate total credits and weighted marks
        for (int i = 0; i < subno; i++) {
            cc[i] = Integer.parseInt(ccFields[i].getText());
            marks[i] = Integer.parseInt(marksFields[i].getText());
            totalCredits += cc[i];

            // Assign grades based on marks
            if (marks[i] >= 90) grades[i] = 10;
            else if (marks[i] >= 80) grades[i] = 9;
            else if (marks[i] >= 70) grades[i] = 8;
            else if (marks[i] >= 60) grades[i] = 7;
            else if (marks[i] >= 45) grades[i] = 6;
            else if (marks[i] >= 40) grades[i] = 5;
            else grades[i] = 0;

            // Calculate weighted marks (credits * grade)
            weightedMarks += cc[i] * grades[i];
        }

        // Calculate SGPA
        sgpa = weightedMarks / totalCredits;

        // Reaction based on SGPA value
        if (sgpa >= 9) reaction = ":)))";
        else if (sgpa >= 8) reaction = ":))";
        else if (sgpa >= 7) reaction = ":)";
        else if (sgpa >= 5) reaction = ":o";
        else reaction = ":(";

        // Output results in the console
        JOptionPane.showMessageDialog(this,
                "Name: " + nameField.getText() + "\n" +
                        "USN: " + usnField.getText() + "\n" +
                        "SGPA = " + sgpa + "\n" +
                        "Reaction: " + reaction,
                "SGPA Result", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void main(String[] args) {
        // Launch the application
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new SGPAUI().setVisible(true);
            }
        });
    }
}
