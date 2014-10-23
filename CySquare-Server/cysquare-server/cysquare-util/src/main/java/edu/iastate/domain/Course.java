package edu.iastate.domain;

public class Course {
	
	private int courseId;
	private String name;
	private String location;
	private String time;
	private String updatedTimestamp;
	private String days;
	
	public int getCourseId() {
		return courseId;
	}
	public void setCourseId(int courseId) {
		this.courseId = courseId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getUpdatedTimestamp() {
		return updatedTimestamp;
	}
	public void setUpdatedTimestamp(String updatedTimestamp) {
		this.updatedTimestamp = updatedTimestamp;
	}
	public String getDays() {
		return days;
	}
	public void setDays(String days) {
		this.days = days;
	}
	
	@Override
	public String toString() {
		return "Course [courseId=" + courseId + ", name=" + name
				+ ", location=" + location + ", time=" + time + ", days="
				+ days + "]";
	}
	
}
