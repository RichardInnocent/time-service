package org.richardinnocent.timeservice.services.callbacks;

import java.net.URI;
import java.net.http.HttpClient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class CurrentTimeCallbackTaskFactoryImpl implements CurrentTimeCallbackTaskFactory {

  private final HttpClient httpClient;
  private final ObjectMapper objectMapper;

  @Autowired
  public CurrentTimeCallbackTaskFactoryImpl(HttpClient httpClient, ObjectMapper objectMapper) {
    this.httpClient = httpClient;
    this.objectMapper = objectMapper;
  }

  @Override
  public CurrentTimeCallback createCallback(URI uri) {
    return new CurrentTimeCallback(uri, httpClient, objectMapper);
  }
}
