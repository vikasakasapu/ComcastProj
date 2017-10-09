package com.adserver;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.adserver.exception.ActiveCampaignException;
import com.adserver.exception.CampaignNotFoundException;
import com.adserver.exception.InvalidInputException;
import com.adserver.model.AdCampaign;
import com.adserver.service.AdService;
import com.adserver.service.AdServiceImpl;

public class AdServiceTest {

	AdService service;

	@Before
	public void setup() {
		service = new AdServiceImpl();
	}

	@After
	public void destroy() {
		service = null;
	}

	@Test
	public void testSaveCampaign() {
		AdCampaign campaign = new AdCampaign("Sony", 200, "Bravia");
		service.deleteAllCampaigns();
		service.saveCampaign(campaign);

		List<AdCampaign> list = service.findAllCampaigns();
		assertThat(list.size(), is(equalTo(1)));
		assertThat(list.get(0).getPartnerId(), is(equalTo(campaign.getPartnerId())));
		assertThat(list.get(0).getDuration(), is(equalTo(campaign.getDuration())));
		assertThat(list.get(0).getContent(), is(equalTo(campaign.getContent())));
	}

	@Test(expected = ActiveCampaignException.class)
	public void testSaveCampaignWithError() {
		AdCampaign campaign = new AdCampaign("Sony", 200, "Bravia");
		service.saveCampaign(campaign);
		service.saveCampaign(campaign);
	}

	@Test(expected = InvalidInputException.class)
	public void testSaveCampaignWithInputError() {
		AdCampaign campaign = new AdCampaign();
		service.saveCampaign(campaign);
	}

	@Test
	public void testSaveBonusCampaigns() {
		AdCampaign campaign = new AdCampaign("Sony", 200, "Bravia");
		service.deleteAllCampaigns();
		service.saveBonusCampaign(campaign);
		service.saveBonusCampaign(campaign);

		List<AdCampaign> list = service.findAllCampaigns();
		assertThat(list.size(), is(equalTo(2)));
	}

	@Test
	public void testFindByPartnerId() {
		AdCampaign campaign1 = new AdCampaign("Sony", 200, "Sample ad content");
		AdCampaign campaign2 = new AdCampaign("Apple", 500, "Sample AD");
		service.deleteAllCampaigns();
		service.saveCampaign(campaign1);
		service.saveCampaign(campaign2);

		AdCampaign result = service.findByPartnerId("Apple");
		assertThat(result.getPartnerId(), is(equalTo(campaign2.getPartnerId())));
		assertThat(result.getDuration(), is(equalTo(campaign2.getDuration())));
		assertThat(result.getContent(), is(equalTo(campaign2.getContent())));
	}

	@Test(expected = CampaignNotFoundException.class)
	public void testFindByPartnerId_WithError() {
		AdCampaign campaign1 = new AdCampaign("Sony", 200, "Sample ad content");
		service.deleteAllCampaigns();
		service.saveCampaign(campaign1);

		AdCampaign result = service.findByPartnerId("Apple");
	}

	@Test
	public void testFindAllCampaigns() {
		AdCampaign campaign1 = new AdCampaign("Sony", 200, "Sample ad content");
		AdCampaign campaign2 = new AdCampaign("Apple", 500, "Sample AD");
		service.deleteAllCampaigns();
		service.saveCampaign(campaign1);
		service.saveCampaign(campaign2);

		List<AdCampaign> list = service.findAllCampaigns();

		assertThat(list.size(), is(equalTo(2)));
		assertThat(list.get(0).getPartnerId(), is(equalTo(campaign1.getPartnerId())));
		assertThat(list.get(1).getDuration(), is(equalTo(campaign2.getDuration())));
	}

	@Test
	public void testDeleteCampaignById() {
		AdCampaign campaign1 = new AdCampaign("Sony", 200, "Sample ad content");
		AdCampaign campaign2 = new AdCampaign("Apple", 500, "Sample AD");
		service.deleteAllCampaigns();
		service.saveCampaign(campaign1);
		service.saveCampaign(campaign2);
		service.deleteCampaigns("Sony");

		List<AdCampaign> list = service.findAllCampaigns();

		assertThat(list.size(), is(equalTo(1)));
		assertThat(list.get(0).getPartnerId(), is(equalTo("Apple")));
	}

	@Test
	public void testDeleteAllCampaigns() {
		AdCampaign campaign1 = new AdCampaign("Sony", 200, "Sample ad content");
		AdCampaign campaign2 = new AdCampaign("Apple", 500, "Sample AD");
		service.saveBonusCampaign(campaign1);
		service.saveBonusCampaign(campaign2);
		service.deleteAllCampaigns();

		List<AdCampaign> list = service.findAllCampaigns();

		assertThat(list.size(), is(equalTo(0)));
	}
}