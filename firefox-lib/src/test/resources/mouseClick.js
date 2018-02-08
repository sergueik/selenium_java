function mouseLeftClick(options) {
    if (!options.selector && !options.point) {
        throw new Error('Error!Element selector and/or click point not defined!');
    }

    if (options.point) {
        const mouseDown = document.createEvent('MouseEvents');
        mouseDown.initMouseEvent('mousedown', true, true, window, 1, options.point.x, options.point.y, options.point.x,
            options.point.y, false, false, false, false, 0, null);
        document.body.dispatchEvent(mouseDown);
        const mouseUp = document.createEvent('MouseEvents');
        mouseUp.initMouseEvent('mouseup', true, true, window, 1, options.point.x, options.point.y, options.point.x,
            options.point.y, false, false, false, false, 0, null);
        document.body.dispatchEvent(mouseUp);
    } else {
        let element;
        if (options.elementIndex != undefined) {
            const elementsArray = document.querySelectorAll(options.selector);
            element = elementsArray[options.elementIndex];
        } else {
            element = document.querySelector(options.selector);
        }
        element.click();
    }
}

mouseLeftClick(arguments[0], arguments[1], arguments[2]);