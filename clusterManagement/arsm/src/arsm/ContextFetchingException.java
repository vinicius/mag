package arsm;

public class ContextFetchingException extends Exception {
	public java.lang.String message = "";
	
	public ContextFetchingException(java.lang.String message)
	{
		this.message = message;
	}

	public String getMessage() {
		return message;
	}
}
