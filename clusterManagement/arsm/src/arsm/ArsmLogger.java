package arsm;
import java.io.*;
import java.util.logging.*;

/*
 * Created on Jun 4, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */

/**
 * @author jrbraga
 * This class implements an logger for register events
 */
public class ArsmLogger {
	private static ArsmLogger _instance = null;
	private Logger logger;

	/**
	 * 
	 */
	
	private ArsmLogger(String logName,String logFile) {
		// TODO Auto-generated constructor stub
		System.out.println("Logging " + logName + " in " + logFile);
		logger = Logger.getLogger(logName);
		try {
	        // Create an appending file handlerint
	        boolean append = true;
	        FileHandler handler = new FileHandler(logFile, append);
	      // Use a custom formatter
	       handler.setFormatter(new ArsmLogger.ArsmLoggerFormatter());
	        // Add to the desired logger
	        logger.addHandler(handler);
	        
	    } catch (IOException e) {
	    	System.err.println("Open file error:" + e);
	    }
	}
	public void info(String info){
		logger.info(info);
	}
	public void warning(String warning){
		logger.warning(warning);
	}
	public void severe(String severe){
		logger.severe(severe);
	}
	  /**
     * Singleton init.
     * @return instance
     */

     public static ArsmLogger init(String logName, String fileName){
                             if(_instance==null)
                             {
                                     _instance = new ArsmLogger (logName, fileName);
                             }
                             return _instance;
     }
     public static ArsmLogger init(){
     	return init("default", "default.log");
     }
     // This custom formatter formats parts of a log record to a single line
     class ArsmLoggerFormatter extends Formatter {
         // This method is called for every log records
         public String format(LogRecord rec) {
             StringBuffer buf = new StringBuffer(1000);
             buf.append(new java.util.Date(rec.getMillis()) + " " + rec.getLevel().toString());
             buf.append(" " +formatMessage(rec) + "\n");
             return buf.toString();
         }
     
         // This method is called just after the handler using this
         // formatter is created
         public String getHead(Handler h) {
             return "ARSM Logger Started:"+ new java.util.Date() +"\n";
         }
     
         // This method is called just after the handler using this
         // formatter is closed
         public String getTail(Handler h) {
         	return "ARSM Logger Finished:"+ new java.util.Date() +"\n";
         }
     }


}


