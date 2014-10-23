package edu.iastate.dao.impl;

import static org.junit.Assert.*;

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
		Course course = courseDao.getCourseInfo("testCourse");
		assertEquals("testCourse", course.getName());
		assertEquals("coov", course.getLocation());
		assertEquals("13:00:00", course.getTime());
		assertNotNull(course.getUpdatedTimestamp());
	}

	private void createData() {
		courseDao.createCourse("testCourse", "coov", "13:00:00", "MWF");		
	}
	
	private void deleteData() {
		courseDao.deleteCourse("testCourse");
	}
}
