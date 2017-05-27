if (typeof jQuery == 'undefined') {
  var jqueryScriptElement  = document.createElement('script');
  jqueryScriptElement.type = 'text/javascript';
  jqueryScriptElement.src  = unescape('http%3a%2f%2fcode.jquery.com%2fjquery-latest.min.js');
  var target = document.getElementsByTagName('head')[0];
  target.appendChild(jqueryScriptElement);
}
  // var target = document.getElementsByTagName('script')[0];
  // target.parentNode.insertBefore(altScript, target);
