package org.richardinnocent.timeservice.controller.models;

import java.util.Objects;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import org.richardinnocent.timeservice.services.callbacks.CallbackConstants;

public class ConfigureFrequencyDto {

  @Min(
      value = CallbackConstants.MINIMUM_CALLBACK_FREQUENCY_SECONDS,
      message = "Frequency must be at least " + CallbackConstants.MINIMUM_CALLBACK_FREQUENCY_SECONDS
          + " seconds"
  )
  @Max(
      value = CallbackConstants.MAXIMUM_CALLBACK_FREQUENCY_SECONDS,
      message = "Frequency must be " + CallbackConstants.MAXIMUM_CALLBACK_FREQUENCY_SECONDS
          + " or less"
  )
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
    if (!(o instanceof ConfigureFrequencyDto)) {
      return false;
    }
    ConfigureFrequencyDto that = (ConfigureFrequencyDto) o;
    return frequencySeconds == that.frequencySeconds;
  }

  @Override
  public int hashCode() {
    return Objects.hash(frequencySeconds);
  }
}
