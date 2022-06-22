package org.richardinnocent.timeservice.services.callbacks;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;

import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class CurrentTimeCallbackTaskFactoryImplTest {

  private final HttpClient httpClient = mock(HttpClient.class);
  private final ObjectMapper objectMapper = mock(ObjectMapper.class);
  private final CurrentTimeCallbackTaskFactory factory =
      new CurrentTimeCallbackTaskFactoryImpl(httpClient, objectMapper);

  @Test
  public void createCallback_Always_CreatesCallbackToCorrectURI() throws URISyntaxException {
    URI uri = new URI("https://test.com");
    CurrentTimeCallback callback = factory.createCallback(uri);
    assertEquals(uri, callback.getUri());
    assertEquals(httpClient, callback.getHttpClient());
    assertEquals(objectMapper, callback.getObjectMapper());
  }

}