package edu.ccrm.domain;

public class Course {
    private final String code;
    private final String title;
    private final int credits;
    private final String department;
    private final Semester semester;
    private Instructor instructor;

    private Course(Builder builder) {
        this.code = builder.code;
        this.title = builder.title;
        this.credits = builder.credits;
        this.department = builder.department;
        this.semester = builder.semester;
        this.instructor = builder.instructor;
    }

    public String getCode() { return code; }
    public String getTitle() { return title; }
    public int getCredits() { return credits; }
    public String getDepartment() { return department; }
    public Semester getSemester() { return semester; }
    public Instructor getInstructor() { return instructor; }
    public void setInstructor(Instructor instructor) { this.instructor = instructor; }

    @Override
    public String toString() {
        return "Course [code=" + code + ", title=" + title + ", credits=" + credits + "]";
    }

    // Static nested Builder class
    public static class Builder {
        private String code;
        private String title;
        private int credits;
        private String department;
        private Semester semester;
        private Instructor instructor;

        public Builder(String code, String title) {
            this.code = code;
            this.title = title;
        }

        public Builder credits(int credits) {
            this.credits = credits;
            return this;
        }

        public Builder department(String department) {
            this.department = department;
            return this;
        }

        public Builder semester(Semester semester) {
            this.semester = semester;
            return this;
        }
        
        public Builder instructor(Instructor instructor) {
            this.instructor = instructor;
            return this;
        }

        public Course build() {
            return new Course(this);
        }
    }
}

