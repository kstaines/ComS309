package edu.iastate.domain;

public class InstructorCourse {

	private int instructorId;
	private int courseId;
	private String updatedTimestamp;
	
	public int getInstructorId() {
		return instructorId;
	}
	public void setInstructorId(int instructorId) {
		this.instructorId = instructorId;
	}
	public int getCourseId() {
		return courseId;
	}
	public void setCourseId(int courseId) {
		this.courseId = courseId;
	}
	public String getUpdatedTimestamp() {
		return updatedTimestamp;
	}
	public void setUpdatedTimestamp(String updatedTimestamp) {
		this.updatedTimestamp = updatedTimestamp;
	}
	@Override
	public String toString() {
		return "InstrucrtorCourse [instructorId=" + instructorId
				+ ", courseId=" + courseId + ", updatedTimestamp="
				+ updatedTimestamp + "]";
	}
	
}
