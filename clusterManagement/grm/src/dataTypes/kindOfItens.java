package dataTypes;
/**
 *	Generated from IDL definition of enum "kindOfItens"
 *	@author JacORB IDL compiler 
 */

public final class kindOfItens
	implements org.omg.CORBA.portable.IDLEntity
{
	private int value = -1;
	public static final int _applicationDirectory = 0;
	public static final kindOfItens applicationDirectory = new kindOfItens(_applicationDirectory);
	public static final int _commonDirectory = 1;
	public static final kindOfItens commonDirectory = new kindOfItens(_commonDirectory);
	public static final int _binaryFile = 2;
	public static final kindOfItens binaryFile = new kindOfItens(_binaryFile);
	public static final int _applicationDescriptionFile = 3;
	public static final kindOfItens applicationDescriptionFile = new kindOfItens(_applicationDescriptionFile);
	public static final int _rootDirectory = 4;
	public static final kindOfItens rootDirectory = new kindOfItens(_rootDirectory);
	public int value()
	{
		return value;
	}
	public static kindOfItens from_int(int value)
	{
		switch (value) {
			case _applicationDirectory: return applicationDirectory;
			case _commonDirectory: return commonDirectory;
			case _binaryFile: return binaryFile;
			case _applicationDescriptionFile: return applicationDescriptionFile;
			case _rootDirectory: return rootDirectory;
			default: throw new org.omg.CORBA.BAD_PARAM();
		}
	}
	protected kindOfItens(int i)
	{
		value = i;
	}
	java.lang.Object readResolve()
	throws java.io.ObjectStreamException
	{
		return from_int(value());
	}
}
