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

import com.jcabi.ssh.Shell;
import com.jcabi.ssh.Ssh;
import com.jcabi.ssh.SshByPassword;
import io.webfolder.cdp.exception.CdpException;
import io.webfolder.cdp.session.SessionFactory;

import java.io.IOException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.stream.Collectors.joining;

public class RemoteLauncher extends AbstractLauncher {

    private final String host;

    private final int sshPort;

    private final String user;

    private final String password;

    private final String privateKey;

    private final String chromeExecutable;

    private Shell shell;

    private int pid = -1;

    RemoteLauncher(String host, int sshPort, int chromePort, String user, String password, String privateKey, String chromeExecutable) {
        super(new SessionFactory(host, chromePort));
        this.host = host;
        this.sshPort = sshPort;
        this.user = user;
        this.password = password;
        this.privateKey = privateKey;
        this.chromeExecutable = chromeExecutable;
    }

    @Override
    public String findChrome() {
        return chromeExecutable;
    }

    @Override
    protected void internalLaunch(List<String> list, List<String> parameters) {
        if (parameters.stream().noneMatch(arg -> arg.startsWith("--remote-debugging-address="))) {
            list.add("--remote-debugging-address=" + host);
        }
        String cmd = list.stream().collect(joining(" "));
        try {
            String result = new Shell.Plain(getShell()).exec(cmd + " > /dev/null 2> /dev/null < /dev/null &\necho ==$!==\n");
            //extracting process id from preformatted shell output
            Matcher m = Pattern.compile("==(\\d+)==").matcher(result);
            m.find();
            pid = Integer.parseInt(m.group(1));
        } catch (IOException e) {
            throw new CdpException(e);
        }
    }

    @Override
    public void kill() {
        if (pid > 0) {
            try {
                new Shell.Empty(getShell()).exec("kill " + pid);
            } catch (IOException e) {
                throw new CdpException(e);
            }
        }
    }

    synchronized public Shell getShell() throws IOException {
        if (shell == null) {
            shell = password.isEmpty()
                    ?
                    new Ssh(host, sshPort, user, privateKey)
                    :
                    new SshByPassword(host, sshPort, user, password);
        }
        return shell;
    }
}
