function  Screen(self) {
    this._self = self;
    this._doc = self.doc;
    this.pageHeight = 0;
    this.pageWidth = 0;
    this.startX = 0;
    this.startY = 0;
    this.endX = 0;
    this.endY = 0;
    
    this.isMouseDown = false;
    this.resizing = false;
    this.dragging = false;
    this.moving = false;
    this.moveX = 0;
    this.moveY = 0;
}
Screen.prototype.captureArea = function () {
    localStorage.setItem('lastAction', 'area');
    var t = $('browser');
    t.insertAdjacentHTML("afterBegin", '<box id="wrapperScreen" class="wrapperScreen">'
            +'  <box id="sc_drag_shadow_top" style="height: 167px; width: 766px;"></box>'
            +'  <box id="sc_drag_shadow_bottom" style="height: 130px; width: 765px;"></box>'
            +'  <box id="sc_drag_shadow_left" style="height: 281px; width: 515px;"></box>'
            +'  <box id="sc_drag_shadow_right" style="height: 318px; width: 514px;"></box>'
            +'  <box id="sc_drag_area" style="left: 515px; top: 167px; width: 250px; height: 150px;">'
            +'      <box id="sc_drag_container"></box>'
            +'      <box id="sc_drag_size" style="top: -18px;">0 x 0</box>'
            +'      <box id="sc_drag_cancel" style="bottom: -25px;">Отмена</box>'
            +'      <box id="sc_drag_crop" style="bottom: -25px;">OK</box>'
            +'      <box id="sc_drag_north_west"></box>'
            +'      <box id="sc_drag_north_east"></box>'
            +'      <box id="sc_drag_south_east"></box>'
            +'      <box id="sc_drag_south_west"></box>'
            +'  </box>'
            +'</box>'
            );
    
    var areaProtector = $('wrapperScreen');
    
    var cancel = $('sc_drag_cancel');
    cancel.addEventListener('mousedown', function () {
        this.removeSelectionArea();
    }.bind(this), true);
    
    var crop = $('sc_drag_crop');
    crop.addEventListener('mousedown', function () {
        var x = this.startX,
            y = this.startY,
            w = this.endX - this.startX,
            h = this.endY - this.startY;
        
        this.removeSelectionArea();
        
        var screenBase64 = this.full(this._doc.defaultView, x, y, w, h);
        this.send(screenBase64);
    }.bind(this), true);
    
    this.onMouseDown = this.onMouseDown.bind(this);
    this.onMouseMove = this.onMouseMove.bind(this);
    this.onMouseUp = this.onMouseUp.bind(this);
    
    areaProtector.addEventListener('mousedown', this.onMouseDown, false);
    this._doc.addEventListener('mousemove', this.onMouseMove, false);
    this._doc.addEventListener('mouseup', this.onMouseUp, false);
    $('sc_drag_container').addEventListener('dblclick', function() {
        var x = this.startX,
            y = this.startY,
            w = this.endX - this.startX,
            h = this.endY - this.startY;
        
        this.removeSelectionArea();
        
        var screenBase64 = this.full(this._doc.defaultView, x, y, w, h);
        this.send(screenBase64);
    }.bind(this), false);
    
    this.pageHeight = areaProtector.clientHeight;
    this.pageWidth = areaProtector.clientWidth;

    var areaElement = $('sc_drag_area');
    areaElement.style.left = this.getElementLeft(areaElement) + 'px';
    areaElement.style.top = this.getElementTop(areaElement) + 'px';
    
    this.startX = this.getElementLeft(areaElement);
    this.startY = this.getElementTop(areaElement); 
    this.endX = this.getElementLeft(areaElement) + 250;
    this.endY = this.getElementTop(areaElement) + 150;
    
    areaElement.style.width = '250px';
    areaElement.style.height = '150px';
    
    this.updateShadow(areaElement);
    this.updateSize();
    
    this.onresize = this.onresize.bind(this);
    this._self.win.addEventListener('resize', this.onresize);
};
Screen.prototype.captureWindow = function () {
    localStorage.setItem('lastAction', 'window');
    var br = $('browser').getBoundingClientRect();
    var screenBase64 = this.full(this._doc.defaultView, 0, br.top);
    this.send(screenBase64);
};
Screen.prototype.captureWebpage = function () {
    localStorage.setItem('lastAction', 'webpage');
    var br = this.body().getBoundingClientRect();
    var height = br.height == this.body().scrollHeight ? br.height : this.body().scrollHeight;
    var screenWin = gBrowser.contentDocument.defaultView;
    var screenBase64 = this.full(screenWin, 0, 0, br.width, height);
    this.send(screenBase64);
};
Screen.prototype.lastAction = function() {
    var action = localStorage.lastAction;
    if (action === 'area') {
        this.captureArea();
    } else if (action === 'window') {
        this.captureWindow();
    } else if (action === 'webpage') {
        this.captureWebpage();
    } else {
        this.captureWindow();
    }
};
Screen.prototype.send = function (screenBase64) {
    var url = this._doc.location.href;
    var title = this._doc.title;
    var tabBrowser = this._self.tabs.addTab("chrome://screenshot/content/showimage.html", true);
    
    tabBrowser.addEventListener("load", function () {
        tabBrowser.contentDocument.body.insertAdjacentHTML('beforeEnd', '<img src="'+screenBase64+'" id="pageCanvas" style="display:none" onload="photoshop.init();" />');
        tabBrowser.contentDocument.body.insertAdjacentHTML('beforeEnd', '<input type="hidden" name="url" id="url" value="'+url+'"/>');
        tabBrowser.contentDocument.body.insertAdjacentHTML('beforeEnd', '<input type="hidden" name="title" id="title" value="'+title+'"/>');
    }, true);
};

Screen.prototype.full = function (screenWin, startX, startY, width, height) {
    startX = startX || 0;
    startY = startY || 0;
    width = width || screenWin.innerWidth - startX;
    height = height || screenWin.innerHeight - startY;
    
    var canvas = this._doc.createElementNS("http://www.w3.org/1999/xhtml", "canvas");
    var context = canvas.getContext('2d');
    var dataUrl = null;
    
    canvas.setAttribute("id", "screenRuCanvas");
    canvas.style.display = 'none';
    canvas.style.overflow = 'visible';
    canvas.width = width;
    canvas.height = height;

    context.drawWindow(screenWin, startX, startY, width, height, "rgba(100,0,0,0)");
    dataUrl = canvas.toDataURL("image/png");
    
    return dataUrl;
};

Screen.prototype.updateShadow = function(areaElement) {
    var offset = 1;
    $('sc_drag_shadow_top').style.height = parseInt(areaElement.style.top) + 'px';
    $('sc_drag_shadow_top').style.width = (parseInt(areaElement.style.left) + parseInt(areaElement.style.width) + offset) + 'px';
    $('sc_drag_shadow_left').style.height = (this.pageHeight - parseInt(areaElement.style.top)) + 'px';
    $('sc_drag_shadow_left').style.width = (parseInt(areaElement.style.left)+ offset) + 'px';

    var height = (parseInt(areaElement.style.top) + parseInt(areaElement.style.height) + offset);
    height = (height < 0) ? 0 : height;
    var width = (this.pageWidth) - offset - (parseInt(areaElement.style.left) + parseInt(areaElement.style.width));
    width = (width < 0) ? 0 : width;
    $('sc_drag_shadow_right').style.height = (height - offset) + 'px';
    $('sc_drag_shadow_right').style.width =  width + 'px';

    height = (this.pageHeight - offset - (parseInt(areaElement.style.top) + parseInt(areaElement.style.height)));
    height = (height < 0) ? 0 : height;
    width = (this.pageWidth) - parseInt(areaElement.style.left);
    width = (width < 0) ? 0 : width;
    $('sc_drag_shadow_bottom').style.height = (height + offset) + 'px';
    $('sc_drag_shadow_bottom').style.width = (width - offset) + 'px';
};
Screen.prototype.updateSize = function() {
    var width = Math.abs(this.endX - this.startX);
    var height = Math.abs(this.endY - this.startY);
    $('sc_drag_size').innerHTML = width + ' x ' + height;
};
Screen.prototype.removeSelectionArea = function () {
    this._doc.removeEventListener('mousedown', this.onMouseDown, false);
    this._doc.removeEventListener('mousemove', this.onMouseMove, false);
    this._doc.removeEventListener('mouseup', this.onMouseUp, false);
    this.removeElement('wrapperScreen');
};

Screen.prototype.onresize = function (ev) {
    var areaProtector = $('wrapperScreen');
    var areaElement = $('sc_drag_area');
    
    this.pageHeight = areaProtector.clientHeight;
    this.pageWidth = areaProtector.clientWidth;
    this.updateShadow(areaElement);
};
Screen.prototype.onMouseDown = function (ev) {
    if (ev.button != 2) {
        var element = ev.target;
        
        if (element) {
            var elementName = element.tagName;
            if (elementName && this._doc) {
                this.isMouseDown = true;

                var areaElement = $('sc_drag_area');
                var xPosition = ev.pageX;
                var yPosition = ev.pageY;

                if (areaElement) {
                    if (element == $('sc_drag_container')) {
                        this.moving = true;
                        this.moveX = xPosition - this.getSize(areaElement).left;
                        this.moveY = yPosition - this.getSize(areaElement).top;
                    } else if (element == $('sc_drag_north_east')) {
                        this.resizing = true;
                        this.startX = this.getSize(areaElement).left;
                        this.startY = this.getSize(areaElement).top + areaElement.clientHeight;
                    } else if (element == $('sc_drag_north_west')) {
                        this.resizing = true;
                        this.startX = this.getSize(areaElement).left + areaElement.clientWidth;
                        this.startY = this.getSize(areaElement).top + areaElement.clientHeight;
                    } else if (element == $('sc_drag_south_east')) {
                        this.resizing = true;
                        this.startX = this.getSize(areaElement).left;
                        this.startY = this.getSize(areaElement).top;
                    } else if (element == $('sc_drag_south_west')) {
                        this.resizing = true;
                        this.startX = this.getSize(areaElement).left + areaElement.clientWidth;
                        this.startY = this.getSize(areaElement).top;
                    } else {
                        this.dragging = true;
                        this.endX = 0;
                        this.endY = 0;
                        this.endX = this.startX = xPosition;
                        this.endY = this.startY = yPosition;
                    }
                }
                ev.preventDefault();
            }
        }
    }
};
Screen.prototype.onMouseMove = function (ev) {
    var element = ev.target;
    if (element && this.isMouseDown) {
      var areaElement = $('sc_drag_area');
      if (areaElement) {
        var xPosition = ev.pageX;
        var yPosition = ev.pageY;
        if (this.dragging || this.resizing) {
            var width = 0;
            var height = 0;
            //var zoom = page.getZoomLevel();
            var viewWidth = Math.round(this._doc.width / 1);
            var viewHeight = Math.round(this._doc.height / 1);
            if (xPosition > viewWidth) {
                xPosition = viewWidth;
            } else if (xPosition < 0) {
                xPosition = 0;
            }
            if (yPosition > viewHeight) {
                yPosition = viewHeight;
            } else if (yPosition < 0) {
                yPosition = 0;
            }
            this.endX = xPosition;
            this.endY = yPosition;
            if (this.startX > this.endX) {
                width = this.startX - this.endX;
                areaElement.style.left = xPosition + 'px';
            } else {
                width = this.endX - this.startX;
                areaElement.style.left = this.startX + 'px';
            }
            if (this.startY > this.endY) {
                height = this.startY - this.endY;
                areaElement.style.top = this.endY + 'px';
            } else {
                height = this.endY - this.startY;
                areaElement.style.top = this.startY + 'px';
            }
            areaElement.style.height = height + 'px';
            areaElement.style.width  = width + 'px';
            if (this._self.win.innerWidth < xPosition) {
                this.body().scrollLeft = xPosition - this._self.win.innerWidth;
            }
            if (this.body().scrollTop + this._self.win.innerHeight < yPosition + 25) {
                this.body().scrollTop = yPosition - this._self.win.innerHeight + 25;
            }
            if (yPosition < this.body().scrollTop) {
                this.body().scrollTop -= 25;
            }
        } else if (this.moving) {
            var newXPosition = xPosition - this.moveX;
            var newYPosition = yPosition - this.moveY;
            if (newXPosition < 0) {
                newXPosition = 0;
            } else if (newXPosition + areaElement.clientWidth > this.pageWidth) {
                newXPosition = this.pageWidth - areaElement.clientWidth;
            }
            if (newYPosition < 0) {
                newYPosition = 0;
            } else if (newYPosition + areaElement.clientHeight > this.pageHeight) {
              newYPosition = this.pageHeight - areaElement.clientHeight;
            }
            areaElement.style.left = newXPosition + 'px';
            areaElement.style.top = newYPosition + 'px';
            this.endX = newXPosition + areaElement.clientWidth;
            this.startX = newXPosition;
            this.endY = newYPosition + areaElement.clientHeight;
            this.startY = newYPosition;
        }
        var crop = this._doc.getElementById('sc_drag_crop');
        var cancel = this._doc.getElementById('sc_drag_cancel');
        if (ev.pageY + 25 > this._doc.height) {
            crop.style.bottom = 0;
            cancel.style.bottom = 0;
        } else {
            crop.style.bottom = '-25px';
            cancel.style.bottom = '-25px';
        }

        var dragSizeContainer = this._doc.getElementById('sc_drag_size');
        if (ev.pageY < 18) {
            dragSizeContainer.style.top = 0;
        } else {
            dragSizeContainer.style.top = '-18px';
        }
        this.updateShadow(areaElement);
        this.updateSize();

      }
    }
};
Screen.prototype.onMouseUp = function (ev) {
    this.isMouseDown = false;
    if (ev.button != 2) {
        this.resizing = false;
        this.dragging = false;
        this.moving = false;
        this.moveX = 0;
        this.moveY = 0;
        var temp;
        if (this.endX < this.startX) {
            temp = this.endX;
            this.endX = this.startX;
            this.startX = temp;
        }
        if (this.endY < this.startY) {
            temp = this.endY;
            this.endY = this.startY;
            this.startY = temp;
        }
    }
};

Screen.prototype.removeElement = function (elemId) {
    var element = $(elemId);
    if (element) {
        element.parentNode.removeChild(element);
    }
};
Screen.prototype.body = function () {
    return gBrowser.contentDocument.body;
};
Screen.prototype.getSize = function (elem) {
    return elem.getBoundingClientRect();
};


Screen.prototype.getElementLeft = function(obj) {
    return (this.body().scrollLeft + (this._doc.documentElement.clientWidth - obj.clientWidth) / 2);
};
  
Screen.prototype.getElementTop = function(obj) {
    return (this.body().scrollTop + (this._doc.documentElement.clientHeight - 200 - obj.clientHeight) / 2);
};