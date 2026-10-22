package com.example.student.service;

import com.example.student.exception.StudentNotFoundException;
import com.example.student.model.Student;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * Singleton service layer — business logic + in-memory persistence.
 */
public class StudentService {

    private static final StudentService INSTANCE = new StudentService();
    private final Map<Integer, Student> db = new LinkedHashMap<>();
    private final AtomicInteger idGen = new AtomicInteger(4);

    private StudentService() {
        db.put(1, new Student(1, "Alice", "Johnson", "alice@uni.edu",  "Computer Science", 3.8, 3));
        db.put(2, new Student(2, "Bob",   "Smith",   "bob@uni.edu",    "Mathematics",      3.5, 2));
        db.put(3, new Student(3, "Carol", "Williams","carol@uni.edu",  "Physics",          3.9, 4));
        db.put(4, new Student(4, "Dave",  "Brown",   "dave@uni.edu",   "Engineering",      3.2, 1));
    }

    public static StudentService getInstance() { return INSTANCE; }

    /** Return all students, optionally filtered by major. */
    public List<Student> findAll(String major) {
        return db.values().stream()
                .filter(s -> major == null || s.getMajor().equalsIgnoreCase(major))
                .collect(Collectors.toList());
    }

    /** Find by ID or throw. */
    public Student findById(int id) {
        Student s = db.get(id);
        if (s == null) throw new StudentNotFoundException(id);
        return s;
    }

    /** Create a new student. */
    public Student create(Student student) {
        int id = idGen.incrementAndGet();
        student.setId(id);
        db.put(id, student);
        return student;
    }

    /** Full update — replace all fields. */
    public Student update(int id, Student student) {
        if (!db.containsKey(id)) throw new StudentNotFoundException(id);
        student.setId(id);
        db.put(id, student);
        return student;
    }

    /** Partial update — only non-null fields are applied. */
    public Student patch(int id, Student patch) {
        Student existing = findById(id);
        if (patch.getFirstName() != null) existing.setFirstName(patch.getFirstName());
        if (patch.getLastName()  != null) existing.setLastName(patch.getLastName());
        if (patch.getEmail()     != null) existing.setEmail(patch.getEmail());
        if (patch.getMajor()     != null) existing.setMajor(patch.getMajor());
        if (patch.getGpa()       > 0)    existing.setGpa(patch.getGpa());
        if (patch.getYear()      > 0)    existing.setYear(patch.getYear());
        return existing;
    }

    /** Delete by ID or throw. */
    public void delete(int id) {
        if (db.remove(id) == null) throw new StudentNotFoundException(id);
    }

    /** Search by name fragment (case-insensitive). */
    public List<Student> search(String query) {
        String q = query.toLowerCase();
        return db.values().stream()
                .filter(s -> s.getFirstName().toLowerCase().contains(q)
                          || s.getLastName().toLowerCase().contains(q)
                          || s.getEmail().toLowerCase().contains(q))
                .collect(Collectors.toList());
    }
}
