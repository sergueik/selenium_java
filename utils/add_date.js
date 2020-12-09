var days=parseInt(WScript.Arguments.Item(0));

Date.prototype.addDays = function(days) { var date = new Date(this.valueOf()); date.setDate(date.getDate() + days); return date; }

var o = (new Date()).addDays(days);

WScript.Echo((1 + o.getMonth()) + '/' + o.getDate() + '/' + o.getFullYear());
