var driveManager;
var loading = false;

function activateStep(stepNo) {
    activatingStep = stepNo;

    document.querySelectorAll("[data-step]").forEach(el => {
        let step = Number(el.getAttribute("data-step"));

        el.classList.remove("disabled");
        el.querySelectorAll("input,textarea").forEach(subEl => subEl.removeAttribute("disabled"));

        if (step == stepNo) {
            el.classList.add("active");
        } else {
            el.classList.remove("active");

            if (step > stepNo) {
                el.classList.add("disabled");
                el.querySelectorAll("input,textarea").forEach(subEl => subEl.setAttribute("disabled", ""));
            }
        }
    });
}

function showErrorIgnoreClosePopup(e) {
    if (e == "popup_closed_by_user" || e.action == "cancel") {
        return;
    }

    showError(e);
}

function showError(message, title) {
    title = title || getLanguageText("Error");

    swal(title.toString(), message.toString(), "error");
}

function showSuccessful(title) {
    swal(title.toString(), "", "success");
}

function setDriveUI() {
    let isDriveReady = driveManager.state.ready;

    document.querySelectorAll("[require-drive]").forEach(el => {
        if (isDriveReady) {
            el.classList.remove("disabled");
        } else {
            el.classList.add("disabled");
        }
    });
}

function setLoadingUI() {
    let board = document.querySelector(".board");
    let loadingPanel = document.getElementById("lbl-converting");

    if (loading) {
        if (board) {
            board.classList.add("disabled");
        }

        if (loadingPanel) {
            loadingPanel.classList.remove("invisible");
        }
    } else {
        if (board) {
            board.classList.remove("disabled");
        }
        

        if (loadingPanel) {
            loadingPanel.classList.add("invisible");
        }
    }

    
    
}

async function initDrive() {
    driveManager = new DriveManager();
    await driveManager.initDrive();

    let data = await driveManager.checkInitialDriveState();
    if (data) {
        let openingFileId = data.state.ids[0];
        let fileInfo = await driveManager.getFileInfo(openingFileId, "id,mimeType,name,size");

        return fileInfo;
    }
}

function getApiServer() {
    return findAndGetAttribute("data-api-server");
}

function getSelectedFileLabel() {
    var result = `${selectingInput.name} (${getSize(selectingInput.size).text})`;

    if (selectingInput.drive) {
        result = "<i class='fab fa-google-drive'></i> " + result;
    }

    return result;
}

function setLoading(value) {
    loading = value;
    setUI();
}

async function fetchUpload(url, files, additionalURLParams) {
    if (additionalURLParams) {
        let params = new URLSearchParams();

        for (var key in additionalURLParams) {
            params.append(key, additionalURLParams[key]);
        }

        url += "?" + params.toString();
    }

    let body = new FormData();

    if (Array.isArray(files)) {
        for (var file of files) {
            body.append("Files", file);
        }
    } else {
        body.append("File", files);
    }

    return await fetch(url, {
        method: "POST",
        body: body,
    })
        .then(response => response.json());
}

async function fetchDrive(url, driveFiles, additionalURLParams) {
    if (additionalURLParams) {
        let params = new URLSearchParams();

        for (var key in additionalURLParams) {
            params.append(key, additionalURLParams[key]);
        }

        url += "?" + params.toString();
    }

    let token = await driveManager.loginDrive(true);

    let body = {};
    if (Array.isArray(driveFiles)) {
        body.Files = driveFiles;
    } else {
        body.File = driveFiles;
    }

    body.OAuthToken = token;
    body = JSON.stringify(body);

    return await fetch(url, {
        method: "POST",
        body: body,
        headers: {
            "Content-Type": "application/json",
        }
    })
        .then(response => response.json());
}

(function () {
    addDelegate(document, "change", "[data-on-change]", function (e, target) {
        let funcName = target.getAttribute("data-on-change");
        let func = window[funcName];

        if (!func) {
            console.warn("No function: " + funcName);
            return;
        }

        func(e, target);
    });
})();