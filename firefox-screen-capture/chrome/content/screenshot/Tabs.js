function Tabs(self) {
    this._self = self;
    
}

Tabs.prototype.getTabId = function() {
    var currDoc = gBrowser.contentDocument; 
    var targetBrowserIndex = gBrowser.getBrowserIndexForDocument(currDoc);
    var tab = gBrowser.tabContainer.childNodes[targetBrowserIndex];
    return tab.linkedPanel;
};

Tabs.prototype.getTabUrl = function() {
    return gBrowser.contentDocument.location.href;
};

Tabs.prototype.addTab = function(url, activ) {
    var tab = gBrowser.addTab(url);
    if (activ) {
        gBrowser.selectedTab = tab;
    }
    return gBrowser.getBrowserForTab(tab);
};