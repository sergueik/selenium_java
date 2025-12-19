const url = "/rtf-viewer.html";

chrome.runtime.onInstalled.addListener(function(details){
    if(details.reason == "install"){
		chrome.tabs.create({ url: url });
    }
});
chrome.action.onClicked.addListener(function(activeTab)
{
    chrome.tabs.create({ url: url });
});
