package core.application;

import java.io.Serializable;

/**
 Class JavaApplication - Represents a Java Application to InteGrade
 
 When an InteGrade component identifies a Java Application, this
 class represent it. Instances of this class are stored on
 ApplicationRepository
 
 @author Rafael Fernandes Lopes
 */
public class JavaApplication implements Serializable {
	protected String className = "";
	protected byte[] byteCode = null;
	
	public String getClassName () {
		return className;
	}
	
	public void setClassName (String aClassName) {
		className = aClassName;
	}
	
	public byte[] getByteCode () {
		return byteCode;
	}
	
	public void setByteCode (byte aByteCode[]) {
		byteCode = aByteCode;
	}
}
