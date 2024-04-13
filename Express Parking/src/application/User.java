package application;

public class User {
	public String firstname;
	public String lastname;
	public String uuid;
	public String email;
	public String password;
	public int permissions;
	public Boolean loginStatus;

	public User(String firstname, String lastname, String uuid, String email, String password,
			int permissions, Boolean loginStatus) {
		super();
		this.firstname = firstname;
		this.lastname = lastname;
		this.uuid = uuid;
		this.email = email;
		this.password = password;
		this.permissions = permissions;
		this.loginStatus = loginStatus;
	}

	public User() {
		super();
	}

	public int getPermissions() {
		return permissions;
	}

	public void setPermissions(int permissions) {
		this.permissions = permissions;
	}

	public void setLoginStatus(Boolean loginStatus) {
		this.loginStatus = loginStatus;
	}

	public Boolean getLoginStatus() {
		return loginStatus;
	}

	public String getUUID() {
		return uuid;
	}

	public void setUUID(String uuid) {
		this.uuid = uuid;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "User [firstName=" + firstname + ", lastname=" + lastname + ", id=" + uuid
				+ ", email=" + email + ", password=" + password + ", perms=" + permissions + ", loginStatus="
				+ loginStatus + "]";
	}

	public String getFirstname() {
		return firstname;
	}

	public String getLastname() {
		// TODO Auto-generated method stub
		return lastname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public void setLastname(String lastname) {
		// TODO Auto-generated method stub
		this.lastname = lastname;
	}

}
