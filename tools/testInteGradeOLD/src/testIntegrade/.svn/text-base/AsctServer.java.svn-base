/*
 * Created on 25/05/2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package testIntegrade;

import org.omg.CORBA.ORB;
import org.omg.PortableServer.POA;
import org.omg.PortableServer.POAHelper;

/**
 * @author rcamargo
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class AsctServer extends Thread {

    ORB orb;
    String asctIor;
    
    /**
     * 
     */
    public AsctServer(ORB orb, TestCoordinator coord) {

        this.orb = orb;
        
        try{
            POA poa = POAHelper.narrow(orb.resolve_initial_references("RootPOA"));
            poa.the_POAManager().activate();
            org.omg.CORBA.Object coordObj = poa.servant_to_reference(new AsctImpl(coord));
            asctIor = orb.object_to_string(coordObj);
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    public String getIor(){
        return asctIor;
    }
    
    public void run() {
        orb.run();
    }

}
