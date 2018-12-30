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

import static java.util.Arrays.asList;

import java.util.List;

import io.webfolder.cdp.Launcher;
import io.webfolder.cdp.session.Session;
import io.webfolder.cdp.session.SessionFactory;

public class BingTranslator {

    public static void main(String[] args) {
        Launcher launcher = new Launcher();

        List<String> words = asList("hello", "world");

        try (SessionFactory factory = launcher.launch();
                            Session session = factory.create()) {
            session
                .navigate("https://www.bing.com/translator")
                .waitDocumentReady()
                .enableConsoleLog()
                .enableDetailLog()
                .enableNetworkLog();

            session
                .click(".sourceText #LS_LangList") // click source language
                .wait(500)
                .click(".sourceText td[value='en']") // choose English
                .wait(500)
                .click(".destinationText #LS_LangList") // click destination language
                .wait(500)
                .click(".destinationText td[value='et']") // choose Estonian
                .wait(500);

            StringBuilder builder = new StringBuilder();
            
            for (String word : words) {
                String text = session
                                .focus(".sourceText .srcTextarea")
                                .selectInputText(".sourceText .srcTextarea")
                                .sendBackspace()
                                .sendKeys(word)
                                .wait(1000)
                                .getText("#destText");
                
                builder.append(text).append(" ");
            }
            
            System.out.println(builder.toString());
        }
    }
}
