package org.richardinnocent.timeservice.services.callbacks;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.time.ZonedDateTime;
import java.util.function.Supplier;

import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CurrentTimeCallbackTest {

  private final HttpClient httpClient = mock(HttpClient.class);

  private final URI uri; // can't mock as it's final

  @SuppressWarnings("unchecked")
  private final Supplier<ZonedDateTime> timeSupplier = mock(Supplier.class);

  private final ObjectMapper objectMapper = mock(ObjectMapper.class);

  {
    try {
      uri = new URI("https://test.com");
    } catch (URISyntaxException e) {
      throw new RuntimeException("Failed to create URI", e);
    }
  }

  private final Runnable callback =
      new CurrentTimeCallback(uri, httpClient, objectMapper, timeSupplier);

  @Test
  public void run_FailsToRequestBody_ExceptionThrown() throws Exception {
    when(objectMapper.writeValueAsString(any())).thenThrow(mock(JsonProcessingException.class));
    assertThrows(RuntimeException.class, callback::run);
  }

  @Test
  public void run_FailsToSendRequest_ExceptionThrown() throws Exception {
    when(objectMapper.writeValueAsString(any())).thenReturn("test body");
    when(httpClient.send(any(), any())).thenThrow(new IOException("test exception"));
    assertThrows(DeliverUpdateException.class, callback::run);
  }

  @Test
  public void run_SendsSuccessfully_NoExceptionThrown() throws Exception {
    when(objectMapper.writeValueAsString(any())).thenReturn("test body");
    callback.run();
  }

}