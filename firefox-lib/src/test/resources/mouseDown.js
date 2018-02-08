  function mouseDown(options) {
      let element;
      const evt = document.createEvent('MouseEvents');
      if (options.point != undefined) {
        let point = options.point;
          evt.initMouseEvent('mousedown', true, true, window, 1, point.x, point.y, point.x, point.y, false, false, false,
              false, 0, null);
      } else {
          if (options.elementIndex != undefined) {
              const elementsArr = document.querySelectorAll(options.elementSelector);
              element = elementsArr[options.elementIndex];
          } else {
              element = document.querySelector(options.elementSelector);
          }
          evt.initMouseEvent('mousedown', true, true, window, 1, 1, 1, 1, 1, false, false, false,
              false, 0, null);
      }
      element.dispatchEvent(evt);
  }

  var optinons = arguments[0];
  /*
  // if (typeof options == 'undefined' || options == null || options == undefined) {
      options = {
          point: {
              x: 100,
              y: 100
          }
      };

  //} else {
     alert(typeof options); // object    
     alert(typeof options.point); // object    
     alert(typeof options.point.x); // object    
  //}
  */
  mouseDown(options);