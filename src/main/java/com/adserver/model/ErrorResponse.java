package com.adserver.model;

public class ErrorResponse {

	private String message;

	public ErrorResponse(String message) {
		if (message == null) {
			message = "Unable to process the request";
		}
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}