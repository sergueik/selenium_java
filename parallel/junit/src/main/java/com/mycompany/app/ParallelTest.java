package com.mycompany.app;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.junit.runners.Parameterized;
import org.junit.runners.model.RunnerScheduler;

public class ParallelTest extends Parameterized {

  private static class ThreadPoolScheduler implements RunnerScheduler {
    private ExecutorService executor;

    public ThreadPoolScheduler() {
      String threads = System.getProperty("junit.parallel.threads", "3");
      int numThreads = Integer.parseInt(threads);
      executor = Executors.newFixedThreadPool(numThreads);
    }

    @Override
    public void finished() {
      executor.shutdown();
      try {
        executor.awaitTermination(10, TimeUnit.MINUTES);
      } catch (InterruptedException e) {
        throw new RuntimeException(e);
      }
    }

    @Override
    public void schedule(Runnable childStatement) {
      executor.submit(childStatement);
    }
  }

  public ParallelTest(Class<?> _class) throws Throwable {
    super(_class);
    setScheduler(new ThreadPoolScheduler());
  }
}