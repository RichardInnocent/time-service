package org.richardinnocent.timeservice.controller.models;

import java.time.ZonedDateTime;
import java.util.Objects;

public class TimeUpdateDto {
  private final ZonedDateTime currentTime;

  public TimeUpdateDto(ZonedDateTime currentTime) {
    this.currentTime = currentTime;
  }

  public ZonedDateTime getCurrentTime() {
    return currentTime;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof TimeUpdateDto)) {
      return false;
    }
    TimeUpdateDto that = (TimeUpdateDto) o;
    return Objects.equals(currentTime, that.currentTime);
  }

  @Override
  public int hashCode() {
    return Objects.hash(currentTime);
  }
}
