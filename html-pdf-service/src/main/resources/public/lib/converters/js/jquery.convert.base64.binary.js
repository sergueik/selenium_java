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

//http://stackoverflow.com/questions/16245767/creating-a-blob-from-a-base64-string-in-javascript
//Original Author: Jeremy Banks
//Modifications made by Farrukh Mirza
//Made this a JQuery plugin
//Converts a base64 String to byteArray to be consumed in a Blob.
//E.g., 
//var binaryArray = $.base64ToBinary({data: "Base64 Encoded String"});
//var blob = new Blob(byteArrays, {type: "application/pdf"});

(function($) {

	$.base64ToBinary = function(opts) {
		if (opts && opts.data) {
			var sliceSize = opts.sliceSize || 512;
			var b64Data = opts.data;

			var byteCharacters = atob(b64Data);
			var byteArrays = [];

			for (var offset = 0; offset < byteCharacters.length; offset += sliceSize) {
				var slice = byteCharacters.slice(offset, offset + sliceSize);

				var byteNumbers = new Array(slice.length);
				for (var i = 0; i < slice.length; i++) {
					byteNumbers[i] = slice.charCodeAt(i);
				}

				var byteArray = new Uint8Array(byteNumbers);

				byteArrays.push(byteArray);
			}

			return byteArrays;
			
		} else {
			console.error("data must be provided, e.g., {data:\"Base64 encoded string.\"}")
		}

	};

}(jQuery));
