var element = arguments[0];
var expression = arguments[1];
return angular.element(element).scope().$eval(expression);