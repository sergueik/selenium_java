var SIZE_UNITS = ["B", "KB", "MB", "GB", "TB"];

function getScript(source) {
    return new Promise(function (resolve) {
        let script = document.createElement('script');
        script.async = 1;

        script.onload = script.onreadystatechange = function (_, isAbort) {
            if (isAbort || !script.readyState || /loaded|complete/.test(script.readyState)) {
                script.onload = script.onreadystatechange = null;
                script = undefined;

                if (!isAbort) {
                    resolve();
                }
            }
        };

        script.src = source;
        document.body.appendChild(script);
    });
}

String.prototype.formatUnicorn = String.prototype.formatUnicorn ||
    function () {
        "use strict";
        var str = this.toString();
        if (arguments.length) {
            var t = typeof arguments[0];
            var key;
            var args = ("string" === t || "number" === t) ?
                Array.prototype.slice.call(arguments)
                : arguments[0];

            for (key in args) {
                str = str.replace(new RegExp("\\{" + key + "\\}", "gi"), args[key]);
            }
        }

        return str;
    };

function loadTextFromBlob(blob) {
    return new Promise((resolve, reject) => {
        try {
            let reader = new FileReader();

            reader.onload = () => {
                resolve(reader.result);
            };

            reader.readAsText(blob);
        } catch (e) {
            reject(e);
        }
    });
}

function loadArrayFromBlob(blob) {
    return new Promise((resolve, reject) => {
        try {
            let reader = new FileReader();

            reader.onload = () => {
                resolve(reader.result);
            };

            reader.readAsArrayBuffer(blob);
        } catch (e) {
            reject(e);
        }
    });
}

function downloadFile(fileName, blob) {
    var a = document.createElement("a");
    a.download = fileName;
    a.href = URL.createObjectURL(blob);
    a.target = "_blank";

    a.click();
}

function openUrl(url, filename) {
    var a = document.createElement("a");

    a.href = url;
    a.target = "_blank";

    if (filename) {
        a.download = filename;
    }

    a.click();
}

function getSize(inByte) {
    var size = Number(inByte);
    var unit = SIZE_UNITS[0];

    for (var i = 1; i < SIZE_UNITS.length; i++) {
        if (size < 1024) {
            break;
        }

        unit = SIZE_UNITS[i];
        size = size / 1024;
    }

    size = Math.floor(size * 10) / 10;

    return {
        size: size,
        unit: unit,
        text: `${size} ${unit}`,
    };
}

function toggleVisibility(el) {
    var parent = el.parentElement;

    parent.childNodes.forEach(c => {
        if (c.classList) {
            c.classList.add("d-none");
        }
    });

    el.classList.remove("d-none");
}

function checkElDisabled(btn) {
    btn = btn || event.target;

    return btn.closest(".disabled");
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

function fromTemplate(html) {
    let template = document.createElement("template");
    template.innerHTML = html;
    return template.content.firstElementChild;
}

function addDelegate(element, eventName, cssMatch, callback) {
    element.addEventListener(eventName, function (e) {
        for (let target = e.target; target && target != this; target = target.parentNode) {
            if (target.matches(cssMatch)) {
                callback(e, target);

                break;
            }
        }
    });
}

function findAndGetAttribute(attr) {
    return document.querySelector(`[${attr}]`).getAttribute(attr);
}

(function () {
    document.querySelectorAll("[data-click]").forEach(el => {
        var funcName = el.getAttribute("data-click");

        el.addEventListener("click", (e) => {
            e.preventDefault();

            var func = window[funcName];

            if (typeof (func) == "function") {
                func(e);
            } else {
                console.warn("No function: " + funcName);
            }
        });
    });
})();