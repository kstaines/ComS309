package edu.iastate.domain;

public class Friend {

	private Integer studentId;
	private Integer friendId;
	
	public Integer getStudentId() {
		return studentId;
	}
	public void setStudentId(Integer studentId) {
		this.studentId = studentId;
	}
	public Integer getFriendId() {
		return friendId;
	}
	public void setFriendId(Integer friendId) {
		this.friendId = friendId;
	}
	@Override
	public String toString() {
		return "Friend [studentId=" + studentId + ", friendId=" + friendId
				+ "]";
	}	
	
}
