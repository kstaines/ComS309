package edu.iastate.domain;

public class StudentCourses {

	private int studentId;
	private int courseId;
	private int updatedTimestamp;
	
	public int getStudentId() {
		return studentId;
	}
	public void setStudentId(int studentId) {
		this.studentId = studentId;
	}
	public int getCourseId() {
		return courseId;
	}
	public void setCourseId(int courseId) {
		this.courseId = courseId;
	}
	
	public int getUpdatedTimestamp() {
		return updatedTimestamp;
	}
	public void setUpdatedTimestamp(int updatedTimestamp) {
		this.updatedTimestamp = updatedTimestamp;
	}
	@Override
	public String toString() {
		return "StudentCourses [studentId=" + studentId + ", courseId="
				+ courseId + ", updatedTimestamp=" + updatedTimestamp + "]";
	}
	
}
