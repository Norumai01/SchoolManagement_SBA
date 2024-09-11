package com.schoolmanagement.repository;

import com.schoolmanagement.models.Course;
import com.schoolmanagement.dao.CourseI;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

public class CourseImpl implements CourseI {

    private SessionFactory sessionFactory;

    public CourseImpl(SessionFactory sessionFactory) { this.sessionFactory = sessionFactory; }


    @Override
    public void createCourse(Course course) {
        Transaction tx = null;
        try (Session session = sessionFactory.openSession()) {
            tx = session.beginTransaction();
            session.save(course);
            tx.commit();
        }
        catch (Exception e) {
            if (tx != null) tx.rollback();
            throw new RuntimeException("Unable to create course: ", e);
        }
    }

    @Override
    public Course getCourseById(int id) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(Course.class, id);
        }
        catch (Exception e) {
            throw new RuntimeException("Unable to get course by Id: ", e);
        }
    }

    @Override
    public List<Course> getAllCourses() {
       try (Session session = sessionFactory.openSession()) {
           return sessionFactory.openSession().createQuery("from Course", Course.class).list();
       }
       catch (Exception e) {
           throw new RuntimeException("Unable to get all courses: ", e);
       }
    }
}
