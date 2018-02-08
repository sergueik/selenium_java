function mouseMove(tgtPoint) {
    const evt = document.createEvent('MouseEvents');
    evt.initMouseEvent('mousemove', true, true, window, 1, tgtPoint.x, tgtPoint.y, tgtPoint.x,
        tgtPoint.y, false, false, false, false, 0, null);

    if (/^dr/i.test('mousemove')) {
        evt.dataTransfer = dataTransfer || createNewDataTransfer();
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

    document.body.dispatchEvent(evt);
}

mouseMove(arguments[0]);
