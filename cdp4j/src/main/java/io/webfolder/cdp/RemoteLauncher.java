/**
 * cdp4j - Chrome DevTools Protocol for Java
 * Copyright © 2017 WebFolder OÜ (support@webfolder.io)
 * <p>
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * <p>
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 * <p>
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
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
