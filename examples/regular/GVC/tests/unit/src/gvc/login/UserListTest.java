/**
 * 
 */
package gvc.login;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import junit.framework.JUnit4TestAdapter;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author paulocb
 *
 */
public class UserListTest {
	private static UserList instance;
	
	public static junit.framework.Test suite() {
		return new JUnit4TestAdapter(UserListTest.class);
	}
	
	@BeforeClass public static void setUp() {
		instance = UserList.getInstance();
	}
	
	@AfterClass public static void tearDown() {
		instance = null;
	}
	
	@Before public void runBeforeEachTest() {
		User aux = new User("testeUsuario", "senhaTesteUsuario", User.USER_GROUP);
		try { instance.removeUser(aux); } catch(Exception e) {

		};
	}

	@After public void runAfterEachTest() {
	}
	
	@Test public void testRemoveUser() throws Exception {
		
		User aux = new User("testeUsuario", "senhaTesteUsuario", User.USER_GROUP);
		int sizeBefore = instance.getUserList().size();
		instance.addUser(aux);
		Set list = instance.getUserList();
		assertTrue(list.contains(aux));
		instance.removeUser(aux);
		list = instance.getUserList();
		assertFalse(list.contains(aux));
		assertTrue(sizeBefore == instance.getUserList().size());
	}

	@Test (expected=UserException.class)
	public void testRemoveInexistentUser() throws Exception {
		User aux = new User("testeUsuario", "senhaTesteUsuario", User.USER_GROUP);
		instance.removeUser(aux);		
	}
	
	@Test (expected=UserException.class)
	public void testRemoveAdmin() throws Exception {
		User aux = new User("admin", "senhaTesteUsuario", User.ADMIN_GROUP);
		instance.removeUser(aux);
	}

	@Test public void testAddUser() throws Exception {		
		User aux = new User("testeUsuario", "senhaTesteUsuario", User.USER_GROUP);
		assertFalse(instance.getUserList().contains(aux));
		instance.addUser(aux);		
		assertTrue(instance.getUserList().contains(aux));
	}

	@Test (expected=UserException.class)
	public void testAddExistentUser() throws Exception {
		User aux = new User("testeUsuario", "senhaTesteUsuario", User.USER_GROUP);
		instance.addUser(aux);
		instance.addUser(aux);				
	}
	
	@Test public void testUpdateUser() throws Exception {
		
		User aux = new User("testeUsuario", "senhaTesteUsuario", User.USER_GROUP);
		instance.addUser(aux);
		aux.setGroup(User.ADMIN_GROUP);
		instance.updateUser(aux);

		Set list = instance.getUserList();
		assertTrue(list.contains(aux));
	}

	@Test (expected=UserException.class)
	public void testUpdateInexistentUser() throws Exception {
		User aux = new User("testeUsuario", "senhaTesteUsuario", User.USER_GROUP);
		assertFalse(instance.getUserList().contains(aux));
		instance.updateUser(aux);		
	}
	
	//public User authenticateUser(String email, String password) {
	@Test
	public void testAuthenticateUser() throws Exception {
		User user = new User("user4Test", "user4Test", User.ADMIN_GROUP);
		instance.addUser(user);
		assertTrue(instance.authenticateUser("user4Test", "user4Test") != null);
		assertTrue(instance.authenticateUser("user4Test", "user4TestWrong") == null);
		assertTrue(instance.authenticateUser("user4TestWrong", "user4Test") == null);
		instance.removeUser(user);
	}
}
