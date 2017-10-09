package com.adserver.exception;

@SuppressWarnings("serial")
public class CampaignNotFoundException extends RuntimeException {

	public CampaignNotFoundException(String message) {
		super(message);
	}
}
