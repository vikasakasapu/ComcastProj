package com.adserver.model;

import java.sql.Timestamp;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

public class AdCampaign {
	@JsonProperty("partner_id")
	private String partnerId;

	private long duration;

	@JsonProperty("ad_content")
	private String content;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone="EST")
	private Timestamp creationDate;

	private boolean isActive = true;

	public AdCampaign() {
	}

	public AdCampaign(String partnerId, long duration, String content) {
		this.partnerId = partnerId;
		this.duration = duration;
		this.content = content;
		this.creationDate = new Timestamp(new Date().getTime());
	}

	public String getPartnerId() {
		return partnerId;
	}

	public void setPartnerId(String partnerId) {
		this.partnerId = partnerId;
	}

	public long getDuration() {
		return duration;
	}

	public void setDuration(long duration) {
		this.duration = duration;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Timestamp getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Timestamp creationDate) {
		this.creationDate = creationDate;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	@Override
	public String toString() {
		return "ADCampaign [partnerId=" + partnerId + ", duration=" + duration + ", content=" + content
				+ ", creationDate=" + creationDate + "]";
	}

}