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

import static java.lang.Thread.sleep;

import java.util.TreeMap;

import org.jvnet.winp.WinProcess;

import io.webfolder.cdp.exception.CdpException;

public class WindowsProcessManager extends ProcessManager {

    private int pid;

    private String cdp4jId;

    @Override
    void setProcess(CdpProcess process) {
        WinProcess winProcess = new WinProcess(process.getProcess());
        pid = winProcess.getPid();
        cdp4jId = getCdp4jId(winProcess, 20);
        if (cdp4jId == null || cdp4jId.trim().isEmpty()) {
            throw new CdpException("cdp4jId not found");
        }
    }

    private String getCdp4jId(WinProcess process, int retryCount) {
        String id = getCdp4jId(process);
        if ( id != null ) {
            return id;
        } else {
            for (int i = 0; i < retryCount; i++) {
                id = getCdp4jId(process);
                if ( id != null ) {
                    return id;
                } else {
                    try {
                        sleep(500);
                    } catch (InterruptedException e) {
                        throw new CdpException(e);
                    }
                }
            }
        }
        return id;
    }

    private String getCdp4jId(WinProcess process) {
        String cdp4jId = null;
        try {
            TreeMap<String, String> variables = process.getEnvironmentVariables();
            cdp4jId = variables.get("CDP4J_ID");
        } catch (Throwable t) {
            // ignore
        }
        if ( cdp4jId != null && ! cdp4jId.trim().isEmpty() ) {
            return cdp4jId;
        }
        try {
            String cmd = process.getCommandLine();
            int start = cmd.indexOf("--cdp4jId=");
            if (start > 0) {
                int end = cmd.indexOf(" ", start + 1);
                if (end == -1) {
                    end = cmd.length();
                }
                cdp4jId = cmd.substring(start + "--cdp4jId=".length(), end);
            }
        } catch (Throwable t) {
            return null;
        }
        return cdp4jId;
    }

    @Override
    public boolean kill() {
        if (pid == 0 ||
                cdp4jId == null ||
                cdp4jId.trim().isEmpty()) {
            return false;
        }
        try {
            WinProcess process = new WinProcess(pid);
            if (process.isRunning()) {
                String cdp4jId = getCdp4jId(process, 20);
                if (cdp4jId == null || cdp4jId.trim().isEmpty()) {
                    throw new CdpException("cdp4jId not found");
                }
                if (pid == process.getPid() &&
                        this.cdp4jId.equals(cdp4jId)) {
                    process.killRecursively();
                    return true;
                } else {
                    return false;
                }
            }
        } catch (Throwable t) {
            return false;
        }
        return false;
    }
}
