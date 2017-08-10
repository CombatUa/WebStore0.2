package ua.alex.web.store.entity;

import java.io.InputStream;
import java.time.LocalDate;

public class User {

    private long Id;
    private String firstName;
    private String lastName;
    private double salary;
    private LocalDate dateOfBirth;
    private byte[] picture;
    public User() {
    }

    public User(Long id, String firstName, String lastName, double salary, LocalDate dateOfBirth) {
        this(id, firstName, lastName, salary, dateOfBirth,null);
    }

    public User(Long id, String firstName, String lastName, double salary, LocalDate dateOfBirth, byte[] picture) {
        Id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.salary = salary;
        this.dateOfBirth = dateOfBirth;
        this.picture = picture;
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    @Override
    public String toString() {
        return "User{" +
                "Id=" + Id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", salary=" + salary +
                ", dateOfBirth=" + dateOfBirth +
                '}';
    }


    public byte[] getPicture() {
        return picture;
    }

    public void setPicture(byte[] picture) {
        this.picture = picture;
    }
}


