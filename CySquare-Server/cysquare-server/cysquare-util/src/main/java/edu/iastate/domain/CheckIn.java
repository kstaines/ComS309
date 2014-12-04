package edu.iastate.domain;

public class CheckIn {

	private int checkinId;
	private int studentId;
	private int courseId;
	private float latitude;
	private float longitude;
	private String updatedTimestamp;

	public int getCheckinId() {
		return checkinId;
	}

	public void setCheckinId(int checkinId) {
		this.checkinId = checkinId;
	}

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

	public float getLatitude() {
		return latitude;
	}

	public void setLatitude(float latitude) {
		this.latitude = latitude;
	}

	public float getLongitude() {
		return longitude;
	}

	public void setLongitude(float longitude) {
		this.longitude = longitude;
	}

	public String getUpdatedTimestamp() {
		return updatedTimestamp;
	}

	public void setUpdatedTimestamp(String updatedTimestamp) {
		this.updatedTimestamp = updatedTimestamp;
	}
	
	@Override
	public String toString() {
		return "CheckIn [checkinId=" + checkinId + ", studentId=" + studentId
				+ ", courseId=" + courseId + ", latitude=" + latitude
				+ ", longitude=" + longitude + ", updatedTimestamp="
				+ updatedTimestamp + "]";
	}
}
