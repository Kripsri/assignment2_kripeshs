package no.uio.inf5750.assignment2.service.impl;

import java.io.Console;
import java.util.Collection;

import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonIgnore;

import no.uio.inf5750.assignment2.dao.*;
import no.uio.inf5750.assignment2.model.Course;
import no.uio.inf5750.assignment2.model.Student;
import no.uio.inf5750.assignment2.service.StudentSystem;

@Component("defaultStudentSystem")
public class DefaultStudentSystem implements StudentSystem {
	static Logger logger = Logger.getLogger(DefaultStudentSystem.class);
	@Autowired
	private CourseDAO courseDAO;
	@Autowired
	private StudentDAO studentDAO;

	@Override
	public int addCourse(String courseCode, String name) {
		Course course = new Course(courseCode, name);
		return courseDAO.saveCourse(course);
	}

	@Override
	public void updateCourse(int courseId, String courseCode, String name) {
		Course course;
		course = courseDAO.getCourse(courseId);
		course.setName(name);
		course.setCourseCode(courseCode);
		courseDAO.saveCourse(course);

	}

	@Override
	public Course getCourse(int courseId) {
		return courseDAO.getCourse(courseId);
	}

	@Override
	public Course getCourseByCourseCode(String courseCode) {
		return courseDAO.getCourseByCourseCode(courseCode);
	}

	@Override
	public Course getCourseByName(String name) {
		return courseDAO.getCourseByName(name);
	}
	
	@Override
	public Collection<Course> getAllCourses() {
		return courseDAO.getAllCourses();
	}

	@Override
	public void delCourse(int courseId) {
		courseDAO.delCourse(courseDAO.getCourse(courseId));
	}

	@Override
	public void addAttendantToCourse(int courseId, int studentId) {
		logger.debug("courseId is '" + courseId + "'");
		logger.debug("studentId is '" + studentId + "'");

		Course course = courseDAO.getCourse(courseId);
		if (course == null)
			logger.debug("course is null");
		else
			logger.debug("course name is '" + course.getName() + "'");

		Student student = studentDAO.getStudent(studentId);
		if (student == null)
			logger.debug("student is null");
		else
			logger.debug("student name is '" + student.getName() + "'");

		if (course.getAttendants() == null)
			logger.debug("course.getAttendants is null");
		else
			logger.debug("course.getAttendants is not null");
		if (student.getCourses() == null)
			logger.debug("student.getCourses is null");
		else
			logger.debug("student.getCourses is not null");
		// course.getAttendants().add(student);
		student.getCourses().add(course);
		studentDAO.saveStudent(student);
	}

	@Override
	public void removeAttendantFromCourse(int courseId, int studentId) {
		Course course = courseDAO.getCourse(courseId);
		Student student = studentDAO.getStudent(studentId);

		logger.debug("Number of the course for particular student" + student.getCourses().size());
		student.getCourses().remove(course);
		studentDAO.saveStudent(student);
	}
	@Override
	public int addStudent(String name) {
		Student student = new Student(name);
		return studentDAO.saveStudent(student);
	}

	@Override
	public void updateStudent(int studentId, String name) {
		Student student = studentDAO.getStudent(studentId);
		student.setName(name);
		studentDAO.saveStudent(student);

	}

	@Override
	public Student getStudent(int studentId) {
		return studentDAO.getStudent(studentId);
	}

	@Override
	public Student getStudentByName(String name) {
		return studentDAO.getStudentByName(name);
	}

	@Override
	public Collection<Student> getAllStudents() {
		return studentDAO.getAllStudents();
	}

	@Override
	public void delStudent(int studentId) {
		logger.debug("inside delete student:" + studentId);
		studentDAO.delStudent(studentDAO.getStudent(studentId));

	}

	@Override
	public void setStudentLocation(int studentId, String latitude, String longitude) {
		Student student = studentDAO.getStudent(studentId);
		student.setLatitude(latitude);
		student.setLongitude(longitude);
		studentDAO.saveStudent(student);

	}

}