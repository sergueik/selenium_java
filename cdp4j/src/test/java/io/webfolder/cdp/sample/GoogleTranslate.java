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
package io.webfolder.cdp.sample;

import static java.lang.Boolean.TRUE;

import java.util.HashMap;
import java.util.Map;

import io.webfolder.cdp.Launcher;
import io.webfolder.cdp.command.Network;
import io.webfolder.cdp.session.Session;
import io.webfolder.cdp.session.SessionFactory;

public class GoogleTranslate {

    public static void main(String[] args) {
        Launcher launcher = new Launcher();

        try (SessionFactory factory = launcher.launch();
                                Session session = factory.create()) {
            session.installSizzle();

            Network network = session.getCommand().getNetwork();
            network.enable();
            network.setCacheDisabled(TRUE);

            Map<String, Object> headers = new HashMap<>();
            headers.put("Accept-Language", "en-US,en;q=1");
            headers.put("Cookie", "");
            network.setExtraHTTPHeaders(headers);
            
            session.navigate("https://translate.google.co.uk");
            session.waitDocumentReady();

            String appName = session.getText("#gt-appname");
            if ( ! appName.equals("Translate") ) {
                session.clearCookies();
                session.wait(1000);
                network.setExtraHTTPHeaders(headers);
                session.reload();
            }
            
            session.click("#gt-sl-gms")
                    .wait(500)
                    .click("div:contains('English'):last")
                    .wait(500)
                    .click("#gt-tl-gms")
                    .wait(500)
                    .click("div:contains('Estonian'):last")
                    .wait(500)
                    .focus("textarea:first")
                    .wait(500)
                    .sendKeys("hello world")
                    .wait(1000);

            System.out.println(session.getText("#gt-res-dir-ctr"));
        }
    }
}
