package edu.ccrm.service;

import edu.ccrm.domain.Course;
import edu.ccrm.domain.Semester;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CourseService {
    private final List<Course> courses = new ArrayList<>();

    public CourseService() {
        // Initial sample data using the addCourse method to keep it consistent
        addCourse("CS101", "Intro to Programming", 3, "Computer Science", Semester.FALL);
        addCourse("MA201", "Calculus I", 4, "Mathematics", Semester.FALL);
        addCourse("PY105", "General Physics", 4, "Physics", Semester.SPRING);
    }

    // This is the new method that the FileService needs
    public void addCourse(String code, String title, int credits, String department, Semester semester) {
        Course course = new Course.Builder(code, title)
                .credits(credits)
                .department(department)
                .semester(semester)
                .build();
        courses.add(course);
    }

    public List<Course> getAllCourses() {
        return new ArrayList<>(courses);
    }

    public Optional<Course> findCourseByCode(String code) {
        return courses.stream()
                .filter(course -> course.getCode().equalsIgnoreCase(code))
                .findFirst();
    }
}

