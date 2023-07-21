package com.xz.datacenter.datacollector.pdf.config;

import io.webfolder.cdp.Browser;
import io.webfolder.cdp.Options;
import io.webfolder.cdp.WfProcessManager;
import io.webfolder.cdp.channel.ChannelFactory;
import io.webfolder.cdp.exception.CdpException;
import io.webfolder.cdp.session.SessionFactory;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.ServerSocket;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;


public class Test {
    //浏览器标签Id
    static String targetId="";
    //contextId
    static String browserContextId;
    //页面sessionId
    static String sessionId="";
    //数据流
    static String ioHandle="";
    static String ioData="";
    static boolean ioEof=false;

    public static void main(String[] args) {
        
    }

    public static void test() {
        try {

//            Options options = Options.builder()
//                    .headless(true)
//                    .build();

//            Launcher launcher = new Launcher(options);
//            launcher.launch();


            String cmdID=Long.toHexString(ThreadLocalRandom.current().nextLong());

            //命令启动浏览器
            List<String> arguments=new ArrayList<>();
            String os = System.getProperty("os.name");
            if(os.toLowerCase().startsWith("windows")){
                arguments.add("C:\\Users\\Administrator\\AppData\\Local\\Google\\Chrome\\Application\\chrome.exe");
            }else if(os.toLowerCase().startsWith("linux")){
                arguments.add("google-chrome");
            }
            arguments.add("--disable-features=TranslateUI");
            arguments.add("--disable-extensions");
            arguments.add("--disable-background-networking");
            arguments.add("--safebrowsing-disable-auto-update");
            arguments.add("--disable-sync");
            arguments.add("--metrics-recording-only");
            arguments.add("--disable-default-apps");
            arguments.add("--mute-audio");
            arguments.add("--no-first-run");
            arguments.add("--no-default-browser-check");
            arguments.add("--disable-plugin-power-saver");
            arguments.add("--disable-popup-blocking");
            arguments.add("--remote-debugging-port=0");
            arguments.add("--user-data-dir=d:\\chromeHome\\2");
            arguments.add("--headless");
            //arguments.add("--cdp4jId="+cmdID);
            //arguments.add("--disable-gpu");
            // arguments.add("--enable-automation");

            Options options = Options.builder()
                    .headless(true)
                    .userDataDir(new File("d:/chromeHome/2").toPath())
                    .build();
            Launcher launcher = new Launcher(options);
            launcher.launch(arguments);
            //launchWithProcessBuilder(arguments,options,launcher);

            URI socketUri=null;


            //Process process=Runtime.getRuntime().exec(String.join(" ",arguments));
            ProcessBuilder builder = new ProcessBuilder(arguments);
            builder.environment().put("CDP4J_ID",cmdID);

            Process process=builder.start();


            Scanner scanner = new Scanner(process.getErrorStream());
            while (scanner.hasNext()){
                String line = scanner.nextLine().trim();
                if (!line.isEmpty() && line.toLowerCase().startsWith("devtools listening on")) {
                    int start = line.indexOf("ws://");
                    socketUri = new URI(line.substring(start, line.length()));
                    break;
                }
            }
            scanner.close();


        } catch (Exception e) {
            e.printStackTrace();
        }


    }



}








 class Launcher {
    private static final boolean JAVA_8 = System.getProperty("java.version").startsWith("1.8.");
    private static final String OS_NAME;
    private static final boolean WINDOWS;
    private static final boolean OSX;
    private final Options options;
    private final ChannelFactory channelFactory;

    public Launcher(Options options, ChannelFactory channelFactory) {
        this.options = options;
        this.channelFactory = channelFactory;
    }

    public Launcher(ChannelFactory channelFactory) {
        this(Options.builder().build(), channelFactory);
    }

    public Launcher(Options options) {
        this(options, createChannelFactory());
    }

    public Launcher() {
        this(Options.builder().build(), createChannelFactory());
    }

    protected String findChrome() {
        String executablePath = this.options.browserExecutablePath();
        if (executablePath != null && !executablePath.trim().isEmpty()) {
            return executablePath;
        } else if (WINDOWS) {
            return this.findChromeWinPath();
        } else {
            return OSX ? this.findChromeOsxPath() : "google-chrome";
        }
    }

    protected String findChromeWinPath() {
        try {
            Iterator var1 = this.getChromeWinPaths().iterator();

            InputStream is;
            String var8;
            while(true) {
                Process process;
                int exitCode;
                do {
                    if (!var1.hasNext()) {
                        return null;
                    }

                    String path = (String)var1.next();
                    process = Runtime.getRuntime().exec(new String[]{"cmd", "/c", "echo", path});
                    exitCode = process.waitFor();
                } while(exitCode != 0);

                is = process.getInputStream();

                try {
                    String location = this.toString(is).trim().replace("\"", "");
                    File chrome = new File(location);
                    if (chrome.exists() && chrome.canExecute()) {
                        var8 = chrome.toString();
                        break;
                    }
                } catch (Throwable var10) {
                    if (is != null) {
                        try {
                            is.close();
                        } catch (Throwable var9) {
                            var10.addSuppressed(var9);
                        }
                    }

                    throw var10;
                }

                if (is != null) {
                    is.close();
                }
            }

            if (is != null) {
                is.close();
            }

            return var8;
        } catch (Throwable var11) {
            return null;
        }
    }

    public boolean isChromeInstalled() {
        return this.findChrome() != null;
    }

    protected List<String> getChromeWinPaths() {
        Browser browser = this.options.getBrowser();
        List<String> prefixes = Arrays.asList("%localappdata%", "%programfiles%", "%programfiles(x86)%");
        List<String> suffixes = Collections.emptyList();
        switch(browser) {
            case Any:
                suffixes = Arrays.asList("\\Google\\Chrome Dev\\Application\\chrome.exe", "\\Google\\Chrome SxS\\Application\\chrome.exe", "\\Google\\Chrome\\Application\\chrome.exe", "\\Microsoft\\Edge\\Application\\msedge.exe");
            case Chrome:
            default:
                suffixes = Arrays.asList("\\Google\\Chrome\\Application\\chrome.exe");
                break;
            case ChromeCanary:
                suffixes = Arrays.asList("\\Google\\Chrome SxS\\Application\\chrome.exe");
                break;
            case ChromeDev:
                suffixes = Arrays.asList("\\\\Google\\\\Chrome Dev\\\\Application\\\\chrome.exe");
                break;
            case MicrosoftEdge:
                suffixes = Arrays.asList("\\Microsoft\\Edge\\Application\\msedge.exe");
        }

        List<String> installations = new ArrayList(prefixes.size() * suffixes.size());
        Iterator var5 = prefixes.iterator();

        while(var5.hasNext()) {
            String prefix = (String)var5.next();
            Iterator var7 = suffixes.iterator();

            while(var7.hasNext()) {
                String suffix = (String)var7.next();
                installations.add(prefix + suffix);
            }
        }

        return installations;
    }

    protected String findChromeOsxPath() {
        Iterator var1 = this.getChromeOsxPaths().iterator();

        File chrome;
        do {
            if (!var1.hasNext()) {
                return null;
            }

            String path = (String)var1.next();
            chrome = new File(path);
        } while(!chrome.exists() || !chrome.canExecute());

        return chrome.toString();
    }

    protected List<String> getChromeOsxPaths() {
        return Arrays.asList("/Applications/Google Chrome Canary.app/Contents/MacOS/Google Chrome Canary", "/Applications/Google Chrome.app/Contents/MacOS/Google Chrome");
    }

    protected List<String> getCommonParameters(String chromeExecutablePath, List<String> arguments) {
        List<String> list = new ArrayList();
        list.add(chromeExecutablePath);
        list.add("--disable-features=TranslateUI");
        list.add("--disable-extensions");
        list.add("--disable-background-networking");
        list.add("--safebrowsing-disable-auto-update");
        list.add("--disable-sync");
        list.add("--metrics-recording-only");
        list.add("--disable-default-apps");
        list.add("--mute-audio");
        list.add("--no-first-run");
        list.add("--no-default-browser-check");
        list.add("--disable-plugin-power-saver");
        list.add("--disable-popup-blocking");
        if (!arguments.isEmpty()) {
            list.addAll(arguments);
        }

        return list;
    }

    protected String toString(InputStream is) {
        Scanner scanner = new Scanner(is);

        String var3;
        try {
            scanner.useDelimiter("\\A");
            var3 = scanner.hasNext() ? scanner.next() : "";
        } catch (Throwable var6) {
            try {
                scanner.close();
            } catch (Throwable var5) {
                var6.addSuppressed(var5);
            }

            throw var6;
        }

        scanner.close();
        return var3;
    }

    public SessionFactory launch(List<String> argument) {
        //launchWithProcessBuilder(arguments);
        List<String> arguments = this.getCommonParameters(this.findChrome(), this.options.arguments());
        if (arguments.contains("--remote-debugging-pipe")) {
            arguments.remove("--remote-debugging-port=0");
        } else {
            arguments.add("--remote-debugging-port=0");
        }

        Path userDataDir = this.options.userDataDir();
        if (this.options.userDataDir() == null) {
            userDataDir = Paths.get(System.getProperty("java.io.tmpdir")).resolve("remote-profile");
        }

        arguments.add(String.format("--user-data-dir=%s", userDataDir.toString()));
        boolean inUse = this.isInUse(userDataDir);
        if (inUse) {
            throw new CdpException("--user-data-dir [" + userDataDir.toString() + "] is used by another process.");
        } else {
            if (this.options.headless()) {
                arguments.add("--headless");
            }

            SessionFactory factory = null;
            switch(this.options.processExecutor()) {
                case ProcessBuilder:
                    factory = this.launchWithProcessBuilder(argument);
                    break;
                case WfExec:
                    if (!WINDOWS) {
                        throw new CdpException("WfExec supports only Windows.");
                    }

                    if (!(this.options.processManager() instanceof WfProcessManager)) {
                        throw new CdpException("WfExec supports only WfProcessManager.");
                    }

                    //factory = WfExecLauncher.launchWithWfExec(this.options, this.channelFactory, arguments);
            }

            //return factory;
        }
        return null;
    }

    private boolean isInUse(Path userDataDir) {
        Path devToolsActivePort = userDataDir.resolve("DevToolsActivePort");
        if (Files.exists(devToolsActivePort, new LinkOption[0])) {
            List lines = Collections.emptyList();

            try {
                lines = Files.readAllLines(devToolsActivePort);
            } catch (IOException var11) {
                throw new CdpException(var11);
            }

            if (lines.size() >= 1) {
                int port = Integer.parseInt((String)lines.get(0));
                if (port > 0) {
                    try {
                        ServerSocket ignored = new ServerSocket(port);

                        boolean var6;
                        try {
                            var6 = false;
                        } catch (Throwable var9) {
                            try {
                                ignored.close();
                            } catch (Throwable var8) {
                                var9.addSuppressed(var8);
                            }

                            throw var9;
                        }

                        ignored.close();
                        return var6;
                    } catch (IOException var10) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    private SessionFactory launchWithProcessBuilder(List<String> arguments) {
        ProcessBuilder builder = new ProcessBuilder(arguments);
        try {
            Process process = builder.start();
            Scanner scanner = new Scanner(process.getErrorStream());

            while(scanner.hasNext()) {
                String line = scanner.nextLine().trim();
                if (!line.isEmpty() && line.toLowerCase(Locale.ENGLISH).startsWith("devtools listening on")) {
                    break;
                }
            }
        } catch (IOException var11) {
            throw new CdpException(var11);
        }

        return null;
    }

    protected static ChannelFactory createChannelFactory() {
        try {
            Class<?> klass = null;
            if (!JAVA_8) {
                klass = io.webfolder.cdp.Launcher.class.getClassLoader().loadClass("io.webfolder.cdp.channel.JreWebSocketFactory");
            } else {
                klass = io.webfolder.cdp.Launcher.class.getClassLoader().loadClass("io.webfolder.cdp.channel.NvWebSocketFactory");
            }

            Constructor<?> constructor = klass.getConstructor();
            return (ChannelFactory)constructor.newInstance();
        } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | SecurityException | IllegalArgumentException | InvocationTargetException | ClassNotFoundException var2) {
            throw new CdpException(var2);
        }
    }

    public boolean kill() {
        return this.options.processManager().kill();
    }

    public Options getOptions() {
        return this.options;
    }

    static {
        OS_NAME = System.getProperty("os.name").toLowerCase(Locale.ENGLISH);
        WINDOWS = OS_NAME.startsWith("windows");
        OSX = OS_NAME.startsWith("mac");
    }
}
