class PopupPage {

    initialize() {
        this.setLocTexts();
    }

    setLocTexts() {
        document.querySelectorAll("[data-loc]").forEach(el => {
            const key = el.getAttribute("data-loc");
            el.innerHTML = key.loc();
        });
    }

}

new PopupPage().initialize();