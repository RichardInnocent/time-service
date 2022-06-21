package org.richardinnocent.timeservice.controller.models;

import java.util.Objects;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

public class SetFrequencyDto {

  @Min(value = 5, message = "Frequency must be at least 5 seconds")
  @Max(value = 4*60*60, message = "Frequency must be 4 hours or less")
  private int frequencySeconds;

  public int getFrequencySeconds() {
    return frequencySeconds;
  }

  public void setFrequencySeconds(int frequencySeconds) {
    this.frequencySeconds = frequencySeconds;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof SetFrequencyDto)) {
      return false;
    }
    SetFrequencyDto that = (SetFrequencyDto) o;
    return frequencySeconds == that.frequencySeconds;
  }

  @Override
  public int hashCode() {
    return Objects.hash(frequencySeconds);
  }
}
