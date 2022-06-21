package org.richardinnocent.timeservice.controller.models;

import java.util.Objects;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.URL;

public class ConfigureCallbackDto {

  @Min(value = 5, message = "Frequency must be at least 5 seconds")
  @Max(value = 4*60*60, message = "Frequency must be 4 hours or less")
  private int frequencySeconds;

  @URL(message = "URL must be a valid URL")
  @NotNull(message = "URL must be specified")
  private String url;

  public int getFrequencySeconds() {
    return frequencySeconds;
  }

  public void setFrequencySeconds(int frequencySeconds) {
    this.frequencySeconds = frequencySeconds;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof ConfigureCallbackDto)) {
      return false;
    }
    ConfigureCallbackDto that = (ConfigureCallbackDto) o;
    return frequencySeconds == that.frequencySeconds
        && Objects.equals(url, that.url);
  }

  @Override
  public int hashCode() {
    return Objects.hash(frequencySeconds, url);
  }
}
