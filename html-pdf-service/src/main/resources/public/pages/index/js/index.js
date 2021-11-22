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

var config = {
		req: {
			params: "service/convert/html/params",
			body: "service/convert/html/body"
		},
		resp: {
			http: "",
			byte: "/byte"
		},
		data: {
			html: "service/test/data/html",
			template: "service/test/data/html/template",
			css: "service/test/data/css",
			json: "service/test/data/json"
		}
};

var configurePage = function(){
	configureRadioButtons();
	configureInputWithDefault();

	configureGeneratePdfButton();
};

var configureInputWithDefault = function(){
	loadTestHtml();
	loadTestDataForTemplate();
	loadTestCss();
	$('#serviceUrl').val(configureConversionUrl());
};

var configureConversionUrl = function(){
	var serviceUrl = config.req.params;
	if($('#reqParamBody').val() === 'REQ_BODY'){
		serviceUrl=config.req.body;
	}
	if($('#httpByte').val() === 'BYTE'){
		serviceUrl=serviceUrl + config.resp.byte;
	}
	console.log("serviceUrl: " + serviceUrl);
	return serviceUrl;
};

var loadTestHtml = function(){
	var url = config.data.html;
	if($('#htmlTemplate').val() === 'TEMPLATE'){
		url = config.data.template;
	}
	$.ajax({
		type: "GET",
		url: url,
		success: function(data){
			$('#htmlData').val(data);
		}
	});
};

var loadTestDataForTemplate = function(){
	$('#jsonData').val('');
	if($('#htmlTemplate').val() === 'TEMPLATE'){
		$.ajax({
			type: "GET",
			url: config.data.json,
			success: function(data){
				$('#jsonData').val(data);
			}
		});
	}
};

var loadTestCss = function(){
	$('#cssData').val('');
	if($('#htmlCss').val() === 'Y'){
		$.ajax({
			type: "GET",
			url: config.data.css,
			success: function(data){
				$('#cssData').val(data);
			}
		});
	}
};


var configureRadioButtons = function(){
	$('#inputType').radioButton(function(){
		console.log("inputType changed: " + $('#htmlTemplate').val());
		configureInputWithDefault();
	});
	$('#inputCss').radioButton(function(){
		console.log("inputCss changed: " + $('#htmlCss').val());
		configureInputWithDefault();
	});

	$('#requestType').radioButton(function(){
		console.log("responseType changed: " + $('#reqParamBody').val());
		configureInputWithDefault();
	});
	
	//	$('#responseType').radioButton();
	$('#responseType').radioButton(function(){
		console.log("responseType changed: " + $('#httpByte').val());
		configureInputWithDefault();
	});
};


//Doesn't work
//JQuery Ajax does not support blob in response by default
//var fetchFile = function(url, html, css){
//	var dto = {html: html, css: css};
//	$.ajax({
//        type: "POST", 
//        url: url,
//        data: dto,
//        success: function(data){
//            var win = window.open();
//            win.document.write(data);
//        }
//    });
//};


var configureGeneratePdfButton = function(){
	$('#generatePdf').click(function(e){
		e.preventDefault();
		
		var req = {};
//		req.html = $('<div>').text($('#htmlData').val()).html();
		req.html = $.escapeHtml($('#htmlData').val());
		
		console.log("HTML: " + req.html);
		
		if($('#cssData').val()){
//			req.css = $('<div>').text($('#cssData').val()).html();
			req.css = $.escapeHtml($('#cssData').val());
			
			console.log("CSS: " + req.css);
		}

		
		if($('#jsonData').val()){
//			req.json= $('<div>').text($('#jsonData').val()).html();
			req.json = $.escapeHtml($('#jsonData').val()); 

			console.log("JSON: " + req.json);
		}

		var reqType = 'reqParam';
		if($('#reqParamBody').val() === 'REQ_BODY'){
			reqType='json';
		}

		////// $.htmlAjaxFileDownload() can only use request params //////// 
//		$.htmlAjaxFileDownload(configureConversionUrl(), req, 'GET');
//		$.htmlAjaxFileDownload(configureConversionUrl(), req);
		
		$('#generatePdf').prop('disabled', true);
		var buttonText = $('#generatePdf').html(); 
		$('#generatePdf').html('Working ...');
		
		///// $.xhrAjaxFileDownload() can use both request params and request body (json)
		
		$.xhrAjaxFileDownload({
			reqType: reqType,
			url: configureConversionUrl(),
			data: req,
			done: function(){
				console.log("Done!");
				$('#generatePdf').prop('disabled', false);
				$('#generatePdf').html(buttonText);
			}
		});
		
		
	});
};

$(document).ready(function(){
	configurePage();
});