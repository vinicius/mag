package gvc.bean;

import gvc.login.User;
import gvc.login.UserException;
import gvc.login.UserList;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

/**
 * @author Root
 *
 */
public class UserDataManagementBean {
	private UserList userManagement;
	private User user;
	private String password;
	
	public UserDataManagementBean() {
		userManagement = UserList.getInstance();
		FacesContext fc = FacesContext.getCurrentInstance();
		user =	
			(User) fc.getApplication().createValueBinding("#{loginBean.user}").getValue(fc);
		password = user.getPassword();
	}
	
	/**
	 * 
	 * @return
	 */
	public String updateUser() {
		try {
			user.setPassword(password);
			userManagement.updateUser(user);
			addMessage("User updated.");
		} catch (UserException e) {
			//e.printStackTrace();
			addErrorMessage(e.getMessage());
		}
		return "done";
	}
	
	private void addMessage(String message) {
		//new FacesMessage(severity, summary, detail);
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
				FacesMessage.SEVERITY_INFO, message, message));
	}
	private void addErrorMessage(String message) {
		//new FacesMessage(severity, summary, detail);
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
				FacesMessage.SEVERITY_WARN, message, message));
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
