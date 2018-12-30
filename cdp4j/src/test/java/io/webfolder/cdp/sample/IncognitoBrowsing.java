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
package io.webfolder.cdp.sample;

import static java.util.Arrays.asList;

import io.webfolder.cdp.AdaptiveProcessManager;
import io.webfolder.cdp.Launcher;
import io.webfolder.cdp.session.Session;
import io.webfolder.cdp.session.SessionFactory;

public class IncognitoBrowsing {

    // Requires Headless Chrome
    // https://chromium.googlesource.com/chromium/src/+/lkgr/headless/README.md
    public static void main(String[] args) {
        Launcher launcher = new Launcher();
        launcher.setProcessManager(new AdaptiveProcessManager());

        try (SessionFactory factory = launcher.launch(asList("--headless", "--disable-gpu"))) {

            String firstContext = null;

            try (Session firstSession = factory.create()) {
                firstContext = firstSession.getBrowserContextId();
                firstSession.navigate("https://httpbin.org/cookies/set?SESSION_ID=1");
                firstSession.waitDocumentReady();
                String session1Content = (String) firstSession.evaluate("window.document.body.textContent");
                System.err.println(session1Content);
            }

            // firstSession & anotherSession share same SESSSION_ID value

            try (Session anotherSession = factory.create(firstContext)) {
                anotherSession.navigate("https://httpbin.org/cookies");
                anotherSession.waitDocumentReady();
                String anotherSessionContent = (String) anotherSession.evaluate("window.document.body.textContent");
                System.err.println(anotherSessionContent);
            }

            String  secondContext = factory.createBrowserContext();
            try (Session secondSession = factory.create(secondContext)) {
                secondSession.navigate("https://httpbin.org/cookies");
                secondSession.waitDocumentReady();
                String session2Content = (String) secondSession.evaluate("window.document.body.textContent");
                System.err.println(session2Content); 
            }

            // Dispose first context
            factory.disposeBrowserContext(firstContext);

            // Dispose second context
            factory.disposeBrowserContext(secondContext);

            launcher.getProcessManager().kill();
        }
    }
}
