package com.adserver.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.adserver.model.AdCampaign;
import com.adserver.service.AdService;

@RestController
public class AdCampaignController {

	@Autowired
	AdService adService;

	@RequestMapping(value = "/ad", method = RequestMethod.POST)
	public ResponseEntity<Void> createCampaign(@RequestBody AdCampaign campaign) {
		campaign.setActive(true);
		adService.saveCampaign(campaign);
		return new ResponseEntity<Void>(HttpStatus.CREATED);
	}
	
	@RequestMapping(value = "/bonus/ad", method = RequestMethod.POST)
	public ResponseEntity<Void> createBonusCampaign(@RequestBody AdCampaign campaign) {
		adService.saveBonusCampaign(campaign);
		return new ResponseEntity<Void>(HttpStatus.CREATED);
	}

	@RequestMapping(value = "/ad", method = RequestMethod.GET)
	public ResponseEntity<List<AdCampaign>> getAllCampaigns() {
		List<AdCampaign> campaigns = adService.findAllCampaigns();
		if (campaigns == null || campaigns.isEmpty()) {
			return new ResponseEntity<List<AdCampaign>>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<AdCampaign>>(campaigns, HttpStatus.OK);
	}

	@RequestMapping(value = "/ad/{partnerId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<AdCampaign> getCampaign(@PathVariable("partnerId") String partnerId) {
		AdCampaign campaign = adService.findByPartnerId(partnerId);
		if (campaign == null) {
			return new ResponseEntity<AdCampaign>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<AdCampaign>(campaign, HttpStatus.OK);
	}

	@RequestMapping(value = "/ad", method = RequestMethod.DELETE)
	public ResponseEntity<AdCampaign> deleteAllCampaigns() {
		adService.deleteAllCampaigns();
		return new ResponseEntity<AdCampaign>(HttpStatus.NO_CONTENT);
	}
	
	@RequestMapping(value = "/ad/{partnerId}", method = RequestMethod.DELETE)
	public ResponseEntity<AdCampaign> deleteCampaignById(@PathVariable("partnerId") String partnerId) {
		adService.deleteCampaigns(partnerId);
		return new ResponseEntity<AdCampaign>(HttpStatus.NO_CONTENT);
	}
}