//node convert_locale.js ./screenshot/chrome/locale/ru-RU/messages.json
var fs = require('fs');
var path = require('path');
var filePath = process.argv[2];
var dirPath = path.dirname(filePath);
var messages = {};

fs.readFile(filePath, function (err, data) {
  if (err) throw err;
  messages = JSON.parse(data);
  generationProp(messages);
  generationDTD(messages);
});

function generationDTD (msg) {
    var resultDTD = '';
    for (var key in msg) {
        resultDTD += '<!ENTITY ' + key + ' "' + msg[key].message + '">' + "\n";
    }
    writeFile('screenshot.dtd', resultDTD);
}

function generationProp(msg) {
    var resultProp = '';
    for (var key in msg) {
        resultProp += key + '=' + msg[key].message + "\n";
    }
    writeFile('screenshot.properties', resultProp);
}

function writeFile(fileName, data) {
    var writeFile = path.join(dirPath, fileName);
    fs.writeFile(writeFile, data, function (err) {
        if (err) throw err;
        console.log('generation finish: ' + fileName);
    });
}