package clusterManagement;


/**
 *	Generated from IDL interface "Crm"
 *	@author JacORB IDL compiler V 2.2.3, 10-Dec-2005
 */

public class _CrmStub
	extends org.omg.CORBA.portable.ObjectImpl
	implements clusterManagement.Crm
{
	private String[] ids = {"IDL:clusterManagement/Crm:1.0"};
	public String[] _ids()
	{
		return ids;
	}

	public final static java.lang.Class _opsClass = clusterManagement.CrmOperations.class;
	public int requestExecution(dataTypes.ApplicationExecutionInformation applicationExecutionInformation, dataTypes.ProcessExecutionInformation processExecutionInformation, java.lang.String[] lrmIors)
	{
		while(true)
		{
		if(! this._is_local())
		{
			org.omg.CORBA.portable.InputStream _is = null;
			try
			{
				org.omg.CORBA.portable.OutputStream _os = _request( "requestExecution", true);
				dataTypes.ApplicationExecutionInformationHelper.write(_os,applicationExecutionInformation);
				dataTypes.ProcessExecutionInformationHelper.write(_os,processExecutionInformation);
				dataTypes.FileNameSequenceHelper.write(_os,lrmIors);
				_is = _invoke(_os);
				int _result = _is.read_long();
				return _result;
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
			org.omg.CORBA.portable.ServantObject _so = _servant_preinvoke( "requestExecution", _opsClass );
			if( _so == null )
				throw new org.omg.CORBA.UNKNOWN("local invocations not supported!");
			CrmOperations _localServant = (CrmOperations)_so.servant;
			int _result;			try
			{
			_result = _localServant.requestExecution(applicationExecutionInformation,processExecutionInformation,lrmIors);
			}
			finally
			{
				_servant_postinvoke(_so);
			}
			return _result;
		}

		}

	}

}
