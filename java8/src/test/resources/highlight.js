/**
 * Creates a floating translucent div at the specified location in order to highlight a particular
 * element. Note that these scripts are run directly in the browser under test, so they need to
 * be ES5 compatible and serializable.
 */
highlight_create = function(top, left, width, height) {
  // console.log('Highlighting at ', top, left, width, height);
  var el = document.createElement('div');
  el.id = 'BP_ELEMENT_HIGHLIGHT__';
  document.body.appendChild(el);
  el.style['position'] = 'absolute';
  el.style['background-color'] = 'lightblue';
  el.style['opacity'] = '0.7';
  el.style['top'] = top + 'px';
  el.style['left'] = left + 'px';
  el.style['width'] = width + 'px';
  el.style['height'] = height + 'px';
};

/**
 * Removes the highlight
 */
highlight_remove = function() {
  var el = document.getElementById('BP_ELEMENT_HIGHLIGHT__');
  if (el) {
    el.parentElement.removeChild(el);
  }
};

// origin: https://github.com/angular/blocking-proxy/blob/master/lib/client_scripts/highlight.js