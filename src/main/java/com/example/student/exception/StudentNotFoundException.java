package com.example.student.exception;

public class StudentNotFoundException extends RuntimeException {
    private final int studentId;

    public StudentNotFoundException(int id) {
        super("Student with ID " + id + " not found");
        this.studentId = id;
    }

    public int getStudentId() { return studentId; }
}
