package org.richardinnocent.timeservice.controller.callback;

import java.net.URI;
import java.net.URISyntaxException;

import javax.validation.ConstraintViolationException;
import javax.validation.Valid;

import org.hibernate.validator.constraints.URL;
import org.richardinnocent.timeservice.controller.models.CallbackDto;
import org.richardinnocent.timeservice.controller.models.ConfigureCallbackDto;
import org.richardinnocent.timeservice.controller.models.ConfigureFrequencyDto;
import org.richardinnocent.timeservice.services.callbacks.CallbackService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(
    path = "/callbacks",
    consumes = MediaType.APPLICATION_JSON_VALUE,
    produces = MediaType.APPLICATION_JSON_VALUE
)
@Validated
public class CallbackController {

  private final CallbackService callbackService;

  public CallbackController(CallbackService callbackService) {
    this.callbackService = callbackService;
  }

  @PostMapping
  public CallbackDto createNewCallback(@Valid @RequestBody ConfigureCallbackDto frequencyDto) {
    URI uri = getUri(frequencyDto.getUrl());
    callbackService.addCallback(uri, frequencyDto.getFrequencySeconds());
    return new CallbackDto(uri, frequencyDto.getFrequencySeconds());
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
      @URL @RequestParam("url") String url,
      @Valid @RequestBody ConfigureFrequencyDto frequencyDto
  ) {
    URI uri = getUri(url);
    callbackService.updateCallback(uri, frequencyDto.getFrequencySeconds());
    return new CallbackDto(uri, frequencyDto.getFrequencySeconds());
  }

  /*
   * Similar comments as above, a DELETE to /callbacks/{callbackId} would be more RESTful, but this
   * is here so I can keep to spec.
   * Also, ideally this should return the resource that was deleted. I could do this with more time,
   * but this will do for now.
   */
  @DeleteMapping(consumes = MediaType.ALL_VALUE)
  public void deleteCallback(@URL @RequestParam("url") String url) {
    URI uri = getUri(url);
    callbackService.removeCallback(uri);
  }

  private URI getUri(String url) throws URIInvalidException {
    try {
      return new URI(url);
    } catch (URISyntaxException e) {
      throw new URIInvalidException(url, e);
    }
  }

  @ExceptionHandler({ConstraintViolationException.class})
  public ResponseEntity<Object> batRequestException(Exception e) {
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
  }

}
