package org.richardinnocent.timeservice.services.callbacks;

import java.net.URI;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class CallbackNotFoundException extends RuntimeException {

  public CallbackNotFoundException(URI uri) {
    super("Callback with URI " + uri + " not found");
  }
}
