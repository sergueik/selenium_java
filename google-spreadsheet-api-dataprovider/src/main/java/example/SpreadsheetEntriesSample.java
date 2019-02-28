/*
 * Copyright (c) 2010 Google Inc.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

package example;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.DataStoreFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.gdata.client.GoogleService;
import com.google.gdata.client.spreadsheet.SpreadsheetService;
import com.google.gdata.data.spreadsheet.SpreadsheetEntry;
import com.google.gdata.data.spreadsheet.SpreadsheetFeed;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

/**
 * Command-line sample (for the Google Spreadsheet API using OAuth 2.0 for login) to show in console
 * all spreadsheet entries on google drive.
 *
 * @author Mikhail Niedre
 */

// see also 
// https://github.com/tommparekh/GoogleSpreadsheetReader/blob/master/src/main/java/GoogleSpreadsheetReader.java
public class SpreadsheetEntriesSample {

  /**
   * Be sure to specify the name of your application. If the application name is {@code null} or
   * blank, the application will log a warning. Suggested format is "MyCompany-ProductName/1.0".
   */
  private static final String APPLICATION_NAME = "gogle-sheet-api-test-233021";

  /** OAuth 2.0 scopes. */
  private static final List<String> SCOPES = Arrays.asList("https://spreadsheets.google.com/feeds");

  private static final String SPREADSHEETS_FEED =
      "https://spreadsheets.google.com/feeds/spreadsheets/private/full";



  /** Directory to store user credentials. */
  private static final java.io.File DATA_STORE_DIR = new java.io.File(
      System.getProperty("user.home"), ".store/oauth2_3_sample");

  private static final String CLIENT_SECRETS = "/client_secrets.json";

  /**
   * Global instance of the {@link DataStoreFactory}. The best practice is to make it a single
   * globally shared instance across your application.
   */
  private static FileDataStoreFactory dataStoreFactory;

  /** Global instance of the HTTP transport. */
  private static HttpTransport httpTransport;

  /** Global instance of the JSON factory. */
  private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();

  private static GoogleClientSecrets clientSecrets;

  /** Authorizes the installed application to access user's protected data. */
  private static Credential authorize() throws Exception {

    // load client secrets
    clientSecrets =
        GoogleClientSecrets.load(
            JSON_FACTORY,
            new InputStreamReader(SpreadsheetEntriesSample.class
                .getResourceAsStream(CLIENT_SECRETS)));

    if (clientSecrets.getDetails().getClientId().startsWith("Enter")
        || clientSecrets.getDetails().getClientSecret().startsWith("Enter ")) {
      System.out.println("Enter Client ID and Secret from https://code.google.com/apis/console/ "
          + "into client_secrets.json");
      System.exit(1);
    }

    httpTransport = GoogleNetHttpTransport.newTrustedTransport();
    dataStoreFactory = new FileDataStoreFactory(DATA_STORE_DIR);

    // set up authorization code flow
    GoogleAuthorizationCodeFlow flow =
        new GoogleAuthorizationCodeFlow.Builder(httpTransport, JSON_FACTORY, clientSecrets, SCOPES)
            .setDataStoreFactory(dataStoreFactory).build();

    AuthorizationCodeInstalledApp app =
        new AuthorizationCodeInstalledApp(flow, new LocalServerReceiver());

    // authorize
    return app.authorize("user");
  }

  public static void main(String[] args) {
    try {
      // authorization
      Credential credential = authorize();

      // connect to google service with credential
      GoogleService googleService = new SpreadsheetService(APPLICATION_NAME);
      googleService.setOAuth2Credentials(credential);

      // Define the URL to request. This should never change.
      URL SPREADSHEET_FEED_URL = new URL(SPREADSHEETS_FEED);

      // Make a request to the API and get all spreadsheets.
      SpreadsheetFeed feed = googleService.getFeed(SPREADSHEET_FEED_URL, SpreadsheetFeed.class);
      List<SpreadsheetEntry> spreadsheets = feed.getEntries();

      // Iterate through all of the spreadsheets returned
      for (SpreadsheetEntry spreadsheet : spreadsheets) {
        // Print the title of this spreadsheet to the screen
        System.out.println(spreadsheet.getTitle().getPlainText());
      }

      // success!
      return;
    }

    catch (IOException e) {
      System.err.println(e.getMessage());
    }

    catch (Throwable t) {
      t.printStackTrace();
    }

    System.exit(1);
  }
}
