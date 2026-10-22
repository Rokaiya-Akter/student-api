package com.example.student.model;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Student {

    private Integer id;
    private String firstName;
    private String lastName;
    private String email;
    private String major;
    private double gpa;
    private int year; // 1=Freshman, 2=Sophomore, 3=Junior, 4=Senior

    public Student() {}

    public Student(int id, String firstName, String lastName,
                   String email, String major, double gpa, int year) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.major = major;
        this.gpa = gpa;
        this.year = year;
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getMajor() { return major; }
    public void setMajor(String major) { this.major = major; }

    public double getGpa() { return gpa; }
    public void setGpa(double gpa) { this.gpa = gpa; }

    public int getYear() { return year; }
    public void setYear(int year) { this.year = year; }
}
