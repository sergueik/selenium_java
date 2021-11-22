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

//Inspired from http://bootsnipp.com/snippets/featured/sexy-radio-butons
//Modifications made by Farrukh Mirza
//Made this a JQuery plugin
//Sets the hidden input tag inside the main div with the value in the first <a> tag
//Updates the hidden input tag when any <a> tag is clicked.
//						<div class="input-group">
//							<div id="divId" class="radioBtn btn-group">
//								<a class="btn btn-primary btn-sm active" data-toggle="inputTagId" data-title="Y">YES</a> 
//								<a class="btn btn-primary btn-sm notActive" data-toggle="inputTagId" data-title="M">MAYBE</a>
//								<a class="btn btn-primary btn-sm notActive" data-toggle="inputTagId" data-title="N">NO</a>
//							</div>
//							<input type="hidden" name="inputTagId" id="inputTagId" />
//						</div>
//Initialization: $('#divId').radioButton() or $('#divId').radioButton(function(){})
//Fetch Value: $('#inputTagId').val();
//Example: 
//		$('#divId').radioButton(function(){
//			alert("Radio option: " + $('#inputTagId').val());
//		});

(function($) {

	$.fn.radioButton = function(callback) {
		//http://stackoverflow.com/questions/306583/this-selector-and-children
		
		//Setting up default value for hidden input element
		var defaultSel = jQuery(this).children('a:first-child').data('title');
		var defaultTog = jQuery(this).children('a:first-child').data('toggle');
		jQuery('#' + defaultTog).prop('value', defaultSel);
		
		//Setting up click event on the radio button
		jQuery(this).children('a').on(
				'click',
				function() {
					//console.log("clicked: " + $(this).prop('id'));
					var sel = jQuery(this).data('title');
					var tog = jQuery(this).data('toggle');
					jQuery('#' + tog).prop('value', sel);

					//console.log("sel: " + sel + ", tog: " + tog);
					
					jQuery('a[data-toggle="' + tog + '"]').not(
							'[data-title="' + sel + '"]').removeClass('active')
							.addClass('notActive');
					jQuery('a[data-toggle="' + tog + '"][data-title="' + sel + '"]')
							.removeClass('notActive').addClass('active');
					
					if(callback){
						callback();
					}
				});
		return this;
	};

}(jQuery));