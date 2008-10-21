package gvc.login;

import gvc.util.ConfigurationManager;
import gvc.util.SerializableUtil;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

/**
 * @author paulocb
 *
 */
public class UserList {
	private static String ADMIN = "admin";
	private static UserList instance;
	private Set<User> userList;
	private ConfigurationManager config;
	private File fileList;
	
	private UserList() {
		// TODO tosco, configurationManager não ta legal, talvez o download4J resolva.
		config = ConfigurationManager.getInstance();
		fileList = config.getUserList();
		try {
			userList = (HashSet<User>) SerializableUtil.readObject(fileList);
		} catch (IOException e) {
			System.out.println(
				"Serialized user list not found, creating a new one.");
			userList = new HashSet<User>();
			userList.add(new User(ADMIN, ADMIN, User.ADMIN_GROUP));
			try {
				SerializableUtil.writeObject(userList, fileList);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 * @return
	 */
	public static UserList getInstance() {
		if(instance == null) instance = new UserList();
		return instance;
	}	
	
	/**
	 * 
	 * @return
	 */
	public Set<User> getUserList() {
		return userList;
	}
	
	/**
	 * 
	 * @param user
	 */
	public synchronized void addUser(User user) throws UserException {
		for(User u: userList)
			if(u.getEmail().equals(user.getEmail()))
				throw new UserException("User already exists.");
		//if(!userList.add(user)) throw new UserException("User already exists.");
		userList.add(user);
		try {
			SerializableUtil.writeObject(userList, fileList);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @param user
	 */
	public synchronized void updateUser(User user) throws UserException {
		for(User aux : userList) {
			if(aux.equals(user)) {
				aux.setGroup(user.getGroup());
				aux.setPassword(user.getPassword());				
				try {
					SerializableUtil.writeObject(userList, fileList);
				} catch (IOException e) {
					e.printStackTrace();
				}
				return;
			}
		}
		throw new UserException("User not found.");
		
	}
	
	/**
	 * 
	 * @param user
	 */
	public synchronized void removeUser(User user) throws UserException {
		if(user.getEmail().equals(ADMIN))
			throw new UserException("Impossible to remove this user.");
		if(!userList.remove(user))
			throw new UserException("User does not exist.");
		try {
			SerializableUtil.writeObject(userList, fileList);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 * @param email
	 * @return
	 */
	public User getUserWithEmail(String email) {
		for(User user: userList)
			if(user.getEmail().equals(email))
				return user;
		return null;
	}
	
	/**
	 * 
	 * @param user
	 * @return
	 */
	public User authenticateUser(String email, String password) {
		for(User user: userList) {
			if(email.equals(user.getEmail())) {
				if(password.equals(user.getPassword())) return user;
				else return null; 
			}
		}
		return null;
	}
	
	/**
	 * @return Returns the config.
	 */
	public ConfigurationManager getConfig() {
		return config;
	}

	/**
	 * @param config The config to set.
	 */
	public void setConfig(ConfigurationManager config) {
		this.config = config;
	}


}
