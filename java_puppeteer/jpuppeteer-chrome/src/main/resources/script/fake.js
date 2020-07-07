function fake() {
  //navigator.webdriver
  Object.defineProperty(navigator, 'webdriver', {
    get: () => undefined
  });
  //navigator.languages
  Object.defineProperty(navigator, 'languages', {
    get: () => ['zh-CN', 'en-US', 'en']
  });
  //navigator.language
  Object.defineProperty(navigator, 'language', {
    get: () => 'zh-CN'
  });
  //navigator.platform
  Object.defineProperty(navigator, 'platform', {
    get: () => 'Win32'
  });
  //window.chrome
  window.chrome = {
    runtime : {
      "PlatformOs": {
        "MAC": "mac",
        "WIN": "win",
        "ANDROID": "android",
        "CROS": "cros",
        "LINUX": "linux",
        "OPENBSD": "openbsd"
      },
      "PlatformArch": {
        "ARM": "arm",
        "X86_32": "x86-32",
        "X86_64": "x86-64"
      },
      "PlatformNaclArch": {
        "ARM": "arm",
        "X86_32": "x86-32",
        "X86_64": "x86-64"
      },
    }
  }
  //navigator.plugins
  function mockPluginsAndMimeTypes() {
    /* global MimeType MimeTypeArray PluginArray */

    // Disguise custom functions as being native
    const makeFnsNative = (fns = []) => {
      const oldCall = Function.prototype.call
      function call() {
        return oldCall.apply(this, arguments)
      }
      // eslint-disable-next-line
      Function.prototype.call = call

      const nativeToStringFunctionString = Error.toString().replace(
        /Error/g,
        'toString'
      )
      const oldToString = Function.prototype.toString

      function functionToString() {
        for (const fn of fns) {
          if (this === fn.ref) {
            return `function ${fn.name}() { [native code] }`
          }
        }

        if (this === functionToString) {
          return nativeToStringFunctionString
        }
        return oldCall.call(oldToString, this)
      }
      // eslint-disable-next-line
      Function.prototype.toString = functionToString
    }

    const mockedFns = []

    const fakeData = {
      mimeTypes: [
        {
          type: 'application/pdf',
          suffixes: 'pdf',
          description: '',
          __pluginName: 'Chrome PDF Viewer'
        },
        {
          type: 'application/x-google-chrome-pdf',
          suffixes: 'pdf',
          description: 'Portable Document Format',
          __pluginName: 'Chrome PDF Plugin'
        },
        {
          type: 'application/x-nacl',
          suffixes: '',
          description: 'Native Client Executable',
          enabledPlugin: Plugin,
          __pluginName: 'Native Client'
        },
        {
          type: 'application/x-pnacl',
          suffixes: '',
          description: 'Portable Native Client Executable',
          __pluginName: 'Native Client'
        }
      ],
      plugins: [
        {
          name: 'Chrome PDF Plugin',
          filename: 'internal-pdf-viewer',
          description: 'Portable Document Format'
        },
        {
          name: 'Chrome PDF Viewer',
          filename: 'mhjfbmdgcfjbbpaeojofohoefgiehjai',
          description: ''
        },
        {
          name: 'Native Client',
          filename: 'internal-nacl-plugin',
          description: ''
        }
      ],
      fns: {
        namedItem: instanceName => {
          // Returns the Plugin/MimeType with the specified name.
          const fn = function (name) {
            if (!arguments.length) {
              throw new TypeError(
                `Failed to execute 'namedItem' on '${instanceName}': 1 argument required, but only 0 present.`
              )
            }
            return this[name] || null
          }
          mockedFns.push({ ref: fn, name: 'namedItem' })
          return fn
        },
        item: instanceName => {
          // Returns the Plugin/MimeType at the specified index into the array.
          const fn = function (index) {
            if (!arguments.length) {
              throw new TypeError(
                `Failed to execute 'namedItem' on '${instanceName}': 1 argument required, but only 0 present.`
              )
            }
            return this[index] || null
          }
          mockedFns.push({ ref: fn, name: 'item' })
          return fn
        },
        refresh: instanceName => {
          // Refreshes all plugins on the current page, optionally reloading documents.
          const fn = function () {
            return undefined
          }
          mockedFns.push({ ref: fn, name: 'refresh' })
          return fn
        }
      }
    }
    // Poor mans _.pluck
    const getSubset = (keys, obj) =>
      keys.reduce((a, c) => ({ ...a, [c]: obj[c] }), {})

    function generateMimeTypeArray() {
      const arr = fakeData.mimeTypes
        .map(obj => getSubset(['type', 'suffixes', 'description'], obj))
        .map(obj => Object.setPrototypeOf(obj, MimeType.prototype))
      arr.forEach(obj => {
        arr[obj.type] = obj
      })

      // Mock functions
      arr.namedItem = fakeData.fns.namedItem('MimeTypeArray')
      arr.item = fakeData.fns.item('MimeTypeArray')

      return Object.setPrototypeOf(arr, MimeTypeArray.prototype)
    }

    const mimeTypeArray = generateMimeTypeArray()
    Object.defineProperty(navigator, 'mimeTypes', {
      get: () => mimeTypeArray
    })

    function generatePluginArray() {
      const arr = fakeData.plugins
        .map(obj => getSubset(['name', 'filename', 'description'], obj))
        .map(obj => {
          const mimes = fakeData.mimeTypes.filter(
            m => m.__pluginName === obj.name
          )
          // Add mimetypes
          mimes.forEach((mime, index) => {
            navigator.mimeTypes[mime.type].enabledPlugin = obj
            obj[mime.type] = navigator.mimeTypes[mime.type]
            obj[index] = navigator.mimeTypes[mime.type]
          })
          obj.length = mimes.length
          return obj
        })
        .map(obj => {
          // Mock functions
          obj.namedItem = fakeData.fns.namedItem('Plugin')
          obj.item = fakeData.fns.item('Plugin')
          return obj
        })
        .map(obj => Object.setPrototypeOf(obj, Plugin.prototype))
      arr.forEach(obj => {
        arr[obj.name] = obj
      })

      // Mock functions
      arr.namedItem = fakeData.fns.namedItem('PluginArray')
      arr.item = fakeData.fns.item('PluginArray')
      arr.refresh = fakeData.fns.refresh('PluginArray')

      return Object.setPrototypeOf(arr, PluginArray.prototype)
    }

    const pluginArray = generatePluginArray()
    Object.defineProperty(navigator, 'plugins', {
      get: () => pluginArray
    })

    // Make mockedFns toString() representation resemble a native function
    makeFnsNative(mockedFns)
  }
  try {
    const isPluginArray = navigator.plugins instanceof PluginArray
    const hasPlugins = isPluginArray && navigator.plugins.length > 0
    if (isPluginArray && hasPlugins) {
      return // nothing to do here
    }
    mockPluginsAndMimeTypes()
  } catch (err) { }
  //navigator.permissions
  const originalQuery = window.navigator.permissions.query;
  // eslint-disable-next-line
  window.navigator.permissions.__proto__.query = parameters =>
    parameters.name === 'notifications'
      ? Promise.resolve({ state: Notification.permission }) //eslint-disable-line
      : originalQuery(parameters)

  // Inspired by: https://github.com/ikarienator/phantomjs_hide_and_seek/blob/master/5.spoofFunctionBind.js
  const oldCall = Function.prototype.call
  function call() {
    return oldCall.apply(this, arguments)
  }
  // eslint-disable-next-line
  Function.prototype.call = call

  const nativeToStringFunctionString = Error.toString().replace(
    /Error/g,
    'toString'
  )
  const oldToString = Function.prototype.toString

  function functionToString() {
    if (this === window.navigator.permissions.query) {
      return 'function query() { [native code] }'
    }
    if (this === functionToString) {
      return nativeToStringFunctionString
    }
    return oldCall.call(oldToString, this)
  }
  // eslint-disable-next-line
  Function.prototype.toString = functionToString
  //webgl
  try {
    /* global WebGLRenderingContext */
    let canvas = document.createElement('canvas');
    let webgl = canvas.getContext('webgl');
    let getParameter = webgl.getParameter;
    WebGLRenderingContext.prototype.getParameter = function (parameter) {
      let ret;
      switch (parameter) {
        case WebGLRenderingContext.ALIASED_LINE_WIDTH_RANGE:
          ret = Float32Array.from([1, 1]);
          break;
        case WebGLRenderingContext.ALIASED_POINT_SIZE_RANGE:
          ret = Float32Array.from([1, 1024]);
          break;
        case WebGLRenderingContext.MAX_CUBE_MAP_TEXTURE_SIZE:
          ret = 16384;
          break;
        case WebGLRenderingContext.MAX_FRAGMENT_UNIFORM_VECTORS:
          ret = 1024;
          break;
        case WebGLRenderingContext.MAX_RENDERBUFFER_SIZE:
          ret = 16384;
          break;
        case WebGLRenderingContext.MAX_TEXTURE_SIZE:
          ret = 16384;
          break;
        case WebGLRenderingContext.MAX_VARYING_VECTORS:
          ret = 30;
          break;
        case WebGLRenderingContext.MAX_VERTEX_UNIFORM_VECTORS:
          ret = 4096;
          break;
        case WebGLRenderingContext.MAX_VIEWPORT_DIMS:
          ret = Int16Array.from([16384, 16384]);
          break;
        case WebGLRenderingContext.MAX_VERTEX_ATTRIBS:
          ret = 16;
          break;
        case 34047:
          ret = 16;
          break;
        case 37445:
          ret = 'Google Inc.';
          break;
        case 37446:
          ret = 'ANGLE (Intel(R) HD Graphics 630 Direct3D11 vs_5_0 ps_5_0)';
          break;
        default:
          ret = getParameter.call(this, parameter);
          break;
      }
      // console.log("getParameter " + parameter + "=" + ret);
      return ret;
    }
  } catch (err) { console.error(err) }
  //window.out...
  try {
    if (!window.outerWidth) {
      window.outerWidth = window.innerWidth;
    }
    if (!window.outerHeight) {
      window.outerHeight = window.innerHeight;
    }
    if (window.outerHeight == window.innerHeight) {
      window.innerHeight = window.outerHeight - 92;
    }
  } catch (err) {
    console.log(err);
  }
}

fake();