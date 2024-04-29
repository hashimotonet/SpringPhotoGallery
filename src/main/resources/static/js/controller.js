var myApp = angular.module('myApp',  [ 'ngRoute' ])
//console.log("data = " + data);
myApp.controller('UserController', 
	function UserController($scope) {
		$scope.json = null;
		//console.log("data = \r\n" + data);

	    $scope.getUsersList = function() {

			var status = JSON.parse(data).length;
			debugger;
			var show = false;
			var buf = null;
			var newData = [];
//			for(var i = 0; i < status; i++) {
//				if (i % 3 == 2) {
//					show = true;
//				} else {
//					show = false;
//				}
//				
//				//newData[i] = { "alt": data[i].alt , "url": data[i].url, "show":  show };
//				newData[i] = { "alt": data[i].alt , "url": data[i].url , "show":  show };
//			}
//			
//			console.log(newData);
			
//			data = JSON.stringify(newData);

			//data = JSON.stringify(data);
			data = JSON.parse(data);

			console.log(data);

			$scope.json = data;
	    };

//		if($scope.index % 3 == 2) {
//			$scope.show = true;
//		} else {
//			$scope.show = false;
//		}
	}
);