var selectedToPdf, selectedFromPdf;
var selectingInput;

var isConvertingFromPdf = true;
var isConverting = false;

var activatingStep = 2;

var convertResult;

function onSelectFileButtonClick() {
    if (checkElDisabled()) {
        return;
    }

    let el = event.target;
    let source = el.closest("[data-source-for]").getAttribute("data-source-for");

    document.querySelector("input[data-source-for='" + source + "']")
        .click();
}

function onFileSelected(ev) {
    var txt = ev.target;
    var file = txt.files[0];

    if (!file) {
        return;
    }

    var selectingFor = isConvertingFromPdf ? "selectedFromPdf" : "selectedToPdf";
    window[selectingFor] = selectingInput = {
        file: file,
        name: file.name,
        size: getSize(file.size).text,
    };

    txt.value = "";
    convertResult = null;
    setUI();
}

async function onConvertButtonClick() {
    if (checkElDisabled()) {
        return;
    }

    try {
        isConverting = true;
        setUI();

        let apiServer = getApiServer();
        let url = apiServer;

        let target = isConvertingFromPdf ?
            document.getElementById("cbo-target").value :
            "pdf";

        let convertResponse = null;
        if (selectingInput.file) {
            url += "api/convert/file";

            let body = new FormData();
            body.append("Target", target);
            body.append("File", selectingInput.file);

            convertResponse = await fetch(url, {
                method: "POST",
                body: body,
            });
        }

        if (!convertResponse.ok) {
            throw await convertResponse.text();
        }

        var response = await convertResponse.json();

        if (response.downloadUrl.startsWith("/")) {
            response.downloadUrl = response.downloadUrl.substr(1);
        }

        convertResult = {
            id: response.id,
            url: apiServer + response.downloadUrl,
        };

        isConverting = false;
        setUI();
    } catch (e) {
        isConverting = false;
        setUI();

        showError(e);
    }
}

function switchInput(isFromPdf) {
    isConvertingFromPdf = isFromPdf;

    selectingInput = isFromPdf ? selectedFromPdf : selectedToPdf;

    setUI();
}

function activateStep(stepNo) {
    activatingStep = stepNo;

    document.querySelectorAll("[data-step]").forEach(el => {
        var step = Number(el.getAttribute("data-step"));

        el.classList.remove("disabled");

        if (step == stepNo) {
            el.classList.add("active");
        } else {
            el.classList.remove("active");

            if (step > stepNo) {
                el.classList.add("disabled");
            }
        }
    });
}

function onSaveToPCButtonClick() {
    var a = document.createElement("a");
    a.href = convertResult.url;
    a.target = "_blank";

    a.click();
}

function setUI() {
    let explanationPanel = document.getElementById("extension-explanation");
    if (isConvertingFromPdf) {
        toggleVisibility(document.getElementById("panel-convert-from-pdf"));

        let targetExtension = document.getElementById("cbo-target").value;
        toggleVisibility(explanationPanel.querySelector("[data-extension='" + targetExtension + "']"));
    } else {
        toggleVisibility(document.getElementById("panel-convert-from-word"));
        toggleVisibility(explanationPanel.querySelector("[data-extension='to-pdf']"));
    }

    if (selectingInput) {
        if (convertResult) {
            activateStep(4);
        } else {
            var panel = document.querySelector("div[data-source-for]:not(.d-none)");

            var lblSelectedFile = panel.querySelector("#lbl-selected-file");
            var content = "";

            content += `${selectingInput.name} (${selectingInput.size})`;

            lblSelectedFile.innerHTML = content;

            activateStep(3);
        }
    } else {
        activateStep(2);
    }

    var panelControls = document.getElementById("panel-controls");
    var lblConverting = document.getElementById("lbl-converting");
    var btnConvert = document.getElementById("btn-convert");
    if (isConverting) {
        panelControls.classList.add("disabled");
        lblConverting.classList.remove("invisible");
    } else {
        lblConverting.classList.add("invisible");
        panelControls.classList.remove("disabled");
    }
}

function addAppEventListeners() {
    document.addEventListener("change", function (ev) {
        let target = ev.target;

        if (target.matches("[name='opt-input']")) {
            let value = target.value;

            switch (value) {
                case "word":
                    switchInput(false);
                    break;
                case "pdf":
                    switchInput(true);
                    break;
            }
        }
    });

    document.getElementById("cbo-target").addEventListener("change", setUI);

    document.querySelectorAll("input[data-source-for]").forEach(el =>
        el.addEventListener("change", onFileSelected));
}

function showError(message, title) {
    title = title || "Error";

    swal(title.toString(), message.toString(), "error");
}

function getApiServer() {
    return document.querySelector("[data-api-server]").getAttribute("data-api-server");
}

function setUIFromRoute() {
    let paths = location.pathname.split('/');
    let lastPath = paths[paths.length - 1];

    if (lastPath.includes("to-pdf")) {
        document.querySelector("[name='opt-input'][value='word']").checked = false;
        isConvertingFromPdf = false;
    } else {
        document.querySelector("[name='opt-input'][value='pdf']").checked = true;
        isConvertingFromPdf = true;

        let options = document.getElementById("cbo-target")
            .querySelectorAll("option");

        for (let option of options) {
            let value = option.value;
            if (lastPath.includes(value)) {
                option.selected = true;
                break;
            }
        }
    }
}

(function () {
    setUIFromRoute();

    addAppEventListeners();

    setUI();
})();