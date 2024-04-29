var myApp = angular.module('myApp',  [ 'ngRoute' ])
//console.log("data = " + data);
myApp.controller('UserController', 
	function UserController($scope) {
		$scope.json = null;

	    $scope.getUsersList = function() {

			data = JSON.parse(data);

			console.log(data);

			$scope.json = data;
	    };

	}
);