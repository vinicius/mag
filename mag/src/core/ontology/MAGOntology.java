// file: MAGOntology.java generated by ontology bean generator.  DO NOT EDIT, UNLESS YOU ARE REALLY SURE WHAT YOU ARE DOING!
package core.ontology;

import jade.content.onto.*;
import jade.content.schema.*;
import jade.util.leap.HashMap;
import jade.content.lang.Codec;
import jade.core.CaseInsensitiveString;

/** file: MAGOntology.java
 * @author ontology bean generator
 * @version 2007/02/20, 11:28:12
 */
public class MAGOntology extends jade.content.onto.Ontology implements ProtegeTools.ProtegeOntology {
   /**
    * These hashmap store a mapping from jade names to either protege names of SlotHolder 
    * containing the protege names.  And vice versa
    */  
   private HashMap jadeToProtege;

  //NAME
  public static final String ONTOLOGY_NAME = "MAG";
  // The singleton instance of this ontology
  private static ProtegeIntrospector introspect = new ProtegeIntrospector();
  private static Ontology theInstance = new MAGOntology();
  public static Ontology getInstance() {
     return theInstance;
  }

   // ProtegeOntology methods
   public SlotHolder getSlotNameFromJADEName(SlotHolder jadeSlot) {
     return (SlotHolder) jadeToProtege.get(jadeSlot);
   }


   // storing the information
   private void storeSlotName(String jadeName, String javaClassName, String slotName){
       jadeToProtege.put(new SlotHolder(javaClassName, jadeName), new SlotHolder(javaClassName, slotName));
   }


   // VOCABULARY
    public static final String NODEACTION_NODE="node";
    public static final String NODEACTION="NodeAction";
    public static final String REQUESTEXECUTIONINFOACTION_EXECUTION="execution";
    public static final String REQUESTEXECUTIONINFOACTION="RequestExecutionInfoAction";
    public static final String REGISTEREXECUTIONACTION_EXECUTION="execution";
    public static final String REGISTEREXECUTIONACTION="RegisterExecutionAction";
    public static final String RECOVERNODEACTION="RecoverNodeAction";
    public static final String SETEXECUTIONACCEPTEDACTION_AHIOR="ahIor";
    public static final String SETEXECUTIONACCEPTEDACTION_EXECUTION="execution";
    public static final String SETEXECUTIONACCEPTEDACTION="SetExecutionAcceptedAction";
    public static final String CHECKPOINTACTION_EXECID="execId";
    public static final String CHECKPOINTACTION_CHECKPOINT="checkpoint";
    public static final String CHECKPOINTACTION="CheckpointAction";
    public static final String SETPROCESSEXECUTIONFINISHEDACTION_AHIOR="ahIor";
    public static final String SETPROCESSEXECUTIONFINISHEDACTION_EXECUTION="execution";
    public static final String SETPROCESSEXECUTIONFINISHEDACTION="setProcessExecutionFinishedAction";
    public static final String SAVECHECKPOINTACTION="SaveCheckpointAction";
    public static final String RETRIEVECHECKPOINTACTION="RetrieveCheckpointAction";
    public static final String UNREGISTEREXECUTIONACTION_EXECUTION="execution";
    public static final String UNREGISTEREXECUTIONACTION="UnregisterExecutionAction";
    public static final String REQUESTINPUTFILESACTION_EXECUTION="execution";
    public static final String REQUESTINPUTFILESACTION="RequestInputFilesAction";
    // Vinicius {
    public static final String REQUESTOUTPUTFILESACTION_EXECUTION="execution";
    public static final String REQUESTOUTPUTFILESACTION="RequestOutputFilesAction";
    // } Vinciius
    public static final String CHANGEEXECUTIONLOCATIONACTION_NEWHOST="newHost";
    public static final String CHANGEEXECUTIONLOCATIONACTION_EXECUTION="execution";
    public static final String CHANGEEXECUTIONLOCATIONACTION="ChangeExecutionLocationAction";
    public static final String OUTPUTFILE="OutputFile";
    public static final String FILE_FILENAME="fileName";
    public static final String FILE_CONTENT="content";
    public static final String FILE="File";
    public static final String EXECUTIONINFO_BINARYNAME="binaryName";
    public static final String EXECUTIONINFO_EXECUTIONSTATE="executionState";
    public static final String EXECUTIONINFO_EXECUTINGHOST="executingHost";
    public static final String EXECUTIONINFO_EXECUTIONTYPE="executionType";
    public static final String EXECUTIONINFO_APPARGS="appArgs";
    public static final String EXECUTIONINFO_ASCTIOR="asctIor";
    public static final String EXECUTIONINFO_APPEXECUTIONID="appExecutionId";
    public static final String EXECUTIONINFO_APPCONSTRAINTS="appConstraints";
    public static final String EXECUTIONINFO_INITIALTIMESTAMP="initialTimestamp";
    public static final String EXECUTIONINFO_NUMBEROFREPLICAS="numberOfReplicas";
    public static final String EXECUTIONINFO_INPUTFILES="inputFiles";
    public static final String EXECUTIONINFO_FINISHTIMESTAMP="finishTimestamp";
    public static final String EXECUTIONINFO_APPREPLICAID="appReplicaId";
    public static final String EXECUTIONINFO_APPNAME="appName";
    public static final String EXECUTIONINFO_APPPREFERENCES="appPreferences";
    public static final String EXECUTIONINFO_APPNODEREQUESTID="appNodeRequestId";
    public static final String EXECUTIONINFO_OUTPUTFILES="outputFiles";
    public static final String EXECUTIONINFO_PLATAFORMTYPE="plataformType";
    public static final String EXECUTIONINFO_APPREPOSID="appReposId";
    public static final String EXECUTIONINFO_APPMAINREQUESTID="appMainRequestId";
    public static final String EXECUTIONINFO_USERNAME="userName";
    public static final String EXECUTIONINFO="ExecutionInfo";
    public static final String INPUTFILE="InputFile";
    public static final String CHECKPOINT_CONTENT="content";
    public static final String CHECKPOINT="Checkpoint";
    public static final String NODE_NODENAME="nodeName";
    public static final String NODE="Node";

  /**
   * Constructor
  */
  private MAGOntology(){ 
    super(ONTOLOGY_NAME, BasicOntology.getInstance());
    introspect.setOntology(this);
    jadeToProtege = new HashMap();
    try { 

    // adding Concept(s)
    ConceptSchema nodeSchema = new ConceptSchema(NODE);
    add(nodeSchema, core.ontology.Node.class);
    ConceptSchema checkpointSchema = new ConceptSchema(CHECKPOINT);
    add(checkpointSchema, core.ontology.Checkpoint.class);
    ConceptSchema inputFileSchema = new ConceptSchema(INPUTFILE);
    add(inputFileSchema, core.ontology.InputFile.class);
    ConceptSchema executionInfoSchema = new ConceptSchema(EXECUTIONINFO);
    add(executionInfoSchema, core.ontology.ExecutionInfo.class);
    ConceptSchema fileSchema = new ConceptSchema(FILE);
    add(fileSchema, core.ontology.File.class);
    ConceptSchema outputFileSchema = new ConceptSchema(OUTPUTFILE);
    add(outputFileSchema, core.ontology.OutputFile.class);

    // adding AgentAction(s)
    AgentActionSchema changeExecutionLocationActionSchema = new AgentActionSchema(CHANGEEXECUTIONLOCATIONACTION);
    add(changeExecutionLocationActionSchema, core.ontology.ChangeExecutionLocationAction.class);
    AgentActionSchema requestInputFilesActionSchema = new AgentActionSchema(REQUESTINPUTFILESACTION);
    add(requestInputFilesActionSchema, core.ontology.RequestInputFilesAction.class);
    // Vinicius {
    AgentActionSchema requestOutputFilesActionSchema = new AgentActionSchema(REQUESTOUTPUTFILESACTION);
    add(requestOutputFilesActionSchema, core.ontology.RequestOutputFilesAction.class);
    // } Vinicius
    AgentActionSchema unregisterExecutionActionSchema = new AgentActionSchema(UNREGISTEREXECUTIONACTION);
    add(unregisterExecutionActionSchema, core.ontology.UnregisterExecutionAction.class);
    AgentActionSchema retrieveCheckpointActionSchema = new AgentActionSchema(RETRIEVECHECKPOINTACTION);
    add(retrieveCheckpointActionSchema, core.ontology.RetrieveCheckpointAction.class);
    AgentActionSchema saveCheckpointActionSchema = new AgentActionSchema(SAVECHECKPOINTACTION);
    add(saveCheckpointActionSchema, core.ontology.SaveCheckpointAction.class);
    AgentActionSchema setProcessExecutionFinishedActionSchema = new AgentActionSchema(SETPROCESSEXECUTIONFINISHEDACTION);
    add(setProcessExecutionFinishedActionSchema, core.ontology.SetProcessExecutionFinishedAction.class);
    AgentActionSchema checkpointActionSchema = new AgentActionSchema(CHECKPOINTACTION);
    add(checkpointActionSchema, core.ontology.CheckpointAction.class);
    AgentActionSchema setExecutionAcceptedActionSchema = new AgentActionSchema(SETEXECUTIONACCEPTEDACTION);
    add(setExecutionAcceptedActionSchema, core.ontology.SetExecutionAcceptedAction.class);
    AgentActionSchema recoverNodeActionSchema = new AgentActionSchema(RECOVERNODEACTION);
    add(recoverNodeActionSchema, core.ontology.RecoverNodeAction.class);
    AgentActionSchema registerExecutionActionSchema = new AgentActionSchema(REGISTEREXECUTIONACTION);
    add(registerExecutionActionSchema, core.ontology.RegisterExecutionAction.class);
    AgentActionSchema requestExecutionInfoActionSchema = new AgentActionSchema(REQUESTEXECUTIONINFOACTION);
    add(requestExecutionInfoActionSchema, core.ontology.RequestExecutionInfoAction.class);
    AgentActionSchema nodeActionSchema = new AgentActionSchema(NODEACTION);
    add(nodeActionSchema, core.ontology.NodeAction.class);

    // adding AID(s)

    // adding Predicate(s)


    // adding fields
    nodeSchema.add(NODE_NODENAME, (TermSchema)getSchema(BasicOntology.STRING), ObjectSchema.OPTIONAL);
    // Vinicius {
//    checkpointSchema.add(CHECKPOINT_CONTENT, (TermSchema)getSchema(BasicOntology.STRING), ObjectSchema.OPTIONAL);
    checkpointSchema.add(CHECKPOINT_CONTENT, (TermSchema)getSchema(BasicOntology.BYTE_SEQUENCE), ObjectSchema.OPTIONAL);
    // } Vinicius
    executionInfoSchema.add(EXECUTIONINFO_USERNAME, (TermSchema)getSchema(BasicOntology.STRING), ObjectSchema.OPTIONAL);
    executionInfoSchema.add(EXECUTIONINFO_APPMAINREQUESTID, (TermSchema)getSchema(BasicOntology.STRING), ObjectSchema.OPTIONAL);
    executionInfoSchema.add(EXECUTIONINFO_APPREPOSID, (TermSchema)getSchema(BasicOntology.STRING), ObjectSchema.OPTIONAL);
    executionInfoSchema.add(EXECUTIONINFO_PLATAFORMTYPE, (TermSchema)getSchema(BasicOntology.STRING), ObjectSchema.OPTIONAL);
    executionInfoSchema.add(EXECUTIONINFO_OUTPUTFILES, outputFileSchema, 0, ObjectSchema.UNLIMITED);
    executionInfoSchema.add(EXECUTIONINFO_APPNODEREQUESTID, (TermSchema)getSchema(BasicOntology.STRING), ObjectSchema.OPTIONAL);
    executionInfoSchema.add(EXECUTIONINFO_APPPREFERENCES, (TermSchema)getSchema(BasicOntology.STRING), ObjectSchema.OPTIONAL);
    executionInfoSchema.add(EXECUTIONINFO_APPNAME, (TermSchema)getSchema(BasicOntology.STRING), ObjectSchema.OPTIONAL);
    executionInfoSchema.add(EXECUTIONINFO_APPREPLICAID, (TermSchema)getSchema(BasicOntology.STRING), ObjectSchema.OPTIONAL);
    executionInfoSchema.add(EXECUTIONINFO_FINISHTIMESTAMP, (TermSchema)getSchema(BasicOntology.STRING), ObjectSchema.OPTIONAL);
    executionInfoSchema.add(EXECUTIONINFO_INPUTFILES, inputFileSchema, 0, ObjectSchema.UNLIMITED);
    executionInfoSchema.add(EXECUTIONINFO_NUMBEROFREPLICAS, (TermSchema)getSchema(BasicOntology.STRING), ObjectSchema.OPTIONAL); // Vinicius
    executionInfoSchema.add(EXECUTIONINFO_INITIALTIMESTAMP, (TermSchema)getSchema(BasicOntology.STRING), ObjectSchema.OPTIONAL);
    executionInfoSchema.add(EXECUTIONINFO_APPCONSTRAINTS, (TermSchema)getSchema(BasicOntology.STRING), ObjectSchema.OPTIONAL);
    executionInfoSchema.add(EXECUTIONINFO_APPEXECUTIONID, (TermSchema)getSchema(BasicOntology.STRING), ObjectSchema.OPTIONAL);
    executionInfoSchema.add(EXECUTIONINFO_ASCTIOR, (TermSchema)getSchema(BasicOntology.STRING), ObjectSchema.OPTIONAL);
    executionInfoSchema.add(EXECUTIONINFO_APPARGS, (TermSchema)getSchema(BasicOntology.STRING), ObjectSchema.OPTIONAL);
    executionInfoSchema.add(EXECUTIONINFO_EXECUTIONTYPE, (TermSchema)getSchema(BasicOntology.STRING), ObjectSchema.OPTIONAL);
    executionInfoSchema.add(EXECUTIONINFO_EXECUTINGHOST, (TermSchema)getSchema(BasicOntology.STRING), ObjectSchema.OPTIONAL);
    executionInfoSchema.add(EXECUTIONINFO_EXECUTIONSTATE, (TermSchema)getSchema(BasicOntology.STRING), ObjectSchema.OPTIONAL);
    executionInfoSchema.add(EXECUTIONINFO_BINARYNAME, (TermSchema)getSchema(BasicOntology.STRING), ObjectSchema.OPTIONAL);
    // Vinicius undo {
    //fileSchema.add(FILE_CONTENT, (TermSchema)getSchema(BasicOntology.STRING), ObjectSchema.OPTIONAL);
    fileSchema.add(FILE_CONTENT, (TermSchema)getSchema(BasicOntology.BYTE_SEQUENCE), ObjectSchema.OPTIONAL);
    // } Vinicius
    fileSchema.add(FILE_FILENAME, (TermSchema)getSchema(BasicOntology.STRING), ObjectSchema.OPTIONAL);
    changeExecutionLocationActionSchema.add(CHANGEEXECUTIONLOCATIONACTION_EXECUTION, executionInfoSchema, ObjectSchema.OPTIONAL);
    changeExecutionLocationActionSchema.add(CHANGEEXECUTIONLOCATIONACTION_NEWHOST, (TermSchema)getSchema(BasicOntology.STRING), ObjectSchema.OPTIONAL);
    requestInputFilesActionSchema.add(REQUESTINPUTFILESACTION_EXECUTION, executionInfoSchema, ObjectSchema.OPTIONAL);
    // Vinicius {
    requestOutputFilesActionSchema.add(REQUESTOUTPUTFILESACTION_EXECUTION, executionInfoSchema, ObjectSchema.OPTIONAL);
    // } Vinicius
    unregisterExecutionActionSchema.add(UNREGISTEREXECUTIONACTION_EXECUTION, executionInfoSchema, ObjectSchema.OPTIONAL);
    setProcessExecutionFinishedActionSchema.add(SETPROCESSEXECUTIONFINISHEDACTION_EXECUTION, executionInfoSchema, ObjectSchema.OPTIONAL);
    setProcessExecutionFinishedActionSchema.add(SETPROCESSEXECUTIONFINISHEDACTION_AHIOR, (TermSchema)getSchema(BasicOntology.STRING), ObjectSchema.OPTIONAL);
    checkpointActionSchema.add(CHECKPOINTACTION_CHECKPOINT, checkpointSchema, ObjectSchema.OPTIONAL);
    checkpointActionSchema.add(CHECKPOINTACTION_EXECID, (TermSchema)getSchema(BasicOntology.STRING), ObjectSchema.OPTIONAL);
    setExecutionAcceptedActionSchema.add(SETEXECUTIONACCEPTEDACTION_EXECUTION, executionInfoSchema, ObjectSchema.OPTIONAL);
    setExecutionAcceptedActionSchema.add(SETEXECUTIONACCEPTEDACTION_AHIOR, (TermSchema)getSchema(BasicOntology.STRING), ObjectSchema.OPTIONAL);
    registerExecutionActionSchema.add(REGISTEREXECUTIONACTION_EXECUTION, executionInfoSchema, ObjectSchema.OPTIONAL);
    requestExecutionInfoActionSchema.add(REQUESTEXECUTIONINFOACTION_EXECUTION, executionInfoSchema, ObjectSchema.OPTIONAL);
    nodeActionSchema.add(NODEACTION_NODE, nodeSchema, ObjectSchema.OPTIONAL);

    // adding name mappings
    storeSlotName("nodeName", "core.ontology.Node", "nodeName");  
    storeSlotName("content", "core.ontology.Checkpoint", "content");  
    storeSlotName("userName", "core.ontology.ExecutionInfo", "userName");  
    storeSlotName("appMainRequestId", "core.ontology.ExecutionInfo", "appMainRequestId");  
    storeSlotName("appReposId", "core.ontology.ExecutionInfo", "appReposId");  
    storeSlotName("plataformType", "core.ontology.ExecutionInfo", "plataformType");  
    storeSlotName("outputFiles", "core.ontology.ExecutionInfo", "outputFiles");  
    storeSlotName("appNodeRequestId", "core.ontology.ExecutionInfo", "appNodeRequestId");  
    storeSlotName("appPreferences", "core.ontology.ExecutionInfo", "appPreferences");  
    storeSlotName("appName", "core.ontology.ExecutionInfo", "appName");  
    storeSlotName("appReplicaId", "core.ontology.ExecutionInfo", "appReplicaId");  
    storeSlotName("finishTimestamp", "core.ontology.ExecutionInfo", "finishTimestamp");  
    storeSlotName("inputFiles", "core.ontology.ExecutionInfo", "inputFiles");  
    storeSlotName("numberOfReplicas", "core.ontology.ExecutionInfo", "numberOfReplicas");  
    storeSlotName("initialTimestamp", "core.ontology.ExecutionInfo", "initialTimestamp");  
    storeSlotName("appConstraints", "core.ontology.ExecutionInfo", "appConstraints");  
    storeSlotName("appExecutionId", "core.ontology.ExecutionInfo", "appExecutionId");  
    storeSlotName("asctIor", "core.ontology.ExecutionInfo", "asctIor");  
    storeSlotName("appArgs", "core.ontology.ExecutionInfo", "appArgs");  
    storeSlotName("executionType", "core.ontology.ExecutionInfo", "executionType");  
    storeSlotName("executingHost", "core.ontology.ExecutionInfo", "executingHost");  
    storeSlotName("executionState", "core.ontology.ExecutionInfo", "executionState");  
    storeSlotName("binaryName", "core.ontology.ExecutionInfo", "binaryName");  
    storeSlotName("content", "core.ontology.File", "content");  
    storeSlotName("fileName", "core.ontology.File", "fileName");  
    storeSlotName("execution", "core.ontology.ChangeExecutionLocationAction", "execution");  
    storeSlotName("newHost", "core.ontology.ChangeExecutionLocationAction", "newHost");  
    storeSlotName("execution", "core.ontology.RequestInputFilesAction", "execution");  
    // Vinicius {
    storeSlotName("execution", "core.ontology.RequestOutputFilesAction", "execution");
    // } Vinicius
    storeSlotName("execution", "core.ontology.UnregisterExecutionAction", "execution");  
    storeSlotName("execution", "core.ontology.SetProcessExecutionFinishedAction", "execution");  
    storeSlotName("ahIor", "core.ontology.SetProcessExecutionFinishedAction", "ahIor");  
    storeSlotName("checkpoint", "core.ontology.CheckpointAction", "checkpoint");  
    storeSlotName("execId", "core.ontology.CheckpointAction", "execId");  
    storeSlotName("execution", "core.ontology.SetExecutionAcceptedAction", "execution");  
    storeSlotName("ahIor", "core.ontology.SetExecutionAcceptedAction", "ahIor");  
    storeSlotName("execution", "core.ontology.RegisterExecutionAction", "execution");  
    storeSlotName("execution", "core.ontology.RequestExecutionInfoAction", "execution");  
    storeSlotName("node", "core.ontology.NodeAction", "node");  

    // adding inheritance
    inputFileSchema.addSuperSchema(fileSchema);
    outputFileSchema.addSuperSchema(fileSchema);
    retrieveCheckpointActionSchema.addSuperSchema(checkpointActionSchema);
    saveCheckpointActionSchema.addSuperSchema(checkpointActionSchema);
    recoverNodeActionSchema.addSuperSchema(nodeActionSchema);

   }catch (java.lang.Exception e) {e.printStackTrace();}
  }
  }
