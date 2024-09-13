package com.schoolmanagement.services;

import com.schoolmanagement.dao.CourseI;
import com.schoolmanagement.models.Course;
import com.schoolmanagement.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.HibernateException;

import java.util.List;

public class CourseService implements CourseI {

    @Override
    public void createCourse(Course course) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.save(course);
            tx.commit();
        }
        catch (Exception e) {
            if (tx != null) tx.rollback();
            throw new HibernateException("Unable to create course: ", e);
        }
    }

    @Override
    public Course getCourseById(int id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(Course.class, id);
        }
        catch (Exception e) {
            throw new HibernateException("Unable to get course by Id: ", e);
        }
    }

    @Override
    public List<Course> getAllCourses() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from Course", Course.class).list();
        }
        catch (Exception e) {
            throw new HibernateException("Unable to get all courses: ", e);
        }
    }
}
