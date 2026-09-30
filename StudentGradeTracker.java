import java.util.ArrayList;
import java.util.Scanner;

class Student {
    String name;
    int[] marks;
    double average;

    Student(String name, int[] marks) {
        this.name = name;
        this.marks = marks;

        int total = 0;

        for (int mark : marks) {
            total += mark;
        }

        average = (double) total / marks.length;
    }

    String getGrade() {
        if (average >= 90)
            return "A+";
        else if (average >= 80)
            return "A";
        else if (average >= 70)
            return "B";
        else if (average >= 60)
            return "C";
        else if (average >= 50)
            return "D";
        else
            return "F";
    }
}

public class StudentGradeTracker {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        ArrayList<Student> students = new ArrayList<>();

        System.out.println("=================================");
        System.out.println("      STUDENT GRADE TRACKER");
        System.out.println("=================================");

        System.out.print("Enter number of students: ");
        int n = sc.nextInt();
        sc.nextLine();

        if (n <= 0) {
            System.out.println("Please enter a valid number of students.");
            sc.close();
            return;
        }

        String[] subjects = {"Java", "Python", "Database"};

        // Input student details
        for (int i = 0; i < n; i++) {

            System.out.println("\nEnter details for Student " + (i + 1));

            System.out.print("Enter student name: ");
            String name = sc.nextLine();

            int[] marks = new int[subjects.length];

            for (int j = 0; j < subjects.length; j++) {

                System.out.print("Enter marks in " + subjects[j] + " (0-100): ");
                int mark = sc.nextInt();

                while (mark < 0 || mark > 100) {
                    System.out.print("Invalid marks! Enter marks between 0 and 100: ");
                    mark = sc.nextInt();
                }

                marks[j] = mark;
            }

            sc.nextLine();

            Student student = new Student(name, marks);
            students.add(student);
        }

        // Calculate highest and lowest scores
        double highest = students.get(0).average;
        double lowest = students.get(0).average;
        String highestStudent = students.get(0).name;
        String lowestStudent = students.get(0).name;

        double totalAverage = 0;

        for (Student student : students) {

            totalAverage += student.average;

            if (student.average > highest) {
                highest = student.average;
                highestStudent = student.name;
            }

            if (student.average < lowest) {
                lowest = student.average;
                lowestStudent = student.name;
            }
        }

        double classAverage = totalAverage / students.size();

        // Display summary report
        System.out.println("\n==============================================");
        System.out.println("              STUDENT REPORT");
        System.out.println("==============================================");

        System.out.printf("%-15s %-10s %-10s %-10s %-10s %-8s%n",
                "Name", "Java", "Python", "Database", "Average", "Grade");

        System.out.println("--------------------------------------------------------------");

        for (Student student : students) {

            System.out.printf("%-15s", student.name);

            for (int mark : student.marks) {
                System.out.printf(" %-10d", mark);
            }

            System.out.printf(" %-10.2f %-8s%n",
                    student.average, student.getGrade());
        }

        System.out.println("\n==============================================");
        System.out.println("             CLASS SUMMARY");
        System.out.println("==============================================");

        System.out.printf("Class Average       : %.2f%n", classAverage);
        System.out.printf("Highest Average     : %.2f (%s)%n", highest, highestStudent);
        System.out.printf("Lowest Average      : %.2f (%s)%n", lowest, lowestStudent);

        System.out.println("Total Students      : " + students.size());

        System.out.println("==============================================");

        sc.close();
    }
}