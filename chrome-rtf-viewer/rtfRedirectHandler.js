'use strict';

function getViewerURL(fileUrl) {
    return chrome.extension.getURL('viewer/viewer.html') + '?file=' + encodeURIComponent(fileUrl);
}

function getHeader(details, headerName) {
    return details.responseHeaders.find(function(element){
        if(element.name.toLowerCase() === headerName){
            return element;
        }
        return false;
    });
}

function contentTypeContains(contentType, string){
    if(contentType.value.indexOf(string) > -1){
        return true;
    }
    return false;
}

function urlIndicatesRtf(url){
    if(url.indexOf('?') > -1){
        url = url.substring(0, url.indexOf('?'));
    }
    return url.toLowerCase().endsWith('.rtf');
}

function contentDispositionIndicatesRtf(contentDisposition){
    if (contentDisposition.value.toLowerCase().startsWith('attachment')) {
        var filenameRegex = /filename[^;=\n]*=((['"]).*?\2|[^;\n]*)/;
        var filename = filenameRegex.exec(contentDisposition.value)[1];
        if (filename) {
            return filename.toLowerCase().endsWith('.rtf');
        }
    }
    return false;
}

function isRtfFile(details) {
    var contentType = getHeader(details, 'content-type');

    var contentDisposition = getHeader(details, 'content-disposition');

    var isRtf = false;

    if (contentType) {
        isRtf = contentTypeContains(contentType, 'application/rtf') ||
            contentTypeContains(contentType, 'text/rtf') ||
            contentTypeContains(contentType, 'text/richtext') ||
            urlIndicatesRtf(details.url);
    }

    if (contentDisposition) {
        isRtf = isRtf || contentDispositionIndicatesRtf(contentDisposition);
    }

    return isRtf;
}

chrome.webRequest.onHeadersReceived.addListener(
    function(details) {
        if (!isRtfFile(details)) {
            return;
        }
        return { redirectUrl: getViewerURL(details.url) };
    },
    {
        urls: ['<all_urls>'],
        types: ['main_frame', 'sub_frame']
    },
    ['blocking','responseHeaders']
);

chrome.webRequest.onBeforeRequest.addListener(
    function (details) {
        return { redirectUrl: getViewerURL(details.url) };
    },
    {
        urls: ['ftp://*/*.rtf', 'file://*/*.RTF'],
        types: ['main_frame', 'sub_frame']
    },
    ['blocking']
);

chrome.webRequest.onBeforeRequest.addListener(
    function (details) {
        return { redirectUrl: getViewerURL(details.url) };
    },
    {
        urls: ['file://*/*.rtf', 'file://*/*.RTF'],
        types: ['main_frame', 'sub_frame']
    },
    ['blocking']
);