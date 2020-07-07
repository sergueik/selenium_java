function waitSelector(selector){

    function hasVisibleBoundingBox(element) {
        const rect = element.getBoundingClientRect();
        return !!(rect.top || rect.bottom || rect.width || rect.height);
    }

    function predicate(node) {
        if (!node) {
            return false;
        }
        const element = node.nodeType === Node.TEXT_NODE ? node.parentElement : node;
        const style = window.getComputedStyle(element);
        const isVisible = style && style.visibility !== 'hidden' && hasVisibleBoundingBox(element);
        return isVisible;
    }
    const node = document.querySelector(selector);
    if (!predicate(node)) {
        return;
    }
    return node;
}