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

import static java.io.File.pathSeparator;
import static java.lang.System.getProperty;
import static java.util.Locale.ENGLISH;

import io.webfolder.cdp.exception.CdpException;
import io.webfolder.cdp.logger.CdpLogger;
import io.webfolder.cdp.logger.CdpLoggerFactory;

public class AdaptiveProcessManager extends ProcessManager {

    private ProcessManager processManager;

    private static final String  OS      = getProperty("os.name").toLowerCase(ENGLISH);

    private static final boolean WINDOWS = ";".equals(pathSeparator);

    private static final boolean LINUX   = "linux".contains(OS);

    private static final boolean MAC     = OS.contains("mac");

    private static final boolean JAVA_8  = getProperty("java.version").startsWith("1.8.");

    private CdpLogger logger = new CdpLoggerFactory().getLogger("cdp4j.process-manager");
    
    public AdaptiveProcessManager() {
        if ( ! JAVA_8 ) {
        		// 	temporarily commented
           // processManager = new DefaultProcessManager();
        } else if (WINDOWS) {
            try {
                processManager = new WindowsProcessManager();
            } catch (Throwable t) {
                logger.error(t.getMessage());
            }
        } else if (LINUX) {
            processManager = new LinuxProcessManager();
        } else if (MAC) {
            processManager = new MacOsProcessManager();            
        } else {
            throw new CdpException(OS + " is not supported by AdaptiveProcessManager");
        }
    }

    @Override
    void setProcess(CdpProcess process) {
        processManager.setProcess(process);
    }

    @Override
    public boolean kill() {
        return processManager.kill();
    }
}
