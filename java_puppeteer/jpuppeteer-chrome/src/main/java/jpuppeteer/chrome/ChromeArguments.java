package jpuppeteer.chrome;

import com.google.common.collect.Lists;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.IOException;
import java.util.AbstractMap;
import java.util.List;
import java.util.Map;

public class ChromeArguments {

    private static final String[] DEFAULT_ARGS = {
            "--disable-background-networking",
            "--enable-features=NetworkService,NetworkServiceInProcess",
            "--disable-background-timer-throttling",
            "--disable-backgrounding-occluded-windows",
            "--disable-breakpad",
            "--disable-client-side-phishing-detection",
            "--disable-default-apps",
            "--disable-dev-shm-usage",
            "--disable-extensions",
            // TODO: Support OOOPIF. @see https://github.com/GoogleChrome/puppeteer/issues/2548
            // BlinkGenPropertyTrees disabled due to crbug.com/937609
            "--disable-features=site-per-process,TranslateUI,BlinkGenPropertyTrees",
            "--disable-hang-monitor",
            "--disable-ipc-flooding-protection",
            "--disable-popup-blocking",
            "--disable-prompt-on-repost",
            "--disable-renderer-backgrounding",
            "--disable-sync",
            "--force-color-profile=srgb",
            "--metrics-recording-only",
            "--no-first-run",
            //"--enable-automation",
            "--password-store=basic",
            "--use-mock-keychain",
    };

    private String executable;

    private boolean headless = false;

    private boolean pipe = false;

    private boolean useTempUserData = true;

    private String userDataDir;

    //填充默认参数
    private List<String> args;

    private ChromeArguments(String executable) {
        this.executable = executable;
        this.args = Lists.newArrayList(DEFAULT_ARGS);
    }

    public boolean isHeadless() {
        return headless;
    }

    public boolean isPipe() {
        return pipe;
    }

    public boolean isUseTempUserData() {
        return useTempUserData;
    }

    public String getUserDataDir() {
        return userDataDir;
    }

    public String[] getCommand() {
        String[] command = new String[args.size() + 1];
        command[0] = executable;
        for(int i=0; i<args.size(); i++) {
            command[i+1] = args.get(i);
        }
        return command;
    }

    private static Map.Entry<String, String> parseArg(String arg) {
        int offset = arg.indexOf("=");
        if (offset == -1) {
            return new AbstractMap.SimpleEntry<>(arg, null);
        } else {
            return new AbstractMap.SimpleEntry<>(arg.substring(0, offset), arg.substring(offset+1));
        }
    }

    public static ChromeArguments parse(String executable, String... args) throws IOException {
        ChromeArguments arguments = new ChromeArguments(executable);
        boolean hasDebugPort = false;
        for(String arg : args) {
            if (arg.startsWith("--remote-debugging-pipe")) {
                //指定pipe作为输入输出通道, 而不是使用websocket
                arguments.pipe = true;
            } else if (arg.startsWith("--user-data-dir=")) {
                //用户文件夹(书签, cookie等等)
                arguments.userDataDir = parseArg(arg).getValue();
                arguments.useTempUserData = false;
            } else if (arg.startsWith("--headless")) {
                //headless 模式
                arguments.headless = true;
            } else if (arg.startsWith("--remote-debugging-port")) {
                hasDebugPort = true;
            }
        }
        CollectionUtils.addAll(arguments.args, args);
        if (arguments.headless) {
            CollectionUtils.addAll(arguments.args, "--hide-scrollbars", "--mute-audio");
        }
        if (StringUtils.isEmpty(arguments.userDataDir)) {
            //如果没有指定用户文件夹, 则使用临时文件夹
            String tmpDir = System.getProperty("java.io.tmpdir");
            if (StringUtils.isEmpty(tmpDir)) {
                throw new IOException("未指定系统临时目录(java.io.tmpdir)");
            }
            tmpDir = StringUtils.stripEnd(tmpDir, "\\/");
            File userDataDir = new File(tmpDir + "/chrome-user-data-" + System.currentTimeMillis());
            if (!userDataDir.mkdir()) {
                throw new IOException("创建临时文件夹[" + userDataDir.getAbsolutePath() + "]失败");
            }
            arguments.userDataDir = userDataDir.getAbsolutePath();
            arguments.args.add("--user-data-dir=" + userDataDir.getAbsolutePath());
        }
        if (!arguments.pipe) {
            if (!hasDebugPort) {
                arguments.args.add("--remote-debugging-port=0");
            }
            arguments.args.add("--remote-debugging-address=0.0.0.0");
        }
        arguments.args.add("about:blank");
        return arguments;
    }

}
