package gvc.bean;

import gvc.login.User;
import gvc.login.UserException;
import gvc.login.UserList;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;

/**
 * @author Root
 *
 */
public class UserManagementBean {
	private UserList userManagement;
	private String email;
	private String password;
	private String group;
	
	public UserManagementBean() {
		userManagement = UserList.getInstance();
		copyValues(getCurrentUserList().get(0));
	}
	
	private void copyValues(User user) {
		email = user.getEmail();
		password = user.getPassword(); 
		group = user.getGroup();		
	}
	private User getCurrentUser() {
		return new User(email, password, group);
	}
	
	private List<User> getCurrentUserList() {
		return new ArrayList<User>(UserList.getInstance().getUserList());
	}
	
	/**
	 * 
	 * @return
	 */
	public Collection<SelectItem> getUserList() {
		Collection<SelectItem> list = new ArrayList<SelectItem>();
		for(User user: getCurrentUserList())
			list.add(new SelectItem(user.getEmail(), user.getEmail()));
		return list;
	}
	
	/**
	 * 
	 * @param event
	 */
	public void selectUserValueChangeListener(ValueChangeEvent event) {
		User user = userManagement.getUserWithEmail(event.getNewValue().toString());
		if(user != null) copyValues(user);
		else {addErrorMessage("User not found");
			copyValues(new User("", "", ""));
		} 
	}
	
	/**
	 * 
	 * @return
	 */
	public String removeUser() {
		try {
			userManagement.removeUser(getCurrentUser());
			addMessage("User removed.");
		} catch (UserException e) {
			//e.printStackTrace();
			addErrorMessage(e.getMessage());
		}
		return "done";
	}
	
	/**
	 * 
	 * @return
	 */
	public String addUser() {
		try {
			userManagement.addUser(getCurrentUser());
			addMessage("User added.");
		} catch (UserException e) {
			//e.printStackTrace();
			addErrorMessage(e.getMessage());
		}
		return "done";
	}
	
	/**
	 * 
	 * @return
	 */
	public String updateUser() {
		try {
			userManagement.updateUser(getCurrentUser());
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
}
