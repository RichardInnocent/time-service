package org.richardinnocent.timeservice.controller.callback;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
@AutoConfigureMockMvc
class CallbackControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Test
  public void post$callback_RequestBodyIsValid_Returns501() throws Exception {
    mockMvc
        .perform(
            post("/callbacks")
                .content("{\"frequencySeconds\":30,\"url\":\"http://test.com\"}")
                .contentType(MediaType.APPLICATION_JSON)
        )
        .andExpect(status().isNotImplemented());
  }

  @Test
  public void post$callback_RequestBodyIsInvalid_Returns400() throws Exception {
    mockMvc
        .perform(
            post("/callbacks")
                .content("{\"frequencySeconds\":0,\"url\":\"http://test.com\"}")
                .contentType(MediaType.APPLICATION_JSON)
        )
        .andExpect(status().isBadRequest());
  }

  @Test
  public void put$callback_RequestBodyAndUrlParameterAreValid_Returns501() throws Exception {
    mockMvc
        .perform(
            put("/callbacks?url=https://test.com")
                .content("{\"frequencySeconds\":30}")
                .contentType(MediaType.APPLICATION_JSON)
        )
        .andExpect(status().isNotImplemented());
  }

  @Test
  public void put$callback_UrlParameterIsMissing_Returns400() throws Exception {
    mockMvc
        .perform(
            put("/callbacks")
                .content("{\"frequencySeconds\":30}")
                .contentType(MediaType.APPLICATION_JSON)
        )
        .andExpect(status().isBadRequest());
  }

  @Test
  public void put$callback_RequestBodyIsInvalid_Returns400() throws Exception {
    mockMvc
        .perform(
            put("/callbacks?url=https://test.com")
                .content("{\"frequencySeconds\":0}")
                .contentType(MediaType.APPLICATION_JSON)
        )
        .andExpect(status().isBadRequest());
  }

  @Test
  public void delete$callback_UrlIsSpecified_Returns501() throws Exception {
    mockMvc
        .perform(delete("/callbacks?url=https://test.com"))
        .andExpect(status().isNotImplemented());
  }

  @Test
  public void delete$callback_UrlIsNotSpecified_Returns400() throws Exception {
    mockMvc
        .perform(delete("/callbacks"))
        .andExpect(status().isBadRequest());
  }

}