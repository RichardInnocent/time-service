package org.richardinnocent.timeservice.services.callbacks;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse.BodyHandlers;
import java.time.ZonedDateTime;
import java.util.function.Supplier;

import org.richardinnocent.timeservice.controller.models.TimeUpdateDto;
import org.springframework.http.MediaType;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class CurrentTimeCallback implements Runnable {

  private final HttpClient httpClient;
  private final URI uri;
  private final Supplier<ZonedDateTime> currentTimeSupplier;
  private final ObjectMapper objectMapper;

  CurrentTimeCallback(URI uri, HttpClient httpClient,ObjectMapper objectMapper) {
    this(uri, httpClient, objectMapper, ZonedDateTime::now);
  }

  CurrentTimeCallback(
      URI uri,
      HttpClient httpClient,
      ObjectMapper objectMapper,
      Supplier<ZonedDateTime> currentTimeSupplier
  ) {
    this.uri = uri;
    this.httpClient = httpClient;
    this.objectMapper = objectMapper;
    this.currentTimeSupplier = currentTimeSupplier;
  }

  @Override
  public void run() {
    sendUpdate(new TimeUpdateDto(currentTimeSupplier.get()));
  }

  public HttpClient getHttpClient() {
    return httpClient;
  }

  public URI getUri() {
    return uri;
  }

  public ObjectMapper getObjectMapper() {
    return objectMapper;
  }

  public String convertToJsonString(TimeUpdateDto dto) {
    try {
      return objectMapper.writeValueAsString(dto);
    } catch (JsonProcessingException e) {
      throw new DeliverUpdateException("Could not create JSON payload", e);
    }
  }

  private void sendUpdate(TimeUpdateDto dto) throws DeliverUpdateException {
    String requestBody = convertToJsonString(dto);
    HttpRequest request = HttpRequest
        .newBuilder()
        .POST(HttpRequest.BodyPublishers.ofString(requestBody))
        .uri(uri)
        .header("Content-Type", MediaType.APPLICATION_JSON_VALUE)
        .build();

    try {
      // Would ideally check the status of the response and do something with it if it's not 2xx
      httpClient.send(request, BodyHandlers.ofString());
    } catch (IOException | InterruptedException e) {
      throw new DeliverUpdateException("failed to send HTTP request", e);
    }
  }
}
