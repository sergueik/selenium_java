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
