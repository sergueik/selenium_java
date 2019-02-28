# Google spreadsheet API sample with oauth2

## Instructions for the Spreadsheet with OAuth2 Command-Line Sample

### Register Your Application
- go to [google developers console](https://console.developers.google.com/project)
  - create new project
  - disable all enabled APIs 
    (because of Google Spreadsheet API is no *popular* Google Apps API)
  - create new client ID for native application
  - fetch *client_secrets.json*
    It will contains *Client ID* and *Client Secret*

### Setup Project in Eclipse
- clone `google-spreadsheet-api-samples` repository
- Import `spreadsheet-oauth2-sample` project
- download and copy *client_secrets.json* to `src/main/resources`
- add dependency for maven
- run *Sample.launch*
 

## possible problems
- modify application name and data store dir
