package arsm;

import java.util.Properties;
import java.io.File;
import java.io.PrintWriter;
import java.io.FileOutputStream;
import org.omg.CORBA.ORB;
import org.omg.PortableServer.POA;
import org.omg.PortableServer.POAHelper;
import org.omg.CORBA.Object;
import org.omg.CosNaming.NamingContext;
import org.omg.CosNaming.NamingContextExtHelper;
import org.omg.CosNaming.NamingContextExt;

/**
 Class ArsmLauncher - Launches a GRM
 @author Braga Junior
 FIXME! TIRAR ISSO, E LANCAR A PARTIR DO LAUNCHER DO AR

*/
public class ArsmLauncher{


/**
 Launches a RSM

 @param args - no args

*/
public static void main(String [] args){
 try{
   ArsmLogger arsmLogger = ArsmLogger.init("integrade.rsm","rsm.log");
   arsmLogger.info("RSM started");
   Properties p = new Properties();
   p.put("org.omg.CORBA.ORBClass","org.jacorb.orb.ORB");
   p.put("org.omg.CORBA.ORBSingletonClass","org.jacorb.orb.ORBSingleton");
   ORB orb = ORB.init(args, p);
   POA poa = POAHelper.narrow(orb.resolve_initial_references("RootPOA"));
   poa.the_POAManager().activate();
   Object rsmObj = poa.servant_to_reference(new ArsmImpl(true));
   //System.out.println(orb.object_to_string( rsmObj ));
   // get a reference to the naming service
   org.omg.CORBA.Object ns = orb.resolve_initial_references("NameService");
   NamingContextExt nameService = NamingContextExtHelper.narrow( ns );
   // register RSM object
   nameService.rebind(nameService.to_name("ARSM"), rsmObj);
   orb.run();
 
 }
 catch(Exception e){
   e.printStackTrace();
 }

}
}


