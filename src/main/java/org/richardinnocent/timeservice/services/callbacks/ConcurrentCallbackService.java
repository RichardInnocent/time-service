package org.richardinnocent.timeservice.services.callbacks;

import java.net.URI;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

public class ConcurrentCallbackService implements CallbackService {

  private final ScheduledExecutorService scheduler;
  private final CurrentTimeCallbackTaskFactory taskFactory;
  private final Map<URI, ScheduledFuture<?>> scheduledTasks = new ConcurrentHashMap<>();

  public ConcurrentCallbackService(int threadCount, CurrentTimeCallbackTaskFactory taskFactory) {
    this(Executors.newScheduledThreadPool(threadCount), taskFactory);
  }

  public ConcurrentCallbackService(
      ScheduledExecutorService scheduler,
      CurrentTimeCallbackTaskFactory taskFactory
  ) {
    this.scheduler = scheduler;
    this.taskFactory = taskFactory;
  }

  @Override
  public void addCallback(URI uri, int frequencySeconds) throws CallbackAlreadyExistsException {
    Runnable task = taskFactory.createCallback(uri);
    // Rather than putting our own synchronisation block around this, let's rely on the Java to do
    // this more optimally via the ConcurrentHashMap. We do need to know if the state changed though
    AtomicBoolean callbackChanged = new AtomicBoolean(false);
    scheduledTasks.computeIfAbsent(uri, previous -> {
      callbackChanged.set(true);
      return scheduler.schedule(task, frequencySeconds, TimeUnit.SECONDS);
    });

    if (!callbackChanged.get()) {
      throw new CallbackAlreadyExistsException(uri);
    }
  }

  @Override
  public void updateCallback(URI uri, int frequencySeconds) throws CallbackNotFoundException {
    Runnable task = taskFactory.createCallback(uri);
    synchronized (scheduledTasks) {
      var oldTask = scheduledTasks.get(uri);
      if (oldTask == null) {
        throw new CallbackNotFoundException(uri);
      }
      oldTask.cancel(false);
      scheduledTasks.put(uri, scheduler.schedule(task, frequencySeconds, TimeUnit.SECONDS));
    }
  }

  @Override
  public void removeCallback(URI uri) throws CallbackNotFoundException {
    var scheduledTask = scheduledTasks.remove(uri);

    if (scheduledTask == null) {
      throw new CallbackNotFoundException(uri);
    }

    scheduledTask.cancel(false);
  }
}
