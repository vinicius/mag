package gvc.bean;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import gvc.login.User;
import gvc.login.UserList;

/**
 * @author Root
 *
 */
public class LoginBean {
	private UserList userManagement;
	private String email;
	private String password;
	private User user;
	
	/**
	 *
	 */
	public LoginBean() {
		userManagement = UserList.getInstance();
	}
	
	private void addMessage(String message) {
		String clientID = "loginForm:email";
		FacesContext.getCurrentInstance().addMessage(clientID, new FacesMessage(
				FacesMessage.SEVERITY_INFO, message, message));
	}

	/**
	 * 
	 * @return
	 */
	public String login() {
		user = userManagement.authenticateUser(email, password);
		if(user == null) {
			addMessage("Login incorrect");
			return "failure";
		}
		return "done";
	}
	
	/**
	 * 
	 * @return
	 */
	public String logout() {
		user = null;
	 	FacesContext facesContext = FacesContext.getCurrentInstance();
	 	HttpSession session =
	 		(HttpSession) facesContext .getExternalContext().getSession(false);
	 	session.invalidate();
		return "home";
	}

	/**
	 * @return Returns the authenticated.
	 */
	public boolean isAuthenticated() {
		return user != null;
	}
	
	public boolean isAdmin() {
		if(user == null) return false;
		return user.getGroup().equals(User.ADMIN_GROUP);
	}

	/**
	 * @return Returns the user.
	 */
	public User getUser() {
		return user;
	}

	/**
	 * @param user The user to set.
	 */
	public void setUser(User user) {
		this.user = user;
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
}
