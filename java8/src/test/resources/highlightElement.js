// origin:  3.5.8_0\content\targetSelecter.js
// Modified in tools.js from selenium-IDE
highlightElement = function(element) {
  var r = element.getBoundingClientRect();
  var style = "pointer-events: none; position: absolute; box-shadow: 0 0 0 1px black; outline: 1px dashed white; outline-offset: -1px; background-color: rgba(250,250,128,0.4); z-index: 100;";
  var pos = "top:" + (r.top + window.scrollY) + "px; left:" + (r.left + window.scrollX) + "px; width:" + r.width + "px; height:" + r.height + "px;";
  var div = window.document.createElement("div");
  div.id = '__ELEMENT_HIGHLIGHT__';
  div.setAttribute("style", "display: none;");
  doc.body.insertBefore(div, doc.body.firstChild);
  div.setAttribute("style", style + pos);
};

unHighlightElement = function(element) {
  var div = document.getElementById('__ELEMENT_HIGHLIGHT__');
  div.setAttribute("style", "display: none;");
};