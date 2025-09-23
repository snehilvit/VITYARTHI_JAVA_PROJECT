package edu.ccrm.domain;

public class Instructor extends Person {
    private String department;
    private String specialization;

    public Instructor(int id, String fullName, String email, String department) {
        super(id, fullName, email);
        this.department = department;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    @Override
    public String getProfile() {
        return "Instructor Profile:\n  ID: " + getId() + "\n  Name: " + getFullName() + "\n  Department: " + department;
    }

    @Override
    public String toString() {
        return "Instructor [name=" + getFullName() + ", department=" + department + "]";
    }
}
