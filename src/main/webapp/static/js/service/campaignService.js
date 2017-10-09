'use strict';

angular.module('myApp').factory('CampaignService', ['$http', '$q', function($http, $q){

    var REST_SERVICE_URI = 'ad/';

    var factory = {
        fetchAllCampaigns: fetchAllCampaigns,
        fetchCampaignById: fetchCampaignById,
        saveCampaign: saveCampaign,
        deleteCampaign:deleteCampaignByPartnerId
    };

    return factory;

    function fetchAllCampaigns() {
        var deferred = $q.defer();
        $http.get(REST_SERVICE_URI)
            .then(
            function (response) {
                deferred.resolve(response.data);
            },
            function(errResponse){
                console.error('Error while fetching Campaigns');
                deferred.reject(errResponse);
            }
        );
        return deferred.promise;
    }
    
    function fetchCampaignById(id) {
        var deferred = $q.defer();
        $http.get(REST_SERVICE_URI+id)
            .then(
            function (response) {
                deferred.resolve(response.data);
            },
            function(errResponse) {
            	alert(errResponse.data.message);
                console.error('Error while fetching Campaigns');
                deferred.reject(errResponse);
            }
        );
        return deferred.promise;
    }

    function saveCampaign(campaign) {
        var deferred = $q.defer();
        $http.post(REST_SERVICE_URI, campaign)
            .then(
            function (response) {
                deferred.resolve(response.data);
            },
            function(errResponse){
            	console.log(errResponse);
            	alert(errResponse.data.message);
                console.error('Error while saving campaign');
                deferred.reject(errResponse);
            }
        );
        return deferred.promise;
    }

    function deleteCampaignByPartnerId(id) {
        var deferred = $q.defer();
        $http.delete(REST_SERVICE_URI+id)
            .then(
            function (response) {
                deferred.resolve(response.data);
            },
            function(errResponse){
                console.error('Error while deleting campaign');
                deferred.reject(errResponse);
            }
        );
        return deferred.promise;
    }

}]);
