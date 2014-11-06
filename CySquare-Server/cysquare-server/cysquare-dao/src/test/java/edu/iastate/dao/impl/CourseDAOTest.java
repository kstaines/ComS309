package edu.iastate.dao.impl;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import edu.iastate.domain.Course;

public class CourseDAOTest {

	private CourseDAO courseDao;
	
	@Before
	public void setUp() {
		courseDao = new CourseDAO();
		deleteData();
		createData();
	}

	@After
	public void cleanUp() {
		deleteData();
	}

	@Test
	public void testGetTestCourse() {
		List<Course> courses = courseDao.getCourseInfo("testCourse");
		assertEquals("testCourse", courses.get(0).getName());
		assertEquals("coov", courses.get(0).getLocation());
		assertEquals("13:00:00", courses.get(0).getTime());
		assertNotNull(courses.get(0).getUpdatedTimestamp());
	}

	private void createData() {
		courseDao.createCourse("testCourse", "coov", "13:00:00", "MWF");		
	}
	
	private void deleteData() {
		courseDao.deleteCourse("testCourse");
	}
}
