var myApp = angular.module('myApp',  [ 'ngRoute' ])
console.log("data = " + data);
myApp.controller('UserController', 
	function UserController($scope) {
		$scope.json = null;
		//alert("data = " + data);

	    $scope.getUsersList = function() {
			//alert("data = " + data);
			$scope.json = data;
	    };
	}
);
