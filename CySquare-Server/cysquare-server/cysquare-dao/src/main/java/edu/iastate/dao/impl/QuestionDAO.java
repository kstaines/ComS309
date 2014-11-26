package edu.iastate.dao.impl;

import java.util.ArrayList;
import java.util.List;

import edu.iastate.dao.ifc.DatabaseAccess;
import edu.iastate.domain.Question;

public class QuestionDAO extends DatabaseAccess {

	/**
	 * Retrieve all questions that were added as a particular
	 * instructor. Search based on the instructor userID to find
	 * all questions posted by that instructor.
	 * @param userId
	 * @return
	 */
	public List<Question> getQuestionsFromInstructor(int userId) {
		
		return new ArrayList<Question>();
	}
	
	/**
	 * To create a question, you will need the instructor userID
	 * and the string of the question.
	 * @param instructorId
	 * @param question
	 */
	public void createQuestion(int instructorId, String question) {
		
	}
	
	/**
	 * To delete a question, you will need the unique question ID
	 * that is stored in the question object.
	 * @param questionId
	 */
	public void deleteQuestion(int questionId) {
		
	}
	
}
