package com.example;

public class SGPAService {

    public double calculateSGPA(int[] credits, int[] grades) {
        double totalCredits = 0;
        double weightedGrades = 0;

        for (int i = 0; i < credits.length; i++) {
            totalCredits += credits[i];
            weightedGrades += credits[i] * grades[i];
        }

        return totalCredits > 0 ? weightedGrades / totalCredits : 0;
    }

    public int[] calculateGrades(int[] marks) {
        int[] grades = new int[marks.length];

        for (int i = 0; i < marks.length; i++) {
            if (marks[i] >= 90) {
                grades[i] = 10;
            } else if (marks[i] >= 80) {
                grades[i] = 9;
            } else if (marks[i] >= 70) {
                grades[i] = 8;
            } else if (marks[i] >= 60) {
                grades[i] = 7;
            } else if (marks[i] >= 45) {
                grades[i] = 6;
            } else if (marks[i] >= 40) {
                grades[i] = 4;
            } else {
                grades[i] = 0;
            }
        }

        return grades;
    }
}