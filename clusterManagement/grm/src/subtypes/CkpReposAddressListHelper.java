package subtypes;


/**
 *	Generated from IDL definition of struct "CkpReposAddressList"
 *	@author JacORB IDL compiler 
 */

public final class CkpReposAddressListHelper
{
	private static org.omg.CORBA.TypeCode _type = null;
	public static org.omg.CORBA.TypeCode type ()
	{
		if (_type == null)
		{
			_type = org.omg.CORBA.ORB.init().create_struct_tc(subtypes.CkpReposAddressListHelper.id(),"CkpReposAddressList",new org.omg.CORBA.StructMember[]{new org.omg.CORBA.StructMember("ipAddress", org.omg.CORBA.ORB.init().create_sequence_tc(0, org.omg.CORBA.ORB.init().create_string_tc(0)), null),new org.omg.CORBA.StructMember("portNumber", org.omg.CORBA.ORB.init().create_sequence_tc(0, org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.from_int(2))), null)});
		}
		return _type;
	}

	public static void insert (final org.omg.CORBA.Any any, final subtypes.CkpReposAddressList s)
	{
		any.type(type());
		write( any.create_output_stream(),s);
	}

	public static subtypes.CkpReposAddressList extract (final org.omg.CORBA.Any any)
	{
		return read(any.create_input_stream());
	}

	public static String id()
	{
		return "IDL:subtypes/CkpReposAddressList:1.0";
	}
	public static subtypes.CkpReposAddressList read (final org.omg.CORBA.portable.InputStream in)
	{
		subtypes.CkpReposAddressList result = new subtypes.CkpReposAddressList();
		int _lresult_ipAddress0 = in.read_long();
		result.ipAddress = new java.lang.String[_lresult_ipAddress0];
		for (int i=0;i<result.ipAddress.length;i++)
		{
			result.ipAddress[i]=in.read_string();
		}

		int _lresult_portNumber1 = in.read_long();
		result.portNumber = new short[_lresult_portNumber1];
	in.read_short_array(result.portNumber,0,_lresult_portNumber1);
		return result;
	}
	public static void write (final org.omg.CORBA.portable.OutputStream out, final subtypes.CkpReposAddressList s)
	{
		
		out.write_long(s.ipAddress.length);
		for (int i=0; i<s.ipAddress.length;i++)
		{
			out.write_string(s.ipAddress[i]);
		}

		
		out.write_long(s.portNumber.length);
		out.write_short_array(s.portNumber,0,s.portNumber.length);
	}
}
