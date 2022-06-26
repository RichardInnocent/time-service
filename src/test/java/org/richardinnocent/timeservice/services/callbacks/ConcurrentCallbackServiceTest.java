package org.richardinnocent.timeservice.services.callbacks;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class ConcurrentCallbackServiceTest {

  private static final URI URI;
  static {
    try {
      URI = new URI("https://test.com");
    } catch (URISyntaxException e) {
      throw new RuntimeException("Could not create test URI", e);
    }
  }

  private final ScheduledExecutorService scheduler = mock(ScheduledExecutorService.class);
  private final CurrentTimeCallbackTaskFactory taskFactory =
      mock(CurrentTimeCallbackTaskFactory.class);
  private final CallbackService service =
      new ConcurrentCallbackService(scheduler, taskFactory);
  private final CurrentTimeCallback callbackTask = mock(CurrentTimeCallback.class, "default task");

  @BeforeEach
  void setUp() {
    when(taskFactory.createCallback(eq(URI))).thenReturn(callbackTask);
    doReturn(mock(ScheduledFuture.class, "default scheduled task"))
        .when(scheduler)
        .scheduleAtFixedRate(eq(callbackTask), anyLong(), anyLong(), eq(TimeUnit.SECONDS));
  }

  @Test
  void addCallback_UriNotRegistered_CallbackIsAdded() {
    int frequencySeconds = 100;
    service.addCallback(URI, frequencySeconds);
    verify(scheduler, times(1))
        .scheduleAtFixedRate(callbackTask, 0, frequencySeconds, TimeUnit.SECONDS);
  }

  @Test
  void addCallback_UriAlreadyRegistered_ExceptionThrown() {
    service.addCallback(URI, 100);
    assertThrows(CallbackAlreadyExistsException.class, () -> service.addCallback(URI, 50));
  }

  @Test
  void updateCallback_UriAlreadyRegistered_OriginalCallbackCancelledAndNewCallbackCreated() {
    int originalFrequencySeconds = 100;
    ScheduledFuture<?> originalScheduledFuture =
        mock(ScheduledFuture.class, "original scheduled task");
    doReturn(originalScheduledFuture)
        .when(scheduler)
        .scheduleAtFixedRate(
            eq(callbackTask),
            eq(0L),
            eq((long) originalFrequencySeconds),
            eq(TimeUnit.SECONDS)
        );

    service.addCallback(URI, originalFrequencySeconds);
    verify(scheduler, times(1))
        .scheduleAtFixedRate(callbackTask, 0,  originalFrequencySeconds, TimeUnit.SECONDS);

    int newFrequencySeconds = 50;
    service.updateCallback(URI, newFrequencySeconds);
    verify(originalScheduledFuture, times(1)).cancel(false);
    verify(scheduler, times(1))
        .scheduleAtFixedRate(
            callbackTask,
            0L,
            newFrequencySeconds,
            TimeUnit.SECONDS
        );
  }

  @Test
  void updateCallback_UriNotRegistered_ExceptionThrown() {
    assertThrows(CallbackNotFoundException.class, () -> service.updateCallback(URI, 50));
  }

  @Test
  void removeCallback_UriAlreadyRegistered_CallbackCancelledAndDeleted() {
    int frequencySeconds = 100;
    ScheduledFuture<?> scheduledFuture =
        mock(ScheduledFuture.class, "scheduled task");
    doReturn(scheduledFuture)
        .when(scheduler)
        .scheduleAtFixedRate(
            eq(callbackTask),
            eq(0L),
            eq((long) frequencySeconds),
            eq(TimeUnit.SECONDS)
        );

    service.addCallback(URI, frequencySeconds);
    verify(scheduler, times(1))
        .scheduleAtFixedRate(callbackTask, 0, frequencySeconds, TimeUnit.SECONDS);

    service.removeCallback(URI);
    verify(scheduledFuture, times(1)).cancel(false);
  }

  @Test
  void removeCallback_UriNotRegistered_ExceptionThrown() {
    assertThrows(CallbackNotFoundException.class, () -> service.removeCallback(URI));
  }

}