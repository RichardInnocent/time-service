package org.richardinnocent.timeservice.controller.callback;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class URIInvalidException extends RuntimeException {
  public URIInvalidException(String uri, Throwable cause) {
    super("URI invalid: " + uri, cause);
  }
}
