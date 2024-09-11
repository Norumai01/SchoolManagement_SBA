package com.schoolmanagement.dao;

import com.schoolmanagement.models.*;

import java.util.List;

public interface StudentI {
    void createStudent(Student student);
    Student getStudentByEmail(String email);
    void registerStudentToCourse(String email, int courseId);
    boolean validateStudent(String email, String password);
    List<Course> getStudentCourses(String email);
}
