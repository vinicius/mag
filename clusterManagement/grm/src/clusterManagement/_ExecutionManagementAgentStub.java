package clusterManagement;


/**
 *	Generated from IDL interface "ExecutionManagementAgent"
 *	@author JacORB IDL compiler V 2.2.3, 10-Dec-2005
 */

public class _ExecutionManagementAgentStub
	extends org.omg.CORBA.portable.ObjectImpl
	implements clusterManagement.ExecutionManagementAgent
{
	private String[] ids = {"IDL:clusterManagement/ExecutionManagementAgent:1.0"};
	public String[] _ids()
	{
		return ids;
	}

	public final static java.lang.Class _opsClass = clusterManagement.ExecutionManagementAgentOperations.class;
	public void setExecutionScheduled(dataTypes.ApplicationExecutionInformation applicationExecutionInformation, dataTypes.ProcessExecutionInformation[] processExecutionInformationSequence)
	{
		while(true)
		{
		if(! this._is_local())
		{
			org.omg.CORBA.portable.InputStream _is = null;
			try
			{
				org.omg.CORBA.portable.OutputStream _os = _request( "setExecutionScheduled", true);
				dataTypes.ApplicationExecutionInformationHelper.write(_os,applicationExecutionInformation);
				dataTypes.ProcessExecutionInformationSequenceHelper.write(_os,processExecutionInformationSequence);
				_is = _invoke(_os);
				return;
			}
			catch( org.omg.CORBA.portable.RemarshalException _rx ){}
			catch( org.omg.CORBA.portable.ApplicationException _ax )
			{
				String _id = _ax.getId();
				throw new RuntimeException("Unexpected exception " + _id );
			}
			finally
			{
				this._releaseReply(_is);
			}
		}
		else
		{
			org.omg.CORBA.portable.ServantObject _so = _servant_preinvoke( "setExecutionScheduled", _opsClass );
			if( _so == null )
				throw new org.omg.CORBA.UNKNOWN("local invocations not supported!");
			ExecutionManagementAgentOperations _localServant = (ExecutionManagementAgentOperations)_so.servant;
			try
			{
			_localServant.setExecutionScheduled(applicationExecutionInformation,processExecutionInformationSequence);
			}
			finally
			{
				_servant_postinvoke(_so);
			}
			return;
		}

		}

	}

	public void setExecutionRefused(dataTypes.ApplicationExecutionInformation applicationExecutionInformation, dataTypes.ProcessExecutionInformation[] processExecutionInformationSequence)
	{
		while(true)
		{
		if(! this._is_local())
		{
			org.omg.CORBA.portable.InputStream _is = null;
			try
			{
				org.omg.CORBA.portable.OutputStream _os = _request( "setExecutionRefused", true);
				dataTypes.ApplicationExecutionInformationHelper.write(_os,applicationExecutionInformation);
				dataTypes.ProcessExecutionInformationSequenceHelper.write(_os,processExecutionInformationSequence);
				_is = _invoke(_os);
				return;
			}
			catch( org.omg.CORBA.portable.RemarshalException _rx ){}
			catch( org.omg.CORBA.portable.ApplicationException _ax )
			{
				String _id = _ax.getId();
				throw new RuntimeException("Unexpected exception " + _id );
			}
			finally
			{
				this._releaseReply(_is);
			}
		}
		else
		{
			org.omg.CORBA.portable.ServantObject _so = _servant_preinvoke( "setExecutionRefused", _opsClass );
			if( _so == null )
				throw new org.omg.CORBA.UNKNOWN("local invocations not supported!");
			ExecutionManagementAgentOperations _localServant = (ExecutionManagementAgentOperations)_so.servant;
			try
			{
			_localServant.setExecutionRefused(applicationExecutionInformation,processExecutionInformationSequence);
			}
			finally
			{
				_servant_postinvoke(_so);
			}
			return;
		}

		}

	}

}
