chrome.action.onClicked.addListener(() => {
    chrome.tabs.create({
        url: "/rtf-viewer.html",
    });
});

