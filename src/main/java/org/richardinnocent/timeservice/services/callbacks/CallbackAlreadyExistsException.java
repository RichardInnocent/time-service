package org.richardinnocent.timeservice.services.callbacks;

import java.net.URI;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class CallbackAlreadyExistsException extends RuntimeException {

  private final URI uri;

  public CallbackAlreadyExistsException(URI uri) {
    super("callback already exists");
    this.uri = uri;
  }

  public URI getUri() {
    return uri;
  }
}
