package voce;

/// A set of static methods that give users access to the main speech 
/// interaction components.  These methods are the only ones exposed to 
/// other programming languages through the Java Native Interface.
public class SpeechInterface
{
	
	private static SpeechRecognizer mRecognizer = null;

	/// Initializes Voce.  The 'vocePath' String specifies the path where 
	/// Voce classes and config file can be found.  'initSynthesis' 
	/// and 'initRecognition' enable these capabilities; if you don't 
	/// need one or the other, not initializing it will save load time 
	/// and memory (though the feature will be disabled, of course).  
	/// 'grammarPath' is a relative or absolute path to one or more 
	/// grammar files (all .gram files in 'grammarPath' will automatically 
	/// be searched).  'grammarName' is the name of a specific grammar 
	/// within a .gram file in the 'grammarPath'.  If the 'grammarName' 
	/// is empty, a simple default grammar will be used.
	public static void init(String vocePath, boolean initRecognition, String grammarPath, String grammarName)
	{
		Utils.setPrintDebug(false);
		Utils.log("debug", "Beginning initialization");

		if (!initRecognition)
		{
			Utils.log("warning", "Recognizer are both" 
				+ "uninitialized.");
		}

		if (initRecognition)
		{
			if (grammarPath.equals(""))
			{
				grammarPath = "./";
			}

			// Always use the same config file.
			String configFilename = "voce.config.xml";

			// Create the speech recognizer.
			Utils.log("", "Initializing recognizer. " 
				+ "This may take some time...");
			mRecognizer = new SpeechRecognizer(vocePath + "/" 
				+ configFilename, grammarPath, grammarName);

			// Enable the recognizer; this will start the recognition 
			// thread.
			setRecognizerEnabled(true);
		}

		Utils.log("", "Initialization complete");
	}

	/// Destroys Voce.	
	public static void destroy()
	{
		Utils.log("debug", "Shutting down...");
		
		if (null != mRecognizer)
		{
			mRecognizer.destroy();
		}

		Utils.log("", "Shutdown complete");
	}
	
	
	/// Returns the number of recognized strings currently in the 
	/// recognizer's queue.
	public static int getRecognizerQueueSize()
	{
		if (null == mRecognizer)
		{
			Utils.log("warning", "getRecognizerQueueSize "
				+ "called before recognizer was initialized.  Returning " 
				+ "0.");
			return 0;
		}

		return mRecognizer.getQueueSize();
	}

	/// Returns and removes the oldest recognized string from the 
	/// recognizer's queue.
	public static String popRecognizedString()
	{
		if (null == mRecognizer)
		{
			Utils.log("warning", "popRecognizedString "
				+ "called before recognizer was initialized.  Returning " 
				+ "an empty string.");
			return "";
		}

		return mRecognizer.popString();
	}

	/// Enables and disables the speech recognizer.
	public static void setRecognizerEnabled(boolean e)
	{
		if (null == mRecognizer)
		{
			Utils.log("warning", "setRecognizerEnabled "
				+ "called before recognizer was initialized.  Request " 
				+ "will be ignored.");
			return;
		}

		mRecognizer.setEnabled(e);
	}

	/// Returns true if the recognizer is currently enabled.
	public static boolean isRecognizerEnabled()
	{
		if (null == mRecognizer)
		{
			Utils.log("warning", "isRecognizerEnabled "
				+ "called before recognizer was initialized.  Returning " 
				+ "false.");
			return false;
		}	

		return mRecognizer.isEnabled();
	}
}
