/**
 * Originally from: https://ghostinspector.com/blog/simulate-drag-and-drop-javascript-casperjs/,
 * https://github.com/kemokid/scripting-sortable/blob/master/script_sortable_dnd_more_general.js.
 * Modified by IgorSasovets (https://github.com/IgorSasovets) to simulate drag and drop action
 * in Firefox browser (v.57.0.4).
 */


function dragAndDrop(selectorDrag, selectorDrop, options) {

    let elemDrag, elemDrop;

    /*******************************GET ELEMENTS********************************/

    if ((!selectorDrag && selectorDrag != null) || (!selectorDrop && selectorDrop != null)) {
        throw new Error('Error!Element selector not defined.');
    }

    if (options.dragElemIndex != undefined) {
        const elements = document.querySelectorAll(selectorDrag);
        elemDrag = elements[options.dragElemIndex];
    } else {
        elemDrag = document.querySelector(selectorDrag);
    }

    elemDrop = (selectorDrop === null && options.dropLocation) ? null : document.querySelector(selectorDrop);

    /***************************************************************************/

    /*************************CHECK USER CUSTOM OPTIONS*************************/

    if (options.makeDraggable)
        makeDraggable(elemDrag);

    /***************************************************************************/

    //function for make element draggable
    function makeDraggable(elm) {
        $(elm).draggable();
    }

    if (elemDrag === undefined || elemDrag === null) {
        throw new Error('Error!Cannot get element with specified selector.');
    }

    // function for triggering mouse events
    function fireMouseEvent(type, elem, dataTransfer) {
        const evt = document.createEvent('MouseEvents');
        evt.initMouseEvent(type, true, true, window, 1, 1, 1, 0, 0, false, false, false, false, 0, elem);
        if (/^dr/i.test(type)) {
            evt.dataTransfer = dataTransfer || createNewDataTransfer();
        }
        (elem === null) ? document.body.dispatchEvent(evt): elem.dispatchEvent(evt);
    }

    function createNewDataTransfer() {
        let data = {};
        return {
            clearData: function(key) {
                if (key === undefined) {
                    data = {};
                } else {
                    delete data[key];
                }
            },
            getData: function(key) {
                return data[key];
            },
            setData: function(key, value) {
                data[key] = value;
            },
            setDragImage: function() {},
            dropEffect: 'none',
            files: [],
            items: [],
            types: [],
            // also effectAllowed
        };
    }

    function mouseMove() {
        const evt = document.createEvent('MouseEvents');
        if (elemDrop === null) {
            evt.initMouseEvent('mousemove', true, true, window, 1, 1, 1, options.dropLocation.x,
                options.dropLocation.y, false, false, false, false, 0, null);
        } else {
            evt.initMouseEvent('mousemove', true, true, window, 1, 1, 1, 0, 0, false, false, false,
                false, 0, elemDrop);
        }
        if (/^dr/i.test('mousemove')) {
            evt.dataTransfer = dataTransfer || createNewDataTransfer();
        }
        (elemDrop === null) ? document.body.dispatchEvent(evt): elemDrop.dispatchEvent(evt);
    }

    function drop() {
        if (elemDrop === null) {
            const evt = document.createEvent('MouseEvents');
            evt.initMouseEvent('mouseup', true, true, window, 1, options.dropLocation.x, options.dropLocation.y,
                options.dropLocation.x, options.dropLocation.y, false, false, false, false, 0, null);
            document.body.dispatchEvent(evt);
        } else {
            fireMouseEvent('mouseup', elemDrop);
        }

    }

    fireMouseEvent('mousedown', elemDrag);
    mouseMove();
    drop();

    return true;
};

dragAndDrop(arguments[0], arguments[1], arguments[2]);