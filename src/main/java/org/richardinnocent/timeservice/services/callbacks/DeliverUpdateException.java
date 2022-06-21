package org.richardinnocent.timeservice.services.callbacks;

public class DeliverUpdateException extends RuntimeException {
  public DeliverUpdateException(String message) {
    super(message);
  }

  public DeliverUpdateException(Throwable cause) {
    super(cause);
  }

  public DeliverUpdateException(String message, Throwable cause) {
    super(message, cause);
  }
}
