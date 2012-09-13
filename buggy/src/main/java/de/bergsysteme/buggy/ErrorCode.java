package de.bergsysteme.buggy; 

/***
 * Translates given error codes to the associated message.
 * 
 * @author Bjoern Berg, bjoern.berg@gmx.de
 * @version 1.0
 * @since 2012-06-27
 */
public class ErrorCode {
	// Reserved for the instance.
	private static ErrorCode _instance;
	// Error Message if code is not found.
	private static final String NOT_FOUND = Messages.getString("ErrorCode.na"); //$NON-NLS-1$
	// Translation table for return code.
	private final String[] codeTable;
	
	private ErrorCode() {
		codeTable = new String[26];
		codeTable[0] = Messages.getString("ErrorCode.0"); //$NON-NLS-1$
		codeTable[1] = Messages.getString("ErrorCode.1"); //$NON-NLS-1$
		codeTable[2] = Messages.getString("ErrorCode.2"); //$NON-NLS-1$
		codeTable[3] = Messages.getString("ErrorCode.3"); //$NON-NLS-1$
		codeTable[4] = Messages.getString("ErrorCode.4"); //$NON-NLS-1$
		codeTable[5] = Messages.getString("ErrorCode.5"); //$NON-NLS-1$
		codeTable[6] = Messages.getString("ErrorCode.6"); //$NON-NLS-1$
		codeTable[7] = Messages.getString("ErrorCode.7"); //$NON-NLS-1$
		codeTable[8] = Messages.getString("ErrorCode.8"); //$NON-NLS-1$
		codeTable[9] = Messages.getString("ErrorCode.9"); //$NON-NLS-1$
		codeTable[10] = Messages.getString("ErrorCode.10"); //$NON-NLS-1$
		codeTable[11] = NOT_FOUND;
		codeTable[12] = Messages.getString("ErrorCode.12"); //$NON-NLS-1$
		codeTable[13] = Messages.getString("ErrorCode.13"); //$NON-NLS-1$
		codeTable[14] = Messages.getString("ErrorCode.14"); //$NON-NLS-1$
		codeTable[15] = Messages.getString("ErrorCode.15"); //$NON-NLS-1$
		codeTable[16] = Messages.getString("ErrorCode.16"); //$NON-NLS-1$
		codeTable[17] = Messages.getString("ErrorCode.17"); //$NON-NLS-1$
		codeTable[18] = Messages.getString("ErrorCode.18"); //$NON-NLS-1$
		codeTable[19] = Messages.getString("ErrorCode.19"); //$NON-NLS-1$
		codeTable[20] = Messages.getString("ErrorCode.20"); //$NON-NLS-1$
		codeTable[21] = Messages.getString("ErrorCode.21"); //$NON-NLS-1$
		codeTable[22] = Messages.getString("ErrorCode.22"); //$NON-NLS-1$
		codeTable[23] = Messages.getString("ErrorCode.23"); //$NON-NLS-1$
		codeTable[24] = Messages.getString("ErrorCode.24"); //$NON-NLS-1$
		codeTable[25] = Messages.getString("ErrorCode.25"); //$NON-NLS-1$
	}
	
	/***
	 * Creates the instance of ErrorCode table provider.
	 * @return instance of error code.
	 */
	protected static ErrorCode getInstance() {
		if (_instance == null) {
			_instance = new ErrorCode();
		}
		return _instance;
	}
	
	/***
	 * Gets the message that is associated with the provided error code.
	 * @param code numeric error code.
	 * @return message associated with error code.
	 */
	private String getTranslation(int code) {
		String translation = null;
		if (code >= 0 && code < codeTable.length) {
			translation = codeTable[code];
		} else {
			translation = NOT_FOUND;
		}
		return translation;
	}
	
	/***
	 * Returns the message that is associated with the provided error code.
	 * @param code numeric error code.
	 * @return message associated with error code.
	 */
	public static String translateCode(int code) {
		return getInstance().getTranslation(code);
	}
}
