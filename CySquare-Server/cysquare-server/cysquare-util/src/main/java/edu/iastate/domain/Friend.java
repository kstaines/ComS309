package edu.iastate.domain;

public class Friend {

	private Integer studentId;
	private Integer friendId;
	private String approved;
	private String updatedTimestamp;
	
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
	public String getApproved() {
		return approved;
	}
	public void setApproved(String approved) {
		this.approved = approved;
	}
	public String getUpdatedTimestamp() {
		return updatedTimestamp;
	}
	public void setUpdatedTimestamp(String updatedTimestamp) {
		this.updatedTimestamp = updatedTimestamp;
	}
	@Override
	public String toString() {
		return "Friend [studentId=" + studentId + ", friendId=" + friendId
				+ ", approved=" + approved + ", updatedTimestamp="
				+ updatedTimestamp + "]";
	}	
	
}
