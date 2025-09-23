package edu.ccrm.service;

import edu.ccrm.domain.Student;
import edu.ccrm.domain.StudentStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

public class StudentService {

    private final List<Student> students = new ArrayList<>();
    private static final AtomicInteger idCounter = new AtomicInteger(0);

    public StudentService() {
        addStudent("Alice Johnson", "alice@example.com", "S001");
        addStudent("Bob Williams", "bob@example.com", "S002");
    }

    public void addStudent(String fullName, String email, String regNo) {
        Student student = new Student(idCounter.incrementAndGet(), fullName, email, regNo);
        students.add(student);
    }

    public Optional<Student> findStudentByRegNo(String regNo) {
        return students.stream()
                .filter(student -> student.getRegNo().equalsIgnoreCase(regNo))
                .findFirst();
    }

    public List<Student> getAllStudents() {
        return new ArrayList<>(students);
    }

    public Optional<Student> updateStudent(String regNo, String newFullName, String newEmail) {
        Optional<Student> studentOpt = findStudentByRegNo(regNo);
        if (studentOpt.isPresent()) {
            Student student = studentOpt.get();
            student.setFullName(newFullName);
            student.setEmail(newEmail);
            return Optional.of(student);
        }
        return Optional.empty();
    }

    public boolean deactivateStudent(String regNo) {
        Optional<Student> studentOpt = findStudentByRegNo(regNo);
        if (studentOpt.isPresent()) {
            studentOpt.get().setStatus(StudentStatus.INACTIVE);
            return true;
        }
        return false;
    }
}

