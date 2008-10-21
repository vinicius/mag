package core.execman.ema.dataBase;


import core.ontology.ExecutionInfo;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


import dataTypes.ApplicationExecutionStateInformation;
import dataTypes.ProcessExecutionStateInformation;

import dataTypes.ApplicationExecutionInformation;
import dataTypes.ProcessExecutionInformation;
import dataTypes.ApplicationType;
import dataTypes.ExecutionRequestId;


public class ExecutionDatabaseManager {
	
	private static ExecutionDatabaseManager instance = null;
	private Statement statement = null;		
	private Connection connection = null;
	private String url = "jdbc:h2:file:./mag/classes/db/ExecutionManager";
		
	/**
	 * Responsible for the database access.
	 * 
	 */
	private ExecutionDatabaseManager() {

		try {
			Class.forName("org.h2.Driver");
		} catch (ClassNotFoundException e) {

			e.printStackTrace();
		}
		
		try {			
			statement = connection().createStatement();			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		
		
			
			String sql1 = 	"CREATE TABLE APPLICATIONEXECUTIONINFORMATION"   	+
							"("													+
								"EXECUTIONID                  VARCHAR(2147483647) NOT NULL,"	+
								"REQUESTID                    VARCHAR(2147483647) NOT NULL,"    +
								"PROCESSID                    VARCHAR(2147483647) NOT NULL,"    +
								"REPLICAID                    VARCHAR(2147483647) NOT NULL,"    +								
								"REQUESTINGASCTIOR            VARCHAR(2147483647) NOT NULL,"	+
								"HOSTNAME		              VARCHAR(100) NOT NULL,"			+
								"APPLICATIONARGUMENTS         VARCHAR(2147483647) NOT NULL,"	+
								"APPLICATIONSTATE             VARCHAR(2147483647) NOT NULL,"	+
								"APPLICATIONREPOSITORYID      VARCHAR(2147483647) NOT NULL,"	+
								"APPLICATIONNAME              VARCHAR(2147483647) NOT NULL,"	+
								"APPLICATIONCONSTRAINTS       VARCHAR(2147483647),"	+
								"APPLICATIONPREFERENCES       VARCHAR(2147483647),"	+
								"APPLICATIONTYPE              VARCHAR(2147483647) NOT NULL,"	+
								"SUBMISSIONTIMESTAMP          VARCHAR(2147483647),"	+
								"FINISHTIMESTAMP              VARCHAR(2147483647),"	+
								"USERID                       VARCHAR(2147483647) NOT NULL"	+
			  				");";												
			
			/*String sql2 = 	"ALTER TABLE APPLICATIONEXECUTIONINFORMATION ADD CONSTRAINT PRIMARY_KEY_1 PRIMARY KEY"							+
							"("								+
							"EXECUTIONID"					+
							");";							*/					

			
			try {
				
				statement.execute(sql1);
				//statement.execute(sql2);			
				
					
			} catch (SQLException e) {				

				e.printStackTrace();
			}
		
	}
	
	public static ExecutionDatabaseManager getInstance(){
		
		if( instance == null ){
			
			instance = new ExecutionDatabaseManager();
		}
		return instance;
	}	
	
	

	/**
	 * Resgister an application in the database.
	 * 
	 * @param appInfo
	 *            Application execution information
	 * @param processInfo
	 *            Process execution information
	 */
	
	public synchronized void registerApplicationExecution(ExecutionInfo appInfo ) {
		
		System.out.println("ExecutionDatabaseMAnager");

		PreparedStatement statement = null;
		String sql = null;
		
		try {
		
			/*sql = "SELECT * FROM ApplicationExecutionInformation WHERE ExecutionID = ?;";

			statement = connection().prepareStatement(sql);
			statement.setString(1, appInfo.getAppExecutionId());

			//System.out
			//		.println("Verificando se ja possui uma aplicacao com esse ID: "
			//				+ sql);
			ResultSet rs = statement.executeQuery();

			
				if (!rs.next()) 
					statement.close();*/
				
				
					sql = "INSERT INTO ApplicationExecutionInformation VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";

					statement = connection().prepareStatement(sql);
					statement.setString(1, appInfo.getAppExecutionId());
					System.out.println(appInfo.getAppExecutionId());
					statement.setString(2, appInfo.getAppMainRequestId());
					System.out.println(appInfo.getAppMainRequestId());
					statement.setString(3, appInfo.getAppNodeRequestId());
					System.out.println(appInfo.getAppNodeRequestId());
					statement.setString(4, appInfo.getAppReplicaId());
					System.out.println(appInfo.getAppReplicaId());
					statement.setString(5, appInfo.getAsctIor());
					System.out.println(appInfo.getAsctIor());
					statement.setString(6, appInfo.getExecutingHost());
					System.out.println(appInfo.getExecutingHost());
					statement.setString(7, appInfo.getAppArgs());
					System.out.println(appInfo.getAppArgs());
					statement.setString(8, "SCHEDULED");
					statement.setString(9, appInfo.getAppReposId());
					System.out.println(appInfo.getAppReposId());
					statement.setString(10, appInfo.getAppName());
					System.out.println(appInfo.getAppName());
					statement.setString(11, appInfo.getAppConstraints());
					System.out.println(appInfo.getAppConstraints());
					statement.setString(12, appInfo.getAppPreferences());
					System.out.println(appInfo.getAppPreferences());
					statement.setString(13, " " );
					System.out.println(appInfo.getExecutionType());
					statement.setLong(14, System.currentTimeMillis());
					System.out.println(System.currentTimeMillis());
					statement.setString(15, " ");
					statement.setString(16, appInfo.getUserName());
					System.out.println(appInfo.getUserName());
					statement.execute();
					statement.close();
				
			
			close();			
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
	

	/**
	 * Change application execution state.
	 * 
	 * @param requestId
	 *            Unique application execution ID.
	 * @param executionState
	 *            Execution State, this value can be found in
	 *            ApplicationExecutionStateTypes class.
	 */
	public void changeApplicationExecutionState(String requestId,
			int executionState) {
		
	}
	
	
	
	
		
	public synchronized ExecutionInfo[] getHostApplications (String hostName) {
		
		ExecutionInfo [] executionInfo = null;
		int i = 0;
		
		PreparedStatement statement = null;
		String sql = null;		
		
		
		try {	
		
			sql = "SELECT * FROM ApplicationExecutionInformation WHERE HostName = ? AND ApplicationState = ?;";
			statement = connection().prepareStatement(sql);
			statement.setString(1, hostName);
			statement.setString(2, "EXECUTING");		
			ResultSet rs = statement.executeQuery();
		
		
		while( rs.next() ){
			executionInfo[i] = new ExecutionInfo();

			executionInfo[i].setAppExecutionId(rs.getString("ExecutioID"));
			System.out.println(rs.getString("ExecutioID"));

			executionInfo[i].setExecutingHost(rs.getString("HostName"));
			System.out.println(rs.getString("HostName"));
			
			executionInfo[i].setAppReposId(rs.getString("ApplicationRepositoryId"));
			System.out.println(rs.getString("ApplicationRepositoryId"));
			
			executionInfo[i].setAppName(rs.getString("ApplicationName"));
			System.out.println(rs.getString("ApplicationName"));
			
			// <executionInfo>
			executionInfo[i].setUserName(rs.getString("UserId"));
			System.out.println(rs.getString("UserId"));
			
			executionInfo[i].setAppArgs(rs.getString("ApplicationArguments"));
			System.out.println(rs.getString("ApplicationArguments"));
			
			executionInfo[i].setAppMainRequestId(rs.getString("RequestId"));
			System.out.println(rs.getString("RequestId"));
			
			executionInfo[i].setAppNodeRequestId(rs.getString("ProcessId"));
			System.out.println(rs.getString("ProcessId"));
			
			executionInfo[i].setAppConstraints(rs.getString("ApplicationConstraints"));
			System.out.println(rs.getString("ApplicationConstraints"));
			
			executionInfo[i].setAppPreferences(rs.getString("ApplicationPreferences"));
			System.out.println(rs.getString("ApplicationPreferences"));		
			
			executionInfo[i].setAsctIor(rs.getString("RequisitingAsctIor"));
			System.out.println(rs.getString("RequisitingAsctIor"));			
			
			i++;
		}	
		statement.close();
		close();			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return executionInfo;
		
	}
	
	public synchronized String[] getReplicasLocations(String requestId, String processId, String replicaId) {
		
		String [] locations = null;
		
		String [] result = null;
		
		int i = 0;
		
		PreparedStatement statement = null;
		String sql = null;
		
		locations = new String[this.NumberOfRecords()]; 
		
		
		try {
		
			sql = "SELECT HostName FROM ApplicationExecutionInformation WHERE RequestId = ? AND ProcessID = ? AND ReplicaID = ? AND ApplicationState <> ?;";
			statement = connection().prepareStatement(sql);
			statement.setString(1, requestId);
			statement.setString(2, processId);
			statement.setString(3, replicaId);
			statement.setString(4, "FINISHED");
			ResultSet rs = statement.executeQuery();			
		
		while( rs.next() ){
			
			locations[i] = rs.getString("HostName");
			
			i++;

		}
		for (int j =0 ; j < locations.length; j++ ){
			System.out.println("Replicas Location "+ locations[j]);
		}
		
		locations = new String [i];
		
		for ( int j= 0; (j < locations.length ) && locations[j]!= null; j++ ){
			
			result[j] = locations[j];
			
		}		
		statement.close();
		close();			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return result;
		
	}	

	/**
	 * This method  Edit Application's State, for executing.
	 * @param executionId - identify the application.
	 * */
	public synchronized void setAcceptedStateApplication(String executionId, String hostname){
		setStateApplication(executionId, hostname, "ACCEPTED");		
	}		
	
	/**
	 * This method  Edit Application's State, for finished.
	 * @param executionId - identify the application.
	 * */
	public synchronized void setFinishedStateApplication(String executionId, String hostname){
		setStateApplication(executionId, hostname, "FINISHED");
		setFinishTimestamp(executionId, Long.toString(System.currentTimeMillis()));
	}	
	
	
	private void setFinishTimestamp(String executionId, String finishTimestamp) {
		PreparedStatement statement = null;
		String sql = null;
		try {
			// Aplicacao iniciando sua execucao
			
				sql = "UPDATE ApplicationExecutionInformation SET FinishTimestamp = ?"
						+ " WHERE ExecutionID = ?;";
				
				statement = connection().prepareStatement(sql);
				statement.setString(2, executionId);		
				statement.setString(1, finishTimestamp);
				statement.execute();
				statement.close();
				close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

	/**
	 * This method Edit Application's State.
	 * @param executionId - identify the application
	 * @param state - Application 's state.
	 * */
	private synchronized void setStateApplication(String executionId,String hostname, String state){
		PreparedStatement statement = null;
		String sql = null;
		try {
			// Aplicacao iniciando sua execucao
			
				sql = "UPDATE ApplicationExecutionInformation SET ApplicationState = ?"
						+ " WHERE ExecutionID = ? AND HostName = ?;";
				
				statement = connection().prepareStatement(sql);
				statement.setString(1, state);
				statement.setString(2, executionId);
				statement.setString(3, hostname);
				statement.execute();
				statement.close();
				close();
		} catch (SQLException e) {
			e.printStackTrace();
		}			
	
	}	

	/**
	 * Change process execution state, it has to be used just when the process
	 * finishes, with abnormal ending or not.
	 * 
	 * @param requestId
	 *            Unique application execution ID.
	 * @param processId
	 *            Process ID.
	 * @param executionState
	 *            Execution state.this value can be found in
	 *            ProcessExecutionStateInformation class.
	 * @param executionCode
	 *            Value return from
	 */
	public void changeProcessExecutionStateToFinished(String requestId,
			String processId, int executionState, int executionCode) {


	}

	/**
	 * Retrieve application execution static information.
	 * 
	 * @param requestId
	 *            Unique application execution ID.
	 * @return Application execution information.
	 */
	public ApplicationExecutionInformation getApplicationExecutionInformation(
			String requestId) {
		return null;

	}

	/**
	 * Retrieve Process execution static information.
	 * 
	 * @param requestId
	 *            Unique application execution ID.
	 * @param processId
	 *            Process ID.
	 * @return Process execution information.
	 */
	public ProcessExecutionInformation getProcessExecutionInformation(
			String requestId, String processId) {

		return null;

	}

	/**
	 * Retrieve application execution state information.
	 * 
	 * @param requestId
	 *            Unique application execution ID.
	 * @return Application execution state information.
	 */
	public ApplicationExecutionStateInformation getApplicationExecutionStateInformation(
			String requestId) {


		return null;
	}

	/**
	 * Retrieve Process execution state information.
	 * 
	 * @param requestId
	 *            Unique application execution ID.
	 * @param processId
	 *            Process ID.
	 * @return Process execution state information.
	 */
	public ProcessExecutionStateInformation getProcessExecutionStateInformation(
			String requestId, String processId) {

		return null;
	}

	private int calculateApplicationExecutionState(String requestId) {
				

		return 0;
	}

	/**
	 * Verify if the application has replicas
	 * 
	 * @param requestId
	 *            Unique application execution ID.
	 * @return true if the application has replicas, and false if not.
	 */
	private boolean hasReplicas(String requestId) {
		return (requestId.split(":").length > 3);
	}

	private int getLastProcessRestartId(String requestId, String processId) {
		return 0;
	}

	/**
	 * Separate the replica ID from request ID.
	 * 
	 * @param requestId
	 *            Original request ID.
	 * @return An array containing the requestID, and the replica ID.
	 */
	private String[] separateReplicaIdFromRequestId(String requestId){
		String[] reqId = requestId.split(":");
		String[] result = new String[2];

		if (reqId.length == 3) {
			result[0] = requestId;
			result[1] = "0";
		} else {
			result[0] = reqId[0] + ":" + reqId[1] + ":" + reqId[2];
			result[1] = reqId[3];
		}
		return result;
	}

	private int NumberOfRecords(){
		PreparedStatement statement = null;
		String sql = null;
		
		int i = 0;
		
		try {			
			sql = "SELECT RequestId FROM ApplicationExecutionInformation";
			statement = connection().prepareStatement(sql);			
			ResultSet rs = statement.executeQuery();
			
		
		while( rs.next() ){			
			i++;

		}

		
		close();			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return i;

		
	}
	
	private Connection connection(){
		if (this.connection == null){
			try {
				this.connection = DriverManager.getConnection(this.url);
				this.connection.setAutoCommit(true);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return this.connection;

	}
	
	private void close(){
		try {		
			connection().close();
			this.connection = null;
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}


}
