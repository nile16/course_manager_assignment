package se.lexicon.course_manager_assignment.data.service.course;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;
import se.lexicon.course_manager_assignment.data.dao.CourseCollectionRepository;
import se.lexicon.course_manager_assignment.data.dao.CourseDao;
import se.lexicon.course_manager_assignment.data.dao.StudentCollectionRepository;
import se.lexicon.course_manager_assignment.data.dao.StudentDao;
import se.lexicon.course_manager_assignment.data.sequencers.CourseSequencer;
import se.lexicon.course_manager_assignment.data.service.converter.ModelToDto;
import se.lexicon.course_manager_assignment.dto.forms.CreateCourseForm;
import se.lexicon.course_manager_assignment.dto.forms.UpdateCourseForm;
import se.lexicon.course_manager_assignment.dto.views.CourseView;
import se.lexicon.course_manager_assignment.model.Course;
import se.lexicon.course_manager_assignment.model.Student;


import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(classes = {CourseManager.class, CourseCollectionRepository.class, ModelToDto.class, StudentCollectionRepository.class})
public class CourseManagerTest {

    @Autowired
    private CourseService courseManager;

    @Autowired
    private CourseDao courseDao;

    @Autowired
    private StudentDao studentDao;

    @Test
    @DisplayName("Test context successfully setup")
    void context_loads() {
        assertNotNull(courseManager);
        assertNotNull(courseDao);
    }

    @Test
    void create() {
        //Arrange
        CreateCourseForm form = new CreateCourseForm(3, "Java OOP", LocalDate.of(2020, 7, 3), 4);
        CourseView result;

        //Act
        result = courseManager.create(form);

        //Assert
        Assert.isTrue(result.getCourseName().equals("Java OOP"), "");
        Assert.isTrue(result.getStartDate().equals(LocalDate.of(2020, 7, 3)), "");
        Assert.isTrue(result.getWeekDuration() == 4, "");
    }

    @Test
    void update() {
        //Arrange
        CreateCourseForm form1 = new CreateCourseForm(3, "Java OOP", LocalDate.of(2020, 7, 3), 4);
        UpdateCourseForm form2;
        CourseView result;
        int id;

        id = courseManager.create(form1).getId();
        form2 = new UpdateCourseForm(id, "Java Fundamentals", LocalDate.of(2020, 8, 3), 5);

        //Act
        result = courseManager.update(form2);

        //Assert
        Assert.isTrue(result.getCourseName().equals("Java Fundamentals"), "");
        Assert.isTrue(result.getStartDate().equals(LocalDate.of(2020, 8, 3)), "");
        Assert.isTrue(result.getWeekDuration() == 5, "");
    }

    @Test
    void searchByCourseName() {
        //Arrange
        List<CourseView> result;

        courseDao.createCourse("Java Fundamentals", LocalDate.of(2020, 8, 1), 3);
        courseDao.createCourse("Java Loops", LocalDate.of(2020, 9, 1), 4);
        courseDao.createCourse("Java OOP", LocalDate.of(2020, 10, 1), 5);

        //Act
        result = courseManager.searchByCourseName("loops");

        //Assert
        Assert.isTrue(result.get(0).getCourseName().equals("Java Loops"), "");
        Assert.isTrue(result.get(0).getStartDate().equals(LocalDate.of(2020, 9, 1)), "");
        Assert.isTrue(result.get(0).getWeekDuration() == 4, "");
    }

    @Test
    void searchByDateBefore() {
        //Arrange
        List<CourseView> result;

        courseDao.createCourse("Java Fundamentals", LocalDate.of(2020, 8, 1), 3);
        courseDao.createCourse("Java Loops", LocalDate.of(2020, 9, 1), 4);
        courseDao.createCourse("Java OOP", LocalDate.of(2020, 10, 1), 5);

        //Act
        result = courseManager.searchByDateBefore(LocalDate.of(2020, 9, 1));

        //Assert
        Assert.isTrue(result.get(0).getCourseName().equals("Java Fundamentals"), "");
        Assert.isTrue(result.get(0).getStartDate().equals(LocalDate.of(2020, 8, 1)), "");
        Assert.isTrue(result.get(0).getWeekDuration() == 3, "");
    }

    @Test
    void searchByDateAfter() {
        //Arrange
        List<CourseView> result;

        courseDao.createCourse("Java Fundamentals", LocalDate.of(2020, 8, 1), 3);
        courseDao.createCourse("Java Loops", LocalDate.of(2020, 9, 1), 4);
        courseDao.createCourse("Java OOP", LocalDate.of(2020, 10, 1), 5);

        //Act
        result = courseManager.searchByDateAfter(LocalDate.of(2020, 9, 1));

        //Assert
        Assert.isTrue(result.get(0).getCourseName().equals("Java OOP"), "");
        Assert.isTrue(result.get(0).getStartDate().equals(LocalDate.of(2020, 10, 1)), "");
        Assert.isTrue(result.get(0).getWeekDuration() == 5, "");
    }

    @Test
    void addStudentToCourse() {
        //Arrange
        CourseView result;
        int studentId, courseId;
        boolean result1, result2;

        studentId = studentDao.createStudent("Kalle Karlsson", "kalle23@gmail.com", "Ankegatan 3").getId();
        courseId = courseDao.createCourse("Java Fundamentals", LocalDate.of(2020, 8, 1), 3).getId();

        //Act
        result1 = courseManager.addStudentToCourse(courseId, studentId);
        result2 = courseManager.addStudentToCourse(courseId, studentId);

        //Assert
        Assert.isTrue(result1, "");
        Assert.isTrue(!result2, "");
        result = courseManager.findById(courseId);
        Assert.isTrue(result.getStudents().get(0).getName().equals("Kalle Karlsson"), "");
        Assert.isTrue(result.getStudents().get(0).getEmail().equals("kalle23@gmail.com"), "");
        Assert.isTrue(result.getStudents().get(0).getAddress().equals("Ankegatan 3"), "");
    }

    @Test
    void removeStudentFromCourse() {
        //Arrange
        CourseView result;
        int studentId, courseId;
        boolean result1, result2;

        studentId = studentDao.createStudent("Kalle Karlsson", "kalle@gmail.com", "Ankegatan 3").getId();
        courseId = courseDao.createCourse("Java Fundamentals", LocalDate.of(2020, 8, 1), 3).getId();
        courseManager.addStudentToCourse(courseId, studentId);
        courseManager.addStudentToCourse(courseId, studentId);

        //Act
        result1 = courseManager.removeStudentFromCourse(courseId, studentId);
        result2 = courseManager.removeStudentFromCourse(courseId, studentId);

        //Assert
        Assert.isTrue(result1, "");
        Assert.isTrue(!result2, "");
        result = courseManager.findById(courseId);
        Assert.isTrue(result.getStudents().size() == 0, "");
    }

    @Test
    void findById() {
        //Arrange
        CourseView result;
        int id;

        courseDao.createCourse("Java Fundamentals", LocalDate.of(2020, 8, 1), 3);
        id = courseDao.createCourse("Java Loops", LocalDate.of(2020, 9, 1), 4).getId();
        courseDao.createCourse("Java OOP", LocalDate.of(2020, 10, 1), 5);

        //Act
        result = courseManager.findById(id);

        //Assert
        Assert.isTrue(result.getCourseName().equals("Java Loops"), "");
        Assert.isTrue(result.getStartDate().equals(LocalDate.of(2020, 9, 1)), "");
        Assert.isTrue(result.getWeekDuration() == 4, "");
    }

    @Test
    void findAll() {
        //Arrange
        List<CourseView> result;

        courseDao.createCourse("Java Fundamentals", LocalDate.of(2020, 8, 1), 3);
        courseDao.createCourse("Java Loops", LocalDate.of(2020, 9, 1), 4);
        courseDao.createCourse("Java OOP", LocalDate.of(2020, 10, 1), 5);

        //Act
        result = courseManager.findAll();

        //Assert
        Assert.isTrue(result.size() == 3, "");
    }

    @Test
    void findByStudentId() {
        //Arrange
        List<CourseView> result;
        int studentId, courseId;

        courseDao.createCourse("Java Fundamentals", LocalDate.of(2020, 8, 1), 3);
        courseId = courseDao.createCourse("Java Loops", LocalDate.of(2020, 9, 1), 4).getId();
        courseDao.createCourse("Java OOP", LocalDate.of(2020, 10, 1), 5);

        studentId = studentDao.createStudent("Kalle Karlsson", "kalle33@gmail.com", "Ankegatan 3").getId();

        courseManager.addStudentToCourse(courseId, studentId);

        //Act
        result = courseManager.findByStudentId(studentId);

        //Assert
        Assert.isTrue(result.get(0).getId() == courseId, "");
    }

    @Test
    void deleteCourse() {
        //Arrange
        CourseView result;
        boolean result1, result2;
        int courseId;

        courseDao.createCourse("Java Fundamentals", LocalDate.of(2020, 8, 1), 3);
        courseId = courseDao.createCourse("Java Loops", LocalDate.of(2020, 9, 1), 4).getId();
        courseDao.createCourse("Java OOP", LocalDate.of(2020, 10, 1), 5);

        //Act
        result1 = courseManager.deleteCourse(courseId);
        result2 = courseManager.deleteCourse(courseId);

        //Assert
        result = courseManager.findById(courseId);
        Assert.isTrue(result1, "");
        Assert.isTrue(!result2, "");
        Assert.isNull(result, "");
    }

    @AfterEach
    void tearDown() {
        courseDao.clear();
        CourseSequencer.setCourseSequencer(0);
    }
}
