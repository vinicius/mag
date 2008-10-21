package dataTypes;

/**
 *	Generated from IDL definition of struct "AppRequeriments"
 *	@author JacORB IDL compiler 
 */

public final class AppRequeriments
	implements org.omg.CORBA.portable.IDLEntity
{
	public AppRequeriments(){}
	public java.lang.String applicationConstraints = "";
	public java.lang.String applicationPreferences = "";
	public AppRequeriments(java.lang.String applicationConstraints, java.lang.String applicationPreferences)
	{
		this.applicationConstraints = applicationConstraints;
		this.applicationPreferences = applicationPreferences;
	}
}
