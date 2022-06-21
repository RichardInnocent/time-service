package org.richardinnocent.timeservice.controller.callback;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
@AutoConfigureMockMvc
class CallbackControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Test
  public void post$callback_RequestBodyIsValid_RequestAccepted() throws Exception {
    mockMvc
        .perform(
            post("/callbacks")
                .content("{\"frequencySeconds\":30,\"url\":\"http://test.com\"}")
                .contentType(MediaType.APPLICATION_JSON)
        )
        .andExpect(status().isOk());
  }

  @Test
  public void post$callback_RequestBodyIsInvalid_RequestAccepted() throws Exception {
    mockMvc
        .perform(
            post("/callbacks")
                .content("{\"frequencySeconds\":0,\"url\":\"http://test.com\"}")
                .contentType(MediaType.APPLICATION_JSON)
        )
        .andExpect(status().isBadRequest());
  }

}