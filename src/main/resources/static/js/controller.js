var myApp = angular.module('myApp',  [ 'ngRoute' ])
console.log("data = " + data);
myApp.controller('UserController', 
	function UserController($scope) {
		$scope.json = null;
		//alert("data = " + data);

	    $scope.getUsersList = function() {

			var status = data.length;
			var show = false;
			var buf = null;
			var newData = [];
			alert("data.length = " + data.length);
			for(var i = 0; i < status; i++) {
				if (i % 3 == 2) {
					show = true;
				} else {
					show = false;
				}
				
				//newData[i] = { "alt": data[i].alt , "url": data[i].url, "show":  show };
				newData[i] = { "alt": data[i].alt , "url": data[i].url , "show":  show };
			}
			
			console.log(newData);
			
			data = JSON.stringify(newData);
			//document.writeln(data);

			data = JSON.parse(data);
			console.log(data);

			$scope.json = data;
	    };

		// undefined
		//alert("$scope.index = " + $scope.index );
		
		// NaN
		//alert("$scope.index % 3 =" +($scope.index % 3));

		if($scope.index % 3 == 2) {
			$scope.show = true;
		} else {
			$scope.show = false;
		}
	}
);
