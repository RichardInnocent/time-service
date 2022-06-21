package org.richardinnocent.timeservice.controller.callback;

import javax.validation.Valid;

import org.richardinnocent.timeservice.controller.models.CallbackDto;
import org.richardinnocent.timeservice.controller.models.ConfigureCallbackDto;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(
    path = "/callbacks",
    consumes = MediaType.APPLICATION_JSON_VALUE,
    produces = MediaType.APPLICATION_JSON_VALUE
)
public class CallbackController {

  @PostMapping
  public CallbackDto createNewCallback(@Valid @RequestBody ConfigureCallbackDto frequencyDto) {
    return null;
  }

}
