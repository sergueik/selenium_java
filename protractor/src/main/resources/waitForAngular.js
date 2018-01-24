/**
 * Wait until Angular has finished rendering and has
 * no outstanding $http calls before continuing. The specific Angular app
 * is determined by the rootSelector.
 *
 * Asynchronous.
 *
 * @param {string} rootSelector The selector housing an ng-app
 * @param {function(string)} callback callback. If a failure occurs, it will
 *     be passed as a parameter.
 */

 waitForAngular = function(rootSelector, callback) {

  try {
    // Wait for both angular1 testability and angular2 testability.

    var testCallback = callback;

    // Wait for angular1 testability first and run waitForAngular2 as a callback
    var waitForAngular1 = function(callback) {

      if (window.angular) {
        var hooks = getNg1Hooks(rootSelector);
        if (!hooks){
          callback();  // not an angular1 app
        }
        else{
          if (hooks.$$testability) {
            hooks.$$testability.whenStable(callback);
          } else if (hooks.$injector) {
            hooks.$injector.get('$browser')
                .notifyWhenNoOutstandingRequests(callback);
          } else if (!rootSelector) {
            throw new Error(
                'Could not automatically find injector on page: "' +
                window.location.toString() + '".  Consider using config.rootEl');
          } else {
            throw new Error(
                'root element (' + rootSelector + ') has no injector.' +
                ' this may mean it is not inside ng-app.');
          }
        }
      }
      else {callback();}  // not an angular1 app
    };

    // Wait for Angular2 testability and then run test callback
    var waitForAngular2 = function() {
      if (window.getAngularTestability) {
        if (rootSelector) {
          var testability = null;
          var el = document.querySelector(rootSelector);
          try{
            testability = window.getAngularTestability(el);
          }
          catch(e){}
          if (testability) {
            testability.whenStable(testCallback);
            return;
          }
        }

        // Didn't specify root element or testability could not be found
        // by rootSelector. This may happen in a hybrid app, which could have
        // more than one root.
        var testabilities = window.getAllAngularTestabilities();
        var count = testabilities.length;

        // No angular2 testability, this happens when
        // going to a hybrid page and going back to a pure angular1 page
        if (count === 0) {
          testCallback();
          return;
        }

        var decrement = function() {
          count--;
          if (count === 0) {
            testCallback();
          }
        };
        testabilities.forEach(function(testability) {
          testability.whenStable(decrement);
        });

      }
      else {testCallback();}  // not an angular2 app
    };

    if (!(window.angular) && !(window.getAngularTestability)) {
      // no testability hook
      throw new Error(
          'both angularJS testability and angular testability are undefined.' +
          '  This could be either ' +
          'because this is a non-angular page or because your test involves ' +
          'client-side navigation, which can interfere with Protractor\'s ' +
          'bootstrapping.  See http://git.io/v4gXM for details');
    } else {waitForAngular1(waitForAngular2);}  // Wait for angular1 and angular2
                                                // Testability hooks sequentially

  } catch (err) {
    callback(err.message);
  }

};

/* Tries to find $$testability and possibly $injector for an ng1 app
 *
 * By default, doesn't care about $injector if it finds $$testability.  However,
 * these priorities can be reversed.
 *
 * @param {string=} selector The selector for the element with the injector.  If
 *   falsy, tries a variety of methods to find an injector
 * @param {boolean=} injectorPlease Prioritize finding an injector
 * @return {$$testability?: Testability, $injector?: Injector} Returns whatever
 *   ng1 app hooks it finds
 */
function getNg1Hooks(selector, injectorPlease) {
  function tryEl(el) {
    try {
      if (!injectorPlease && angular.getTestability) {
        var $$testability = angular.getTestability(el);
        if ($$testability) {
          return {$$testability: $$testability};
        }
      } else {
        var $injector = angular.element(el).injector();
        if ($injector) {
          return {$injector: $injector};
        }
      }
    } catch(err) {}
  }
  function trySelector(selector) {
    var els = document.querySelectorAll(selector);
    for (var i = 0; i < els.length; i++) {
      var elHooks = tryEl(els[i]);
      if (elHooks) {
        return elHooks;
      }
    }
  }

  if (selector) {
    return trySelector(selector);
  } else if (window.__TESTABILITY__NG1_APP_ROOT_INJECTOR__) {
    var $injector = window.__TESTABILITY__NG1_APP_ROOT_INJECTOR__;
    var $$testability = null;
    try {
      $$testability = $injector.get('$$testability');
    } catch (e) {}
    return {$injector: $injector, $$testability: $$testability};
  } else {
    return tryEl(document.body) ||
        trySelector('[ng-app]') || trySelector('[ng\\:app]') ||
        trySelector('[ng-controller]') || trySelector('[ng\\:controller]');
  }
}

var log = function(message){ console.log("callback: " + message); return(message); }

var rootSelector = arguments[0] || 'document';
var callback = arguments[1] || log;

waitForAngular(rootSelector, callback);
