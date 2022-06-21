package org.richardinnocent.timeservice.controller.callback;

import javax.validation.Valid;

import org.richardinnocent.timeservice.controller.models.CallbackDto;
import org.richardinnocent.timeservice.controller.models.ConfigureCallbackDto;
import org.richardinnocent.timeservice.controller.models.ConfigureFrequencyDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(
    path = "/callbacks",
    consumes = MediaType.APPLICATION_JSON_VALUE,
    produces = MediaType.APPLICATION_JSON_VALUE
)
public class CallbackController {

  private static final RuntimeException NOT_IMPLEMENTED_EXCEPTION = new NotImplementedException();

  @PostMapping
  public CallbackDto createNewCallback(@Valid @RequestBody ConfigureCallbackDto frequencyDto) {
    throw NOT_IMPLEMENTED_EXCEPTION;
  }

  /*
   * I personally wouldn't normally implement it like this. I think a more RESTful way of doing
   * this would be to make a PUT request to /callbacks/{callbackId}. Theoretically, I could have
   * used the URL as the ID but this feels funky to me, especially with how this might end up
   * looking with URL encoding. I kept it like this for now rather than replace it with a separate
   * route to keep to the spec.
   */
  @PutMapping
  public CallbackDto updateCallback(
      @RequestParam("url") String url,
      @Valid @RequestBody ConfigureFrequencyDto frequencyDto
  ) {
    throw NOT_IMPLEMENTED_EXCEPTION;
  }

  /*
   * Similar comments as above, a DELETE to /callbacks/{callbackId} would be more RESTful, but this
   * is here so I can keep to spec.
   */
  @DeleteMapping(consumes = MediaType.ALL_VALUE)
  public CallbackDto deleteCallback(@RequestParam("url") String url) {
    throw NOT_IMPLEMENTED_EXCEPTION;
  }

  @ResponseStatus(HttpStatus.NOT_IMPLEMENTED)
  private static class NotImplementedException extends RuntimeException {
    private NotImplementedException() {}
  }

}
