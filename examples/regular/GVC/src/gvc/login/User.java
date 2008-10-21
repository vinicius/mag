package gvc.login;

import java.io.Serializable;

/**
 * @author paulocb
 *
 */
public class User implements Serializable {
	
	public static String ADMIN_GROUP = "Admin";
	public static String USER_GROUP = "User";
	
	private String email;
	private String password;
	private String group;
	
	/**
	 * @param email
	 * @param group
	 * @param password
	 */
	public User(String email, String password, String group) {
		super();
		this.email = email;
		this.group = group;
		this.password = password;
	}
	
	/**
	 * @return Returns the email.
	 */
	public String getEmail() {
		return email;
	}
	/**
	 * @param email The email to set.
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	/**
	 * @return Returns the group.
	 */
	public String getGroup() {
		return group;
	}
	/**
	 * @param group The group to set.
	 */
	public void setGroup(String group) {
		this.group = group;
	}
	/**
	 * @return Returns the password.
	 */
	public String getPassword() {
		return password;
	}
	/**
	 * @param password The password to set.
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	
	public boolean equals(Object obj) {
		if(!(obj instanceof User)) return false;
		User user = (User) obj;
		return user.getEmail().equals(email);
	}
	
	public int hashCode() {
		return email.hashCode();
	}
		
}
