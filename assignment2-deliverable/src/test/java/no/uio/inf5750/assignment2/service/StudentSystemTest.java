package no.uio.inf5750.assignment2.service;

import java.util.Set;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import static org.junit.Assert.*;
import no.uio.inf5750.assignment2.dao.CourseDAO;
import no.uio.inf5750.assignment2.dao.StudentDAO;
import no.uio.inf5750.assignment2.model.Course;
import no.uio.inf5750.assignment2.model.Student;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:/META-INF/assignment2/beans.xml" })
@Transactional
public class StudentSystemTest {
	static Logger logger = Logger.getLogger(StudentSystemTest.class);
	@Autowired
	StudentSystem studentSystem;

	@Test
	public void GetCourseId() {
		String courseCode = "INF5181";
		String courseName = "Open";
		int courseId = studentSystem.addCourse(courseCode, courseName);
		Course course = studentSystem.getCourse(courseId);
		assertNotNull(course);
		assertEquals(courseId, course.getId());
	}
}