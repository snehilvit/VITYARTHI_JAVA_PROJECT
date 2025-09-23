package edu.ccrm.service;

import edu.ccrm.domain.Course;
import edu.ccrm.domain.Enrollment;
import edu.ccrm.domain.Student;

public class EnrollmentService {
    public Enrollment enrollStudent(Student student, Course course) {
        Enrollment enrollment = new Enrollment(student, course);
        student.addEnrollment(enrollment);
        return enrollment;
    }
}
