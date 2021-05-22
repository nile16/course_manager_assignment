package se.lexicon.course_manager_assignment.model;

import org.junit.jupiter.api.Test;
import org.springframework.util.Assert;

import java.time.LocalDate;
import java.util.HashSet;

public class CourseTest {
    @Test
    public void getId() {
        //Arrange
        Course course = new Course(3);
        int result;

        //Act
        result = course.getId();

        //Assert
        Assert.isTrue(result == 3, "ID must be 3");
    }

    @Test
    void setCourseName() {
        //Arrange
        Course course = new Course(3);
        String result;

        //Act
        course.setCourseName("Java OOP");

        //Assert
        result = course.getCourseName();
        Assert.isTrue(result == "Java OOP", "Name must be 'Java OOP'");
    }

    @Test
    void getCourseName() {
        //Arrange
        Course course = new Course(3);
        course.setCourseName("Java OOP");
        String result;

        //Act
        result = course.getCourseName();

        //Assert
        Assert.isTrue(result == "Java OOP", "Name must be 'Java OOP'");
    }

    @Test
    void setStartDate() {
        //Arrange
        Course course = new Course(3);
        LocalDate result, startDate = LocalDate.of(2020, 6, 12);

        //Act
        course.setStartDate(startDate);

        //Assert
        result = course.getStartDate();
        Assert.isTrue(result.isEqual(startDate), "Startdate must be same as set");
    }

    @Test
    void getStartDate() {
        //Arrange
        Course course = new Course(3);
        LocalDate result, startDate = LocalDate.of(2020, 6, 12);

        course.setStartDate(startDate);

        //Act
        result = course.getStartDate();

        //Assert
        Assert.isTrue(result.isEqual(startDate), "Startdate must be same as set");
    }

    @Test
    void setWeekDuration() {
        //Arrange
        Course course = new Course(3);
        int result;

        //Act
        course.setWeekDuration(5);

        //Assert
        result = course.getWeekDuration();
        Assert.isTrue(result == 5, "Weekduration must be 5");
    }

    @Test
    void getWeekDuration() {
        //Arrange
        Course course = new Course(3);
        int result;

        course.setWeekDuration(5);

        //Act
        result = course.getWeekDuration();

        //Assert
        Assert.isTrue(result == 5, "Weekduration must be 5");
    }

    @Test
    void setStudents() {
        //Arrange
        Course course = new Course(3);
        HashSet<Student> result, students = new HashSet<>();

        students.add(new Student(4));
        students.add(new Student(5));

        //Act
        course.setStudents(students);

        //Assert
        result = course.getStudents();
        Assert.isTrue(result.equals(students), "Students must be same as set");
    }

    @Test
    void getStudents() {
        //Arrange
        Course course = new Course(3);
        HashSet<Student> result, students = new HashSet<>();

        students.add(new Student(4));
        students.add(new Student(5));
        course.setStudents(students);

        //Act
        result = course.getStudents();

        //Assert
        Assert.isTrue(result.equals(students), "Students must be same as set");
    }

    @Test
    void enrollStudent() {
        //Arrange
        Course course = new Course(3);
        HashSet<Student> result, students = new HashSet<>();
        Student student1, student2;
        Boolean result1, result2, result3, result4;

        student1 = new Student(4);
        student2 = new Student(5);

        //Act
        result1 = course.enrollStudent(student1);
        result2 = course.enrollStudent(student2);
        result3 = course.enrollStudent(student2);
        result4 = course.enrollStudent(null);

        //Assert
        result = course.getStudents();
        Assert.isTrue(result1, "Should be true");
        Assert.isTrue(result2, "Should be true");
        Assert.isTrue(!result3, "Should be false");
        Assert.isTrue(!result4, "Should be false");
        Assert.isTrue(result.contains(student1), "Student1 must be present");
        Assert.isTrue(result.contains(student2), "Student2 must be present");
    }

    @Test
    void unenrollStudent() {
        //Arrange
        Course course = new Course(3);
        HashSet<Student> result, students = new HashSet<>();
        Student student1, student2;
        Boolean result1, result2, result3, result4;

        student1 = new Student(4);
        student2 = new Student(5);
        course.enrollStudent(student1);
        course.enrollStudent(student2);

        //Act
        result1 = course.unenrollStudent(student1);
        result2 = course.unenrollStudent(student2);
        result3 = course.unenrollStudent(student2);
        result4 = course.enrollStudent(null);

        //Assert
        result = course.getStudents();
        Assert.isTrue(result1, "Should be true");
        Assert.isTrue(result2, "Should be true");
        Assert.isTrue(!result3, "Should be false");
        Assert.isTrue(!result4, "Should be false");
        Assert.isTrue(!result.contains(student1), "Student1 must be absent");
        Assert.isTrue(!result.contains(student2), "Student2 must be absent");
    }

    @Test
    void equals() {
        //Arrange
        Course course0, course1, course2, course3, course4, course5;
        Boolean result1, result2, result3, result4, result5;

        course0 = new Course(3);
        course1 = new Course(3);
        course2 = new Course(4);
        course3 = new Course(3);
        course4 = new Course(3);
        course5 = new Course(3);

        course0.setCourseName("Java OOP");
        course0.setWeekDuration(3);
        course0.setStartDate(LocalDate.of(2020, 12, 1));

        course1.setCourseName("Java OOP");
        course1.setWeekDuration(3);
        course1.setStartDate(LocalDate.of(2020, 12, 1));

        course2.setCourseName("Java OOP");
        course2.setWeekDuration(3);
        course2.setStartDate(LocalDate.of(2020, 12, 1));

        course3.setCourseName("Java Fundamentals");
        course3.setWeekDuration(3);
        course3.setStartDate(LocalDate.of(2020, 12, 1));

        course4.setCourseName("Java OOP");
        course4.setWeekDuration(5);
        course4.setStartDate(LocalDate.of(2020, 12, 1));

        course5.setCourseName("Java OOP");
        course5.setWeekDuration(3);
        course5.setStartDate(LocalDate.of(2020, 12, 2));

        //Act
        result1 = course0.equals(course1);
        result2 = course0.equals(course2);
        result3 = course0.equals(course3);
        result4 = course0.equals(course4);
        result5 = course0.equals(course5);

        //Assert
        Assert.isTrue(result1, "Should be true as courses are equal");
        Assert.isTrue(!result2, "Should be false as ids differ");
        Assert.isTrue(!result3, "Should be false as course name differ");
        Assert.isTrue(!result4, "Should be false as duration differ");
        Assert.isTrue(!result5, "Should be false as start date differ");
    }

    @Test
    void hash() {
        //Arrange
        Course course1, course2;
        int result1, result2;

        course1 = new Course(3);
        course2 = new Course(3);

        course1.setCourseName("Java OOP");
        course1.setWeekDuration(3);
        course1.setStartDate(LocalDate.of(2020, 12, 1));

        course2.setCourseName("Java OOP");
        course2.setWeekDuration(3);
        course2.setStartDate(LocalDate.of(2020, 12, 1));

        //Act
        result1 = course1.hashCode();
        result2 = course2.hashCode();

        //Assert
        Assert.isTrue(result1 == result2, "Hash codes should be equal");
    }
}
