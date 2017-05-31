function Utils(doc) {
    this._doc = doc;
}

Utils.prototype.consoleBash = function(msg) {
    var console = Components.classes['@mozilla.org/consoleservice;1'].getService(Components.interfaces.nsIConsoleService);
    if (Object.prototype.toString.call(msg) != "[object String]") {
        for (var key in msg) {
            console.logStringMessage("    " + key + ': ' + msg[key] + "\n");
        }
    } else {
        console.logStringMessage(msg);
    }
};

function alert (msg, opt_title) {
    opt_title = opt_title || "message";
    if (msg == null) {
        msg = "null";
    }
    Cc["@mozilla.org/embedcomp/prompt-service;1"].getService(Ci.nsIPromptService).alert(null, opt_title, msg.toString());
};
function $(id) {
  return screenshot.doc.getElementById(id);
}