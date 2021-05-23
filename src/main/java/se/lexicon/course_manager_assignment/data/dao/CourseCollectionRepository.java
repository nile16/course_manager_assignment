package se.lexicon.course_manager_assignment.data.dao;



import se.lexicon.course_manager_assignment.data.sequencers.CourseSequencer;
import se.lexicon.course_manager_assignment.model.Course;
import se.lexicon.course_manager_assignment.model.Student;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Locale;


public class CourseCollectionRepository implements CourseDao{

    private Collection<Course> courses;

    public CourseCollectionRepository(Collection<Course> courses) {
        this.courses = courses;
    }

    @Override
    public Course createCourse(String courseName, LocalDate startDate, int weekDuration) {

        Course newCourse = new Course(CourseSequencer.nextCourseId());

        newCourse.setCourseName(courseName);
        newCourse.setStartDate(startDate);
        newCourse.setWeekDuration(weekDuration);
        courses.add(newCourse);

        return newCourse;
    }

    @Override
    public Course findById(int id) {
        for (Course course : courses) {
            if (course.getId() == id ) {
                return course;
            }
        }
        return null;
    }

    @Override
    public Collection<Course> findByNameContains(String name) {
        Collection<Course> matchingCourses = new ArrayList<Course>();

        for (Course course : courses) {
            if (course.getCourseName().toLowerCase(Locale.ROOT).contains(name.trim().toLowerCase(Locale.ROOT))) {
                matchingCourses.add(course);
            }
        }

        return matchingCourses;
    }

    @Override
    public Collection<Course> findByDateBefore(LocalDate end) {
        Collection<Course> matchingCourses = new ArrayList<Course>();

        for (Course course : courses) {
            if (course.getStartDate().isBefore(end)) {
                matchingCourses.add(course);
            }
        }

        return matchingCourses;
    }

    @Override
    public Collection<Course> findByDateAfter(LocalDate start) {
        Collection<Course> matchingCourses = new ArrayList<Course>();

        for (Course course : courses) {
            if (course.getStartDate().isAfter(start)) {
                matchingCourses.add(course);
            }
        }

        return matchingCourses;
    }

    @Override
    public Collection<Course> findAll() {
        return courses;
    }

    @Override
    public Collection<Course> findByStudentId(int studentId) {
        Collection<Course> matchingCourses = new HashSet<>();

        for (Course course : courses) {
            for (Student student : course.getStudents())
            if (student.getId() == studentId) {
                matchingCourses.add(course);
            }
        }

        return matchingCourses;
    }

    @Override
    public boolean removeCourse(Course course) {
        if (courses.contains(course)) {
            courses.remove(course);
            return true;
        }
        return false;
    }

    @Override
    public void clear() {
        this.courses = new HashSet<>();
    }
}
