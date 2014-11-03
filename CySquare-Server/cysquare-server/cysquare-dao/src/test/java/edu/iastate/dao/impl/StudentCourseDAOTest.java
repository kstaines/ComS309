package edu.iastate.dao.impl;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import edu.iastate.domain.Course;

public class StudentCourseDAOTest {

	private StudentCourseDAO studentCourseDao;
	
	@Before
	public void setUp() {
		studentCourseDao = new StudentCourseDAO();
//		deleteData();
//		createData();
	}

	@After
	public void cleanUp() {
//		deleteData();
	}

//	@Test
//	public void testGetTestCourse() {
//		Course course = studentCourseDao.getCourses(studentId)
//		assertEquals("testCourse", course.getName());
//		assertEquals("coov", course.getLocation());
//		assertEquals("13:00:00", course.getTime());
//		assertNotNull(course.getUpdatedTimestamp());
//	}
//
//	private void createData() {
//		studentCourseDao.createCorrelation(0, 1);	
//	}
//	
//	private void deleteData() {
//		studentCourseDao.deleteCourse("testCourse");
//	}
}
