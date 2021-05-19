package se.lexicon.course_manager_assignment.model;

import java.util.Objects;

public class Student {
    private int id;
    private String name;
    private String email;
    private String address;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return Objects.equals(getId(), student.getId()) && Objects.equals(getName(), student.getName()) && Objects.equals(getEmail(), student.getEmail()) && Objects.equals(getAddress(), student.getAddress());
    }

    public int hashCode() {
        return Objects.hash(getId(), getName(), getEmail(), getAddress());
    }

    public String toString() {
        return getId() + ", " + getName() + ", " + getAddress() + ", " + getEmail();
    }
}
