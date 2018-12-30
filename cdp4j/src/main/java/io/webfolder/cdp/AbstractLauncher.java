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

import static java.lang.String.format;
import static java.lang.Thread.sleep;
import static java.util.Collections.emptyList;

import java.util.ArrayList;
import java.util.List;

import io.webfolder.cdp.exception.CdpException;
import io.webfolder.cdp.session.SessionFactory;

abstract class AbstractLauncher {

    protected final SessionFactory factory;

    private volatile boolean launched;

    public AbstractLauncher(final SessionFactory factory) {
        this.factory = factory;
    }

    protected List<String> getCommonParameters(String chromeExecutablePath, List<String> arguments) {
        List<String> list = new ArrayList<>();
        list.add(chromeExecutablePath);

        if (factory.getPort() > 0) {
            list.add(format("--remote-debugging-port=%d", factory.getPort()));
        }

        // Disable built-in Google Translate service
        list.add("--disable-translate");
        // Disable all chrome extensions entirely
        list.add("--disable-extensions");
        // Disable various background network services, including extension updating,
        // safe browsing service, upgrade detector, translate, UMA
        list.add("--disable-background-networking");
        // Disable fetching safebrowsing lists, likely redundant due to disable-background-networking
        list.add("--safebrowsing-disable-auto-update");
        // Disable syncing to a Google account
        list.add("--disable-sync");
        // Disable reporting to UMA, but allows for collection
        list.add("--metrics-recording-only");
        // Disable installation of default apps on first run
        list.add("--disable-default-apps");
        // Mute any audio
        list.add("--mute-audio");
        // Skip first run wizards
        list.add("--no-first-run");
        list.add("--no-default-browser-check");
        list.add("--disable-plugin-power-saver");
        list.add("--disable-popup-blocking");

        if (!arguments.isEmpty()) {
            list.addAll(arguments);
        }
        return list;
    }

    public abstract String findChrome();

    public boolean launched() {
        return launched;
    }

    public final SessionFactory launch() {
        return launch(findChrome(), emptyList());
    }

    public final SessionFactory launch(List<String> arguments) {
        return launch(findChrome(), arguments);
    }

    public final SessionFactory launch(String chromeExecutablePath, List<String> arguments) {
        if (chromeExecutablePath == null || chromeExecutablePath.trim().isEmpty()) {
            throw new CdpException("chrome not found");
        }
        if ( ! launched ) {
            List<String> list = getCommonParameters(chromeExecutablePath, arguments);
            internalLaunch(list, arguments);
            launched = true;
        }

        int     retryCount = 0;
        boolean connected  = factory.ping();

        while ( ! ( connected = factory.ping() ) && retryCount < 50 ) {
            try {
                sleep(100);
            } catch (InterruptedException e) {
                // ignore
            }
            retryCount += 1;
        }

        if ( ! connected ) {
            throw new CdpException("Unable to connect to the browser");
        }

        return factory;
    }

    protected abstract void internalLaunch(List<String> list, List<String> params);

    public abstract void kill();
}
