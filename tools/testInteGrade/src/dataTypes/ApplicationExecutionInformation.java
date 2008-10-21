package dataTypes;

/**
 *	Generated from IDL definition of struct "ApplicationExecutionInformation"
 *	@author JacORB IDL compiler 
 */

public final class ApplicationExecutionInformation
	implements org.omg.CORBA.portable.IDLEntity
{
	public ApplicationExecutionInformation(){}
	public java.lang.String requestingAsctIor = "";
	public java.lang.String originalGrmIor = "";
	public java.lang.String previousGrmIor = "";
	public dataTypes.ApplicationType applicationType;
	public java.lang.String applicationRepositoryIor = "";
	public java.lang.String applicationName = "";
	public java.lang.String basePath = "";
	public java.lang.String applicationConstraints = "";
	public java.lang.String applicationPreferences = "";
	public java.lang.String[] availableBinaries;
	public boolean forceDifferentNodes;
	public int numberOfReplicas;
	public ApplicationExecutionInformation(java.lang.String requestingAsctIor, java.lang.String originalGrmIor, java.lang.String previousGrmIor, dataTypes.ApplicationType applicationType, java.lang.String applicationRepositoryIor, java.lang.String applicationName, java.lang.String basePath, java.lang.String applicationConstraints, java.lang.String applicationPreferences, java.lang.String[] availableBinaries, boolean forceDifferentNodes, int numberOfReplicas)
	{
		this.requestingAsctIor = requestingAsctIor;
		this.originalGrmIor = originalGrmIor;
		this.previousGrmIor = previousGrmIor;
		this.applicationType = applicationType;
		this.applicationRepositoryIor = applicationRepositoryIor;
		this.applicationName = applicationName;
		this.basePath = basePath;
		this.applicationConstraints = applicationConstraints;
		this.applicationPreferences = applicationPreferences;
		this.availableBinaries = availableBinaries;
		this.forceDifferentNodes = forceDifferentNodes;
		this.numberOfReplicas = numberOfReplicas;
	}
}
