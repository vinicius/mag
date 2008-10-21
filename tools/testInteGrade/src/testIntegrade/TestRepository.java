package testIntegrade;
import org.omg.CORBA.ORB;

import clusterManagement.ApplicationRepository;
import clusterManagement.DirectoryCreationException;
import clusterManagement.DirectoryNotEmptyException;
import clusterManagement.DirectoryNotFoundException;
import clusterManagement.InvalidPathNameException;
import clusterManagement.SecurityException;

public class TestRepository {
	private ORB orb;
	private ApplicationRepository appRepos;
    private String appReposIor;

	TestRepository(ORB orb, ApplicationRepository appRepos){
		this.orb = orb;
		this.appRepos = appRepos;
		
		appReposIor = orb.object_to_string(appRepos);
		 
	}

	boolean createDirectoryTest( String dirName ) {
		try {
			appRepos.createDirectory(dirName);
		} catch (InvalidPathNameException e) {
			e.printStackTrace();
			return false;
		} catch (DirectoryCreationException e) {
			e.printStackTrace();
			return false;
		} catch (SecurityException e) {
			e.printStackTrace();
			return false;
		}
		return true;		
	}

	boolean removeDirectoryTest( String dirName ) {
		try {
			appRepos.removeDirectory(dirName);
		} catch (InvalidPathNameException e) {
			e.printStackTrace();
			return false;
		} catch (DirectoryNotFoundException e) {
			e.printStackTrace();
			return false;
		} catch (DirectoryNotEmptyException e) {
			e.printStackTrace();
			return false;
		} catch (SecurityException e) {
			e.printStackTrace();
			return false;
		}

		return true;		
	}

	void makeTest(){
		this.createDirectoryTest("MyDirectory");
		this.createDirectoryTest("MyDirectory");
	}
	
}
