/**
 * cdp4j Commercial License
 *
 * Copyright 2017, 2018 WebFolder OÜ
 *
 * Permission  is hereby  granted,  to "____" obtaining  a  copy of  this software  and
 * associated  documentation files  (the "Software"), to deal in  the Software  without
 * restriction, including without limitation  the rights  to use, copy, modify,  merge,
 * publish, distribute  and sublicense  of the Software,  and to permit persons to whom
 * the Software is furnished to do so, subject to the following conditions:
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR  IMPLIED,
 * INCLUDING  BUT NOT  LIMITED  TO THE  WARRANTIES  OF  MERCHANTABILITY, FITNESS  FOR A
 * PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL  THE AUTHORS  OR COPYRIGHT
 * HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF
 * CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE
 * OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
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
