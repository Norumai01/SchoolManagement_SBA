package com.schoolmanagement.services;

import com.schoolmanagement.models.Course;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import com.schoolmanagement.models.Student;
import com.schoolmanagement.repository.StudentImpl;

import java.util.List;

public class StudentService {

    private StudentImpl studentRepository;

    public StudentService(StudentImpl studentRepository) { this.studentRepository = studentRepository; }

    public void createStudent(Student student) { studentRepository.createStudent(student); }

    public Student getStudentByEmail(String email) { return studentRepository.getStudentByEmail(email); }

    public void registerStudentToCourse(String email, int courseId) { studentRepository.registerStudentToCourse(email, courseId); }

    public boolean validateStudent(String email, String password) { return studentRepository.validateStudent(email, password); }

    public List<Course> getStudentCourses(String email) { return studentRepository.getStudentCourses(email); }
}
