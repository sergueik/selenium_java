// shorter version, not functional
function simulate(f, c, d, e) {
    var b, a = null;
    for (b in eventMatchers)
        if (eventMatchers[b].test(c)) {
            a = b;
            break
        }
    if (!a) return !1;
    document.createEvent ? (b = document.createEvent(a), a == 'HTMLEvents' ? b.initEvent(c, !0, !0) : b.initMouseEvent(c, !0, !0, document.defaultView, 0, d, e, d, e, !1, !1, !1, !1, 0, null), f.dispatchEvent(b)) : (a = document.createEventObject(), a.detail = 0, a.screenX = d, a.screenY = e, a.clientX = d, a.clientY = e, a.ctrlKey = !1, a.altKey = !1, a.shiftKey = !1, a.metaKey = !1, a.button = 1, f.fireEvent('on' + c, a));
    return !0
}
var eventMatchers = {
    HTMLEvents: /^(?:load|unload|abort|error|select|change|submit|reset|focus|blur|resize|scroll)$/,
    MouseEvents: /^(?:click|dblclick|mouse(?:down|up|over|move|out))$/
};
simulate(arguments[0], 'mousedown', 0, 0);
simulate(arguments[0], 'mousemove', arguments[1], arguments[2]);
simulate(arguments[0], 'mouseup', arguments[1], arguments[2]);