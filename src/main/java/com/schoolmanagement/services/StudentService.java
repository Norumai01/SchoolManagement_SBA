package com.schoolmanagement.services;

import com.schoolmanagement.models.Course;
import com.schoolmanagement.dao.StudentI;
import com.schoolmanagement.models.Student;
import com.schoolmanagement.utils.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;

public class StudentService implements StudentI {

    @Override
    public void createStudent(Student student) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.save(student);
            tx.commit();
        }
        catch (Exception e) {
            if (tx != null) tx.rollback();
            // Rollback before throwing exception.
            throw new HibernateException("Unable to create student: ", e);
        }
    }

    @Override
    public Student getStudentByEmail(String email) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(Student.class, email);
        }
        catch (Exception e) {
            throw new HibernateException("Student not found: ", e);
        }
    }

    @Override
    public void registerStudentToCourse(String email, int courseId) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            Student student = session.get(Student.class, email);
            Course course = session.get(Course.class, courseId);
            if (student != null || course != null) {
                student.getCourses().add(course);
                session.update(student);
            }
            else {
                throw new HibernateException("Student or Course not found");
            }
            tx.commit();
        }
        catch (Exception e) {
            if (tx != null) tx.rollback();
            throw new HibernateException("Exception: ", e);
        }
    }

    @Override
    public boolean validateStudent(String email, String password) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Student student = session.get(Student.class, email);
            return (student != null) && (student.getPassword().equals(password));
        }
        catch (Exception e) {
            throw new HibernateException("Unable to validate: ", e);
        }
    }

    @Override
    public List<Course> getStudentCourses(String email) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Student student = session.get(Student.class, email);
            if (student != null) {
                return new ArrayList<>(student.getCourses());
            }
            return new ArrayList<>();
        }
        catch (Exception e) {
            throw new HibernateException("Unable to get student courses: ", e);
        }
    }
}
