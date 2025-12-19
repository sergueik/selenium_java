#!/usr/bin/env bash

# Clean up old build
rm -rf build/



# Build chrome zip file
mkdir -p build/chrome

cp -R external/ build/chrome
cp -R viewer/ build/chrome
cp -R icons/ build/chrome
cp LICENSE build/chrome
cp manifest.json build/chrome
cp README.md build/chrome
cp rtfRedirectHandler.js build/chrome
cp background.js build/chrome

cd build/chrome
zip -rq ../rtf-viewer-chrome.zip *

cd ../..



# Build firefox zip file
mkdir -p build/firefox

cp -R external/ build/firefox
cp -R viewer/ build/firefox
cp -R icons/ build/firefox
cp LICENSE build/firefox
cp manifest-firefox.json build/firefox/manifest.json
cp README.md build/firefox
cp rtfRedirectHandler.js build/firefox
cp background.js build/firefox

cd build/firefox
zip -rq ../rtf-viewer-firefox.zip *

cd ../..