package core.ontology;


import jade.content.*;
import jade.util.leap.*;
import jade.core.*;

/**
   * Protege name: Node
* @author ontology bean generator
* @version 2007/02/20, 11:28:12
*/
public class Node implements Concept {

   /**
   * Protege name: nodeName
   */
   private String nodeName;
   public void setNodeName(String value) { 
    this.nodeName=value;
   }
   public String getNodeName() {
     return this.nodeName;
   }

}
