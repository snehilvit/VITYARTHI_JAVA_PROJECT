package edu.ccrm.io;

import edu.ccrm.domain.Course;
import edu.ccrm.domain.Semester;
import edu.ccrm.domain.Student;
import edu.ccrm.service.CourseService;
import edu.ccrm.service.StudentService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FileService {

    public void importStudents(String filePath, StudentService studentService) throws IOException {
        Path path = Paths.get(filePath);
        try (Stream<String> lines = Files.lines(path)) {
            lines.skip(1) // Skip header row
                 .map(line -> line.split(","))
                 .forEach(data -> studentService.addStudent(data[0], data[1], data[2]));
            System.out.println("Students imported successfully.");
        }
    }

    public void importCourses(String filePath, CourseService courseService) throws IOException {
         Path path = Paths.get(filePath);
        try (Stream<String> lines = Files.lines(path)) {
            lines.skip(1) // Skip header row
                 .map(line -> line.split(","))
                 .forEach(data -> courseService.addCourse(data[0], data[1], Integer.parseInt(data[2]), data[3], Semester.valueOf(data[4].toUpperCase())));
            System.out.println("Courses imported successfully.");
        }
    }

    public void exportData(String directory, StudentService studentService, CourseService courseService) throws IOException {
        Path dirPath = Paths.get(directory);
        if (!Files.exists(dirPath)) {
            Files.createDirectories(dirPath);
        }

        Path studentsPath = dirPath.resolve("students_export.csv");
        List<String> studentLines = studentService.getAllStudents().stream()
                .map(s -> s.getFullName() + "," + s.getEmail() + "," + s.getRegNo())
                .collect(Collectors.toList());
        studentLines.add(0, "FullName,Email,RegNo");
        Files.write(studentsPath, studentLines);

        Path coursesPath = dirPath.resolve("courses_export.csv");
        List<String> courseLines = courseService.getAllCourses().stream()
                .map(c -> c.getCode() + "," + c.getTitle() + "," + c.getCredits() + "," + c.getDepartment() + "," + c.getSemester())
                .collect(Collectors.toList());
        courseLines.add(0, "Code,Title,Credits,Department,Semester");
        Files.write(coursesPath, courseLines);

        System.out.println("Data exported to " + directory);
    }

    public void backupData(String sourceDir) throws IOException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");
        String timestamp = LocalDateTime.now().format(formatter);
        Path sourcePath = Paths.get(sourceDir);
        Path backupPath = sourcePath.getParent().resolve("backups/backup_" + timestamp);
        
        Files.createDirectories(backupPath);

        try (Stream<Path> stream = Files.walk(sourcePath)) {
            stream.forEach(source -> {
                try {
                    Files.copy(source, backupPath.resolve(sourcePath.relativize(source)), StandardCopyOption.REPLACE_EXISTING);
                } catch (IOException e) {
                    System.err.println("Failed to backup file: " + source + " - " + e.getMessage());
                }
            });
        }
        System.out.println("Backup created at: " + backupPath);
    }
}
