package voce;

public class Utils
{
	private static boolean mPrintDebug = false;

	/// A simple message logging function.  The message type gets printed 
	/// before the actual message.
	static public void log(String msgType, String msg)
	{
		// If we're ignoring debug messages and this one is a debug 
		// message, return.
		if (!mPrintDebug && msgType.equals("debug"))
		{
			return;
		}

		String finalMessage = "[Voce";

		if (!msgType.equals(""))
		{
			finalMessage = finalMessage + " " + msgType;
		}

		finalMessage = finalMessage + "] " + msg;
		System.out.println(finalMessage);
		System.out.flush();
	}
	
	/// Sets how much debug output to print ('true' prints debug and error 
	/// messages; 'false' prints only error messages).
	static public void setPrintDebug(boolean printDebug)
	{
		mPrintDebug = printDebug;
	}
}
