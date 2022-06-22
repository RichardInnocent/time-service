package org.richardinnocent.timeservice.services.callbacks;

import java.net.URI;
import java.net.http.HttpClient;

import com.fasterxml.jackson.databind.ObjectMapper;

public class CurrentTimeCallbackTaskFactoryImpl implements CurrentTimeCallbackTaskFactory {

  private final HttpClient httpClient;
  private final ObjectMapper objectMapper;

  public CurrentTimeCallbackTaskFactoryImpl(HttpClient httpClient, ObjectMapper objectMapper) {
    this.httpClient = httpClient;
    this.objectMapper = objectMapper;
  }

  @Override
  public CurrentTimeCallback createCallback(URI uri) {
    return new CurrentTimeCallback(uri, httpClient, objectMapper);
  }
}
