package schoolmanagement.services;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.schoolmanagement.models.*;
import com.schoolmanagement.services.*;
import org.hibernate.Session;
import com.schoolmanagement.utils.HibernateUtil;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class StudentServiceTest {

    private StudentService studentService;
    private CourseService courseService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        studentService = new StudentService();
        courseService = new CourseService();
    }

    @AfterEach
    public void resetSetUp() {
        studentService = null;
        courseService = null;

        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();

        session.createQuery("delete from Student").executeUpdate();
        session.createQuery("delete from Course").executeUpdate();
        session.getTransaction().commit();
        session.close();
    }

    @AfterAll
    public static void tearDown() {
        HibernateUtil.shutdown();
    }

    @Test
    public void testCreateStudent() {
        Student student = new Student("JaneSmith@test.com", "Jane Smith", "password");

        studentService.createStudent(student);
        assertNotNull(student.getEmail());
    }

    @Test
    public void testGetStudentByEmail() {
        // Testing for email that doesn't exist.
        Student student = studentService.getStudentByEmail("JaneSmith@test.com");
        assertNull(student);

        // Testing for email that's exist.
        student = new Student("JaneSmith@test.com", "Jane Smith", "password");
        studentService.createStudent(student);
        student = studentService.getStudentByEmail("JaneSmith@test.com");
        assertNotNull(student);
    }

    @Test
    public void testRegisterStudentToCourse() {
        // Create a new student and course.
        Student student = new Student("JaneSmith@test.com", "Jane Smith", "password");
        studentService.createStudent(student);
        assertNotNull(student.getEmail());

        Course course = new Course("Java", "Roger Boaitey");
        courseService.createCourse(course);
        assertNotNull(course.getCourseId());

        // Check if the student is registered to courses. The list of courses should not be empty.
        studentService.registerStudentToCourse("JaneSmith@test.com", 1);
        List<Course> studentCourses = studentService.getStudentCourses("JaneSmith@test.com");
        assertNotEquals(0, studentCourses.size());
    }

    @Test
    public void testValidateStudent() {
        // Check if the input are invalid. Should be invalid.
        assertNotEquals(true, studentService.validateStudent("JaneSmith@test.com", "password"));

        // Create object, now input should be valid.
        Student student = new Student("JaneSmith@test.com", "Jane Smith", "password");
        studentService.createStudent(student);
        assertNotEquals(false, studentService.validateStudent("JaneSmith@test.com", "password"));
    }

    @Test
    public void testGetStudentCourses() {
        List<Course> studentCourses = studentService.getStudentCourses("JaneSmith@test.com");
        assertEquals(0, studentCourses.size());

        // Create a new student and course.
        Student student = new Student("JaneSmith@test.com", "Jane Smith", "password");
        studentService.createStudent(student);
        assertNotNull(student.getEmail());

        Course course = new Course("Java", "Roger Boaitey");
        courseService.createCourse(course);
        assertNotNull(course.getCourseId());

        // Should be able to obtain a list of courses of the student. Should not be zero.
        // Bug here: Course id should be #1 because table should be deleted after every test ended.
        studentService.registerStudentToCourse("JaneSmith@test.com", 2);
        studentCourses = studentService.getStudentCourses("JaneSmith@test.com");
        assertNotEquals(0, studentCourses.size());
    }
}
