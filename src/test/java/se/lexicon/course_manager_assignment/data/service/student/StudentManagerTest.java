package se.lexicon.course_manager_assignment.data.service.student;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;
import se.lexicon.course_manager_assignment.data.dao.CourseCollectionRepository;
import se.lexicon.course_manager_assignment.data.dao.StudentCollectionRepository;
import se.lexicon.course_manager_assignment.data.dao.StudentDao;
import se.lexicon.course_manager_assignment.data.sequencers.StudentSequencer;
import se.lexicon.course_manager_assignment.data.service.converter.ModelToDto;
import se.lexicon.course_manager_assignment.dto.forms.CreateStudentForm;
import se.lexicon.course_manager_assignment.dto.forms.UpdateStudentForm;
import se.lexicon.course_manager_assignment.dto.views.StudentView;
import se.lexicon.course_manager_assignment.model.Student;


import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(classes = {StudentManager.class, CourseCollectionRepository.class, StudentCollectionRepository.class, ModelToDto.class})
public class StudentManagerTest {

    @Autowired
    private StudentService studentManager;
    @Autowired
    private StudentDao studentDao;

    @Test
    @DisplayName("Test context successfully setup")
    void context_loads() {
        assertNotNull(studentManager);
        assertNotNull(studentDao);
    }

    @Test
    void create() {
        //Arrange
        CreateStudentForm form = new CreateStudentForm(3, "Kalle Karlsson", "kalle@yahoo.com", "Yahoovägen 47");
        StudentView result;

        //Act
        result = studentManager.create(form);

        //Assert
        Assert.isTrue(result.getName().equals("Kalle Karlsson"), "");
        Assert.isTrue(result.getEmail().equals("kalle@yahoo.com"), "");
        Assert.isTrue(result.getAddress().equals("Yahoovägen 47"), "");
    }

    @Test
    void update() {
        //Arrange
        CreateStudentForm form1 = new CreateStudentForm(3, "Kalle Karlsson", "kalle@yahoo.com", "Yahoovägen 47");
        UpdateStudentForm form2;
        StudentView result;
        int id;

        id = studentManager.create(form1).getId();
        form2 = new UpdateStudentForm(id, "Kalle Larsson", "lasse@yahoo.com", "Yahoovägen 49");

        //Act
        result = studentManager.update(form2);

        //Assert
        Assert.isTrue(result.getName().equals("Kalle Larsson"), "");
        Assert.isTrue(result.getEmail().equals("lasse@yahoo.com"), "");
        Assert.isTrue(result.getAddress().equals("Yahoovägen 49"), "");
    }

    @Test
    void findById() {
        //Arrange
        Student student;
        StudentView result;
        int studentId;

        studentDao.createStudent("Kalle Karlsson", "kalle@gmail.com", "Ankevägen 45");
        student = studentDao.createStudent("Lars Karlsson", "lars@gmail.com", "Ankevägen 47");
        studentDao.createStudent("Kent Karlsson", "kent@gmail.com", "Ankevägen 49");

        studentId = student.getId();

        //Act
        result = studentManager.findById(studentId);

        //Assert
        Assert.isTrue(result.getId() == studentId, "");
        Assert.isTrue(result.getName().equals("Lars Karlsson"), "");
        Assert.isTrue(result.getEmail().equals("lars@gmail.com"), "");
        Assert.isTrue(result.getAddress().equals("Ankevägen 47"), "");
    }

    @Test
    void searchByEmail() {
        //Arrange
        StudentView result;

        studentDao.createStudent("Kalle Karlsson", "kalle@gmail.com", "Ankevägen 45");
        studentDao.createStudent("Lars Karlsson", "lars@gmail.com", "Ankevägen 47");
        studentDao.createStudent("Kent Karlsson", "kent@gmail.com", "Ankevägen 49");

        //Act
        result = studentManager.searchByEmail("lars@gmail.com");

        //Assert
        Assert.isTrue(result.getName().equals("Lars Karlsson"), "");
        Assert.isTrue(result.getEmail().equals("lars@gmail.com"), "");
        Assert.isTrue(result.getAddress().equals("Ankevägen 47"), "");
    }

    @Test
    void searchByName() {
        //Arrange
        List<StudentView> result;

        studentDao.createStudent("Kalle Karlsson", "kalle@gmail.com", "Ankevägen 45");
        studentDao.createStudent("Lars Karlsson", "lars@gmail.com", "Ankevägen 47");
        studentDao.createStudent("Kent Karlsson", "kent@gmail.com", "Ankevägen 49");

        //Act
        result = studentManager.searchByName("lars");

        //Assert
        Assert.isTrue(result.get(0).getName().equals("Lars Karlsson"), "");
        Assert.isTrue(result.get(0).getEmail().equals("lars@gmail.com"), "");
        Assert.isTrue(result.get(0).getAddress().equals("Ankevägen 47"), "");
    }

    @Test
    void findAll() {
        //Arrange
        List<StudentView> result;

        studentDao.createStudent("Kalle Karlsson", "kalle@gmail.com", "Ankevägen 45");
        studentDao.createStudent("Lars Karlsson", "lars@gmail.com", "Ankevägen 47");
        studentDao.createStudent("Kent Karlsson", "kent@gmail.com", "Ankevägen 49");

        //Act
        result = studentManager.findAll();

        //Assert
        Assert.isTrue(result.size() == 3, "");
    }

    @Test
    void deleteStudent() {
        //Arrange
        List<StudentView> result;
        boolean result1, result2;

        studentDao.createStudent("Kalle Karlsson", "kalle@gmail.com", "Ankevägen 45");
        studentDao.createStudent("Lars Karlsson", "lars@gmail.com", "Ankevägen 47");
        studentDao.createStudent("Kent Karlsson", "kent@gmail.com", "Ankevägen 49");

        //Act
        result1 = studentManager.deleteStudent(2);
        result2 = studentManager.deleteStudent(2);

        //Assert
        result = studentManager.findAll();
        Assert.isTrue(result.size() == 2, "");
        Assert.isTrue(result1, "");
        Assert.isTrue(!result2, "");
    }

    @AfterEach
    void tearDown() {
        StudentSequencer.setStudentSequencer(0);
        studentDao.clear();
    }
}
