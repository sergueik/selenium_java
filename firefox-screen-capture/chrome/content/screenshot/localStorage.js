Object.defineProperty(window, "localStorage", new (function () {
    var aKeys = [], oStorage = {};
    Object.defineProperty(oStorage, "getItem", {
      value: function (sKey) { 
          return sqliteStorage.getItem(escape(sKey));
      },
      writable: false,
      configurable: false,
      enumerable: false
    });
    Object.defineProperty(oStorage, "key", {
      value: function (nKeyId) { return aKeys[nKeyId]; },
      writable: false,
      configurable: false,
      enumerable: false
    });
    Object.defineProperty(oStorage, "setItem", {
      value: function (sKey, sValue) {
        if(!sKey) { return; }
        sqliteStorage.setItem(escape(sKey), escape(sValue));
      },
      writable: false,
      configurable: false,
      enumerable: false
    });
    Object.defineProperty(oStorage, "length", {
      get: function () { return aKeys.length; },
      configurable: false,
      enumerable: false
    });
    Object.defineProperty(oStorage, "removeItem", {
      value: function (sKey) {
        if(!sKey) { return; }
        sqliteStorage.removeItem(escape(sKey));
      },
      writable: false,
      configurable: false,
      enumerable: false
    });
    this.get = function () {
      var iThisIndx;
      for (var sKey in oStorage) {
        iThisIndx = aKeys.indexOf(sKey);
        if (iThisIndx === -1) { 
            oStorage.setItem(sKey, oStorage[sKey]); 
        } else { 
            aKeys.splice(iThisIndx, 1); 
        }
        delete oStorage[sKey];
      }
      for (aKeys; aKeys.length > 0; aKeys.splice(0, 1)) { oStorage.removeItem(aKeys[0]); }
      var aCouples = sqliteStorage.getAllItems();
      for (var iKey in aCouples) {
         iKey = unescape(iKey);
         oStorage[iKey] = unescape(aCouples[iKey]);
         aKeys.push(iKey);
      }
      return oStorage;
    };
    this.configurable = false;
    this.enumerable = true;
  })());
