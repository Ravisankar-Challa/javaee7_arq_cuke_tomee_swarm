package com.example.exception;

public class ApplicationException extends Exception {
	private final int code;
	private final String errorMessage;
	public ApplicationException(int code, String message) {
		super();
		this.code = code;
		this.errorMessage = message;
	}
	public int getCode() {
		return code;
	}
	public String getErrorMessage() {
		return errorMessage;
	}
}
