package edu.iastate.domain;

public class UserAccount {

	private Integer userId;
	private String username;
	private String password;
	private String updatedTimestamp;
	private String userType;
	private Integer totalPts;
	private String approved;
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public String getUpdatedTimestamp() {
		return updatedTimestamp;
	}
	public void setUpdatedTimestamp(String updatedTimestamp) {
		this.updatedTimestamp = updatedTimestamp;
	}
	public String getUserType() {
		return userType;
	}
	public void setUserType(String userType) {
		this.userType = userType;
	}
	public Integer getTotalPts() {
		return totalPts;
	}
	public void setTotalPts(Integer totalPts) {
		this.totalPts = totalPts;
	}
	public String getApproved() {
		return approved;
	}
	public void setApproved(String approved) {
		this.approved = approved;
	}
	@Override
	public String toString() {
		return "UserAccount [userId=" + userId + ", username=" + username
				+ ", password=" + password + ", updatedTimestamp="
				+ updatedTimestamp + ", userType=" + userType + ", totalPts="
				+ totalPts + ", approved=" + approved + "]";
	}
	
}
