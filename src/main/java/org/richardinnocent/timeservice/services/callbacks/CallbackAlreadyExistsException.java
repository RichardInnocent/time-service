package org.richardinnocent.timeservice.services.callbacks;

import java.net.URI;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class CallbackAlreadyExistsException extends RuntimeException {

  public CallbackAlreadyExistsException(URI uri) {
    super("Callback with URI " + uri + " already exists");
  }
}
