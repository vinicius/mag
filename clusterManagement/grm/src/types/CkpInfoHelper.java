package types;


/**
 *	Generated from IDL definition of struct "CkpInfo"
 *	@author JacORB IDL compiler 
 */

public final class CkpInfoHelper
{
	private static org.omg.CORBA.TypeCode _type = null;
	public static org.omg.CORBA.TypeCode type ()
	{
		if (_type == null)
		{
			_type = org.omg.CORBA.ORB.init().create_struct_tc(types.CkpInfoHelper.id(),"CkpInfo",new org.omg.CORBA.StructMember[]{new org.omg.CORBA.StructMember("ckpNumber", org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.from_int(3)), null),new org.omg.CORBA.StructMember("ckpSize", org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.from_int(3)), null),new org.omg.CORBA.StructMember("ipAddress", org.omg.CORBA.ORB.init().create_sequence_tc(0, org.omg.CORBA.ORB.init().create_string_tc(0)), null),new org.omg.CORBA.StructMember("portNumber", org.omg.CORBA.ORB.init().create_sequence_tc(0, org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.from_int(2))), null),new org.omg.CORBA.StructMember("checksum", org.omg.CORBA.ORB.init().create_sequence_tc(0, org.omg.CORBA.ORB.init().create_string_tc(0)), null)});
		}
		return _type;
	}

	public static void insert (final org.omg.CORBA.Any any, final types.CkpInfo s)
	{
		any.type(type());
		write( any.create_output_stream(),s);
	}

	public static types.CkpInfo extract (final org.omg.CORBA.Any any)
	{
		return read(any.create_input_stream());
	}

	public static String id()
	{
		return "IDL:types/CkpInfo:1.0";
	}
	public static types.CkpInfo read (final org.omg.CORBA.portable.InputStream in)
	{
		types.CkpInfo result = new types.CkpInfo();
		result.ckpNumber=in.read_long();
		result.ckpSize=in.read_long();
		int _lresult_ipAddress6 = in.read_long();
		result.ipAddress = new java.lang.String[_lresult_ipAddress6];
		for (int i=0;i<result.ipAddress.length;i++)
		{
			result.ipAddress[i]=in.read_string();
		}

		int _lresult_portNumber7 = in.read_long();
		result.portNumber = new short[_lresult_portNumber7];
	in.read_short_array(result.portNumber,0,_lresult_portNumber7);
		int _lresult_checksum8 = in.read_long();
		result.checksum = new java.lang.String[_lresult_checksum8];
		for (int i=0;i<result.checksum.length;i++)
		{
			result.checksum[i]=in.read_string();
		}

		return result;
	}
	public static void write (final org.omg.CORBA.portable.OutputStream out, final types.CkpInfo s)
	{
		out.write_long(s.ckpNumber);
		out.write_long(s.ckpSize);
		
		out.write_long(s.ipAddress.length);
		for (int i=0; i<s.ipAddress.length;i++)
		{
			out.write_string(s.ipAddress[i]);
		}

		
		out.write_long(s.portNumber.length);
		out.write_short_array(s.portNumber,0,s.portNumber.length);
		
		out.write_long(s.checksum.length);
		for (int i=0; i<s.checksum.length;i++)
		{
			out.write_string(s.checksum[i]);
		}

	}
}
