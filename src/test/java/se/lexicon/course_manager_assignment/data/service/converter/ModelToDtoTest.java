package se.lexicon.course_manager_assignment.data.service.converter;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;
import se.lexicon.course_manager_assignment.dto.views.CourseView;
import se.lexicon.course_manager_assignment.dto.views.StudentView;
import se.lexicon.course_manager_assignment.model.Course;
import se.lexicon.course_manager_assignment.model.Student;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(classes = {ModelToDto.class})
public class ModelToDtoTest {

    @Autowired
    private Converters converters;

    @Test
    @DisplayName("Test context successfully setup")
    void context_loads() {
        assertNotNull(converters);
    }

    @Test
    void studentToStudentView() {
        //Arrange
        Student student = new Student(3);
        StudentView result;

        student.setName("Kalle Karlsson");
        student.setEmail("kalle@yahoo.com");
        student.setAddress("Bergsvägen 23");

        //Act
        result = converters.studentToStudentView(student);

        //Assert
        Assert.isTrue(result.getId() == 3, "");
        Assert.isTrue(result.getName().equals("Kalle Karlsson"), "");
        Assert.isTrue(result.getEmail().equals("kalle@yahoo.com"), "");
        Assert.isTrue(result.getAddress().equals("Bergsvägen 23"), "");
    }

    @Test
    void courseToCourseView() {
        //Arrange
        Course course = new Course(2);
        CourseView result;
        Student student = new Student(3);
        HashSet<Student> students = new HashSet<>();

        student.setName("Kalle Karlsson");
        student.setEmail("kalle@yahoo.com");
        student.setAddress("Bergsvägen 23");

        students.add(student);

        course.setCourseName("Java OOP");
        course.setStartDate(LocalDate.of(2020, 11, 12));
        course.setWeekDuration(4);
        course.setStudents(students);

        //Act
        result = converters.courseToCourseView(course);

        //Assert
        Assert.isTrue(result.getId() == 2, "");
        Assert.isTrue(result.getCourseName().equals("Java OOP"), "");
        Assert.isTrue(result.getStartDate().equals(LocalDate.of(2020, 11, 12)), "");
        Assert.isTrue(result.getWeekDuration() == 4, "");
        Assert.isTrue(result.getStudents().get(0).getId() == 3, "");
        Assert.isTrue(result.getStudents().get(0).getName().equals("Kalle Karlsson"), "");
        Assert.isTrue(result.getStudents().get(0).getEmail().equals("kalle@yahoo.com"), "");
        Assert.isTrue(result.getStudents().get(0).getAddress().equals("Bergsvägen 23"), "");
    }

    @Test
    void coursesToCourseViews() {
        //Arrange
        HashSet<Course> courses = new HashSet<>();
        HashSet<Student> students = new HashSet<>();
        Student student;
        Course course;
        List<CourseView> result;

        student = new Student(3);
        student.setName("Kalle Karlsson");
        student.setEmail("kalle@yahoo.com");
        student.setAddress("Bergsvägen 23");
        students.add(student);

        course = new Course(3);
        course.setCourseName("Java OOP");
        course.setStartDate(LocalDate.of(2020, 11, 12));
        course.setWeekDuration(4);
        course.setStudents(students);
        courses.add(course);

        //Act
        result = converters.coursesToCourseViews(courses);

        //Assert
        Assert.isTrue(result.get(0).getId() == 3, "");
        Assert.isTrue(result.get(0).getCourseName().equals("Java OOP"), "");
        Assert.isTrue(result.get(0).getStartDate().equals(LocalDate.of(2020, 11, 12)), "");
        Assert.isTrue(result.get(0).getWeekDuration() == 4, "");
        Assert.isTrue(result.get(0).getStudents().get(0).getId() == 3, "");
        Assert.isTrue(result.get(0).getStudents().get(0).getName().equals("Kalle Karlsson"), "");
        Assert.isTrue(result.get(0).getStudents().get(0).getEmail().equals("kalle@yahoo.com"), "");
        Assert.isTrue(result.get(0).getStudents().get(0).getAddress().equals("Bergsvägen 23"), "");
    }

    @Test
    void studentsToStudentViews() {
        //Arrange
        HashSet<Student> students = new HashSet<>();
        Student student;
        List<StudentView> result;

        student = new Student(3);
        student.setName("Kalle Karlsson");
        student.setEmail("kalle@yahoo.com");
        student.setAddress("Bergsvägen 23");
        students.add(student);

        //Act
        result = converters.studentsToStudentViews(students);

        //Assert
        Assert.isTrue(result.get(0).getId() == 3, "");
        Assert.isTrue(result.get(0).getName().equals("Kalle Karlsson"), "");
        Assert.isTrue(result.get(0).getEmail().equals("kalle@yahoo.com"), "");
        Assert.isTrue(result.get(0).getAddress().equals("Bergsvägen 23"), "");

    }
}
