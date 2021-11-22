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

//http://stackoverflow.com/questions/16086162/handle-file-download-from-ajax-post
//Original Idea Author: Jonathan Amend
//Modifications made by Farrukh Mirza
//Made this a JQuery plugin
//This plugin only redirects binary data in a download window.
//No Ajax happens in this, as the assumption is that the Ajax happens elsewhere.

(function($) {


	$.showFileDownload = function(opts){
		if(opts && opts.file){
	        var filename = "document.pdf";
	        if(opts.fileName){
	        	filename = opts.fileName; 
	        	var filenameRegex = /[^;=\n]*=((['"]).*?\2|[^;\n]*)/;
	            var matches = filenameRegex.exec(filename);
	            if (matches != null && matches[1]) filename = matches[1].replace(/['"]/g, '');
	            filename = filename.replace(/\s|\./g,'_');
	            var lastIndexOf_=filename.lastIndexOf("_");
	            filename = filename.substring(0,lastIndexOf_) + filename.substring(lastIndexOf_).replace("_", ".");
	        }
	        var type = "application/pdf";
	        if(opts.contentType){
	        	type = opts.contentType;
	        }

	        var blob = new Blob($.isArray(opts.file)? opts.file: [opts.file], { type: type });
	        if (typeof window.navigator.msSaveBlob !== 'undefined') {
	            // IE workaround for "HTML7007: One or more blob URLs were revoked by closing the blob for which they were created. These URLs will no longer resolve as the data backing the URL has been freed."
	            window.navigator.msSaveBlob(blob, filename);
	            setTimeout(function () { if(opts.done){opts.done();}}, 100); // cleanup
	        } else {
	            var URL = window.URL || window.webkitURL;
	            var downloadUrl = URL.createObjectURL(blob);

	            if (filename) {
	                // use HTML5 a[download] attribute to specify filename
	                var a = document.createElement("a");
	                // safari doesn't support this yet
	                if (typeof a.download === 'undefined') {
	                    window.location = downloadUrl;
	                } else {
	                    a.href = downloadUrl;
	                    a.download = filename;
	                    document.body.appendChild(a);
	                    a.click();
	                }
	            } else {
	                window.location = downloadUrl;
	            }

	            setTimeout(function () { URL.revokeObjectURL(downloadUrl); if(opts.done){opts.done();}}, 100); // cleanup
	        }
			
		} else {
			console.error("Options unavailable. Options Must have file as a byte array, e.g., {file:...}. All options: {fileName:\"SomeFile.PDF\", contentType: \"application/pdf\", file: ...}")
		}
    		
		
	};

}(jQuery));


