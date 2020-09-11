function elementScroll(x, y) {
    this.scroll({
        left: x,
        top: y,
        behavior: 'instant'
    });
    return {
        scrollX: this.scrollX,
        scrollY: this.scrollY
    };
}