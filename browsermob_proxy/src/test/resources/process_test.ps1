# based on
# https://stackoverflow.com/questions/4015324/how-to-make-http-post-web-request
# https://docs.microsoft.com/en-us/dotnet/api/system.net.webclient.downloadfile?view=netframework-4.5.1#System_Net_WebClient_DownloadFile_System_Uri_System_String_

# Step 1

add-Type -TypeDefinition @"
using System.Net;
using System;
using System.Windows.Forms;
using System.IO;
using System.Text;

public class Step1
{

	public static String m()
	{

		String baseUrl = "https://accounts.google.com/ListAccounts";
		String queryString = "gpsia=1";
		queryString += "&source=ChromiumBrowser";
		queryString += "&json=standard";

		var request = (HttpWebRequest)WebRequest.Create(baseUrl + "?" + queryString);

		var postData = "name=";
		postData += "&value=";
		postData += "&comment=";
		var postDataAscii = Encoding.ASCII.GetBytes(postData);

		request.Method = "POST";
		request.ContentType = "application/x-www-form-urlencoded";
		request.ContentLength = postDataAscii.Length;

		using (var stream = request.GetRequestStream()) {
			stream.Write(postDataAscii, 0, postDataAscii.Length);
		}

		var response = (HttpWebResponse)request.GetResponse();

		var responseString = new StreamReader(response.GetResponseStream()).ReadToEnd();
		return (responseString);
	}

}
"@ -referencedAssemblies 'System.net', 'System.Windows.Forms'
# write-error [Step1]::m()

<#
["gaia.l.a.r",[]
]
#>

# Step 2


add-Type -TypeDefinition @"
using System.Net;
using System;
using System.Windows.Forms;
using System.IO;
using System.Text;

public class Step2
{

	public static String m()
	{

		String baseUrl = "http://bandi.servizi.politicheagricole.it/taxcredit/default.aspx";

		var request = (HttpWebRequest)WebRequest.Create(baseUrl);

		var postData = "__EVENTTARGET=ctl00$phContent$Login$btn_ini_accedi";
		postData += "&__EVENTARGUMENT=";
		postData += "&__VIEWSTATE=/wEPDwUKMTU2NjA0NjQwOA8WAh4TVmFsaWRhdGVSZXF1ZXN0TW9kZQIBFgJmD2QWAgIED2QWAmYPZBYIAgMPDxYCHgRUZXh0BSBUYXggQ3JlZGl0IFJpcXVhbGlmaWNhemlvbmUgMjAxOWRkAgUPDxYCHgtOYXZpZ2F0ZVVybAUWTUlCQUNfRF8yMDE3LTEyLTIwLnBkZmRkAgcPFgIeB1Zpc2libGVoFgQCAQ8PFgIfAgUbL3RheGNyZWRpdC9SaWNoaWVkZW50ZS5hc3B4ZGQCAw8PFgIfAgUfL3RheGNyZWRpdC9kZWZhdWx0LmFzcHg/b3BlPXByZ2RkAgsPZBYCAgEPDxYCHgZUb3RhbGUCUWQWBgIBD2QWAgIDDxYCHwNoZAIDDw8WAh8DaGQWAmYPDxYEHghDc3NDbGFzcwUSYWxlcnQgYWxlcnQtZGFuZ2VyHgRfIVNCAgJkFgYCAQ8PFgIeCEltYWdlVXJsBRhpbW1hZ2luaS9Fc2NsYW1hdGl2by5wbmdkZAIDDw8WAh8BZWRkAgUPD2QWAh4Hb25jbGljawU4JCgnI2N0bDAwX3BoQ29udGVudF9Mb2dpbl9NZXNzYWdnaW9IVE1MX3BubE1zZycpLmhpZGUoKTtkAgUPFgIfA2gWFAIFDw8WAh8DaGRkAggPFgIfA2hkAgoPFgIfAQUBOWQCCw8WAh8BBQUgcGVyIGQCDA8WAh8BBQE5ZAIODw8WAh8BZWRkAhIPDxYCHwNoZGQCEw8PFgIfA2hkZAIUDw8WBB8CBRcvdGF4Y3JlZGl0L2RlZmF1bHQuYXNweB8DaGRkAhYPFgIfA2hkZBFGGBiGL0VfOjliCHoRUvUXRwFr2O0LA3Am4hkswYjt";
		postData += "&__VIEWSTATEGENERATOR=4A5300F5";
		postData += "&__EVENTVALIDATION=/wEdAAPqfHjyPjPLhZQ0gCN7kL7fnLm0Vjwm97JXaXwQkxRUvqENglAZvUiFSPeQehC9G+/Hgpcnu1wBJHh59e2MeXYAFQAAGTLNMLMalE4zm2L3lQ==";

		var postDataAscii = Encoding.ASCII.GetBytes(postData);

		request.Method = "POST";
		request.ContentType = "application/x-www-form-urlencoded";
		request.ContentLength = postDataAscii.Length;

		using (var stream = request.GetRequestStream()) {
			stream.Write(postDataAscii, 0, postDataAscii.Length);
		}

		var response = (HttpWebResponse)request.GetResponse();

		var responseString = new StreamReader(response.GetResponseStream()).ReadToEnd();
		return (responseString);
	}

}
"@ -referencedAssemblies 'System.net', 'System.Windows.Forms'
# [Step2]::m()
<#
<!DOCTYPE html>



<html>
<head><meta http-equiv="Content-Type" content="text/html; charset=utf-8" /><title>

</title>
    <!--<link rel="stylesheet" type="text/css" href="http://fonts.googleapis.com/css?family=Titillium+Web|HelveticaNeue-Light|Helvetica+Neue+Light|Helvetica+Neue|Helvetica|Arial|Lucida+Grande|sans-serif" />-->
	<link rel="stylesheet" type="text/css" href="/Content/fontgoogle.css" /><link rel="stylesheet" type="text/css" href="/Content/bootstrap.min.css" /><link rel="stylesheet" type="text/css" href="/Content/stile-custom.css" /><link rel="stylesheet" type="text/css" href="/Content/myInputFile.css" /><link rel="stylesheet" type="text/css" href="/Content/open-iconic/font/css/open-iconic-bootstrap.css" /><link rel="stylesheet" type="text/css" href="/Content/jquery.datetimepicker.css" /><link rel="icon" href="/common/immagini/favicon.ico" type="image/vnd.microsoft.icon" />



	<script type="text/javascript" src="/Scripts/jquery-3.2.1.min.js"></script>
    <script type="text/javascript" src="/Scripts/jquery.datetimepicker.js"></script>
    <script type="text/javascript" src="/Scripts/jquery.dirtyforms.min.js"></script>
    <script type="text/javascript" src="/Scripts/autonumeric/autonumeric.js"></script>
    <script type="text/javascript" src="/Scripts/bootstrap-filestyle.min.js"></script>
	<script type="text/javascript" src="/Scripts/bootstrap.min.js"></script>
    <!-- Bootstrap Dialog plugin: vedere https://nakupanda.github.io/bootstrap3-dialog/ -->
	<script type="text/javascript" src="/Scripts/bootstrap-dialog.js"></script>
	<script type="text/javascript" src="/Scripts/script-custom.js"></script>
	<script type="text/javascript" src="/Scripts/myInputFile.js"></script>
    <script src="/scripts/jquery.blockUI.js"></script>



	
</head>

<body id="ctl00_PageBody">
	<form name="aspnetForm" method="post" action="./default.aspx" id="aspnetForm">
<input type="hidden" name="__VIEWSTATE" id="__VIEWSTATE" value="/wEPDwUKMTU2NjA0NjQwOA8WAh4TVmFsaWRhdGVSZXF1ZXN0TW9kZQIBFgJmD2QWAgIED2QWAmYPZBYIAgMPDxYCHgRUZXh0BSBUYXggQ3JlZGl0IFJpcXVhbGlmaWNhemlvbmUgMjAxOWRkAgUPDxYCHgtOYXZpZ2F0ZVVybAUWTUlCQUNfRF8yMDE3LTEyLTIwLnBkZmRkAgcPFgIeB1Zpc2libGVoFgQCAQ8PFgIfAgUbL3RheGNyZWRpdC9SaWNoaWVkZW50ZS5hc3B4ZGQCAw8PFgIfAgUfL3RheGNyZWRpdC9kZWZhdWx0LmFzcHg/b3BlPXByZ2RkAgsPZBYCAgEPDxYCHgZUb3RhbGUCUWQWBgIBD2QWAgIDDxYCHwNoZAIDDw8WAh8DaGQWAmYPDxYEHghDc3NDbGFzcwUSYWxlcnQgYWxlcnQtZGFuZ2VyHgRfIVNCAgJkFgYCAQ8PFgIeCEltYWdlVXJsBRhpbW1hZ2luaS9Fc2NsYW1hdGl2by5wbmdkZAIDDw8WAh8BZWRkAgUPD2QWAh4Hb25jbGljawU4JCgnI2N0bDAwX3BoQ29udGVudF9Mb2dpbl9NZXNzYWdnaW9IVE1MX3BubE1zZycpLmhpZGUoKTtkAgUPFgIfA2gWFAIFDw8WAh8DaGRkAggPFgIfA2hkAgoPFgIfAQUBOWQCCw8WAh8BBQUgcGVyIGQCDA8WAh8BBQE5ZAIODw8WAh8BZWRkAhIPDxYCHwNoZGQCEw8PFgIfA2hkZAIUDw8WBB8CBRcvdGF4Y3JlZGl0L2RlZmF1bHQuYXNweB8DaGRkAhYPFgIfA2hkZBFGGBiGL0VfOjliCHoRUvUXRwFr2O0LA3Am4hkswYjt" />


<script src="/ScriptResource.axd?d=pxfNNJZvyVC_tzSf-3YBgv41BVP86EmZe3JjAJUarzk85p16lP0TrOomqGEiq3hqN_XvtfrOQJoPZNQJN-w5GEvciLtKGja699bLWLTmFsWKJ2nbEwcpPtKT4beutooPQncUAddWJoAeQw4-GwroBaN3swuerHFo5Dz-GuZjVzlLZgSauDL1waKqUCbmb3rk0&amp;t=ffffffff999c3159" type="text/javascript"></script>
<input type="hidden" name="__VIEWSTATEGENERATOR" id="__VIEWSTATEGENERATOR" value="4A5300F5" />
<input type="hidden" name="__EVENTVALIDATION" id="__EVENTVALIDATION" value="/wEdAAPqfHjyPjPLhZQ0gCN7kL7fnLm0Vjwm97JXaXwQkxRUvqENglAZvUiFSPeQehC9G+/Hgpcnu1wBJHh59e2MeXYAFQAAGTLNMLMalE4zm2L3lQ==" />

        <nav class="container-fluid">
			<div class="navbar testata">
				<div class="col-xs-1">
                    <a href="Default.aspx"><img src="/common/immagini/logoMipaaf.png" /></a>
				</div>
				<div class="col-xs-9 navbar-btn">
					<a class="testobianco hidden-xs" href="//www.politicheagricole.it/flex/cm/pages/ServeBLOB.php/L/IT/IDPagina/11925">
						Ministero delle politiche agricole alimentari, forestali e del turismo<br /><strong>Servizi on line</strong>
					</a>
				</div>
				<div class="col-xs-2">
					<img src="/common/immagini/acronimoMipaaf.png" class=" pull-right" />
				</div>
			</div>
        </nav>

        <div class="container-fluid">
            <div class="navbar navbar-default">
                    <div class="navbar-header">
                        <button class="navbar-toggle" type="button" data-toggle="collapse" data-target="#mynavbar" aria-controls="navbar" aria-expanded="false" aria-label="Toggle navigation">
                            <span class="icon-bar"></span>
                            <span class="icon-bar"></span>
                            <span class="icon-bar"></span>
                        </button>
                        <a class="navbar-brand" href="#">
                            <span id="ctl00_lblTitoloBando" class="grassetto">Tax Credit Riqualificazione 2019</span>
                        </a>
                    </div>
                    <div class="collapse navbar-collapse" id="mynavbar">
                        <ul class="nav navbar-nav navbar-right">
                            <li><a id="ctl00_aLinkBando" href="MIBAC_D_2017-12-20.pdf" target="_blank"><span class="glyphicon glyphicon-file"></span> Normativa</a></li>
                            <li><a href="TaxCredit_2019_Manuale_Utente.pdf" target="_blank"><span class="glyphicon glyphicon-book"></span> Manuale utente</a></li>

                        </ul>
                    </div>
                </div>
        </div>

		


		


    <div class="container-fluid">

        <div id="ctl00_phContent_Login_divScelta" class="row row-eq-height">
            <div class="col-xs-12 col-sm-6">
                <div class="panel">

                        <div class="panel-heading titolo">Sei già registrato?</div>
                        <div class="panel-body text-center">
                            <a id="ctl00_phContent_Login_btn_ini_accedi" href="javascript:__doPostBack(&#39;ctl00$phContent$Login$btn_ini_accedi&#39;,&#39;&#39;)">
                                <p><img src="/common/immagini/Login.png" alt="ACCEDI" width="100" /></p>
                                <span class="btn btn-info grassetto">A C C E D I</span>
                            </a>
                        </div>


                </div>
            </div>
            <div class="col-xs-12 col-sm-6">
                <div class="panel">
                    <div class="panel-heading titolo">Registrati adesso</div>
                    <div class="panel-body text-center">
                        <a id="ctl00_phContent_Login_btn_ini_registrati" href="javascript:__doPostBack(&#39;ctl00$phContent$Login$btn_ini_registrati&#39;,&#39;&#39;)">
                            <p><img src="/common/immagini/Registrazione.png" alt="REGISTRATI" width="100" /></p>
                            <span class="btn btn-info grassetto">REGISTRATI</span>
                        </a>
                    </div>
                </div>
            </div>
        </div>

        <!--<div class="alert alert-warning">
            <h3>AVVISO IMPORTANTE: Si comunica che dalle ore 12.00 del 24 febbraio fino alle ore 21.00 del 25 febbraio pp.vv.
                l'applicazione sarà inaccessibile per manutenzione di sistemi terzi
            </h3>
        </div>-->

    </div>

    <div class="container-fluid">

    </div>



    <div class="container-fluid" style="clear: both;">
        <h1 class="alert alert-warning text-center"><strong><a href="TaxCredit_2019_Procedure_finali.pdf">Scarica qui <span class="glyphicon glyphicon-download-alt"></span> le istruzioni aggiuntive relative al Click Day</a></strong></h1>
        <h4 class="alert alert-info">E' disponibile un file con le domande frequenti (<a href="http://www.turismo.beniculturali.it/wp-content/uploads/2019/02/Faq-2019.pdf">FAQ <span class="glyphicon glyphicon-download-alt"></span></a>) aggiornato progressivamente.</h4>
    </div>


        <!-- Tolto perchè copre testo/campi-->
        <!--
		<div id="footer" class="nav navbar navbar-default navbar-fixed-bottom piede visible-lg">
			Ministero delle politiche agricole alimentari, forestali e del turismo
			<br />
			Sede: Via Venti Settembre, 20 - 00187 Roma - C.F. 97099470581<br />
            Centralino: 06 46651 - Fax: 06 4742314 - e-mail:
            <a href="mailto:urp@politicheagricole.it">urp@politicheagricole.it</a>
		</div>
        -->

    <div id="Block_div"><img src="/Common/Immagini/loading.gif" alt="Click qui per sbloccare" /></div>

	</form>

	<script type="text/javascript">

        function setdatepicker() {
            $('.datepicker').each(function (i, obj) {
                $('#' + obj.id).datetimepicker({
                    lang: 'it',
                    format: 'd/m/Y',
                    timepicker: false
                });
            });
        }
        setdatepicker();
        $('.timepicker').datetimepicker({
            datepicker: false,
            step: 30,
            format: 'H:i'
        });

	  $(document).ready(function () {
		  $('[data-toggle="help_masterpage"]').tooltip();
		});


    function BloccaUI(sender, args) {
      // Whatever you want to happen when the async callback starts

        if (typeof (Page_ClientValidate) == 'function') {
            Page_ClientValidate();
        }

        if (document.getElementById("aspnetForm").checkValidity()) {
            try {
                $.blockUI({
                  message: $('#Block_div'),
                  css:
                  {
                  padding: 0,
                  margin: 0,
                  textAlign: 'center',
                  fontSize: 12,
                  cursor: 'auto',
                  border: 'none',
                  background:'none'
                  }
                });
                return true;
            }
            catch (e) {
                return false;
            }
            return true;
        }
    }

    function SbloccaUI(sender, args) {
      // Whatever you want to happen when async callback ends
      $('body').unblockUI();
    }

//	data-toggle="help_mess" data-placement="auto" title="Comune italiano oppure Stato estero"

    var offset = 0;


    //$.ajax({
    //    method: 'GET',
    //    async: true,
    //    cache: false,
    //    url: "menu.aspx",
    //    crossDomain: true,
    //    error: function (req, textstatus, errors) {
    //        alert(errors);
    //    },
    //    success: function (req, textStatus) {
    //        var dateString = req.getResponseHeader('Date');
    //        if (dateString.indexOf('GMT') === -1) {
    //        dateString += ' GMT';
    //        }
    //        var date = new Date(dateString);
    //        var serverTimeMillisGMT = Date.parse(new Date(Date.parse(dateString)).toUTCString());
    //        var localMillisUTC = Date.parse(new Date().toUTCString());
    //        offset = serverTimeMillisGMT - localMillisUTC;
    //        setInterval(function () {
    //            $("#divClock").text((new Date(Date.now() + timeDiff)).toTimeString().substring(0, 9));
    //        }, 1000);
    //    }
    //});

    $.ajax({
        "url": "/common/ora.aspx",
        "method": "GET"
    }).done(function (response) {
        var date = new Date(response);
        var clickday = new Date('2019-04-03T10:00:00');
        var serverMillis = Date.parse(date.toUTCString());
        var localMillis = Date.parse(new Date().toUTCString());
        //offset = serverMillis - localMillis;
        offset = 0;
        setInterval(function () {
            var ora = (new Date(Date.now() + offset)).toTimeString().substring(0, 9);
            if (Date.now() >= clickday) {
                $(".trasmissione").removeAttr('disabled');
                //$("#lnkNuova").hide();
            };
            $("#divClock").text(ora);
        }, 1000);
    }).fail(function (errore) {
        alert("Errore nella visualizzazione dell'orario");
    });

</script>

</body>
</html>


#>
# Step 3

add-Type -TypeDefinition @"
using System.Net;
using System;
using System.Windows.Forms;
using System.IO;
using System.Text;

public class Step3
{

	public static String m()
	{

		String baseUrl = "http://bandi.servizi.politicheagricole.it/taxcredit/default.aspx";

		var request = (HttpWebRequest)WebRequest.Create(baseUrl);
		var postData = "&__EVENTTARGET=";
		postData += "&__EVENTARGUMENT=";
		postData += "&__VIEWSTATE=/wEPDwUKMTU2NjA0NjQwOA8WAh4TVmFsaWRhdGVSZXF1ZXN0TW9kZQIBFgJmD2QWAgIED2QWAmYPZBYIAgMPDxYCHgRUZXh0BSBUYXggQ3JlZGl0IFJpcXVhbGlmaWNhemlvbmUgMjAxOWRkAgUPDxYCHgtOYXZpZ2F0ZVVybAUWTUlCQUNfRF8yMDE3LTEyLTIwLnBkZmRkAgcPFgIeB1Zpc2libGVoFgQCAQ8PFgIfAgUbL3RheGNyZWRpdC9SaWNoaWVkZW50ZS5hc3B4ZGQCAw8PFgIfAgUfL3RheGNyZWRpdC9kZWZhdWx0LmFzcHg/b3BlPXByZ2RkAgsPZBYCAgEPDxYCHgZUb3RhbGUCUWQWBgIBDxYCHwNoFgYCAQ9kFgICAQ8PFgIfA2hkZAIDDxYCHwNoZAIFDw8WAh8DaGRkAgMPDxYCHwNoZBYCZg8PFgQeCENzc0NsYXNzBRJhbGVydCBhbGVydC1kYW5nZXIeBF8hU0ICAmQWBgIBDw8WAh4ISW1hZ2VVcmwFGGltbWFnaW5pL0VzY2xhbWF0aXZvLnBuZ2RkAgMPDxYCHwFlZGQCBQ8PZBYCHgdvbmNsaWNrBTgkKCcjY3RsMDBfcGhDb250ZW50X0xvZ2luX01lc3NhZ2dpb0hUTUxfcG5sTXNnJykuaGlkZSgpO2QCBQ8WAh8DZxYYAgEPFgIfAQUGQUNDRURJZAIDDw8WAh8BBXdBY2NlZGkgY29uIGwnaW5kaXJpenpvIGRpIHBvc3RhIGVsZXR0cm9uaWNhIGUgbGEgcGFzc3dvcmQgdGVtcG9yYW5lYSAoUElOKSBjaGUgdGkgw6ggc3RhdGEgZm9ybml0YSB0cmFtaXRlIGUtbWFpbC48YnIvPmRkAgUPDxYEHwNnHwFlZGQCCA8WAh8DZ2QCCg8WAh8BBQE5ZAILDxYCHwEFBSBwZXIgZAIMDxYCHwEFATlkAhIPDxYCHwNnZGQCEw8PFgIfA2hkZAIUDw8WBB8CBRcvdGF4Y3JlZGl0L2RlZmF1bHQuYXNweB8DZ2RkAhYPFgIfA2dkAhgPFgIfA2hkZATWaC16QMsWOxvYB+d20NIfDFCcAWDt5Nm2gAeehPz2";
		postData += "&__VIEWSTATEGENERATOR=4A5300F5";
		postData += "&__EVENTVALIDATION=/wEdAAhyJ8EsATVLJgmwop+5tL2xhrUoe2Lr2n8kOOUSGLupjIFWup+VRiWdvBn+KkliN5b2FxufEDc1X9SAFtdYXe2FXCRwcNkrMKNKOTvUlUaeO4/BeqN0xFYqM9ZLvofY/+tHq5aHOj1whwMnNE5ceEYZFdMALHXWN87k6ZPIKQsVrk4mY3X5bxyIBn1GLHtcrrhXpVXwZI+KljU0PqQWT69X";
		postData += "&ctl00$phContent$Login$txtEmail=Hotel3@hotelvintage.33mail.com";
		postData += "&ctl00$phContent$Login$txtOTP=87630100";
    // TODO: find out how to read the actual arithmetic challenge 
		postData += "&ctl00$phContent$Login$txtRisultato=81";
		postData += "&ctl00$phContent$Login$btnAccedi=ACCEDI";

		var postDataAscii = Encoding.ASCII.GetBytes(postData);

		request.Method = "POST";
		request.ContentType = "application/x-www-form-urlencoded";
		request.ContentLength = postDataAscii.Length;

		using (var stream = request.GetRequestStream()) {
			stream.Write(postDataAscii, 0, postDataAscii.Length);
		}

		var response = (HttpWebResponse)request.GetResponse();

		var responseString = new StreamReader(response.GetResponseStream()).ReadToEnd();
		return (responseString);
	}

}
"@ -referencedAssemblies 'System.net', 'System.Windows.Forms'

[Step3]::m()

<#
pagina base 65 - errore di configurazione - rawUrl=/common/errore.aspx
#>

