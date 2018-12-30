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
import static java.lang.Integer.compare;
import static java.lang.Integer.parseInt;
import static java.lang.Math.round;
import static java.lang.String.format;
import static java.lang.String.valueOf;
import static java.lang.System.getProperty;
import static java.lang.Thread.sleep;
import static java.nio.file.Files.copy;
import static java.nio.file.Files.createDirectories;
import static java.nio.file.Files.delete;
import static java.nio.file.Files.exists;
import static java.nio.file.Files.getPosixFilePermissions;
import static java.nio.file.Files.isDirectory;
import static java.nio.file.Files.isExecutable;
import static java.nio.file.Files.list;
import static java.nio.file.Files.setPosixFilePermissions;
import static java.nio.file.Files.size;
import static java.nio.file.Paths.get;
import static java.nio.file.attribute.PosixFilePermission.GROUP_EXECUTE;
import static java.nio.file.attribute.PosixFilePermission.GROUP_READ;
import static java.nio.file.attribute.PosixFilePermission.GROUP_WRITE;
import static java.nio.file.attribute.PosixFilePermission.OTHERS_EXECUTE;
import static java.nio.file.attribute.PosixFilePermission.OTHERS_READ;
import static java.nio.file.attribute.PosixFilePermission.OTHERS_WRITE;
import static java.nio.file.attribute.PosixFilePermission.OWNER_EXECUTE;
import static java.nio.file.attribute.PosixFilePermission.OWNER_READ;
import static java.nio.file.attribute.PosixFilePermission.OWNER_WRITE;
import static java.util.Locale.ENGLISH;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.PosixFilePermission;
import java.util.Collections;
import java.util.EnumSet;
import java.util.Enumeration;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipFile;
import org.apache.commons.compress.utils.IOUtils;

import io.webfolder.cdp.exception.CdpException;
import io.webfolder.cdp.logger.CdpLogger;
import io.webfolder.cdp.logger.CdpLoggerFactory;
import io.webfolder.cdp.logger.LoggerFactory;

public class ChromiumDownloader implements Downloader {

    private static final String OS            = getProperty("os.name").toLowerCase(ENGLISH);

    private static final boolean WINDOWS      = ";".equals(pathSeparator);

    private static final boolean MAC          = OS.contains("mac");

    private static final boolean LINUX        = OS.contains("linux");

    private static final String DOWNLOAD_HOST = "https://storage.googleapis.com/chromium-browser-snapshots";

    private static final int TIMEOUT          = 10 * 1000; // 10 seconds

    private static final PosixFilePermission[] DECODE_MAP = {
            OTHERS_EXECUTE,
            OTHERS_WRITE,
            OTHERS_READ,
            GROUP_EXECUTE,
            GROUP_WRITE,
            GROUP_READ,
            OWNER_EXECUTE,
            OWNER_WRITE, OWNER_READ
    };

    private final CdpLogger logger;

    public ChromiumDownloader() {
        this(new CdpLoggerFactory());
    }

    public ChromiumDownloader(LoggerFactory loggerFactory) {
        this.logger = loggerFactory.getLogger("cdp4j.downloader");
    }

    @Override
    public Path download() {
        return download(getLatestVersion());
    }

    public static ChromiumVersion getLatestVersion() {
        String url = DOWNLOAD_HOST;

        if ( WINDOWS ) {
            url += "/Win_x64/LAST_CHANGE";
        } else if ( LINUX ) {
            url += "/Linux_x64/LAST_CHANGE";
        } else if ( MAC ) {
            url += "/Mac/LAST_CHANGE";
        } else {
            throw new CdpException("Unsupported OS found - " + OS);
        }

        try {
            URL u = new URL(url);

            HttpURLConnection conn = (HttpURLConnection) u.openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(TIMEOUT);
            conn.setReadTimeout(TIMEOUT);

            if ( conn.getResponseCode() != 200 ) {
                throw new CdpException(conn.getResponseCode() + " - " + conn.getResponseMessage());
            }

            String result = null;
            try (Scanner s = new Scanner(conn.getInputStream())) {
                s.useDelimiter("\\A");
                result = s.hasNext() ? s.next() : "";
            }
            return new ChromiumVersion(Integer.parseInt(result));
        } catch (IOException e) {
            throw new CdpException(e);
        }
    }

    public static Path getChromiumPath(ChromiumVersion version) {
        Path destinationRoot = get(getProperty("user.home"))
                                .resolve(".cdp4j")
                                .resolve("chromium-" + valueOf(version.getRevision()));
        return destinationRoot;
    }

    public static Path getExecutable(ChromiumVersion version) {
        Path destinationRoot = getChromiumPath(version);
        Path executable = destinationRoot.resolve("chrome.exe");
        if ( LINUX ) {
            executable = destinationRoot.resolve("chrome");
        } else if ( MAC ) {
            executable = destinationRoot.resolve("Chromium.app/Contents/MacOS/Chromium");
        }
        return executable;
    }

    public Path download(ChromiumVersion version) {
        final Path destinationRoot = getChromiumPath(version);
        final Path executable = getExecutable(version);

        String url;
        if ( WINDOWS ) {
            url = format("%s/Win_x64/%d/chrome-win32.zip", DOWNLOAD_HOST, version.getRevision());
        } else if ( LINUX ) {
            url = format("%s/Linux_x64/%d/chrome-linux.zip", DOWNLOAD_HOST, version.getRevision());
        } else if ( MAC ) {
            url = format("%s/Mac/%d/chrome-mac.zip", DOWNLOAD_HOST, version.getRevision());
        } else {
            throw new CdpException("Unsupported OS found - " + OS);
        }

        try {
            URL u = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) u.openConnection();
            conn.setRequestMethod("HEAD");
            conn.setConnectTimeout(TIMEOUT);
            conn.setReadTimeout(TIMEOUT);
            if ( conn.getResponseCode() != 200 ) {
                throw new CdpException(conn.getResponseCode() + " - " + conn.getResponseMessage());
            }
            long contentLength = conn.getHeaderFieldLong("x-goog-stored-content-length", 0);
            String fileName = url.substring(url.lastIndexOf("/") + 1, url.lastIndexOf(".")) + "-r" + version.getRevision() + ".zip";
            Path archive = get(getProperty("java.io.tmpdir")).resolve(fileName);
            if ( exists(archive) && contentLength != size(archive) ) {
                delete(archive);
            }
            if ( ! exists(archive) ) {
                logger.info("Downloading Chromium [revision=" + version.getRevision() + "] 0%");
                u = new URL(url);
                if ( conn.getResponseCode() != 200 ) {
                    throw new CdpException(conn.getResponseCode() + " - " + conn.getResponseMessage());
                }
                conn = (HttpURLConnection) u.openConnection();
                conn.setConnectTimeout(TIMEOUT);
                conn.setReadTimeout(TIMEOUT);
                Thread thread = null;
                AtomicBoolean halt = new AtomicBoolean(false);
                Runnable progress = () -> {
                    try {
                        long fileSize = size(archive);
                        logger.info("Downloading Chromium [revision={}] {}%",
                                version.getRevision(), round((fileSize * 100L) / contentLength));
                    } catch (IOException e) {
                        // ignore
                    }
                };
                try (InputStream is = conn.getInputStream()) {
                    logger.info("Download location: " + archive.toString());
                    thread = new Thread(() -> {
                        while (true) {
                            try {
                                if (halt.get()) {
                                    break;
                                }
                                progress.run();
                                sleep(1000);
                            } catch (Throwable e) {
                                // ignore
                            }
                        }
                    });
                    thread.setName("cdp4j");
                    thread.setDaemon(true);
                    thread.start();
                    copy(conn.getInputStream(), archive);
                } finally {
                    if ( thread != null ) {
                        progress.run();
                        halt.set(true);
                    }
                }
            }
            logger.info("Extracting to: " + destinationRoot.toString());
            if (exists(archive)) {
                createDirectories(destinationRoot);
                unpack(archive.toFile(), destinationRoot.toFile());
            }

            if ( ! exists(executable) || ! isExecutable(executable) ) {
                throw new CdpException("Chromium executable not found: " + executable.toString());
            }

            if ( ! WINDOWS ) {
                Set<PosixFilePermission> permissions = getPosixFilePermissions(executable);
                if ( ! permissions.contains(OWNER_EXECUTE)) {
                    permissions.add(OWNER_EXECUTE);
                    setPosixFilePermissions(executable, permissions);
                }
                if ( ! permissions.contains(GROUP_EXECUTE) ) {
                    permissions.add(GROUP_EXECUTE);
                    setPosixFilePermissions(executable, permissions);
                }
            }
        } catch (IOException e) {
            throw new CdpException(e);
        }
        return executable;
    }

    public static List<ChromiumVersion> getInstalledVersions() {
        Path chromiumRootPath = get(getProperty("user.home")).resolve(".cdp4j");
        if ( ! Files.exists(chromiumRootPath) ) {
            return Collections.emptyList();
        }
        try {
            List<ChromiumVersion> list = list(chromiumRootPath)
                                            .filter(p -> isDirectory(p))
                                            .filter(p -> p.getFileName().toString().startsWith("chromium-"))
                                            .map(p -> new ChromiumVersion(parseInt(p.getFileName().toString().split("-")[1])))
                                        .collect(Collectors.toList());
            list.sort((o1, o2) -> compare(o2.getRevision(), o1.getRevision()));
            return list;
        } catch (IOException e) {
            throw new CdpException(e);
        }
    }

    public static ChromiumVersion getLatestInstalledVersion() {
        List<ChromiumVersion> versions = getInstalledVersions();
        return ! versions.isEmpty() ? versions.get(0) : null;
    }

    private static void unpack(File archive, File destionation) throws IOException {
        try (ZipFile zip = new ZipFile(archive)) {
            Map<File, String> symLinks = new LinkedHashMap<>();
            Enumeration<ZipArchiveEntry> iterator = zip.getEntries();
            // Top directory name we are going to ignore
            String parentDirectory = iterator.nextElement().getName();
            // Iterate files & folders
            while (iterator.hasMoreElements()) {
                ZipArchiveEntry entry = iterator.nextElement();
                String name = entry.getName().substring(parentDirectory.length());
                File outputFile = new File(destionation, name);
                if ( name.startsWith("interactive_ui_tests") ) {
                    continue;
                }
                if (entry.isUnixSymlink()) {
                    symLinks.put(outputFile, zip.getUnixSymlink(entry));
                } else if ( ! entry.isDirectory() ) {
                    if ( ! outputFile.getParentFile().isDirectory() ) {
                        outputFile.getParentFile().mkdirs();
                    }
                    try (FileOutputStream outStream = new FileOutputStream(outputFile)) {
                        IOUtils.copy(zip.getInputStream(entry), outStream);
                    }
                }
                // Set permission
                if ( ! entry.isUnixSymlink() && outputFile.exists() )
                    try {
                        Files.setPosixFilePermissions(outputFile.toPath(), modeToPosixPermissions(entry.getUnixMode()));
                    } catch (Exception e) {
                        // ignore
                    }
            }
            for (Map.Entry<File, String> entry : symLinks.entrySet()) {
                try {
                    Path source = Paths.get(entry.getKey().getAbsolutePath());
                    Path target = source.getParent().resolve(entry.getValue());
                    if ( !source.toFile().exists() )
                        Files.createSymbolicLink(source, target);
                } catch (Exception e) {
                    // ignore
                }
            }
        }
    }

    private static Set<PosixFilePermission> modeToPosixPermissions(final int mode) {
        int mask = 1;
        Set<PosixFilePermission> perms = EnumSet.noneOf(PosixFilePermission.class);
        for (PosixFilePermission flag : DECODE_MAP) {
            if ( (mask & mode) != 0 ) {
                perms.add(flag);
            }
            mask = mask << 1;
        }
        return perms;
    }
}
