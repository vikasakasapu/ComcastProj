'use strict';

angular.module('myApp').controller('CampaignController', ['$scope', 'CampaignService', function($scope, CampaignService) {
	var self = this;
	self.campaign={"partner_id":null,"duration":null,"ad_content":'', "active":''};
	self.campaigns=[];
	self.search={"partner_id":null,"duration":null,"ad_content":'', "active":''};
	
	self.submit = submit;
	self.searchCampaign = searchCampaign;
	self.remove = remove;
	self.reset = reset;
	self.fetchAll = fetchAllCampaigns;


	fetchAllCampaigns();

	function fetchAllCampaigns(){
		CampaignService.fetchAllCampaigns()
		.then(
				function(d) {
					self.campaigns = d;
				},
				function(errResponse){
					console.error('Error while fetching Campaigns');
				}
		);
	}
	
	function fetchCampaignById(id){
		CampaignService.fetchCampaignById(id)
		.then(
				function(d) {
					self.search = d;
				},
				function(errResponse){
					self.search = {"partner_id":null,"duration":null,"ad_content":'', "active":''};
					console.error('Error while fetching Campaigns');
				}
		);
	}

	function createCampaign(campaign){
		CampaignService.saveCampaign(campaign)
		.then(
				fetchAllCampaigns,
				function(errResponse){
					console.error('Error while creating Campaign');
				}
		);
	}

	function deleteCampaign(partnerId){
		CampaignService.deleteCampaign(partnerId)
		.then(
				fetchAllCampaigns,
				function(errResponse){
					console.error('Error while deleting campaign');
				}
		);
	}

	function submit() {
		createCampaign(self.campaign);
		reset();
	}
	
	function searchCampaign() {
		fetchCampaignById(self.searchId);
	}

	function remove(partnerId){
		console.log('id to be deleted', partnerId);
		if(self.campaign.partner_id === partnerId) {
			reset();
		}
		deleteCampaign(partnerId);
	}


	function reset(){
		self.campaign={partner_id:null,duration:null,ad_content:''};
		$scope.myForm.$setPristine(); //reset Form
	}

}]);
