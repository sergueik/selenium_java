var app = angular.module('javafxwebdemo', []);

app.controller('JavaFXWebDemoController', function($scope) {

	// fruits
	$scope.fruits = [];
	$scope.update = function() {
		$scope.fruits = [ {
			getName : function() {
				return 'loading...'
			}
		} ];
		var callback = function(data) {
			// clone the array, to prevent [$rootScope:infdig] exception when
			// using the raw array returned from
			// Java directly
			try {
				// TODO:
				// https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Global_Objects/JSON/stringify
				$scope.fruits = eval(data).slice();
				// $scope.fruits = Array.from(data);
				// TypeError: undefined is not a function (evaluating
				// 'Array.from(data)')
			} catch (err) {
				throw new Error("Exception: " + err.toString() + ", data: "
						+ "'" + data + "'");
			}
			$scope.$apply();
		}
		fruitsService.loadFruits(callback);
	}

	$scope.update();
	// calculator
	$scope.number1 = 40;
	$scope.number2 = 2;

	$scope.sum = function() {
		return calculatorService.sum($scope.number1, $scope.number2);
	}
});
