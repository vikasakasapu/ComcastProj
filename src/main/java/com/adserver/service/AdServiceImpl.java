package com.adserver.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.springframework.stereotype.Service;

import com.adserver.exception.ActiveCampaignException;
import com.adserver.exception.CampaignNotFoundException;
import com.adserver.exception.InvalidInputException;
import com.adserver.model.AdCampaign;

@Service("adService")
public class AdServiceImpl implements AdService {

	private static List<AdCampaign> campaigns;

	public AdServiceImpl() {
		initializeCampaigns();
	}

	private void initializeCampaigns() {
		if (campaigns == null) {
			campaigns = new ArrayList<>();
		}
	}

	@Override
	public void saveCampaign(AdCampaign campaign) {
		validateCampaign(campaign);

		AdCampaign existingCampaign = null;
		try {
			existingCampaign = findByPartnerId(campaign.getPartnerId());
		} catch (Exception e) {
		}
		if (existingCampaign != null) {
			throw new ActiveCampaignException(
					"Already a campaign is active for partner id : " + campaign.getPartnerId());
		}
		campaigns.add(campaign);
	}

	@Override
	public void saveBonusCampaign(AdCampaign campaign) {
		validateCampaign(campaign);
		campaigns.add(campaign);
	}

	private void validateCampaign(AdCampaign campaign) {
		boolean valid = false;
		if (campaign != null) {
			if (campaign.getPartnerId() != null && campaign.getDuration() > 0 && campaign.getContent() != null) {
				valid = true;
			}
		}
		if (!valid) {
			throw new InvalidInputException("Input is not valid");
		}
		campaign.setCreationDate(new Timestamp(new Date().getTime()));
	}

	@Override
	public AdCampaign findByPartnerId(String partnerId) {
		for (AdCampaign campaign : campaigns) {
			if (campaign.getPartnerId().equalsIgnoreCase(partnerId)) {
				if (campaign.isActive()) {
					long totalTime = campaign.getCreationDate().getTime() + campaign.getDuration() * 1000;
					if (totalTime > System.currentTimeMillis()) {
						return campaign;
					}
				}
			}
		}
		throw new CampaignNotFoundException("No active ad campaigns exist for the partner id " + partnerId);
	}

	@Override
	public List<AdCampaign> findAllCampaigns() {
		checkCampaigns();
		return campaigns;
	}

	private void checkCampaigns() {
		for (AdCampaign campaign : campaigns) {
			if (campaign.isActive()) {
				long totalTime = campaign.getCreationDate().getTime() + campaign.getDuration() * 1000;
				if (totalTime < System.currentTimeMillis()) {
					campaign.setActive(false);
				}
			}
		}
	}

	@Override
	public void deleteAllCampaigns() {
		campaigns.clear();
	}

	@Override
	public void deleteCampaigns(String partnerId) {
		Iterator<AdCampaign> iterator = campaigns.iterator();
		while (iterator.hasNext()) {
			AdCampaign next = iterator.next();
			if (next.getPartnerId().equals(partnerId)) {
				iterator.remove();
			}
		}
	}
}