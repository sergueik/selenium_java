### Info
this directory contains source of the __RTF Viewer__  __Chrome Extension__. This plugin does not have other external depencies than [rtf.js](https://github.com/tbluemel/rtf.js) for rendering. The `rtf.js` is loaded from the 'js' folder (version to be identified).

### Update
1. install the extension
`chrome-extension://mjbmfbhblkemncpbmeepkccpjfakamkd/rtf-viewer.html` in Chrome compatible browser

2. Copy the extenion into workspace
```sh
cp -R ~/AppData/Local/Vivaldi/User\ Data/Default/Extensions/mjbmfbhblkemncpbmeepkccpjfakamkd/* /c/developer/sergueik/selenium_java/rtf-file-viewer
```
this will create version-specific folder structure:
```text
..\RTF-FILE-VIEWER\3.0.0_0
├───css
├───js
├───notify
├───webfonts
├───_locales
│   ├───ar
│   ├───bg
│   ├───ca
│   ├───cs
│   ├───da
│   ├───de
│   ├───el
│   ├───en
│   ├───en_US
...
│   ├───uk
│   ├───vi
│   ├───zh_CN
│   └───zh_TW
└───_metadata
```

