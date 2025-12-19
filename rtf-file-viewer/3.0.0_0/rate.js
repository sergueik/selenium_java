const updateUrl = chrome.runtime.getManifest().update_url?.toLowerCase();
const id = chrome.runtime.id;

const storeUrl = (updateUrl && updateUrl.includes("microsoft")) ?
    `https://microsoftedge.microsoft.com/addons/detail/` + id :
    `https://chrome.google.com/webstore/detail/${id}/reviews`;

document.querySelectorAll(".teaser").forEach(el =>
    el.setAttribute("href", storeUrl));