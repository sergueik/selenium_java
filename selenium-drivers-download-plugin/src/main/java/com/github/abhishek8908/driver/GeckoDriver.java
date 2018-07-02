package com.github.abhishek8908.driver;

import com.github.abhishek8908.driver.logger.Logger;
import com.github.abhishek8908.util.DriverUtil;
import org.apache.commons.configuration.ConfigurationException;

import java.io.File;
import java.io.IOException;

public class GeckoDriver extends Logger implements IDriver {

	private final String driverName = "geckodriver";
	private String ver;
	private String os;
	private String driverDir;
	private String ext;

	public GeckoDriver(DriverSettings settings) {
		this.ver = settings.getVer();
		System.setProperty("ver", this.ver);
		getLog().info("Set System property: " + "ver=" + System.getProperty("ver"));
		this.os = settings.getOs();
		System.setProperty("os", this.os);
		getLog().info("Set System property: " + "os=" + System.getProperty("os"));
		this.driverDir = settings.getDriverDir();
		System.setProperty("ext", setExt());
		getLog().info("Set System property: " + "ext=" + System.getProperty("ext"));
	}

	private boolean isDriverAvailable() throws IOException {
		DriverUtil.checkDriverDirExists(driverDir);
		return DriverUtil.checkDriverVersionExists(driverName, ver, driverDir);
	}

	private String setExt() {
		this.ext = (os.toLowerCase().contains("win")) ? "zip" : "tar.gz";
		return ext;
	}

	public GeckoDriver getDriver() throws IOException, ConfigurationException {

		if (!isDriverAvailable()) {
			DriverUtil.download(driverName, driverDir, ver);
		} else {
			getLog().info(
					"Driver " + driverName + " already exists at location " + driverDir);
			// setDriverInSystemProperty();
		}
		return this;

	}

	@Override
	public void setDriverInSystemProperty() {
		System.setProperty("webdriver.gecko.driver",
				driverDir + File.separator + driverName + "-" + ver + "-" + os);
		getLog().info("Setting webdriver.gecko.driver : "
				+ System.getProperty("webdriver.gecko.driver"));
	}
	/*
	 https://api.github.com/repos/mozilla/geckodriver/releases
	 JSON:
	 [
	{
	  "url": "https://api.github.com/repos/mozilla/geckodriver/releases/11508322",
	  "assets_url": "https://api.github.com/repos/mozilla/geckodriver/releases/11508322/assets",
	  "upload_url": "https://uploads.github.com/repos/mozilla/geckodriver/releases/11508322/assets{?name,label}",
	  "html_url": "https://github.com/mozilla/geckodriver/releases/tag/v0.21.0",
	  "id": 11508322,
	  "node_id": "MDc6UmVsZWFzZTExNTA4MzIy",
	  "tag_name": "v0.21.0",
	  "target_commitish": "master",
	  "name": "",
	  "draft": false,
	  "author": {
	    "login": "AutomatedTester",
	    "id": 128518,
	    "node_id": "MDQ6VXNlcjEyODUxOA==",
	    "avatar_url": "https://avatars1.githubusercontent.com/u/128518?v=4",
	    "gravatar_id": "",
	    "url": "https://api.github.com/users/AutomatedTester",
	    "html_url": "https://github.com/AutomatedTester",
	    "followers_url": "https://api.github.com/users/AutomatedTester/followers",
	    "following_url": "https://api.github.com/users/AutomatedTester/following{/other_user}",
	    "gists_url": "https://api.github.com/users/AutomatedTester/gists{/gist_id}",
	    "starred_url": "https://api.github.com/users/AutomatedTester/starred{/owner}{/repo}",
	    "subscriptions_url": "https://api.github.com/users/AutomatedTester/subscriptions",
	    "organizations_url": "https://api.github.com/users/AutomatedTester/orgs",
	    "repos_url": "https://api.github.com/users/AutomatedTester/repos",
	    "events_url": "https://api.github.com/users/AutomatedTester/events{/privacy}",
	    "received_events_url": "https://api.github.com/users/AutomatedTester/received_events",
	    "type": "User",
	    "site_admin": false
	  },
	  "prerelease": false,
	  "created_at": "2018-06-15T20:46:25Z",
	  "published_at": "2018-06-15T20:57:11Z",
	  "assets": [
	    {
	      "url": "https://api.github.com/repos/mozilla/geckodriver/releases/assets/7551150",
	      "id": 7551150,
	      "node_id": "MDEyOlJlbGVhc2VBc3NldDc1NTExNTA=",
	      "name": "geckodriver-v0.21.0-arm7hf.tar.gz",
	      "label": "",
	      "uploader": {
	        "login": "AutomatedTester",
	        "id": 128518,
	        "node_id": "MDQ6VXNlcjEyODUxOA==",
	        "avatar_url": "https://avatars1.githubusercontent.com/u/128518?v=4",
	        "gravatar_id": "",
	        "url": "https://api.github.com/users/AutomatedTester",
	        "html_url": "https://github.com/AutomatedTester",
	        "followers_url": "https://api.github.com/users/AutomatedTester/followers",
	        "following_url": "https://api.github.com/users/AutomatedTester/following{/other_user}",
	        "gists_url": "https://api.github.com/users/AutomatedTester/gists{/gist_id}",
	        "starred_url": "https://api.github.com/users/AutomatedTester/starred{/owner}{/repo}",
	        "subscriptions_url": "https://api.github.com/users/AutomatedTester/subscriptions",
	        "organizations_url": "https://api.github.com/users/AutomatedTester/orgs",
	        "repos_url": "https://api.github.com/users/AutomatedTester/repos",
	        "events_url": "https://api.github.com/users/AutomatedTester/events{/privacy}",
	        "received_events_url": "https://api.github.com/users/AutomatedTester/received_events",
	        "type": "User",
	        "site_admin": false
	      },
	      "content_type": "application/gzip",
	      "state": "uploaded",
	      "size": 3192918,
	      "download_count": 3941,
	      "created_at": "2018-06-15T20:58:12Z",
	      "updated_at": "2018-06-15T20:58:13Z",
	      "browser_download_url": "https://github.com/mozilla/geckodriver/releases/download/v0.21.0/geckodriver-v0.21.0-arm7hf.tar.gz"
	    }
	  ],
	  "tarball_url": "https://api.github.com/repos/mozilla/geckodriver/tarball/v0.1.0",
	  "zipball_url": "https://api.github.com/repos/mozilla/geckodriver/zipball/v0.1.0",
	  "body": ""
	}
	]
	 */
}
