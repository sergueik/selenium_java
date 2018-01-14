// Force java to connect the calculatorService object before any subsequent javascript files,
// such as angular controllers, are executed. This sentence must be called
// before any other script which will use calculatorService
alert("__CONNECT__BACKEND__calculatorService");
