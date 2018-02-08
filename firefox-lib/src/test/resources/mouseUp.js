function mouseUp(point) {
    const evt = document.createEvent('MouseEvents');
    if (point !== undefined) {
        evt.initMouseEvent('mouseup', true, true, window, 1, point.x, point.y, point.x, point.y, false, false, false,
            false, 0, null);
    } else {
        evt.initMouseEvent('mouseup', true, true, window, 1, 1, 1, 1, 1, false, false, false,
            false, 0, null);
    }
    document.body.dispatchEvent(evt);
}

mouseUp(arguments[0]);
