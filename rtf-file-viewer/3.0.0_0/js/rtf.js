class Rtf {
    constructor(name, data) {
        this.name = name;
        this.data = data;
    }
}

class Viewer {
    constructor(rtf, baseUrl){
        this.rtf = rtf;
        this.baseUrl = baseUrl;
        
    }

    _stringToBinaryArray(string) {
        var buffer = new ArrayBuffer(string.length);
        var bufferView = new Uint8Array(buffer);
        for (var i=0; i<string.length; i++) {
            bufferView[i] = string.charCodeAt(i);
        }
        return buffer;
    }

    renderDocument(){
        try {
            RTFJS.loggingEnabled(false);
            WMFJS.loggingEnabled(false);
            EMFJS.loggingEnabled(false);

            var baseUrl = this.baseUrl;

            var settings = {
                onImport: function(relURL, cb) {
                    const file = baseUrl + relURL;
                    const ext  = relURL.replace(/^.*\.([^\.]+)$/, '$1').toLowerCase();
                    let keyword;
                    switch(ext) {
                        case 'emf':
                            keyword = 'emfblip';
                            break;
                        case 'wmf':
                            keyword = 'wmetafile';
                            break;
                        default:
                            return cb({error});
                    }

                    var request = new XMLHttpRequest();
                    request.open("GET", file, true);
                    request.responseType = "arraybuffer";

                    request.onload = function (event) {
                        var blob = request.response;
                        if (blob) {
                            let height = 300;
                            cb({keyword, blob, height});
                        } else {
                            let error = new Error(request.statusText);
                            cb({error});
                        }
                    };

                    request.send(null);
                }
            }

            var doc = new RTFJS.Document(this._stringToBinaryArray(this.rtf.data), settings);

            //Set title if meta data available
            var meta = doc.metadata();
            if(meta.title && meta.title.trim() !== ""){
                document.title = meta.title;
            }else{
                document.title = this.rtf.name;
            }

            //Display document
            doc.render().then(renderedElements => {
                var mainElement = $("#main").empty();
                renderedElements.forEach(function(renderedElement){
                    mainElement.append(DOMPurify.sanitize(renderedElement[0], {SAFE_FOR_JQUERY: true}));
                });
            });
        }catch (error) {
            if (error instanceof RTFJS.Error || error instanceof WMFJS.Error || error instanceof EMFJS.Error) {
                $("#main").text("Error: " + error.message);
                console.error(error);
            } else {
                throw error;
            }
        }
    }

    _downloadRtfFile(event){
        var blob = new Blob([this.rtf.data], {type: "text/rtf"});
        var url = URL.createObjectURL(blob);
        var a = event.target;
        a.href = url;
        a.download = this.rtf.name;
    }
}


function loadDataXhr(){
    var rtfUrl = decodeURIComponent(location.search.replace("?file=",""));
    var xhr = new XMLHttpRequest();
    xhr.open("GET", rtfUrl, true);
    xhr.onreadystatechange = function() {
        if(xhr.readyState < 4) {
            $("#main").text("Loading ...");
        }
        if (xhr.readyState == 4) {
            //XHR requests for local files (file://) don't have a status
            if ((xhr.status == 0 || (xhr.status >= 200 && xhr.status < 300))
                && xhr.responseText && xhr.responseText != "") {
                //Save data for later use
                const rtf = new Rtf(rtfUrl.substring(rtfUrl.lastIndexOf("/") + 1), xhr.responseText)

                //Render document
                const viewer = new Viewer(rtf, rtfUrl.replace(/^(.*\/)[^\/]*$/, '$1'));
                viewer.renderDocument();
            } else{
                $("#main").text("Error: File not Found");
            }
        }
    };
    xhr.send();
}

function loadDataFileReader(file){
    let reader = new FileReader();
    reader.onload = function(event) {
        const rtf = new Rtf(file.name, event.target.result);

        //Render document
        const viewer = new Viewer(rtf, "");
        viewer.renderDocument();
    };
    reader.readAsText(file);
}


document.addEventListener('DOMContentLoaded', function(){
    if(location.search.startsWith("?file=")) {
        document.getElementById("main").classList.remove("hidden");
        document.getElementById("toolbar").classList.remove("hidden");
        loadDataXhr();
    } else {
        document.getElementById("file-upload").classList.remove("hidden");

        document.getElementById("upload-field").addEventListener("change", function () {
            document.getElementById("file-upload").classList.add("hidden");
            document.getElementById("main").classList.remove("hidden");
            document.getElementById("toolbar").classList.remove("hidden");
            loadDataFileReader(this.files[0])
        }, false)
    }
}, true);