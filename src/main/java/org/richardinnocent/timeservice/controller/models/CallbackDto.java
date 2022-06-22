package org.richardinnocent.timeservice.controller.models;

import java.net.URI;
import java.util.Objects;

public class CallbackDto {

  private final URI url;
  private final int frequencySeconds;

  public CallbackDto(URI url, int frequencySeconds) {
    this.url = url;
    this.frequencySeconds = frequencySeconds;
  }

  public URI getUrl() {
    return url;
  }

  public int getFrequencySeconds() {
    return frequencySeconds;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof CallbackDto)) {
      return false;
    }
    CallbackDto that = (CallbackDto) o;
    return frequencySeconds == that.frequencySeconds
        && Objects.equals(url, that.url);
  }

  @Override
  public int hashCode() {
    return Objects.hash(url, frequencySeconds);
  }
}
