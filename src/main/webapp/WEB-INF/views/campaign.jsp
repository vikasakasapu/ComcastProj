<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
  <head>  
    <title>Simple Ad Server</title>  
    <style>
      .partnerId.ng-valid {
          background-color: lightgreen;
      }
      .partnerId.ng-dirty.ng-invalid-required {
          background-color: red;
      }
      .partnerId.ng-dirty.ng-invalid-minlength {
          background-color: yellow;
      }

      .adcontent.ng-valid {
          background-color: lightgreen;
      }
      .adcontent.ng-dirty.ng-invalid-required {
          background-color: red;
      }
      .adcontent.ng-dirty.ng-invalid-adcontent {
          background-color: yellow;
      }

    </style>
     <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
     <link href="<c:url value='/static/css/app.css' />" rel="stylesheet"></link>
  </head>
  <body ng-app="myApp" class="ng-cloak">
      <div class="generic-container" ng-controller="CampaignController as ctrl">
          <div class="panel panel-default">
              <div class="panel-heading"><span class="lead">AD Campaign Registration Form </span></div>
              <div class="formcontainer">
                  <form ng-submit="ctrl.submit()" name="myForm" class="form-horizontal">
                      <div class="row">
                          <div class="form-group col-md-12">
                              <label class="col-md-2 control-lable" for="file">Partner ID</label>
                              <div class="col-md-7">
                                  <input type="text" ng-model="ctrl.campaign.partner_id" name="partnerId" class="partnerId form-control input-sm" 
                                  	placeholder="Enter your partner id" required ng-minlength="3"/>
                                  <div class="has-error" ng-show="myForm.$dirty">
                                      <span ng-show="myForm.partnerId.$error.required">This is a required field</span>
                                      <span ng-show="myForm.partnerId.$error.minlength">Minimum length required is 3</span>
                                      <span ng-show="myForm.partnerId.$invalid">This field is invalid </span>
                                  </div>
                              </div>
                          </div>
                      </div>
                        
                      
                      <div class="row">
                          <div class="form-group col-md-12">
                              <label class="col-md-2 control-lable" for="file">Duration</label>
                              <div class="col-md-7">
                                  <input type="number" ng-model="ctrl.campaign.duration" class="form-control input-sm" 
                                  	placeholder="Enter your Campaign Duration" required/>
                              </div>
                          </div>
                      </div>
                                  
                      <div class="row">
                          <div class="form-group col-md-12">
                              <label class="col-md-2 control-lable" for="file">AD Content</label>
                              <input type="text" ng-model="ctrl.campaign.ad_content" name="content" 
                                  	placeholder="Enter your Campaign content" required ng-minlength="3"/>
                                  
                              <!-- <div class="col-md-7">
                                  <input type="text" ng-model="ctrl.campaign.ad_content" name="content" class="adcontent form-control input-sm" 
                                  	placeholder="Enter your Campaign content" required ng-minlength="3"/>
                                  <div class="has-error" ng-show="myForm.$dirty">
                                      <span ng-show="myForm.content.$error.required">This is a required field</span>
                                      <span ng-show="myForm.content.$invalid">This field is invalid </span>
                                  </div>
                              </div> -->
                          </div>
                      </div>

                      <div class="row">
                          <div class="form-actions floatRight">
                              <input type="submit"  value="Add" class="btn btn-primary btn-sm" ng-disabled="myForm.$invalid">
                              <button type="button" ng-click="ctrl.reset()" class="btn btn-warning btn-sm" ng-disabled="myForm.$pristine">Reset Form</button>
                          </div>
                      </div>
                  </form>
              </div>
          </div>
          
          <div class="panel panel-default">
              <div class="panel-heading"><span class="lead">Search Campaign by Partner ID</span></div>
              <div class="formcontainer">
                  <form ng-submit="ctrl.searchCampaign()" name="mySearchForm" class="form-horizontal">
                      <div class="row">
                          <div class="form-group col-md-12">
                              <label class="col-md-2 control-lable" for="file">Partner ID</label>
                              <div class="col-md-7">
                                  <input type="text" ng-model="ctrl.searchId" name="partnerId" class="partnerId form-control input-sm" 
                                  	placeholder="Enter your partner id" required ng-minlength="3"/>
                              </div>
                          </div>
                      </div>
                      <div class="row">
                          <div class="form-actions floatRight">
                              <input type="submit"  value="Search" class="btn btn-primary btn-sm" ng-disabled="mySearchForm.$invalid">
                          </div>
                      </div>
                  </form>
              </div>
          </div>
          <div class="panel panel-default">
                <!-- Default panel contents -->
              <div class="panel-heading"><span class="lead">Search Result </span></div>
              <div class="tablecontainer">
                  <table class="table table-hover">
                      <thead>
                          <tr>
                              <th>Partner ID</th>
                              <th>Duration</th>
                              <th>Creation Date</th>
                              <th>AD Content</th>
                              <th>Active</th>
                          </tr>
                      </thead>
                      <tbody>
                          <tr>
                              <td><span ng-bind="ctrl.search.partner_id"></span></td>
                              <td><span ng-bind="ctrl.search.duration"></span></td>
                              <td><span ng-bind="ctrl.search.creationDate"></span></td>
                              <td><span ng-bind="ctrl.search.ad_content"></span></td>
                              <td><span ng-bind="ctrl.search.active"></span></td>                              
                          </tr>
                      </tbody>
                  </table>
              </div>
          </div>
          
          <div class="panel panel-default">
                <!-- Default panel contents -->
              <div class="panel-heading"><span class="lead">List of Campaigns 
                <button type="button" class="btn btn-info btn-sm" ng-click="ctrl.fetchAll()" >
		          <span class="glyphicon glyphicon-refresh"></span> Refresh
		        </button>
		      </span></div>
              <div class="tablecontainer">
                  <table class="table table-hover">
                      <thead>
                          <tr>
                              <th>Partner ID</th>
                              <th>Duration</th>
                              <th>Creation Date</th>
                              <th>AD Content</th>
                              <th>Active</th>
                              <th width="20%"></th>
                          </tr>
                      </thead>
                      <tbody>
                          <tr ng-repeat="c in ctrl.campaigns">
                              <td><span ng-bind="c.partner_id"></span></td>
                              <td><span ng-bind="c.duration"></span></td>
                              <td><span ng-bind="c.creationDate"></span></td>
                              <td><span ng-bind="c.ad_content"></span></td>
                              <td><span ng-bind="c.active"></span></td>
                              <td>
                              <button type="button" ng-click="ctrl.remove(c.partner_id)" class="btn btn-danger custom-width">Remove</button>
                              </td>
                          </tr>
                      </tbody>
                  </table>
              </div>
          </div>
      </div>
      
      <script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.4.4/angular.js"></script>
      <script src="<c:url value='/static/js/app.js' />"></script>
      <script src="<c:url value='/static/js/service/campaignService.js' />"></script>
      <script src="<c:url value='/static/js/controller/campaignController.js' />"></script>
  </body>
</html>