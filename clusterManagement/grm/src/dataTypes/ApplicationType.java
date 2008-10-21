package dataTypes;
/**
 *	Generated from IDL definition of enum "ApplicationType"
 *	@author JacORB IDL compiler 
 */

public final class ApplicationType
	implements org.omg.CORBA.portable.IDLEntity
{
	private int value = -1;
	public static final int _regular = 0;
	public static final ApplicationType regular = new ApplicationType(_regular);
	public static final int _parametric = 1;
	public static final ApplicationType parametric = new ApplicationType(_parametric);
	public static final int _bsp = 2;
	public static final ApplicationType bsp = new ApplicationType(_bsp);
	public int value()
	{
		return value;
	}
	public static ApplicationType from_int(int value)
	{
		switch (value) {
			case _regular: return regular;
			case _parametric: return parametric;
			case _bsp: return bsp;
			default: throw new org.omg.CORBA.BAD_PARAM();
		}
	}
	protected ApplicationType(int i)
	{
		value = i;
	}
	java.lang.Object readResolve()
	throws java.io.ObjectStreamException
	{
		return from_int(value());
	}
}
