package com.example;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class SGPAApp extends Application {

    private SGPAService sgpaService = new SGPAService();

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("SGPA Calculator");

        // Create UI elements
        Label nameLabel = new Label("Name:");
        TextField nameField = new TextField();

        Label usnLabel = new Label("USN:");
        TextField usnField = new TextField();

        Label subNoLabel = new Label("Number of Subjects:");
        TextField subNoField = new TextField();

        Button submitButton = new Button("Submit");
        TextArea resultArea = new TextArea();
        resultArea.setEditable(false);

        // Set up the layout
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10));
        grid.setVgap(8);
        grid.setHgap(10);

        grid.add(nameLabel, 0, 0);
        grid.add(nameField, 1, 0);
        grid.add(usnLabel, 0, 1);
        grid.add(usnField, 1, 1);
        grid.add(subNoLabel, 0, 2);
        grid.add(subNoField, 1, 2);
        grid.add(submitButton, 1, 3);
        grid.add(resultArea, 0, 4, 2, 1);

        // Handle button click
        submitButton.setOnAction(e -> {
            String name = nameField.getText();
            String usn = usnField.getText();
            int subNo = Integer.parseInt(subNoField.getText());

            // Here you would implement the logic to gather subject names, credits, and marks
            // For simplicity, let's assume we have fixed data for demonstration
            int[] credits = {3, 4, 3}; // Example credits for 3 subjects
            int[] marks = {85, 75, 90}; // Example marks for 3 subjects

            int[] grades = sgpaService.calculateGrades(marks);
            double sgpa = sgpaService.calculateSGPA(credits, grades);

            // Display the result
            StringBuilder result = new StringBuilder();
            result.append("Name: ").append(name).append("\n");
            result.append("USN: ").append(usn).append("\n");
            result.append("SGPA: ").append(sgpa).append("\n");
            resultArea.setText(result.toString());
        });

        // Set the scene
        Scene scene = new Scene(grid, 400, 300);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}