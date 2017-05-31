var screenshot = {};

window.addEventListener("load", function() {
    screenshot.main();    
});
screenshot.main = function () {
    this.doc = document;
    this.win = window;
    this.utils = new Utils(this.doc);
    this.tabs = new Tabs(this);
    this.screen = new Screen(this);
};