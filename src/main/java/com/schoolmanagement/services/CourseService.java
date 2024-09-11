package com.schoolmanagement.services;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import com.schoolmanagement.models.Course;
import com.schoolmanagement.repository.CourseImpl;

import java.util.List;

public class CourseService {

    private CourseImpl courseRepository;

    public CourseService(CourseImpl courseRepository) { this.courseRepository = courseRepository; }

    public void createCourse(Course course) { courseRepository.createCourse(course); }

    public Course getCourseById(int id) { return courseRepository.getCourseById(id); }

    public List<Course> getAllCourses() { return courseRepository.getAllCourses(); }
}
