package no.uio.inf5750.assignment2.dao.hibernate;

import java.util.Collection;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import no.uio.inf5750.assignment2.dao.*;
import no.uio.inf5750.assignment2.model.*;

@Transactional
public class HibernateStudentDAO implements StudentDAO {
	@Autowired
	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	// @Override
	// public int saveStudent(Student student) {
	// Session session = sessionFactory.getCurrentSession();
	// return (Integer) session.save(student);

	// }
	@Override
	public int saveStudent(Student student) {
		sessionFactory.getCurrentSession().save(student);
		return student.getId();
	}

	@Override
	public Student getStudent(int id) {
		Session session = sessionFactory.getCurrentSession();
		return (Student) session.get(Student.class, id);
	}

	@Override
	public Student getStudentByName(String name) {
		Session session = sessionFactory.getCurrentSession();
		return (Student) session.get(Student.class, name);
	}

	@Override
	@SuppressWarnings({ "deprecation", "unchecked" })
	public Collection<Student> getAllStudents() {
		Session session = sessionFactory.getCurrentSession();
		return session.createCriteria(Student.class).list();
	}

	@Override
	public void delStudent(Student student) {
		Session session = sessionFactory.getCurrentSession();
		Student studentToDelete = session.get(Student.class, student.getId());
		session.delete(studentToDelete);

	}

}