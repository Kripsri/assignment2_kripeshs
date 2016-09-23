package no.uio.inf5750.assignment2.gui.controller;

import no.uio.inf5750.assignment2.model.Student;
import no.uio.inf5750.assignment2.model.Course;
import no.uio.inf5750.assignment2.service.StudentSystem;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import java.util.Collection;

import org.apache.log4j.Logger;

@RestController
public class ApiController {

	static Logger logger = Logger.getLogger(ApiController.class);

	@Autowired
	private StudentSystem studentSystem;

	@RequestMapping(value = "/api/student", method = RequestMethod.GET)
	@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "@id")
	@ResponseBody
	public Collection<Student> getAllStudents() {
		return studentSystem.getAllStudents();
	}

	@RequestMapping(value = "api/student/{student}/location", method = RequestMethod.GET)
	@ResponseBody
	public Collection<Student> setLocation(@PathVariable("student") int student,
			@RequestParam(value = "longitude") String longitude, @RequestParam(value = "latitude") String latitude) {
		System.err.println("inside setLocation API controller + student = " + student + "  longitude = " + longitude
				+ " latidude = " + latitude);
		studentSystem.setStudentLocation(student, latitude, longitude);
		return studentSystem.getAllStudents();
	}

	@RequestMapping(value = "/api/course", method = RequestMethod.GET)
	@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "@id")
	@ResponseBody
	public Collection<Course> getAllCourses() {
		return studentSystem.getAllCourses();
	}
}
