/*
 * {{{ header & license
 * Copyright (c) 2016 Farrukh Mirza
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public License
 * as published by the Free Software Foundation; either version 3
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA 02111-1307, USA.
 * }}}
 */


//Idea borrowed from http://stackoverflow.com/questions/16086162/handle-file-download-from-ajax-post
//Original Author: Pavle Predic
//Modifications made by Farrukh Mirza
//Made this a JQuery plugin
//Usage: $.htmlAjaxFileDownload(url, requestObject); or $.htmlAjaxFileDownload(url, requestObject, 'GET');

//default enctype="application/x-www-form-urlencoded"
//enctype="application/json" is browser dependent and may not be available on all browsers.

(function($) {
	$.htmlAjaxFileDownload = function(url, reqObj, method) {
//		var form = $('<form method="POST" target="_blank" action="' + url + '">');
		var form = $('<form method="'+(method?method:'POST')+'" target="_blank" action="' + url + '">');
		
		$.each(reqObj, function(k, v) {
	        form.append($('<input type="hidden" name="' + k +'" value="' + v + '">'));
	    });
		
		$('body').append(form);
	    form.submit();
	};

}(jQuery));

//(function($) {
//	$.htmlAjaxJsonFileDownload = function(url, reqObj, method) {
//		var form = $('<form method="'+(method?method:'POST')+'" target="_blank" action="' + url + '" accept-charset="UTF-8" enctype="application/json">');
//		
//		$.each(reqObj, function(k, v) {
//	        form.append($('<input type="hidden" name="' + k +'" value="' + v + '">'));
//	    });
//		
//		$('body').append(form);
//	    form.submit();
//	};
//
//}(jQuery));

