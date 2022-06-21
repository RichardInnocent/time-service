package org.richardinnocent.timeservice.services.callbacks;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublisher;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandler;
import java.net.http.HttpResponse.BodyHandlers;
import java.time.ZonedDateTime;
import java.util.function.Supplier;

import org.richardinnocent.timeservice.controller.models.TimeUpdateDto;
import org.springframework.http.MediaType;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class CurrentTimeCallback implements Runnable {

  private final HttpClient client;
  private final URI uri;
  private final Supplier<ZonedDateTime> currentTimeSupplier;
  private final ObjectMapper objectMapper;

  public CurrentTimeCallback(URI uri, HttpClient client,ObjectMapper objectMapper) {
    this(uri, client, objectMapper, ZonedDateTime::now);
  }

  CurrentTimeCallback(
      URI uri,
      HttpClient client,
      ObjectMapper objectMapper,
      Supplier<ZonedDateTime> currentTimeSupplier
  ) {
    this.uri = uri;
    this.client = client;
    this.objectMapper = objectMapper;
    this.currentTimeSupplier = currentTimeSupplier;
  }

  @Override
  public void run() {
    sendUpdate(new TimeUpdateDto(currentTimeSupplier.get()));
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
      client.send(request, BodyHandlers.ofString());
    } catch (IOException | InterruptedException e) {
      throw new DeliverUpdateException("failed to send HTTP request", e);
    }
  }
}
