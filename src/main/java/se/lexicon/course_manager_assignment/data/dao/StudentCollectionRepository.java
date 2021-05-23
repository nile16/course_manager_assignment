package se.lexicon.course_manager_assignment.data.dao;



import se.lexicon.course_manager_assignment.data.sequencers.StudentSequencer;
import se.lexicon.course_manager_assignment.model.Student;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Locale;


public class StudentCollectionRepository implements StudentDao {

    private Collection<Student> students;

    public StudentCollectionRepository(Collection<Student> students) {
        this.students = students;
    }

    @Override
    public Student createStudent(String name, String email, String address) {
        Student newStudent = new Student(StudentSequencer.nextStudentId());

        newStudent.setName(name);
        newStudent.setEmail(email);
        newStudent.setAddress(address);
        students.add(newStudent);

        return newStudent;
    }

    @Override
    public Student findByEmailIgnoreCase(String email) {
        for (Student student : students) {
            if (student.getEmail().equalsIgnoreCase(email)) {
                return student;
            }
        }
        return null;
    }

    @Override
    public Collection<Student> findByNameContains(String name) {
        Collection<Student> matchingStudents = new ArrayList<Student>();

        for (Student student : students) {
            if (student.getName().toLowerCase(Locale.ROOT).contains(name.trim().toLowerCase(Locale.ROOT))) {
                matchingStudents.add(student);
            }
        }

        return matchingStudents;
    }

    @Override
    public Student findById(int id) {
        for (Student student : students) {
            if (student.getId() == id) {
                return student;
            }
        }
        return null;
    }

    @Override
    public Collection<Student> findAll() {
        return students;
    }

    @Override
    public boolean removeStudent(Student student) {
        if (students.contains(student)) {
            students.remove(student);
            return true;
        }
        return false;
    }

    @Override
    public void clear() {
        this.students = new HashSet<>();
    }
}
