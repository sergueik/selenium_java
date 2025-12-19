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

String.prototype.toElement = function () {
    const template = document.createElement("template");
    template.innerHTML = this;
    return template.content.firstElementChild;
}

String.prototype.loc = function () {
    return chrome.i18n.getMessage(this) || this;
}

Node.prototype.createElement = function (name) {
    const el = document.createElement(name);
    this.appendChild(el);
    return el;
}

HTMLElement.prototype.appendScript = function (source, isModule) {
    return new Promise(resolve => {
        let script = document.createElement('script');
        script.async = 1;
        if (isModule) {
            script.type = "module";
        }

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
        this.appendChild(script);
    });
}

HTMLElement.prototype.addDelegate = function (eventName, cssMatch, callback) {
    this.addEventListener(eventName, function (e) {
        for (let target = e.target; target && target != this; target = target.parentNode) {
            if (target.matches(cssMatch)) {
                callback(e, target);

                break;
            }
        }
    });
};

HTMLElement.prototype.findClosestAttr = function (attr) {
    let el = this.closest(`[${attr}]`);

    if (el) {
        return el.getAttribute(attr);
    }

    return null;
}

HTMLElement.prototype.isDisabled = function () {
    return this.closest(".disabled") || this.closest("[disabled]");
};

HTMLElement.prototype.exclusiveDisplay = function () {
    var parent = this.parentElement;

    parent.childNodes.forEach(c => {
        if (c.classList) {
            c.classList.add("d-none");
        }
    });

    this.classList.remove("d-none");
};

HTMLElement.prototype.exclusiveVisibility = function () {
    var parent = this.parentElement;

    parent.childNodes.forEach(c => {
        if (c.classList) {
            c.classList.add("invisible");
        }
    });

    this.classList.remove("invisible");
};

HTMLElement.prototype.setDisabled = function (disabled) {
    if (disabled) {
        this.setAttribute("disabled", "");
        this.classList.add("disabled");
    } else {
        this.removeAttribute("disabled");
        this.classList.remove("disabled");
    }
};

HTMLElement.prototype.loc = function () {
    this.querySelectorAll("[data-loc]:not([data-loc-done])").forEach(el => {
        const key = el.getAttribute("data-loc");
        el.innerHTML = key.loc();
        el.setAttribute("data-loc-done", "");
    });
}