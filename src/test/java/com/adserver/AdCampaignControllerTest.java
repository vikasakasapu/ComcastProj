package com.adserver;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.nio.charset.Charset;
import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.adserver.configuration.CampaignConfiguration;
import com.adserver.configuration.CampaignInitializer;
import com.adserver.controller.AdCampaignController;
import com.adserver.exception.CampaignNotFoundException;
import com.adserver.model.AdCampaign;
import com.adserver.service.AdService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { CampaignConfiguration.class, CampaignInitializer.class })
@WebAppConfiguration
public class AdCampaignControllerTest {

	private MockMvc mockMvc;

	@Mock
	private AdService serviceMock;

	@InjectMocks
	private AdCampaignController campaignController;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(campaignController).build();
	}

	public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(),
			MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

	@Test
	public void findAllCampaigns_ShouldReturnSucess() throws Exception {
		AdCampaign first = new AdCampaign("Sony", 20L, "Sony Bravia");
		AdCampaign second = new AdCampaign("Apple", 50L, "Apple Inc. is an American multinational technology company");

		when(serviceMock.findAllCampaigns()).thenReturn(Arrays.asList(first, second));

		mockMvc.perform(get("/ad")).andExpect(status().isOk()).andExpect(content().contentType(APPLICATION_JSON_UTF8))
				.andExpect(jsonPath("$", hasSize(2))).andExpect(jsonPath("$[0].partner_id", is(first.getPartnerId())))
				.andExpect(jsonPath("$[0].duration", is((int) first.getDuration())))
				.andExpect(jsonPath("$[0].ad_content", is(first.getContent())))

		.andExpect(jsonPath("$[1].partner_id", is(second.getPartnerId())))
				.andExpect(jsonPath("$[1].duration", is((int) second.getDuration())))
				.andExpect(jsonPath("$[1].ad_content", is(second.getContent())));

		verify(serviceMock, times(1)).findAllCampaigns();
		verifyNoMoreInteractions(serviceMock);
	}

	@Test
	public void findByPartnerId_ShouldReturnSucess() throws Exception {
		AdCampaign first = new AdCampaign("Sony", 20L, "Sony Bravia");

		when(serviceMock.findByPartnerId("Sony")).thenReturn(first);

		mockMvc.perform(get("/ad/{partner_id}", "Sony")).andExpect(status().isOk())
				.andExpect(content().contentType(APPLICATION_JSON_UTF8))
				.andExpect(jsonPath("$.partner_id", is(first.getPartnerId())))
				.andExpect(jsonPath("$.duration", is((int) first.getDuration())))
				.andExpect(jsonPath("$.ad_content", is(first.getContent())));

		verify(serviceMock, times(1)).findByPartnerId("Sony");
		verifyNoMoreInteractions(serviceMock);
	}

	@Test(expected = Exception.class)
	public void findByPartnerId_ShouldReturnHttpStatusCode404() throws Exception {
		when(serviceMock.findByPartnerId("Sony")).thenThrow(new CampaignNotFoundException(""));

		mockMvc.perform(get("/ad/{partner_id}", "Sony")).andExpect(status().isNotFound());

		verify(serviceMock, times(1)).findByPartnerId("Sony");
		verifyNoMoreInteractions(serviceMock);
	}

	@Test
	public void saveCampaign_ShouldReturnSucess() throws Exception {

		mockMvc.perform(post("/ad").contentType(APPLICATION_JSON_UTF8)
				.content("{\"partner_id\":\"Sony\",\"duration\":\"20\",\"ad_content\":\"Bravia\"}"))
				.andExpect(status().is2xxSuccessful());
	}

	@Test
	public void saveBonusCampaign_ShouldReturnSucess() throws Exception {

		mockMvc.perform(post("/bonus/ad").contentType(APPLICATION_JSON_UTF8)
				.content("{\"partner_id\":\"Sony\",\"duration\":\"20\",\"ad_content\":\"Bravia\"}"))
				.andExpect(status().isCreated());
	}

	@Test
	public void deleteCampaign_ShouldReturnSucess() throws Exception {
		mockMvc.perform(delete("/ad")).andExpect(status().isNoContent());
	}

	@Test
	public void deleteCampaignById_ShouldReturnSucess() throws Exception {
		mockMvc.perform(delete("/ad/{partner_id}", "Sony")).andExpect(status().is2xxSuccessful());
	}
}