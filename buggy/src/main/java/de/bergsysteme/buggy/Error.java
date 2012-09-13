package de.bergsysteme.buggy;

public class Error {
	private int code;
	private String errorMessage;
	
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public void setCode(String code) {
		this.code = DataType.toInteger(code);
	}
	public String getError() {
		return errorMessage;
	}
	public void setError(String errorMessage) {
		this.errorMessage = errorMessage;
	}
}
