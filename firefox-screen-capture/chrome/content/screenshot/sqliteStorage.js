Object.defineProperty(window, "sqliteStorage", new (function () {
    var file = Components.classes["@mozilla.org/file/directory_service;1"]
                .getService(Components.interfaces.nsIProperties)
                .get("ProfD", Components.interfaces.nsIFile);
    var storageService = Components.classes["@mozilla.org/storage/service;1"]
                            .getService(Components.interfaces.mozIStorageService);
    var mDBConn = null;
    var tableName = 'screenshot';
    var aKeys = [], sStorage = {};
    
    file.append("ScreenshotData");
    if( !file.exists() || !file.isDirectory() ) {
       file.create(Components.interfaces.nsIFile.DIRECTORY_TYPE, 0777);
    }
    file.append("screenshot.sqlite");

    mDBConn = storageService.openDatabase(file);
    
    var create = function () {
        mDBConn.createTable(tableName, "id integer primary key autoincrement, Name_key TEXT, Key_value TEXT");
        mDBConn.executeSimpleSQL('CREATE UNIQUE INDEX idx_name_key ON ' + tableName + ' (Name_key)');
    };
    
    Object.defineProperty(sStorage, "getItem", {
      value: function (sKey) { 
          var statement = null;
          var result = null;

          if (!mDBConn.tableExists(tableName)) {
              create();
          }
          statement = mDBConn.createStatement("SELECT Key_value FROM " + tableName + " where Name_key = '" + sKey + "'");
          while (statement.step()) {
              result = statement.row['Key_value'];
          }
          
          return result;
      },
      writable: false,
      configurable: false,
      enumerable: false
    });
    
    Object.defineProperty(sStorage, "setItem", {
      value: function (sKey, sValue) { 
          if (!mDBConn.tableExists(tableName)) {
              create();
          }
          mDBConn.executeSimpleSQL("REPLACE INTO " + tableName + " (Name_key, Key_value) VALUES ('"+sKey+"', '"+sValue+"')");
      },
      writable: false,
      configurable: false,
      enumerable: false
    });
    
    Object.defineProperty(sStorage, "removeItem", {
      value: function (sKey) { 
          if (!mDBConn.tableExists(tableName)) {
              create();
          }
          mDBConn.executeSimpleSQL("DELETE FROM " + tableName + " WHERE Name_key = '"+sKey+"'");
      },
      writable: false,
      configurable: false,
      enumerable: false
    });
    
    Object.defineProperty(sStorage, "getAllItems", {
      value: function () { 
          var statement = null;
          var result = {};
          
          if (!mDBConn.tableExists(tableName)) {
              create();
          }
          statement = mDBConn.createStatement("SELECT Name_key, Key_value FROM " + tableName + "");
          while (statement.step()) {
              result[statement.row['Name_key']] = statement.row['Key_value'];
          }
          
          return result;
      },
      writable: false,
      configurable: false,
      enumerable: false
    });
    
    this.get = function () {
      var iThisIndx;
      for (var sKey in sStorage) {
        iThisIndx = aKeys.indexOf(sKey);
        if (iThisIndx === -1) { sStorage.setItem(sKey, sStorage[sKey]); }
        else { aKeys.splice(iThisIndx, 1); }
        delete sStorage[sKey];
      }
      for (aKeys; aKeys.length > 0; aKeys.splice(0, 1)) { sStorage.removeItem(aKeys[0]); }
      return sStorage;
    };
    
    this.configurable = false;
    this.enumerable = true;
  })());