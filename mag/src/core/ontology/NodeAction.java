package core.ontology;


import jade.content.*;
import jade.util.leap.*;
import jade.core.*;

/**
   * Protege name: NodeAction
* @author ontology bean generator
* @version 2007/02/20, 11:28:12
*/
public class NodeAction implements AgentAction {

   /**
   * Protege name: node
   */
   private Node node;
   public void setNode(Node value) { 
    this.node=value;
   }
   public Node getNode() {
     return this.node;
   }

}
