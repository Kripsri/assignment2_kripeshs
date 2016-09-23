package no.uio.inf5750.assignment2.dao.hibernate;

import java.util.Collection;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.annotation.JsonIgnore;

import no.uio.inf5750.assignment2.dao.*;
import no.uio.inf5750.assignment2.model.*;

@Transactional
public class HibernateCourseDAO implements CourseDAO {

	@Autowired
	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	public int saveCourse(Course course) {
		Session session = sessionFactory.getCurrentSession();
		return (Integer) session.save(course);
	}

	@Override
	public Course getCourse(int id) {
		Session session = sessionFactory.getCurrentSession();
		return (Course) session.get(Course.class, id);
	}

	@Override
	public Course getCourseByCourseCode(String courseCode) {
		Session session = sessionFactory.getCurrentSession();
		return (Course) session.get(Course.class, courseCode);
	}

	@Override
	public Course getCourseByName(String name) {
		Session session = sessionFactory.getCurrentSession();
		return (Course) session.get(Course.class, name);
	}

	@Override
	@SuppressWarnings({ "deprecation", "unchecked" })
	public Collection<Course> getAllCourses() {
		Session session = sessionFactory.getCurrentSession();
		return session.createCriteria(Course.class).list();
	}

	@Override
	public void delCourse(Course course) {
		Session session = sessionFactory.getCurrentSession();

		Course courseToDelete = session.get(Course.class, course.getId());
		session.delete(courseToDelete);
	}

}