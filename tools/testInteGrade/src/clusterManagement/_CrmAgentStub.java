package clusterManagement;


/**
 *	Generated from IDL interface "CrmAgent"
 *	@author JacORB IDL compiler V 2.2.3, 10-Dec-2005
 */

public class _CrmAgentStub
	extends org.omg.CORBA.portable.ObjectImpl
	implements clusterManagement.CrmAgent
{
	private String[] ids = {"IDL:clusterManagement/CrmAgent:1.0"};
	public String[] _ids()
	{
		return ids;
	}

	public final static java.lang.Class _opsClass = clusterManagement.CrmAgentOperations.class;
	public void requestRemoteExecution(dataTypes.ApplicationExecutionInformation applicationExecutionInformation, dataTypes.ProcessExecutionInformation[] processExecutionInformationSequence, java.lang.String[] lrmIors)
	{
		while(true)
		{
		if(! this._is_local())
		{
			org.omg.CORBA.portable.InputStream _is = null;
			try
			{
				org.omg.CORBA.portable.OutputStream _os = _request( "requestRemoteExecution", true);
				dataTypes.ApplicationExecutionInformationHelper.write(_os,applicationExecutionInformation);
				dataTypes.ProcessExecutionInformationSequenceHelper.write(_os,processExecutionInformationSequence);
				dataTypes.FileNameSequenceHelper.write(_os,lrmIors);
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
			org.omg.CORBA.portable.ServantObject _so = _servant_preinvoke( "requestRemoteExecution", _opsClass );
			if( _so == null )
				throw new org.omg.CORBA.UNKNOWN("local invocations not supported!");
			CrmAgentOperations _localServant = (CrmAgentOperations)_so.servant;
			try
			{
			_localServant.requestRemoteExecution(applicationExecutionInformation,processExecutionInformationSequence,lrmIors);
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
