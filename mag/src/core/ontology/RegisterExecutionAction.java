package core.ontology;


import jade.content.*;
import jade.util.leap.*;
import jade.core.*;

/**
   * Protege name: RegisterExecutionAction
* @author ontology bean generator
* @version 2007/02/20, 11:28:12
*/
public class RegisterExecutionAction implements AgentAction {

   /**
   * Protege name: execution
   */
   private ExecutionInfo execution;
   public void setExecution(ExecutionInfo value) { 
    this.execution=value;
   }
   public ExecutionInfo getExecution() {
     return this.execution;
   }

}
