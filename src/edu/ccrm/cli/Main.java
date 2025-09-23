package edu.ccrm.cli;

import edu.ccrm.config.AppConfig;
import edu.ccrm.domain.Course;
import edu.ccrm.domain.Enrollment;
import edu.ccrm.domain.Grade;
import edu.ccrm.domain.Student;
import edu.ccrm.domain.exceptions.CourseNotFoundException;
import edu.ccrm.domain.exceptions.StudentNotFoundException;
import edu.ccrm.io.FileService;
import edu.ccrm.service.CourseService;
import edu.ccrm.service.EnrollmentService;
import edu.ccrm.service.StudentService;
import edu.ccrm.util.DirectoryUtils;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.Scanner;

public class Main {

    private static final StudentService studentService = new StudentService();
    private static final CourseService courseService = new CourseService();
    private static final EnrollmentService enrollmentService = new EnrollmentService();
    private static final FileService fileService = new FileService();
    private static final Scanner scanner = new Scanner(System.in);
    private static final AppConfig config = AppConfig.getInstance();

    public static void main(String[] args) {
        System.out.println("Welcome to the Campus Course & Records Manager (CCRM)!");
        boolean exit = false;

        while (!exit) {
            printMainMenu();
            System.out.print("Enter your choice: ");
            String input = scanner.nextLine();
            int choice = -1;
            try {
                choice = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
                continue;
            }

            switch (choice) {
                case 1: manageStudents(); break;
                case 2: manageCourses(); break;
                case 3: manageEnrollments(); break;
                case 4: manageGrading(); break;
                case 5: viewStudentTranscript(); break;
                case 6: fileOperations(); break;
                case 9:
                    exit = true;
                    break; // Just break the loop
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
        
        printPlatformSummary();
        System.out.println("Exiting application. Goodbye!");
        scanner.close();
    }

    private static void printMainMenu() {
        System.out.println("\n--- CCRM Main Menu ---");
        System.out.println("1. Manage Students");
        System.out.println("2. Manage Courses");
        System.out.println("3. Manage Enrollment");
        System.out.println("4. Manage Grading");
        System.out.println("5. View Student Transcript");
        System.out.println("6. File Operations & Utilities");
        System.out.println("9. Exit");
    }

    private static void manageStudents() {
        System.out.println("\n--- Manage Students ---");
        System.out.println("1. Add a new Student");
        System.out.println("2. List all Students");
        System.out.println("3. Update a Student");
        System.out.println("4. Deactivate a Student");
        System.out.print("Enter your choice: ");
        int choice = Integer.parseInt(scanner.nextLine());
        
        switch (choice) {
            case 1:
                addStudent();
                break;
            case 2:
                System.out.println("\n--- All Students ---");
                studentService.getAllStudents().forEach(System.out::println);
                break;
            case 3:
                updateStudent();
                break;
            case 4:
                deactivateStudent();
                break;
            default:
                System.out.println("Invalid choice.");
        }
    }

    private static void addStudent() {
        System.out.print("Enter Full Name: ");
        String name = scanner.nextLine();
        System.out.print("Enter Email: ");
        String email = scanner.nextLine();
        System.out.print("Enter Registration Number: ");
        String regNo = scanner.nextLine();
        studentService.addStudent(name, email, regNo);
        System.out.println("Student added successfully!");
    }
    
    private static void updateStudent() {
        System.out.print("Enter Registration Number of student to update: ");
        String regNo = scanner.nextLine();
        System.out.print("Enter new Full Name: ");
        String newName = scanner.nextLine();
        System.out.print("Enter new Email: ");
        String newEmail = scanner.nextLine();
        Optional<Student> updatedStudent = studentService.updateStudent(regNo, newName, newEmail);
        if (updatedStudent.isPresent()) {
            System.out.println("Student updated successfully!");
        } else {
            System.out.println("Student not found.");
        }
    }

    private static void deactivateStudent() {
        System.out.print("Enter Registration Number of student to deactivate: ");
        String regNo = scanner.nextLine();
        boolean success = studentService.deactivateStudent(regNo);
        if (success) {
            System.out.println("Student deactivated successfully.");
        } else {
            System.out.println("Student not found.");
        }
    }

    private static void manageCourses() {
        System.out.println("\n--- Manage Courses ---");
        System.out.println("1. List all Courses");
        System.out.print("Enter your choice: ");
        int choice = Integer.parseInt(scanner.nextLine());
        if (choice == 1) {
            System.out.println("\n--- All Courses ---");
            courseService.getAllCourses().forEach(System.out::println);
        }
    }
    
    private static void manageEnrollments() {
        System.out.println("\n--- Enroll Student in Course ---");
        try {
            System.out.print("Enter Student Registration Number (e.g., S001): ");
            String regNo = scanner.nextLine();
            Student student = studentService.findStudentByRegNo(regNo)
                .orElseThrow(() -> new StudentNotFoundException("Student with registration number " + regNo + " not found."));

            System.out.print("Enter Course Code (e.g., CS101): ");
            String courseCode = scanner.nextLine();
            Course course = courseService.findCourseByCode(courseCode)
                .orElseThrow(() -> new CourseNotFoundException("Course with code " + courseCode + " not found."));
            
            enrollmentService.enrollStudent(student, course);
            System.out.println("Enrollment successful!");

        } catch (StudentNotFoundException | CourseNotFoundException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
    
    private static void manageGrading() {
        System.out.println("\n--- Assign Grade to Student ---");
        try {
            System.out.print("Enter Student Registration Number: ");
            String regNo = scanner.nextLine();
            Student student = studentService.findStudentByRegNo(regNo)
                .orElseThrow(() -> new StudentNotFoundException("Student not found."));

            System.out.print("Enter Course Code: ");
            String courseCode = scanner.nextLine();
            
            Optional<Enrollment> enrollmentOpt = student.getEnrollments().stream()
                .filter(e -> e.getCourse().getCode().equalsIgnoreCase(courseCode))
                .findFirst();

            Enrollment enrollment = enrollmentOpt.orElseThrow(() -> new CourseNotFoundException("Student is not enrolled in this course."));

            System.out.print("Enter Grade (S, A, B, C, D, E, F): ");
            String gradeStr = scanner.nextLine().toUpperCase();
            Grade grade = Grade.valueOf(gradeStr);

            enrollment.setGrade(grade);
            System.out.println("Grade assigned successfully.");

        } catch (StudentNotFoundException | CourseNotFoundException e) {
            System.err.println("Error: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.err.println("Error: Invalid grade entered.");
        }
    }
    
    private static void viewStudentTranscript() {
        System.out.println("\n--- View Student Transcript ---");
        try {
            System.out.print("Enter Student Registration Number: ");
            String regNo = scanner.nextLine();
            Student student = studentService.findStudentByRegNo(regNo)
                .orElseThrow(() -> new StudentNotFoundException("Student not found."));
            
            System.out.println("\n-------------------------------------------");
            System.out.println("Transcript for: " + student.getFullName() + " (" + student.getRegNo() + ")");
            System.out.println("-------------------------------------------");
            if (student.getEnrollments().isEmpty()) {
                System.out.println("No courses enrolled.");
            } else {
                for (Enrollment e : student.getEnrollments()) {
                    System.out.printf("Course: %-10s | Title: %-25s | Grade: %s\n", 
                        e.getCourse().getCode(), e.getCourse().getTitle(), e.getGrade());
                }
            }
            System.out.println("-------------------------------------------");

        } catch (StudentNotFoundException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
    
    private static void fileOperations() {
        System.out.println("\n--- File Operations & Utilities ---");
        System.out.println("1. Export All Data");
        System.out.println("2. Create a Backup");
        System.out.println("3. Get Backup Directory Size");
        System.out.print("Enter your choice: ");
        int choice = Integer.parseInt(scanner.nextLine());
        String dataDir = config.getDataFolderPath();

        try {
            switch(choice) {
                case 1:
                    fileService.exportData(dataDir, studentService, courseService);
                    break;
                case 2:
                    fileService.backupData(dataDir);
                    break;
                case 3:
                    long size = DirectoryUtils.calculateDirectorySize(Paths.get(dataDir).getParent().resolve("backups"));
                    System.out.printf("Total size of backups directory: %.2f KB\n", size / 1024.0);
                    break;
                default:
                    System.out.println("Invalid choice.");
            }
        } catch (IOException e) {
            System.err.println("A file operation failed: " + e.getMessage());
        }
    }

    private static void printPlatformSummary() {
        System.out.println("\n--- Java Platform Summary ---");
        System.out.println("Java SE (Standard Edition): For desktop and server applications. The core Java platform.");
        System.out.println("Java ME (Micro Edition): For resource-constrained devices like mobiles and embedded systems.");
        System.out.println("Java EE (Enterprise Edition): An extension of SE for large-scale, web, and enterprise applications.");
        System.out.println("-----------------------------");
    }
}

