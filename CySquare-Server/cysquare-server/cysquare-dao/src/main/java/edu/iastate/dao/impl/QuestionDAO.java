package edu.iastate.dao.impl;

import java.util.ArrayList;
import java.util.List;

import edu.iastate.dao.ifc.DatabaseAccess;
import edu.iastate.domain.Question;

public class QuestionDAO extends DatabaseAccess {

	public List<Question> getQuestionsFromInstructor(int userId) {
		
		return new ArrayList<Question>();
	}
	
}
