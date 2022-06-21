package org.richardinnocent.timeservice.controller.models;

import java.util.Objects;

public class CallbackDto {

  private String callbackId;
  private int frequencySeconds;

  public String getCallbackId() {
    return callbackId;
  }

  public void setCallbackId(String callbackId) {
    this.callbackId = callbackId;
  }

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
    if (!(o instanceof CallbackDto)) {
      return false;
    }
    CallbackDto that = (CallbackDto) o;
    return frequencySeconds == that.frequencySeconds
        && Objects.equals(callbackId, that.callbackId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(callbackId, frequencySeconds);
  }
}
