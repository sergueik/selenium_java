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

import io.webfolder.cdp.RemoteLauncher;
import io.webfolder.cdp.RemoteLauncherBuilder;
import io.webfolder.cdp.session.SessionFactory;

import java.io.*;
import java.nio.charset.StandardCharsets;

import static java.util.Arrays.asList;

public class RemoteLaunching {

    @SuppressWarnings("unused")
    public static void main(String[] args) throws IOException {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);

        try (BufferedReader br = new BufferedReader(new InputStreamReader(
                new FileInputStream(new File("path/to/your/private/key")),
                StandardCharsets.UTF_8))) {
            String s;
            while ( (s = br.readLine()) != null)  {
                pw.println(s);
            }
        }

        RemoteLauncher l = new RemoteLauncherBuilder()
                .withHost("1.2.3.4").withChromePort(12345)
                .withUser("chromeuser")
                .withPrivateKey(sw.toString())
                .create();

        SessionFactory sf = l.launch(asList("--headless", "--disable-gpu"));

        l.kill();
    }
}
