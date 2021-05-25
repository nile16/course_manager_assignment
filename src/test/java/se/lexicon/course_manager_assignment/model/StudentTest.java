package se.lexicon.course_manager_assignment.model;

import org.junit.jupiter.api.Test;
import org.springframework.util.Assert;


public class StudentTest {
    @Test
    public void getId() {
        //Arrange
        Student student = new Student(3);
        int result;

        //Act
        result = student.getId();

        //Assert
        Assert.isTrue(result == 3, "ID must be 3");
    }

    @Test
    public void setName() {
        //Arrange
        Student student = new Student(3);
        String result;

        //Act
        student.setName("Kalle Karlsson");
        student.setName(null);

        //Assert
        result = student.getName();
        Assert.isTrue(result.equals("Kalle Karlsson"), "Name must be Kalle Karlsson");
    }

    @Test
    public void getName() {
        //Arrange
        Student student = new Student(3);
        String result;
        student.setName("Kalle Karlsson");

        //Act
        result = student.getName();

        //Assert
        Assert.isTrue(result.equals("Kalle Karlsson"), "Name must be Kalle Karlsson");
    }

    @Test
    public void setEmail() {
        //Arrange
        Student student = new Student(3);
        String result;

        //Act
        student.setEmail("Kalle@gmail.com");
        student.setEmail(null);

        //Assert
        result = student.getEmail();
        Assert.isTrue(result.equals("Kalle@gmail.com"), "Email must be Kalle@gmail.com");
    }

    @Test
    public void getEmail() {
        //Arrange
        Student student = new Student(3);
        String result;
        student.setEmail("Kalle@gmail.com");

        //Act
        result = student.getEmail();

        //Assert
        Assert.isTrue(result.equals("Kalle@gmail.com"), "Email must be Kalle@gmail.com");
    }

    @Test
    public void setAddress() {
        //Arrange
        Student student = new Student(3);
        String result;

        //Act
        student.setAddress("Kullgatan 34");
        student.setAddress(null);

        //Assert
        result = student.getAddress();
        Assert.isTrue(result.equals("Kullgatan 34"), "Address must be Kullgatan 34");
    }

    @Test
    public void getAddress() {
        //Arrange
        Student student = new Student(3);
        String result;
        student.setAddress("Kullgatan 34");

        //Act
        result = student.getAddress();

        //Assert
        Assert.isTrue(result.equals("Kullgatan 34"), "Address must be Kullgatan 34");
    }

    @Test
    public void equals() {
        //Arrange
        Student student1 = new Student(1);
        Student student2 = new Student(2);
        Student student3 = new Student(1);
        student3.setName("Kalle");
        Student student4 = new Student(1);
        student4.setEmail("kalle@gmail.com");
        Student student5 = new Student(1);
        student5.setAddress("Lingongatan 45");
        boolean result1, result2, result3, result4, result5, result6;

        //Act
        result1 = student1.equals(student1);
        result2 = student1.equals(student2);
        result3 = student1.equals(student3);
        result4 = student1.equals(student4);
        result5 = student1.equals(student5);
        result6 = student1.equals(null);

        //Assert
        Assert.isTrue(result1, "Return value should be true");
        Assert.isTrue(!result2, "Return value should be false");
        Assert.isTrue(!result3, "Return value should be false");
        Assert.isTrue(!result4, "Return value should be false");
        Assert.isTrue(!result5, "Return value should be false");
        Assert.isTrue(!result6, "Return value should be false");
    }

    @Test
    public void hash() {
        //Arrange
        Student student1 = new Student(1);
        Student student2 = new Student(1);
        int result1, result2;

        //Act
        result1 = student1.hashCode();
        result2 = student2.hashCode();

        //Assert
        Assert.isTrue(result1 == result2, "Hash codes should be equal");
    }
}
