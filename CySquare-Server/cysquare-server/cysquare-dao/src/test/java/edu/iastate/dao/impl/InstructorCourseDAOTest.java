package edu.iastate.dao.impl;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import edu.iastate.domain.InstructorCourse;

public class InstructorCourseDAOTest {

	InstructorCourseDAO instCourseDao = new InstructorCourseDAO();
	
	@Before
	public void setUp() throws Exception {
		deleteData();
		createData();
	}

	@After
	public void tearDown() throws Exception {
		deleteData();
	}

	@Test
	public void testCreateCorrelation() {
		List<InstructorCourse> instCourse = instCourseDao.getCourses(0);
		assertNotNull(instCourse);
		assertNotNull(instCourse.get(0));
		assertEquals(false, instCourse.isEmpty());
	}
	
	private void createData() {
		instCourseDao.createCorrelation(0, 1);
	}
	
	private void deleteData() {
		instCourseDao.deleteCorrelation(0, 1);
	}

}
