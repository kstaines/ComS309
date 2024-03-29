package edu.iastate.domain;

public class StudentCourse {

	private Integer studentId;
	private Integer courseId;
	private Integer points;
	private Integer timesCheckedIn;
	private Integer updatedTimestamp;
	
	public Integer getStudentId() {
		return studentId;
	}
	public void setStudentId(Integer studentId) {
		this.studentId = studentId;
	}
	public Integer getCourseId() {
		return courseId;
	}
	public void setCourseId(Integer courseId) {
		this.courseId = courseId;
	}
	public Integer getPoints() {
		return points;
	}
	public void setPoints(Integer points) {
		this.points = points;
	}
	public Integer getTimesCheckedIn() {
		return timesCheckedIn;
	}
	public void setTimesCheckedIn(Integer timesCheckedIn) {
		this.timesCheckedIn = timesCheckedIn;
	}
	public Integer getUpdatedTimestamp() {
		return updatedTimestamp;
	}
	public void setUpdatedTimestamp(Integer updatedTimestamp) {
		this.updatedTimestamp = updatedTimestamp;
	}
	@Override
	public String toString() {
		return "StudentCourses [studentId=" + studentId + ", courseId="
				+ courseId + ", updatedTimestamp=" + updatedTimestamp + "]";
	}
	
}
