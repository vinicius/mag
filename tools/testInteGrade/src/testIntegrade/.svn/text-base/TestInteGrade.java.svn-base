/*
 * Created on 25/05/2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package testIntegrade;

import org.omg.CORBA.ORB;
import org.omg.CORBA.Object;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;

import clusterManagement.ApplicationRepository;
import clusterManagement.ApplicationRepositoryHelper;
import clusterManagement.Grm;
import clusterManagement.GrmHelper;

/**
 * @author rcamargo
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class TestInteGrade {
        
    private TestCoordinator coordinator = null;
    private TestRepository repositoryTest = null;
    private AsctServer asct = null;
    
    private ORB orb;
    private Grm grm;
    private ApplicationRepository appRepos;

    public static void main(String[] args) {
        TestInteGrade testIntegrade = new TestInteGrade();
        testIntegrade.initStubs();
        testIntegrade.prepareTests();
        testIntegrade.startServer();
        //testIntegrade.performRepositoryTests();
        testIntegrade.performTests(args);
        System.exit(0);
    }
    
    private void prepareTests() {
        coordinator = new TestCoordinator(orb, grm, appRepos);
    }
    
    private void performRepositoryTests() {    	
    		repositoryTest = new TestRepository(orb, appRepos);
    		repositoryTest.makeTest();
    }

    private void performTests(String[] args) {    	
    		TestSuite testSuitMatrixSimple = new TestSuiteMatrixSimple(1000, 1, appRepos); 
        coordinator.performTest(testSuitMatrixSimple);        
    }
    
    private void startServer() {
        asct = new AsctServer(orb, coordinator);
        coordinator.setAsctIor(asct.getIor());
        asct.start();        
    }
    
    private void initStubs(){

    	System.out.println("Initializing stubs...");
    	
        orb = ORB.init(new String [] {}, null);
        try{
        	org.omg.CORBA.Object ns = orb.resolve_initial_references("NameService");
			NamingContextExt nameService = NamingContextExtHelper.narrow( ns );
			
			Object grmObject = nameService.resolve(nameService.to_name("GRM"));
			grm = GrmHelper.narrow(grmObject);

			Object appReposObject = nameService.resolve(nameService.to_name("AR"));
			appRepos = ApplicationRepositoryHelper.narrow(appReposObject);
        }
        catch(Exception exception){
        	exception.printStackTrace();
        	System.exit(-1);
        }

    }

}
