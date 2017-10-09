package com.adserver.service;

import java.util.List;

import com.adserver.model.AdCampaign;

public interface AdService {

	void saveCampaign(AdCampaign campaign);

	AdCampaign findByPartnerId(String partnerId);

	List<AdCampaign> findAllCampaigns();

	void deleteAllCampaigns();

	void saveBonusCampaign(AdCampaign campaign);

	void deleteCampaigns(String partnerId);
}
