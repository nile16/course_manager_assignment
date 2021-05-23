package se.lexicon.course_manager_assignment.data.dao;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;
import se.lexicon.course_manager_assignment.data.sequencers.CourseSequencer;
import se.lexicon.course_manager_assignment.model.Course;
import se.lexicon.course_manager_assignment.model.Student;


import java.time.LocalDate;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.assertFalse;

@SpringBootTest(classes = {CourseCollectionRepository.class})
public class CourseCollectionRepositoryTest {

    @Autowired
    private CourseDao courseDao;

    @Test
    @DisplayName("Test context successfully setup")
    void context_loads() {
        assertFalse(courseDao == null);
    }

    @Test
    void createCourse() {
        //Arrange
        Course result;

        //Act
        result = courseDao.createCourse("Java OOP", LocalDate.of(2020,6,6), 4);

        //Assert
        Assert.isTrue(courseDao.findAll().contains(result), " ");
        Assert.isTrue(result.getCourseName().equals("Java OOP"), " ");
        Assert.isTrue(result.getStartDate().equals(LocalDate.of(2020,6,6)), " ");
        Assert.isTrue(result.getWeekDuration() == 4, " ");
    }

    @Test
    void findById() {
        //Arrange
        Course course, result1, result2;
        int id;

        course = courseDao.createCourse("Java OOP", LocalDate.of(2020,6,6), 4);
        id = course.getId();

        //Act
        result1 = courseDao.findById(id);
        result2 = courseDao.findById(id+1);

        //Assert
        Assert.isTrue(course.equals(result1), "");
        Assert.isNull(result2, "");
    }

    @Test
    void findByNameContains() {
        //Arrange
        Collection<Course> result1, result2, result3;
        Course course;

        course = courseDao.createCourse("Java OOP", LocalDate.of(2020,6,6), 4);

        //Act
        result1 = courseDao.findByNameContains("ava");
        result2 = courseDao.findByNameContains("oop");
        result3 = courseDao.findByNameContains("sava");

        //Assert
        Assert.isTrue(result1.contains(course), "");
        Assert.isTrue(result2.contains(course), "");
        Assert.isTrue(!result3.contains(course), "");
    }

    @Test
    void findByDateBefore() {
        //Arrange
        Collection<Course> result;
        Course course1, course2, course3;

        course1 = courseDao.createCourse("Java OOP 1", LocalDate.of(2020,5,6), 4);
        course2 = courseDao.createCourse("Java OOP 2", LocalDate.of(2020,6,6), 4);
        course3 = courseDao.createCourse("Java OOP 3", LocalDate.of(2020,7,6), 4);

        //Act
        result = courseDao.findByDateBefore(LocalDate.of(2020,6,1));

        //Assert
        Assert.isTrue(result.contains(course1), "");
        Assert.isTrue(!result.contains(course2), "");
        Assert.isTrue(!result.contains(course3), "");
    }

    @Test
    void findByDateAfter() {
        //Arrange
        Collection<Course> result;
        Course course1, course2, course3;

        course1 = courseDao.createCourse("Java OOP 1", LocalDate.of(2020,5,6), 4);
        course2 = courseDao.createCourse("Java OOP 2", LocalDate.of(2020,6,6), 4);
        course3 = courseDao.createCourse("Java OOP 3", LocalDate.of(2020,7,6), 4);

        //Act
        result = courseDao.findByDateAfter(LocalDate.of(2020,6,1));

        //Assert
        Assert.isTrue(!result.contains(course1), "");
        Assert.isTrue(result.contains(course2), "");
        Assert.isTrue(result.contains(course3), "");
    }

    @Test
    void findAll() {
        //Arrange
        Collection<Course> result;
        Course course1, course2, course3;

        course1 = courseDao.createCourse("Java OOP 1", LocalDate.of(2020,5,6), 4);
        course2 = courseDao.createCourse("Java OOP 2", LocalDate.of(2020,6,6), 4);
        course3 = courseDao.createCourse("Java OOP 3", LocalDate.of(2020,7,6), 4);

        //Act
        result = courseDao.findAll();

        //Assert
        Assert.isTrue(result.contains(course1), "");
        Assert.isTrue(result.contains(course2), "");
        Assert.isTrue(result.contains(course3), "");
    }

    @Test
    void findByStudentId() {
        //Arrange
        Collection<Course> result;
        Course course1, course2, course3;
        Student student1, student2, student3;

        course1 = courseDao.createCourse("Java OOP 1", LocalDate.of(2020,5,6), 4);
        course2 = courseDao.createCourse("Java OOP 2", LocalDate.of(2020,6,6), 4);
        course3 = courseDao.createCourse("Java OOP 3", LocalDate.of(2020,7,6), 4);

        student1 = new Student(1);
        student2 = new Student(2);
        student3 = new Student(3);

        course1.enrollStudent(student1);
        course1.enrollStudent(student2);
        course1.enrollStudent(student3);

        course2.enrollStudent(student1);
        course2.enrollStudent(student2);

        course3.enrollStudent(student1);

        //Act
        result = courseDao.findByStudentId(2);

        //Assert
        Assert.isTrue(result.contains(course1), "");
        Assert.isTrue(result.contains(course2), "");
        Assert.isTrue(!result.contains(course3), "");
    }

    @Test
    void removeCourse() {
        //Arrange
        Collection<Course> allCourses;
        Course course1, course2, course3;
        boolean result1, result2;

        course1 = courseDao.createCourse("Java OOP 1", LocalDate.of(2020,5,6), 4);
        course2 = courseDao.createCourse("Java OOP 2", LocalDate.of(2020,6,6), 4);
        course3 = courseDao.createCourse("Java OOP 3", LocalDate.of(2020,7,6), 4);

        //Act
        result1 = courseDao.removeCourse(course2);
        result2 = courseDao.removeCourse(course2);

        //Assert
        allCourses = courseDao.findAll();
        Assert.isTrue(allCourses.contains(course1), "");
        Assert.isTrue(!allCourses.contains(course2), "");
        Assert.isTrue(allCourses.contains(course3), "");
        Assert.isTrue(result1, "");
        Assert.isTrue(!result2, "");
    }

    @AfterEach
    void tearDown() {
        courseDao.clear();
        CourseSequencer.setCourseSequencer(0);
    }
}
