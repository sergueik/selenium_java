function $(id) {
    return document.getElementById(id);
}
function addClass(elem, name) {
    var addons = name.split(' ').filter(function(name) {
        return !hasClass(elem, name);
    }, this);

    if (addons.length)
        elem.className += ' ' + addons.join(' ');
}
function hasClass(elem, name) {
    var className = elem.className;
    if (name instanceof RegExp)
        return name.test(className);
    return (' ' + className + ' ').indexOf(' ' + name + ' ') >= 0;
}
function i18nReplace(id, messageKey) {
    if ($(id)) {
        return $(id).innerHTML = i18n[messageKey];
    }
    return null;
}
UploadUI.init();

var canvas = new Canvas();
var photoshop = {
    canvas: document.createElement("canvas"),
    img: null,
    tabTitle: '',
    url: '',
    query: '',
    rating: 0,
    startX: 0,
    startY: 0,
    endX: 0,
    endY: 0,
    dragFlag: false,
    flag: 'rectangle',
    layerId: 'layer0',
    canvasId: '',
    color: '#ff0000',
    highlightColor: '',
    lastValidAction: 0,
    markedArea: [],
    isDraw: true,
    offsetX: 0,
    offsetY: 36,
    nowHeight: 0,
    nowWidth: 0,
    highlightType: 'border',
    highlightMode: 'rectangle',
    text: '',
    timeoutIdClose: null,
    i18nReplace: i18nReplace,
    initCanvas: function() {
        $('canvas').addEventListener('selectstart', function() {
            return false;
        }, false);
        $('mask-canvas').addEventListener('selectstart', function() {
            return false;
        }, false);

        $('canvas').width = $('mask-canvas').width = $('photo').style.width = photoshop.canvas.width = photoshop.img.width;
        $('canvas').height = $('mask-canvas').height = $('photo').style.height = photoshop.canvas.height = photoshop.img.height;
        var context = photoshop.canvas.getContext('2d');
        context.drawImage(photoshop.img, 0, 0);
        context = $('canvas').getContext('2d');
        context.drawImage(photoshop.canvas, 0, 0);
        $('canvas').style.display = 'block';
    },
    init: function() {
        photoshop.img = $("pageCanvas");
        photoshop.initTools();
        photoshop.initCanvas();
        
        var showBoxHeight = function() {
            $('showBox').style.height = window.innerHeight - photoshop.offsetY - 1;
        };
        photoshop.url = $("url").value;
        photoshop.tabTitle = $("title").value;
        setTimeout(showBoxHeight, 50);
    },
    markCurrentElement: function(element) {
        if (element && element.parentNode) {
            var children = element.parentNode.children;
            for (var i = 0; i < children.length; i++) {
                var node = children[i];
                if (node == element) {
                    element.className = 'mark';
                } else {
                    node.className = '';
                }
            }
        }
    },
    setHighLightMode: function() {
        photoshop.highlightType = localStorage.highlightType || 'border';
        photoshop.color = localStorage.highlightColor || '#FF0000';
        $(photoshop.layerId).style.border = '2px solid ' + photoshop.color;
        if (photoshop.highlightType == 'rect') {
            $(photoshop.layerId).style.backgroundColor = photoshop.color;
            $(photoshop.layerId).style.opacity = 0.5;
        }
        if (photoshop.flag == 'rectangle') {
            $(photoshop.layerId).style.borderRadius = '0 0';
        } else if (photoshop.flag == 'radiusRectangle') {
            $(photoshop.layerId).style.borderRadius = '6px 6px';
        } else if (photoshop.flag == 'ellipse') {
            $(photoshop.layerId).style.border = '0';
            $(photoshop.layerId).style.backgroundColor = '';
            $(photoshop.layerId).style.opacity = 1;
        }

    },
    setBlackoutMode: function() {
        photoshop.color = '#000000';
        $(photoshop.layerId).style.opacity = 1;
        $(photoshop.layerId).style.backgroundColor = '#000000';
        $(photoshop.layerId).style.border = '2px solid #000000';
    },
    setTextMode: function() {
        localStorage.setItem('fontSize', localStorage.fontSize || '16');
        localStorage.setItem('fontColor', localStorage.fontColor || '#FF0000');
        photoshop.color = localStorage.fontColor;
        $(photoshop.layerId).setAttribute('contentEditable', true);
        $(photoshop.layerId).style.border = '1px dotted #333333';
        $(photoshop.layerId).style.cursor = 'text';
        $(photoshop.layerId).style.lineHeight = localStorage.fontSize + 'px';
        $(photoshop.layerId).style.fontSize = localStorage.fontSize + 'px';
        $(photoshop.layerId).style.color = photoshop.color;
        $(photoshop.layerId).innerHTML = '<br/>';
        $(photoshop.layerId).addEventListener('blur', function() {
            photoshop.setTextToArray(this.id);
        }, true);
        var layer = $(photoshop.layerId);
        layer.addEventListener('click', function() {
            this.style.border = '1px dotted #333333';
        }, true);
        layer.addEventListener('mouseout', function() {
            if (!photoshop.dragFlag) {
                this.style.borderWidth = 0;
            }
        }, false);
        layer.addEventListener('mousemove', function() {
            this.style.border = '1px dotted #333333';
        }, false);
    },
    setTextToArray: function(id) {
        var str = $(id).textContent.split("\n");
        if (photoshop.markedArea.length > 0) {
            for (var i = photoshop.markedArea.length - 1; i >= 0; i--) {
                if (photoshop.markedArea[i].id == id) {
                    photoshop.markedArea[i].context = str;
                    break;
                }
            }
            $(id).style.borderWidth = 0;
        }
    },
    openOptionPage: function() {},
    closeCurrentTab: function() {
         var mainWindow = window.QueryInterface(Components.interfaces.nsIInterfaceRequestor)
                       .getInterface(Components.interfaces.nsIWebNavigation)
                       .QueryInterface(Components.interfaces.nsIDocShellTreeItem)
                       .rootTreeItem
                       .QueryInterface(Components.interfaces.nsIInterfaceRequestor)
                       .getInterface(Components.interfaces.nsIDOMWindow);

        mainWindow.gBrowser.removeCurrentTab();
    },
    sendJira: function() {
        jira.showJiraDialog();
        jira.sendJira();
    },
    finish: function() {
        var context = $('canvas').getContext('2d');
        context.drawImage(photoshop.canvas, 0, 0);
    },
    colorRgba: function(color, opacity) {
        var sColor = color.toLowerCase();
        var sColorChange = [];
        for (var i = 1; i < sColor.length; i += 2) {
            sColorChange.push(parseInt("0x" + sColor.slice(i, i + 2)));
        }
        return "rgba(" + sColorChange.join(",") + "," + opacity + ")";
    },
    /**
     * Undo the current operation
     */
    toDo: function(element, what) {
        photoshop.flag = what;
        photoshop.isDraw = true;
        photoshop.markCurrentElement(element);
    },
    setDivStyle: function(x, y) {
        $(photoshop.layerId).setAttribute("style", "");
        $(photoshop.layerId).setAttribute("contentEditable", false);
        switch (photoshop.flag) {
            case 'rectangle':
            case 'radiusRectangle':
            case 'ellipse':
                photoshop.setHighLightMode();
                break;
            case 'redact':
                photoshop.setBlackoutMode();
                break;
            case 'text':
                photoshop.setTextMode();
                break;
            case 'line':
            case 'arrow':
                photoshop.drawLineOnMaskCanvas(x, y, x, y, 'lineStart',
                        photoshop.layerId);
                break;
            case 'blur':
                photoshop.createCanvas(photoshop.layerId);
                break;
        }
    },
    /**
     * Create a layer and set style
     */
    createDiv: function() {
        photoshop.lastValidAction++;
        photoshop.layerId = 'layer' + photoshop.lastValidAction;
        if ($(photoshop.layerId)) {
            photoshop.removeElement(photoshop.layerId);
        }
        var divElement = document.createElement('div');
        divElement.id = photoshop.layerId;
        divElement.className = 'layer';
        $('photo').appendChild(divElement);
        if (photoshop.flag == 'blur') {
            photoshop.createCanvas(photoshop.layerId);
        }
        return divElement;
    },
    createCanvas: function(parentId) {
        photoshop.canvasId = 'cav-' + parentId;
        if (!$(photoshop.canvasId)) {
            var cav = document.createElement('canvas');
            cav.id = photoshop.canvasId;
            cav.width = 10;
            cav.height = 10;
            $(photoshop.layerId).appendChild(cav);
            return cav;
        }
        return $(photoshop.canvasId);

    },
    createCloseButton: function(parent, id, left, top, flag) {
        var imgElement = document.createElement('img');
        imgElement.id = id;
        imgElement.src = 'chrome://screenshot/skin/img/cross.png';
        imgElement.className = 'closeButton';
        imgElement.style.left = left - 15 + 'px';
        if (photoshop.flag == 'line' || photoshop.flag == 'arrow') {
            imgElement.style.left = left / 2 - 5 + 'px';
            imgElement.style.top = top / 2 - 5 + 'px';
        }
        imgElement.onclick = function() {
            $(parent).style.display = 'none';
            photoshop.removeLayer(parent);
        };
        $(parent).onmousemove = function() {
            if (!photoshop.dragFlag) {
                photoshop.showCloseButton(id);
                $(parent).style.zIndex = 110;
                photoshop.isDraw = (flag == 'text' ? false : photoshop.isDraw);
            }
        };
        $(parent).onmouseout = function(e) {
            clearTimeout(this.timeoutIdClose);
        }.bind(this);
        
        $(parent).onmouseout = function(e) {
            this.timeoutIdClose = setTimeout(function() {
                photoshop.hideCloseButton(id);
                $(parent).style.zIndex = 100;
                photoshop.isDraw = true;  
            }, 100);
        }.bind(this);
        
        imgElement.onmouseover = function(e) {
            clearTimeout(this.timeoutIdClose);
        }.bind(this);
        
        imgElement.onmouseout = function(e) {
            this.timeoutIdClose = setTimeout(function() {
                photoshop.hideCloseButton(id);
                $(parent).style.zIndex = 100;
                photoshop.isDraw = true;
            }, 100);
        }.bind(this);
        
        $(parent).appendChild(imgElement);
        return imgElement;
    },
    showCloseButton: function(id) {
        $(id).style.display = 'block';
    },
    hideCloseButton: function(id) {
        $(id).style.display = 'none';
        photoshop.isDraw = true;
    },
    removeLayer: function(id) {
        for (var i = 0; i < photoshop.markedArea.length; i++) {
            if (photoshop.markedArea[i].id == id) {
                photoshop.markedArea.splice(i, 1);
                break;
            }
        }
        photoshop.removeElement(id);
    },
    /**
     *  Set the starting point(x,y) when mouse pressed
     */
    onMouseDown: function(event) {
        if (photoshop.isDraw && event.button != 2) {
            photoshop.startX = event.pageX + $('showBox').scrollLeft -
                    photoshop.offsetX;
            photoshop.startY = event.pageY + $('showBox').scrollTop -
                    photoshop.offsetY;
            photoshop.setDivStyle(photoshop.startX, photoshop.startY);
            photoshop.dragFlag = true;

            $(photoshop.layerId).style.left = photoshop.startX + 'px';
            $(photoshop.layerId).style.top = photoshop.startY + 'px';
            $(photoshop.layerId).style.height = 0;
            $(photoshop.layerId).style.width = 0;
            $(photoshop.layerId).style.display = 'block';
        }
    },
    onMouseUp: function(event) {
        $('mask-canvas').style.zIndex = 10;
        photoshop.endX = event.pageX + $('showBox').scrollLeft -
                photoshop.offsetX;
        if (photoshop.endX > photoshop.canvas.width) {
            photoshop.endX = photoshop.canvas.width;
        }

        if (photoshop.endX < 0) {
            photoshop.endX = 0;
        }

        photoshop.endY = event.pageY + $('showBox').scrollTop -
                photoshop.offsetY;
        if (photoshop.endY > photoshop.canvas.height) {
            photoshop.endY = photoshop.canvas.height;
        }
        if (photoshop.endY < 0) {
            photoshop.endY = 0;
        }
        if (photoshop.isDraw && photoshop.dragFlag && (photoshop.endX !=
                photoshop.startX || photoshop.endY != photoshop.startY)) {
            if (photoshop.flag == 'line' || photoshop.flag == 'arrow') {
                photoshop.drawLineOnMaskCanvas(photoshop.startX, photoshop.startY,
                        photoshop.endX, photoshop.endY, 'drawEnd', photoshop.layerId);
            } else if (photoshop.flag == 'blur') {
                canvas.blurImage(photoshop.canvas, $(photoshop.canvasId),
                        photoshop.layerId, photoshop.startX, photoshop.startY,
                        photoshop.endX, photoshop.endY);
            } else if (photoshop.flag == 'ellipse') {
                photoshop.drawEllipseOnMaskCanvas(photoshop.endX,
                        photoshop.endY, 'end', photoshop.layerId);
            }
            photoshop.markedArea.push({
                'id': photoshop.layerId,
                'startX': photoshop.startX,
                'startY': photoshop.startY,
                'endX': photoshop.endX,
                'endY': photoshop.endY,
                'width': photoshop.nowWidth,
                'height': photoshop.nowHeight,
                'flag': photoshop.flag,
                'highlightType': photoshop.highlightType,
                'fontSize': localStorage.fontSize,
                'color': photoshop.color,
                'context': ''
            });
            $(photoshop.layerId).focus();
            var imageBtnId = 'close_' + photoshop.layerId;
            photoshop.createCloseButton(photoshop.layerId, imageBtnId,
                    photoshop.nowWidth, photoshop.nowHeight, photoshop.flag);
            photoshop.createDiv();
        } else if (photoshop.endX ==
                photoshop.startX && photoshop.endY == photoshop.startY) {
            photoshop.removeElement(photoshop.layerId);
            photoshop.createDiv();
        }
        photoshop.dragFlag = false;

    },
    /**
     * Refresh div‘s height and width when the mouse move
     */
    onMouseMove: function(event) {
        if (photoshop.dragFlag) {
            $('mask-canvas').style.zIndex = 200;
            photoshop.endX = event.pageX + $('showBox').scrollLeft -
                    photoshop.offsetX;
            if (photoshop.endX > photoshop.canvas.width) {
                photoshop.endX = photoshop.canvas.width;
            }

            if (photoshop.endX < 0) {
                photoshop.endX = 0;
            }

            photoshop.endY = event.pageY + $('showBox').scrollTop -
                    photoshop.offsetY;
            if (photoshop.endY > photoshop.canvas.height) {
                photoshop.endY = photoshop.canvas.height;
            }
            if (photoshop.endY < 0) {
                photoshop.endY = 0;
            }
            photoshop.nowHeight = photoshop.endY - photoshop.startY - 1;
            photoshop.nowWidth = photoshop.endX - photoshop.startX - 1;

            if (photoshop.nowHeight < 0) {
                $(photoshop.layerId).style.top = photoshop.endY + 'px';
                photoshop.nowHeight = -1 * photoshop.nowHeight;
            }
            if (photoshop.nowWidth < 0) {
                $(photoshop.layerId).style.left = photoshop.endX + 'px';
                photoshop.nowWidth = -1 * photoshop.nowWidth;
            }

            $(photoshop.layerId).style.height = photoshop.nowHeight - 3;
            $(photoshop.layerId).style.width = photoshop.nowWidth - 3;
            if (photoshop.flag == 'line' || photoshop.flag == 'arrow') {
                photoshop.drawLineOnMaskCanvas(photoshop.startX, photoshop.startY,
                        photoshop.endX, photoshop.endY, 'lineDrawing', photoshop.layerId);
            } else if (photoshop.flag == 'blur') {
                $(photoshop.layerId).style.height = photoshop.nowHeight;
                $(photoshop.layerId).style.width = photoshop.nowWidth;
                canvas.blurImage(photoshop.canvas, $(photoshop.canvasId),
                        photoshop.layerId, photoshop.startX, photoshop.startY,
                        photoshop.endX, photoshop.endY);
            } else if (photoshop.flag == 'ellipse') {
                photoshop.drawEllipseOnMaskCanvas(photoshop.endX,
                        photoshop.endY, 'drawing', photoshop.layerId);
            }
        }

    },
    /**
     * Remove a div
     */
    removeElement: function(id) {
        if ($(id)) {
            $(id).parentNode.removeChild($(id));
        }
    },
    /**
     * Use fillStyle, fillText and fillRect functions to draw rectangles,
     * and render to canvas
     */
    draw: function() {
        var context = $('canvas').getContext('2d');
        for (var j = 0; j < photoshop.markedArea.length; j++) {
            var mark = photoshop.markedArea[j];

            var x = (mark.startX < mark.endX) ? mark.startX : mark.endX;
            var y = (mark.startY < mark.endY) ? mark.startY : mark.endY;
            var width = mark.width;
            var height = mark.height;
            var color = mark.color;
            switch (mark.flag) {
                case 'rectangle':
                    if (mark.highlightType == 'border') {
                        canvas.drawStrokeRect(context, color, x, y, width, height, 2);
                    } else {
                        var color = changeColorToRgba(color, 0.5);
                        canvas.drawFillRect(context, color, x, y, width, height);
                    }
                    break;
                case 'radiusRectangle':
                    canvas.drawRoundedRect(
                            context, color, x, y, width, height, 6, mark.highlightType);
                    break;
                case 'ellipse':
                    x = (mark.startX + mark.endX) / 2;
                    y = (mark.startY + mark.endY) / 2;
                    var xAxis = Math.abs(mark.endX - mark.startX) / 2;
                    var yAxis = Math.abs(mark.endY - mark.startY) / 2;
                    canvas.drawEllipse(
                            context, color, x, y, xAxis, yAxis, 3, mark.highlightType);
                    break;
                case 'redact':
                    canvas.drawFillRect(context, color, x, y, width, height);
                    break;
                case 'text':
                    for (var i = 0; i < mark.context.length; i++) {
                        canvas.setText(context, mark.context[i], color, mark.fontSize, 'arial', mark.fontSize, x, y + mark.fontSize * i, width);
                    }
                    break;
                case 'blur':
                    var imageData = context.getImageData(
                            x, y, photoshop.markedArea[j].width,
                            photoshop.markedArea[j].height);
                    imageData = canvas.boxBlur(
                            imageData, photoshop.markedArea[j].width,
                            photoshop.markedArea[j].height, 10);
                    context.putImageData(
                            imageData, x, y, 0, 0, photoshop.markedArea[j].width,
                            photoshop.markedArea[j].height);
                    break;
                case 'line':
                    canvas.drawLine(
                            context, color, 'round', 2,
                            mark.startX, mark.startY, mark.endX, mark.endY);
                    break;
                case 'arrow':
                    canvas.drawArrow(
                            context, color, 2, 4, 10, 'round',
                            mark.startX, mark.startY, mark.endX, mark.endY);
                    break;
            }
        }
    },
    save: function() {
        photoshop.draw();
        var formatParam = localStorage.screenshootQuality || 'png';
        var dataUrl = $('canvas').toDataURL('image/png');
        var fileName = photoshop.tabTitle + '.' + formatParam;
        
        photoshop.download(dataUrl, fileName);
        
        photoshop.finish();
    },
    download: function(dataUrl, fileName) {
        var a = document.createElement('a');
        a.setAttribute('download', fileName);
        a.href = dataUrl;
        a.innerHTML = fileName;
        a.style.display = 'none';
        document.body.appendChild(a);
        a.click();
    },
    copy: function() {
        photoshop.draw();
        var formatParam = localStorage.screenshootQuality || 'png';
        var dataUrl = $('canvas').toDataURL('image/png');
        var copyFlag = bg.plugin.saveToClipboard(dataUrl);
        var messageKey = 'tip_copy_failed';
        var messageClass = 'tip_failed';
        if (copyFlag) {
            messageKey = 'tip_copy_succeed';
            messageClass = 'tip_succeed';
        }
        var message = "Проверка"//chrome.i18n.getMessage(messageKey);
        photoshop.showTip(messageClass, message);
        photoshop.finish();
    },
    printImage: function() {
        photoshop.draw();
        var formatParam = localStorage.screenshootQuality || 'png';
        var dataUrl = $('canvas').toDataURL('image/png');
        var width = $('canvas').width;
        var height = $('canvas').height;
    },
    drawLineOnMaskCanvas: function(startX, startY, endX, endY, type, layerId) {
        var ctx = $('mask-canvas').getContext('2d');
        ctx.clearRect(0, 0, $('mask-canvas').width, $('mask-canvas').height);
        if (type == 'drawEnd') {
            var offset = 20;
            var width = Math.abs(endX - photoshop.startX) > 0 ? Math.abs(endX - photoshop.startX) : 0;
            var height = Math.abs(endY - photoshop.startY) > 0 ? Math.abs(endY - photoshop.startY) : 0;
            var offsetLeft = parseInt($(layerId).style.left);
            var offsetTop = parseInt($(layerId).style.top);
            startX = startX - offsetLeft + offset / 2;
            startY = startY - offsetTop + offset / 2;
            endX = endX - offsetLeft + offset / 2;
            endY = endY - offsetTop + offset / 2;
            $(layerId).style.left = offsetLeft - offset / 2;
            $(layerId).style.top = offsetTop - offset / 2;
            var cavCopy = photoshop.createCanvas(layerId);
            cavCopy.width = width + offset;
            cavCopy.height = height + offset;
            ctx = cavCopy.getContext('2d');
        }
        if (localStorage.lineType == 'line') {
            canvas.drawLine(ctx, localStorage.lineColor, 'round', 2, startX, startY, endX, endY);
        } else {
            canvas.drawArrow(ctx, localStorage.lineColor, 2, 4, 10, 'round', startX, startY, endX, endY);
        }

    },
    createColorPadStr: function(type) {
        var colorList = ['#000000', '#0036ff', '#008000', '#dacb23', '#d56400',
            '#c70000', '#be00b3', '#1e2188', '#0090ff', '#22cc01', '#ffff00',
            '#ff9600', '#ff0000', '#ff008e', '#7072c3', '#49d2ff', '#9dff3d',
            '#ffffff', '#ffbb59', '#ff6b6b', '#ff6bbd'];
        var colorPadStr = '';
        colorPadStr += '<div id="colorpad">';
        for (var i = 0; i < colorList.length; i++) {
            var color = colorList[i];
            var specialStyle = '';
            if (color == '#ffffff') {
                specialStyle = 'border: 1px solid #444; width: 12px; height: 12px;';
            }
            colorList[i] = '<a title="' + color +
                    '" style="background:' +
                    color + ';' + specialStyle + '"></a>';
        }

        colorPadStr = colorPadStr + colorList.join("\n") + '</div>';
        return colorPadStr;
    },
    addEventColorPadStr: function(elem, type) {
        for (var i = 0, len = elem.length; i < len; i++) {
            elem[i].addEventListener('click', function() {
                photoshop.colorPadPick(this.title, type);
            }, false);
        }
    },
    colorPadPick: function(color, type) {
        photoshop.color = color;
        if (type == 'highlight') {
            localStorage.setItem('highlightColor', color);
            photoshop.setHighlightColorBoxStyle(color);
        } else if (type == 'text') {
            localStorage.setItem('fontColor', color);
            $('fontColorBox').style.backgroundColor = color;
        } else if (type == 'line') {
            localStorage.setItem('lineColor', color);
            photoshop.setLineColorBoxStyle();
        } else if (type == 'ellipse') {
            $('ellipseBox').style.borderColor = color;
        }
    },
    setHighlightColorBoxStyle: function(color) {
        var highlightColorBox = $('highlightColorBox');
        highlightColorBox.style.borderColor = color;
        localStorage.setItem('highlightType', localStorage.highlightType || 'border');
        if (localStorage.highlightType == 'border') {
            highlightColorBox.style.background = '#ffffff';
            highlightColorBox.style.opacity = 1;
            $('borderMode').className = 'mark';
            $('rectMode').className = '';
        } else if (localStorage.highlightType == 'rect') {
            highlightColorBox.style.background = color;
            highlightColorBox.style.opacity = 0.5;
            $('borderMode').className = '';
            $('rectMode').className = 'mark';
        }
        if (photoshop.flag == 'rectangle') {
            highlightColorBox.style.borderRadius = '0 0';
        } else if (photoshop.flag == 'radiusRectangle') {
            highlightColorBox.style.borderRadius = '3px 3px';
        } else if (photoshop.flag == 'ellipse') {
            highlightColorBox.style.borderRadius = '12px 12px';
        }
        photoshop.markCurrentElement($(photoshop.flag));
    },
    setBlackoutColorBoxStyle: function() {
        localStorage.setItem('blackoutType', localStorage.blackoutType || 'redact');
        if (localStorage.blackoutType == 'redact') {
            $('blackoutBox').className = 'rectBox';
            $('redact').className = 'mark';
            $('blur').className = '';
        } else if (localStorage.blackoutType == 'blur') {
            $('blackoutBox').className = 'blurBox';
            $('redact').className = '';
            $('blur').className = 'mark';
        }
    },
    setFontSize: function(size) {
        var id = 'size_' + size;
        localStorage.setItem('fontSize', size);
        $('size_10').className = '';
        $('size_16').className = '';
        $('size_18').className = '';
        $('size_32').className = '';
        if ($(id)) {
            $(id).className = 'mark';
        }
    },
    setLineColorBoxStyle: function() {
        localStorage.setItem('lineType', localStorage.lineType || 'line');
        localStorage.setItem('lineColor', localStorage.lineColor || '#FF0000');
        photoshop.color = localStorage.lineColor;
        var ctx = $('lineIconCav').getContext('2d');
        ctx.clearRect(0, 0, 14, 14);
        if (localStorage.lineType == 'line') {
            $('straightLine').className = 'mark';
            $('arrow').className = '';
            canvas.drawLine(ctx, photoshop.color, 'round', 2, 1, 13, 13, 1);
        } else if (localStorage.lineType == 'arrow') {
            $('straightLine').className = '';
            $('arrow').className = 'mark';
            canvas.drawArrow(ctx, photoshop.color, 2, 4, 7, 'round', 1, 13, 13, 1);
        }

    },
    initTools: function() {
        photoshop.i18nReplace('tHighlight', 'highlight');
        photoshop.i18nReplace('tRedact', 'redact');
        photoshop.i18nReplace('redactText', 'solid_black');
        photoshop.i18nReplace('tText', 'text');
        photoshop.i18nReplace('tCopy', 'copy');
        photoshop.i18nReplace('tSave', 'save');
        photoshop.i18nReplace('tUpload', 'share');
        photoshop.i18nReplace('tPrint', 'print');
        photoshop.i18nReplace('tClose', 'close');
        photoshop.i18nReplace('border', 'border');
        photoshop.i18nReplace('rect', 'rect');
        photoshop.i18nReplace('blurText', 'blur');
        photoshop.i18nReplace('lineText', 'line');
        photoshop.i18nReplace('size_10', 'size_small');
        photoshop.i18nReplace('size_16', 'size_normal');
        photoshop.i18nReplace('size_18', 'size_large');
        photoshop.i18nReplace('size_32', 'size_huge');
        localStorage.setItem('fontSize', localStorage.fontSize || 16);
        var fontSize = localStorage.fontSize;
        if (fontSize != 10 && fontSize != 16 && fontSize != 18 && fontSize != 32) {
            localStorage.setItem('fontSize', 16);
        }
        photoshop.flag = localStorage.highlightMode || 'rectangle';
        localStorage.setItem('highlightMode', photoshop.flag);
        localStorage.setItem('highlightColor', localStorage.highlightColor || '#FF0000');
        localStorage.setItem('fontColor', localStorage.fontColor || '#FF0000');
        photoshop.highlightType = localStorage.highlightType || 'border';
        localStorage.setItem('highlightType', photoshop.highlightType);
        localStorage.setItem('blackoutType', localStorage.blackoutType || 'redact');
        localStorage.setItem('lineType', localStorage.lineType || 'line');
        localStorage.setItem('lineColor', localStorage.lineColor || '#FF0000');
        photoshop.setHighlightColorBoxStyle(localStorage.highlightColor);
        $('fontColorBox').style.backgroundColor = localStorage.fontColor || '#FF0000';
        $('btnHighlight').addEventListener('click', function() {
            photoshop.toDo(this, localStorage.highlightMode);
            photoshop.setHighlightColorBoxStyle(localStorage.highlightColor);
        }, false);

        $('btnBlackout').addEventListener('click', function() {
            photoshop.toDo(this, localStorage.blackoutType);
            photoshop.setBlackoutColorBoxStyle();
        }, false);

        $('btnText').addEventListener('click', function() {
            photoshop.toDo(this, 'text');
        }, false);

        $('btnLine').addEventListener('click', function() {
            photoshop.toDo(this, localStorage.lineType);
            photoshop.setLineColorBoxStyle();
        }, false);

        photoshop.setHighlightColorBoxStyle(localStorage.highlightColor);
        $('borderMode').addEventListener('click', function() {
            localStorage.setItem('highlightType', 'border');
        }, false);
        $('rectMode').addEventListener('click', function() {
            localStorage.setItem('highlightType', 'rect');
        }, false);
        $('rectangle').addEventListener('click', function() {
            localStorage.setItem('highlightMode', photoshop.flag = 'rectangle');
            photoshop.markCurrentElement(this);
        }, false);
        $('radiusRectangle').addEventListener('click', function() {
            localStorage.setItem('highlightMode', photoshop.flag = 'radiusRectangle');
            photoshop.markCurrentElement(this);
        }, false);
        $('ellipse').addEventListener('click', function() {
            localStorage.setItem('highlightMode', photoshop.flag = 'ellipse');
            photoshop.markCurrentElement(this);
        }, false);
        photoshop.setBlackoutColorBoxStyle();
        $('redact').addEventListener('click', function() {
            localStorage.setItem('blackoutType', 'redact');
        }, false);
        $('blur').addEventListener('click', function() {
            localStorage.setItem('blackoutType', 'blur');
        }, false);

        photoshop.setLineColorBoxStyle();

        $('highlightColorPad').innerHTML = photoshop.createColorPadStr('highlight');
        $('fontColorPad').innerHTML = photoshop.createColorPadStr('text');
        $('lineColorPad').innerHTML = photoshop.createColorPadStr('line');

        photoshop.addEventColorPadStr(document.querySelectorAll('#highlightColorPad a'), 'highlight');
        photoshop.addEventColorPadStr(document.querySelectorAll('#fontColorPad a'), 'text');
        photoshop.addEventColorPadStr(document.querySelectorAll('#lineColorPad a'), 'line');


        $('straightLine').addEventListener('click', function() {
            localStorage.setItem('lineType', 'line');
            photoshop.setLineColorBoxStyle();
        }, false);
        $('arrow').addEventListener('click', function() {
            localStorage.setItem('lineType', 'arrow');
            photoshop.setLineColorBoxStyle();
        }, false);

        photoshop.setFontSize(localStorage.fontSize);
        $('size_10').addEventListener('click', function() {
            photoshop.setFontSize(10);
        }, false);
        $('size_16').addEventListener('click', function() {
            photoshop.setFontSize(16);
        }, false);
        $('size_18').addEventListener('click', function() {
            photoshop.setFontSize(18);
        }, false);
        $('size_32').addEventListener('click', function() {
            photoshop.setFontSize(32);
        }, false);
    },
    drawEllipseOnMaskCanvas: function(endX, endY, type, layerId) {
        var ctx = $('mask-canvas').getContext('2d');
        ctx.clearRect(0, 0, $('mask-canvas').width, $('mask-canvas').height);
        var x = (photoshop.startX + endX) / 2;
        var y = (photoshop.startY + endY) / 2;
        var xAxis = Math.abs(endX - photoshop.startX) / 2;
        var yAxis = Math.abs(endY - photoshop.startY) / 2;
        canvas.drawEllipse(ctx, photoshop.color, x, y, xAxis, yAxis, 3, photoshop.highlightType);
        if (type == 'end') {
            var offsetLeft = parseInt($(layerId).style.left);
            var offsetTop = parseInt($(layerId).style.top);
            var startX = photoshop.startX - offsetLeft;
            var startY = photoshop.startY - offsetTop;
            var newEndX = photoshop.endX - offsetLeft;
            var newEndY = photoshop.endY - offsetTop;
            x = (startX + newEndX) / 2;
            y = (startY + newEndY) / 2;
            xAxis = Math.abs(newEndX - startX) / 2;
            yAxis = Math.abs(newEndY - startY) / 2;
            var cavCopy = photoshop.createCanvas(layerId);
            cavCopy.width = Math.abs(endX - photoshop.startX);
            cavCopy.height = Math.abs(endY - photoshop.startY);
            var ctxCopy = cavCopy.getContext('2d');
            canvas.drawEllipse(ctxCopy, photoshop.color, x, y,
                    xAxis, yAxis, 3, photoshop.highlightType);
            ctx.clearRect(0, 0, $('mask-canvas').width, $('mask-canvas').height);
        }
    },
    showTip: function(className, message, delay) {
        delay = delay || 2000;
        var div = document.createElement('div');
        div.className = className;
        div.innerHTML = message;
        document.body.appendChild(div);
        div.style.left = (document.body.clientWidth - div.clientWidth) / 2 + 'px';
        window.setTimeout(function() {
            document.body.removeChild(div);
        }, delay);
    }
};

$('photo').addEventListener('mousemove', photoshop.onMouseMove, true);
$('photo').addEventListener('mousedown', photoshop.onMouseDown, true);
$('photo').addEventListener('mouseup', photoshop.onMouseUp, true);
document.addEventListener('mouseup', photoshop.onMouseUp, true);
document.addEventListener('mousemove', photoshop.onMouseMove, true);

$('btnSave').addEventListener('click', photoshop.save, true);
$('btnClose').addEventListener('click', photoshop.closeCurrentTab, true);
