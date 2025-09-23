package edu.ccrm.domain;

import java.util.ArrayList;
import java.util.List;

public class Student extends Person {
    private String regNo;
    private StudentStatus status;
    private List<Enrollment> enrollments;

    public Student(int id, String fullName, String email, String regNo) {
        super(id, fullName, email);
        this.regNo = regNo;
        this.status = StudentStatus.ACTIVE;
        this.enrollments = new ArrayList<>();
    }

    public String getRegNo() {
        return regNo;
    }

    public StudentStatus getStatus() {
        return status;
    }

    public void setStatus(StudentStatus status) {
        this.status = status;
    }

    public List<Enrollment> getEnrollments() {
        return enrollments;
    }

    // This is the new method that fixes the final compilation error
    public void addEnrollment(Enrollment enrollment) {
        this.enrollments.add(enrollment);
    }

    @Override
    public String getProfile() {
        return "Student Profile:\n  ID: " + getId() + "\n  Name: " + getFullName() + "\n  Reg No: " + regNo + "\n  Status: " + status;
    }

    @Override
    public String toString() {
        return "Student [regNo=" + regNo + ", name=" + getFullName() + ", status=" + status + "]";
    }
}

