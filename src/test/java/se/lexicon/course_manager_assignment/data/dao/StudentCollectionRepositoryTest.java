package se.lexicon.course_manager_assignment.data.dao;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;
import se.lexicon.course_manager_assignment.data.sequencers.StudentSequencer;
import se.lexicon.course_manager_assignment.model.Student;


import java.util.Collection;

import static org.junit.jupiter.api.Assertions.assertFalse;

@SpringBootTest(classes = {StudentCollectionRepository.class})
public class StudentCollectionRepositoryTest {

    @Autowired
    private StudentDao studentDao;

    @Test
    @DisplayName("Test context successfully setup")
    void context_loads() {
        assertFalse(studentDao == null);
    }

    @Test
    void createStudent() {
        //Arrange
        Student result;

        //Act
        result = studentDao.createStudent("Kalle Karlsson", "kalle@hotmail.com", "Björkvägen 30, Ankeborg");

        //Assert
        Assert.isTrue(studentDao.findAll().contains(result), "New student should be stored");
        Assert.isTrue(result.getName().equals("Kalle Karlsson"), "Name should match");
        Assert.isTrue(result.getEmail().equals("kalle@hotmail.com"), "Email should match");
        Assert.isTrue(result.getAddress().equals("Björkvägen 30, Ankeborg"), "Address should match");
    }

    @Test
    void findByEmailIgnoreCase() {
        //Arrange
        Student result1, result2, result3, student;

        student = studentDao.createStudent("Kalle Karlsson", "kalle@hotmail.com", "Björkvägen 30, Ankeborg");

        //Act
        result1 = studentDao.findByEmailIgnoreCase("kalle@hotmail.com");
        result2 = studentDao.findByEmailIgnoreCase("kALLe@hotMAIL.com");
        result3 = studentDao.findByEmailIgnoreCase("kalle23@hotmail.com");

        //Assert
        Assert.isTrue(student.equals(result1), "Student should be returned");
        Assert.isTrue(student.equals(result2), "Student should be returned");
        Assert.isNull(result3, "Result should be null");
    }

    @Test
    void findByNameContains() {
        //Arrange
        Student student;
        Collection<Student> result1, result2, result3;

        student = studentDao.createStudent("Kalle Karlsson", "kalle@hotmail.com", "Björkvägen 30, Ankeborg");

        //Act
        result1 = studentDao.findByNameContains("kalle");
        result2 = studentDao.findByNameContains("KARL");
        result3 = studentDao.findByNameContains("salle");

        //Assert
        Assert.isTrue(result1.contains(student), "Returned list should contain Student");
        Assert.isTrue(result2.contains(student), "Returned list should contain Student");
        Assert.isTrue(!result3.contains(student), "Returned list should not contain Student");
    }

    @Test
    void findById() {
        //Arrange
        Student result1, result2, result3, student;
        int id;

        student = studentDao.createStudent("Kalle Karlsson", "kalle@hotmail.com", "Björkvägen 30, Ankeborg");
        id = student.getId();

        //Act
        result1 = studentDao.findById(id);
        result2 = studentDao.findById(id + 1);

        //Assert
        Assert.isTrue(student.equals(result1), "Student should be returned");
        Assert.isNull(result2, "Return value should be null");
    }

    @Test
    void findAll() {
        //Arrange
        Student student1, student2;
        Collection<Student> result;

        student1 = studentDao.createStudent("Kalle Karlsson", "kalle@hotmail.com", "Björkvägen 30, Ankeborg");
        student2 = studentDao.createStudent("Kalle Larsson", "kalle@gmail.com", "Björkvägen 32, Ankeborg");

        //Act
        result = studentDao.findAll();

        //Assert
        Assert.isTrue(result.contains(student1), "Should contain student1");
        Assert.isTrue(result.contains(student2), "Should contain student2");
        Assert.isTrue(result.size() == 2, "Size should be 2");
    }

    @Test
    void removeStudent() {
        //Arrange
        Student student;
        boolean result1, result2;

        student = studentDao.createStudent("Kalle Karlsson", "kalle@hotmail.com", "Björkvägen 30, Ankeborg");

        //Act
        result1 = studentDao.removeStudent(student);
        result2 = studentDao.removeStudent(student);

        //Assert
        Assert.isTrue(result1, "Return value should be true");
        Assert.isTrue(!result2, "Return value should be false");
        Assert.isTrue(!studentDao.findAll().contains(student), "Student should be absent");
    }

    @AfterEach
    void tearDown() {
        studentDao.clear();
        StudentSequencer.setStudentSequencer(0);
    }
}
