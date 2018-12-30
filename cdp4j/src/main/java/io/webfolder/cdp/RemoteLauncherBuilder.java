/**
 * cdp4j - Chrome DevTools Protocol for Java
 * Copyright © 2017 WebFolder OÜ (support@webfolder.io)
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package io.webfolder.cdp;

public class RemoteLauncherBuilder {

    private String host = "127.0.0.1";

    private int sshPort = 22;

    private int chromePort = 9222;

    private String user = "root";

    private String password = "";

    private String privateKey = "";

    private String chromeExecutable = "google-chrome";

    public RemoteLauncherBuilder withHost(String host) {
        if (host == null) {
            throw new IllegalArgumentException();
        }
        this.host = host;
        return this;
    }

    public RemoteLauncherBuilder withSshPort(int sshPort) {
        this.sshPort = sshPort;
        return this;
    }

    public RemoteLauncherBuilder withChromePort(int chromePort) {
        this.chromePort = chromePort;
        return this;
    }

    public RemoteLauncherBuilder withUser(String user) {
        if (user == null) {
            throw new IllegalArgumentException();
        }
        this.user = user;
        return this;
    }

    public RemoteLauncherBuilder withPassword(String password) {
        if (password == null) {
            throw new IllegalArgumentException();
        }
        this.password = password;
        return this;
    }


    public RemoteLauncherBuilder withPrivateKey(String privateKey) {
        if (privateKey == null) {
            throw new IllegalArgumentException();
        }
        this.privateKey = privateKey;
        return this;
    }

    public RemoteLauncherBuilder withChromeExecutable(String chromeExecutable) {
        if (chromeExecutable == null) {
            throw new IllegalArgumentException();
        }
        this.chromeExecutable = chromeExecutable;
        return this;
    }

    public RemoteLauncher create() {
        return new RemoteLauncher(host, sshPort,
                chromePort, user, password, privateKey, chromeExecutable);
    }
}
