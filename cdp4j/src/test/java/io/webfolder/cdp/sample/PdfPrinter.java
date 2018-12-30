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

import static java.lang.Thread.sleep;
import static java.util.Arrays.asList;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

import io.webfolder.cdp.AdaptiveProcessManager;
import io.webfolder.cdp.Launcher;
import io.webfolder.cdp.session.Session;
import io.webfolder.cdp.session.SessionFactory;

public class PdfPrinter implements AutoCloseable {

    private SessionFactory factory;

    private Timer timer = new Timer("cdp4j-task-manager", true);

    private Launcher launcher;

    private ExecutorService pool = Executors.newCachedThreadPool();

    private List<String> arguments;

    public PdfPrinter(List<String> arguments) {
        this.arguments = Collections.unmodifiableList(arguments);
    }
    
    public void execute(Consumer<Session> func) {
        pool.execute(() -> {
            String context = factory.createBrowserContext();
            Session session = factory.create(context);
            try {
                func.accept(session);
            } catch (Throwable t) {
                throw new RuntimeException(t);
            } finally {
                if ( session != null ) {
                    try {
                        session.close();
                    } catch (Throwable t) {
                        // ignore
                    }
                }
                if (factory.ping()) {
                    factory.disposeBrowserContext(context);
                }
            }
        });
    }

    public void init() {
        launcher = new Launcher();
        launcher.setProcessManager(new AdaptiveProcessManager());

        factory = launcher.launch(arguments);

        int delay = 1000;

        timer.scheduleAtFixedRate(new TimerTask() {

            @Override
            public void run() {
                if ( factory != null ) {
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
                        restart();
                    }
                }
            }
        }, delay, delay);
    }

    protected void restart() {
        SessionFactory oldFactory = factory;
        Thread shutdownThread = new Thread(() -> {
            try {
                oldFactory.close();
            } catch (Throwable t) {
                // ignore
            }
        });
        shutdownThread.setDaemon(true);
        shutdownThread.setName("cdp4j-task-manager");
        shutdownThread.start();

        launcher.kill();
        launcher = new Launcher();

        factory = launcher.launch();
    }

    @Override
    public void close() {
        pool.shutdown();
        timer.cancel();
        factory.close();
        launcher.kill();
    }

    public static void main(String[] args) {        
        PdfPrinter manager = new PdfPrinter(Arrays.asList("--disable-gpu", "--headless"));
        manager.init();

        List<String> urls = asList(
                                    "http://www.google.com",
                                    "http://www.bing.com");

        CountDownLatch latch = new CountDownLatch(urls.size());

        for (String next : urls) {
            manager.execute(new Consumer<Session>() {

                @Override
                public void accept(Session s) {
                    s.navigate(next);
                    try {
                        s.waitDocumentReady();
                        byte[] content = s.getCommand().getPage().printToPDF();
                        if ( content != null ) {
                            System.out.println("PDF size: " + content.length + " (" + next + ")");
                        }
                    } catch (Throwable e) {
                        //
                    } finally {
                        latch.countDown();
                    }
                }
            });
        }

        try {
            latch.await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            manager.close();   
        }
    }
}
