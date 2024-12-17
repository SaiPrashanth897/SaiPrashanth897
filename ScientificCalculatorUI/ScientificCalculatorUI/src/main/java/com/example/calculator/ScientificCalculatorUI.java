package com.example.calculator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class ScientificCalculatorUI extends JFrame {
    private JTextField inputField;
    private JTextArea outputArea;

    public ScientificCalculatorUI() {
        setTitle("Scientific Calculator");
        setSize(400, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(240, 240, 240)); // Light gray background

        inputField = new JTextField();
        inputField.setFont(new Font("Arial", Font.BOLD, 24));
        inputField.setBackground(new Color(255, 255, 255)); // White background
        inputField.setForeground(new Color(0, 0, 0)); // Black text

        outputArea = new JTextArea();
        outputArea.setEditable(false);
        outputArea.setFont(new Font("Arial", Font.PLAIN, 18));
        outputArea.setBackground(new Color(255, 255, 255)); // White background
        outputArea.setForeground(new Color(0, 0, 0)); // Black text

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(9, 4));
        buttonPanel.setBackground(new Color(200, 200, 200)); // Light gray for button panel

        String[] buttons = {
                "7", "8", "9", "/",
                "4", "5", "6", "*",
                "1", "2", "3", "-",
                "0", ".", "=", "+",
                "Sin", "Cos", "Tan", "Log",
                "Sqrt", "Exp", "Clear", "Exit",
                "Matrix", "Conversions", "Complex", "Statistics"
        };

        for (String text : buttons) {
            JButton button = new JButton(text);
            button.setFont(new Font("Arial", Font.BOLD, 18));
            button.setBackground(new Color(100, 149, 237)); // Cornflower blue
            button.setForeground(Color.WHITE); // White text
            button.setFocusPainted(false);
            button.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 1));
            button.addActionListener(new ButtonClickListener());
            button.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseEntered(java.awt.event.MouseEvent evt) {
                    button.setBackground(new Color(70, 130, 180)); // Steel blue on hover
                }

                public void mouseExited(java.awt.event.MouseEvent evt) {
                    button.setBackground(new Color(100, 149, 237)); // Reset to original color
                }
            });
            buttonPanel.add(button);
        }

        add(inputField, BorderLayout.NORTH);
        add(new JScrollPane(outputArea), BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private class ButtonClickListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String command = e.getActionCommand();
            if (command.equals("=")) {
                calculateResult();
            } else if (command.equals("Clear")) {
                inputField.setText("");
                outputArea.setText("");
            } else if (command.equals("Exit")) {
                System.exit(0);
            } else if (command.equals("Matrix")) {
                matrixOperation();
            } else if (command.equals("Conversions")) {
                numberConversions();
            } else if (command.equals("Statistics")) {
                statisticalOperations();
            } else {
                inputField.setText(inputField.getText() + command);
            }
        }
    }

    private void calculateResult() {
        String input = inputField.getText();
        try {
            double result = evaluateExpression(input);
            outputArea.append(input + " = " + result + "\n");
            inputField.setText("");
        } catch (Exception e) {
            outputArea.append("Error: " + e.getMessage() + "\n");
        }
    }

    private double evaluateExpression(String expression) {
        // Check for scientific functions first
        if (expression.startsWith("Sin")) {
            return Math.sin(Math.toRadians(Double.parseDouble(expression.replace("Sin", "").trim())));
        } else if (expression.startsWith("Cos")) {
            return Math.cos(Math.toRadians(Double.parseDouble(expression.replace("Cos", "").trim())));
        } else if (expression.startsWith("Tan")) {
            return Math.tan(Math.toRadians(Double.parseDouble(expression.replace("Tan", "").trim())));
        } else if (expression.startsWith("Log")) {
            return Math.log10(Double.parseDouble(expression.replace("Log", "").trim()));
        } else if (expression.startsWith("Sqrt")) {
            return Math.sqrt(Double.parseDouble(expression.replace("Sqrt", "").trim()));
        } else if (expression.startsWith("Exp")) {
            return Math.exp(Double.parseDouble(expression.replace("Exp", "").trim()));
        } else {
            // Handle basic arithmetic operations
            String[] operators = {"+", "-", "*", "/"};
            for (String operator : operators) {
                if (expression.contains(operator)) {
                    String[] parts = expression.split("\\" + operator);
                    if (parts.length == 2) {
                        double num1 = Double.parseDouble(parts[0].trim());
                        double num2 = Double.parseDouble(parts[1].trim());

                        switch (operator) {
                            case "+":
                                return num1 + num2;
                            case "-":
                                return num1 - num2;
                            case "*":
                                return num1 * num2;
                            case "/":
                                if (num2 != 0) {
                                    return num1 / num2;
                                } else {
                                    throw new ArithmeticException("Division by zero");
                                }
                        }
                    }
                }
            }
            throw new IllegalArgumentException("Invalid expression");
        }
    }

    private void numberConversions() {
        // Create a dialog for unit conversions
        JDialog conversionDialog = new JDialog(this, "Unit Conversions", true);
        conversionDialog.setLayout(new GridLayout(5, 2));
        conversionDialog.setSize(300, 200);
        conversionDialog.getContentPane().setBackground(new Color(240, 240, 240)); // Light gray background

        JLabel conversionLabel = new JLabel("Enter value:");
        conversionLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        JTextField valueField = new JTextField();
        JButton lengthButton = new JButton("Length");
        JButton weightButton = new JButton("Weight");
        JButton temperatureButton = new JButton("Temperature");
        JButton closeButton = new JButton("Close");

        conversionDialog.add(conversionLabel);
        conversionDialog.add(valueField);
        conversionDialog.add(lengthButton);
        conversionDialog.add(weightButton);
        conversionDialog.add(temperatureButton);
        conversionDialog.add(closeButton);

        lengthButton.addActionListener(e -> performConversion(valueField, "length"));
        weightButton.addActionListener(e -> performConversion(valueField, "weight"));
        temperatureButton.addActionListener(e -> performConversion(valueField, "temperature"));
        closeButton.addActionListener(e -> conversionDialog.dispose());

        conversionDialog.setVisible(true);
    }

    private void performConversion(JTextField valueField, String type) {
        try {
            double value = Double.parseDouble(valueField.getText());
            String result = "";
            switch (type) {
                case "length":
                    result = "Meters to Feet: " + (value * 3.28084) + "\n" +
                            "Feet to Meters: " + (value / 3.28084);
                    break;
                case "weight":
                    result = "Kilograms to Pounds: " + (value * 2.20462) + "\n" +
                            "Pounds to Kilograms: " + (value / 2.20462);
                    break;
                case "temperature":
                    result = "Celsius to Fahrenheit: " + (value * 9/5 + 32) + "\n" +
                            "Fahrenheit to Celsius: " + ((value - 32) * 5/9);
                    break;
            }
            JOptionPane.showMessageDialog(this, result, type.substring(0, 1).toUpperCase() + type.substring(1) + " Conversion", JOptionPane.INFORMATION_MESSAGE);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid input. Please enter a numeric value.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void matrixOperation() {
        // Create a dialog for matrix operations
        JDialog matrixDialog = new JDialog(this, "Matrix Operations", true);
        matrixDialog.setSize(400, 300);
        matrixDialog.setLayout(new GridLayout(5, 1));
        matrixDialog.getContentPane().setBackground(new Color(240, 240, 240)); // Light gray background

        JTextField rowsField = new JTextField("Enter number of rows");
        JTextField colsField = new JTextField("Enter number of columns");
        JButton addButton = new JButton("Add Matrices");
        JButton subtractButton = new JButton("Subtract Matrices");
        JButton multiplyButton = new JButton("Multiply Matrices");

        matrixDialog.add(rowsField);
        matrixDialog.add(colsField);
        matrixDialog.add(addButton);
        matrixDialog.add(subtractButton);
        matrixDialog.add(multiplyButton);

        addButton.addActionListener(e -> performMatrixOperation(rowsField, colsField, "add"));
        subtractButton.addActionListener(e -> performMatrixOperation(rowsField, colsField, "subtract"));
        multiplyButton.addActionListener(e -> performMatrixOperation(rowsField, colsField, "multiply"));

        matrixDialog.setVisible(true);
    }

    private void performMatrixOperation(JTextField rowsField, JTextField colsField, String operation) {
        try {
            int rows = Integer.parseInt(rowsField.getText());
            int cols = Integer.parseInt(colsField.getText());
            double[][] matrixA = inputMatrix(rows, cols, "Matrix A");
            double[][] matrixB = inputMatrix(rows, cols, "Matrix B");
            double[][] result;

            switch (operation) {
                case "add":
                    result = addMatrices(matrixA, matrixB);
                    displayResult(result, "Addition Result");
                    break;
                case "subtract":
                    result = subtractMatrices(matrixA, matrixB);
                    displayResult(result, "Subtraction Result");
                    break;
                case "multiply":
                    int colsB = Integer.parseInt(JOptionPane.showInputDialog("Enter number of columns for Matrix B"));
                    matrixB = inputMatrix(cols, colsB, "Matrix B");
                    result = multiplyMatrices(matrixA, matrixB);
                    displayResult(result, "Multiplication Result");
                    break;
                default:
                    throw new IllegalArgumentException("Invalid operation");
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter valid numeric values for rows and columns.", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private double[][] inputMatrix(int rows, int cols, String matrixName) {
        double[][] matrix = new double[rows][cols];
        for (int i = 0; i < rows; i++) {
            String rowInput = JOptionPane.showInputDialog("Enter row " + (i + 1) + " of " + matrixName + " (comma-separated):");
            String[] values = rowInput.split(",");
            for (int j = 0; j < cols; j++) {
                matrix[i][j] = Double.parseDouble(values[j].trim());
            }
        }
        return matrix;
    }

    private double[][] addMatrices(double[][] a, double[][] b) {
        int rows = a.length;
        int cols = a[0].length;
        double[][] result = new double[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                result[i][j] = a[i][j] + b[i][j];
            }
        }
        return result;
    }

    private double[][] subtractMatrices(double[][] a, double[][] b) {
        int rows = a.length;
        int cols = a[0].length;
        double[][] result = new double[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                result[i][j] = a[i][j] - b[i][j];
            }
        }
        return result;
    }

    private double[][] multiplyMatrices(double[][] a, double[][] b) {
        int rowsA = a.length;
        int colsA = a[0].length;
        int colsB = b[0].length;
        double[][] result = new double[rowsA][colsB];
        for (int i = 0; i < rowsA; i++) {
            for (int j = 0; j < colsB; j++) {
                for (int k = 0; k < colsA; k++) {
                    result[i][j] += a[i][k] * b[k][j];
                }
            }
        }
        return result;
    }

    private void displayResult(double[][] result, String title) {
        StringBuilder resultString = new StringBuilder();
        for (double[] row : result) {
            for (double value : row) {
                resultString.append(value).append(" ");
            }
            resultString.append("\n");
        }
        JOptionPane.showMessageDialog(this, resultString.toString(), title, JOptionPane.INFORMATION_MESSAGE);
    }

    private
    void statisticalOperations() {
        // Use dialog boxes for statistical operations
        String[] options = {"Mean, Median, Mode", "Variance and Standard Deviation", "Correlation and Regression Analysis"};
        int choice = JOptionPane.showOptionDialog(this, "Choose statistical operation:", "Statistics",
                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);

        switch (choice) {
            case 0:
                meanMedianMode();
                break;
            case 1:
                varianceAndStandardDeviation();
                break;
            case 2:
                correlationAndRegression();
                break;
            default:
                JOptionPane.showMessageDialog(this, "Invalid choice.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void meanMedianMode() {
        String input = JOptionPane.showInputDialog("Enter the numbers (comma-separated):");
        String[] numberStrings = input.split(",");
        double[] numbers = Arrays.stream(numberStrings).mapToDouble(Double::parseDouble).toArray();

        // Calculate Mean
        double mean = Arrays.stream(numbers).average().orElse(0.0);
        StringBuilder result = new StringBuilder("Mean: " + mean + "\n");

        // Calculate Median
        Arrays.sort(numbers);
        double median;
        if (numbers.length % 2 == 0) {
            median = (numbers[numbers.length / 2 - 1] + numbers[numbers.length / 2]) / 2;
        } else {
            median = numbers[numbers.length / 2];
        }
        result.append("Median: ").append(median).append("\n");

        // Calculate Mode
        Map<Double, Integer> frequencyMap = new HashMap<>();
        for (double num : numbers) {
            frequencyMap.put(num, frequencyMap.getOrDefault(num, 0) + 1);
        }

        double mode = numbers[0];
        int maxCount = 0;
        for (Map.Entry<Double, Integer> entry : frequencyMap.entrySet()) {
            if (entry.getValue() > maxCount) {
                maxCount = entry.getValue();
                mode = entry.getKey();
            }
        }
        result.append("Mode: ").append(mode);
        JOptionPane.showMessageDialog(this, result.toString(), "Mean, Median, Mode", JOptionPane.INFORMATION_MESSAGE);
    }

    private void varianceAndStandardDeviation() {
        String input = JOptionPane.showInputDialog("Enter the numbers (comma-separated):");
        String[] numberStrings = input.split(",");
        double[] numbers = Arrays.stream(numberStrings).mapToDouble(Double::parseDouble).toArray();

        // Calculate Mean
        double mean = Arrays.stream(numbers).average().orElse(0.0);

        // Calculate Variance
        double variance = Arrays.stream(numbers)
                .map(num -> Math.pow(num - mean, 2))
                .average()
                .orElse(0.0);
        double standardDeviation = Math.sqrt(variance);

        String result = "Variance: " + variance + "\nStandard Deviation: " + standardDeviation;
        JOptionPane.showMessageDialog(this, result, "Variance and Standard Deviation", JOptionPane.INFORMATION_MESSAGE);
    }

    private void correlationAndRegression() {
        String inputX = JOptionPane.showInputDialog("Enter the x values (comma-separated):");
        String inputY = JOptionPane.showInputDialog("Enter the y values (comma-separated):");
        String[] xStrings = inputX.split(",");
        String[] yStrings = inputY.split(",");

        double[] x = Arrays.stream(xStrings).mapToDouble(Double::parseDouble).toArray();
        double[] y = Arrays.stream(yStrings).mapToDouble(Double::parseDouble).toArray();

        if (x.length != y.length) {
            JOptionPane.showMessageDialog(this, "The number of x and y values must be the same.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Calculate means
        double meanX = Arrays.stream(x).average().orElse(0.0);
        double meanY = Arrays.stream(y).average().orElse(0.0);

        // Calculate correlation
        double numerator = 0.0;
        double denominatorX = 0.0;
        double denominatorY = 0.0;

        for (int i = 0; i < x.length; i++) {
            numerator += (x[i] - meanX) * (y[i] - meanY);
            denominatorX += Math.pow(x[i] - meanX, 2);
            denominatorY += Math.pow(y[i] - meanY, 2);
        }

        double correlation = numerator / Math.sqrt(denominatorX * denominatorY);
        double slope = numerator / denominatorX;
        double intercept = meanY - slope * meanX;


        String result = "Correlation: " + correlation + "\nRegression Line: y = " + slope + "x + " + intercept;
        JOptionPane.showMessageDialog(this, result, "Correlation and Regression Analysis", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ScientificCalculatorUI calculator = new ScientificCalculatorUI();
            calculator.setVisible(true);
        });
    }
}