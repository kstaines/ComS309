package edu.iastate.dao.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import edu.iastate.domain.StudentCourse;

public class StudentCourseDAOTest {

	private StudentCourseDAO studentCourseDao;
	
	@Before
	public void setUp() {
		studentCourseDao = new StudentCourseDAO();
		deleteData();
		createData();
	}

	@After
	public void cleanUp() {
		deleteData();
	}

	@Test
	public void testGetTestCorrelation() {
		List<StudentCourse> studCourses = studentCourseDao.getCourses(0);
		assertNotNull(studCourses);
		assertNotNull(studCourses.get(0));
		assertEquals(Integer.valueOf(1), studCourses.get(0).getCourseId());
	}

	private void createData() {
		studentCourseDao.createCorrelation(0, 1);	
	}
	
	private void deleteData() {
		studentCourseDao.deleteCorrelation(0, 1);
	}
}
