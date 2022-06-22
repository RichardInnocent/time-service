package org.richardinnocent.timeservice.controller.callback;

import org.junit.jupiter.api.Test;
import org.richardinnocent.timeservice.services.callbacks.CallbackAlreadyExistsException;
import org.richardinnocent.timeservice.services.callbacks.CallbackNotFoundException;
import org.richardinnocent.timeservice.services.callbacks.CallbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
@AutoConfigureMockMvc
class CallbackControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private CallbackService callbackService;

  @Test
  void post$callback_RequestBodyIsValidAndCallbackIsAddedSuccessfully_Returns200()
      throws Exception {
    mockMvc
        .perform(
            post("/callbacks")
                .content("""
                             {"frequencySeconds":30,"url":"https://test.com"}""")
                .contentType(MediaType.APPLICATION_JSON)
        )
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(content().json("""
                                      {"url": "https://test.com","frequencySeconds": 30}"""));
    verify(callbackService, times(1)).addCallback(
        argThat(uri -> uri.toString().equals("https://test.com")),
        eq(30)
    );
  }

  @Test
  void post$callback_RequestBodyIsValidButCallbackIsNotAddedSuccessfully_ReturnsAppropriateResponseCode()
      throws Exception {
    doThrow(mock(CallbackAlreadyExistsException.class))
        .when(callbackService)
        .addCallback(any(), anyInt());
    mockMvc
        .perform(
            post("/callbacks")
                .content("""
                             {"frequencySeconds":30,"url":"https://test.com"}""")
                .contentType(MediaType.APPLICATION_JSON)
        )
        .andExpect(status().isConflict());
  }

  @Test
  void post$callback_RequestBodyIsInvalid_Returns400() throws Exception {
    mockMvc
        .perform(
            post("/callbacks")
                .content("""
                             {"frequencySeconds":0,"url":"https://test.com"}""") // 0 frequency
                .contentType(MediaType.APPLICATION_JSON)
        )
        .andExpect(status().isBadRequest());
  }

  @Test
  void post$callback_UrlIsInvalid_Returns400() throws Exception {
    mockMvc
        .perform(
            post("/callbacks")
                .content("""
                             {"frequencySeconds":30,"url":"not a valid URL"}""")
                .contentType(MediaType.APPLICATION_JSON)
        )
        .andExpect(status().isBadRequest());
  }

  @Test
  void put$callback_RequestBodyAndUrlParameterAreValidAndUpdateIsSuccessful_Returns200()
      throws Exception {
    mockMvc
        .perform(
            put("/callbacks?url=https://test.com")
                .content("""
                    {"frequencySeconds":30}""")
                .contentType(MediaType.APPLICATION_JSON)
        )
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(content().json(""" 
                                      {"url": "https://test.com","frequencySeconds": 30}"""));
    verify(callbackService, times(1)).updateCallback(
        argThat(uri -> uri.toString().equals("https://test.com")),
        eq(30)
    );
  }

  @Test
  void put$callback_RequestBodyAndUrlParameterAreValidButUpdatedIsNotSuccessful_ReturnsAppropriateStatusCode()
      throws Exception {
    doThrow(mock(CallbackNotFoundException.class))
        .when(callbackService)
        .updateCallback(any(), anyInt());
    mockMvc
        .perform(
            put("/callbacks?url=https://test.com")
                .content("""
                    {"frequencySeconds":30}""")
                .contentType(MediaType.APPLICATION_JSON)
        )
        .andExpect(status().isNotFound());
  }

  @Test
  void put$callback_UrlParameterIsMissing_Returns400() throws Exception {
    mockMvc
        .perform(
            put("/callbacks")
                .content("""
                             {"frequencySeconds":30}""")
                .contentType(MediaType.APPLICATION_JSON)
        )
        .andExpect(status().isBadRequest());
  }

  @Test
  void put$callback_RequestBodyIsInvalid_Returns400() throws Exception {
    mockMvc
        .perform(
            put("/callbacks?url=https://test.com")
                .content("""
                             {"frequencySeconds":0}""")
                .contentType(MediaType.APPLICATION_JSON)
        )
        .andExpect(status().isBadRequest());
  }

  @Test
  void put$callback_UrlIsInvalid_Returns400() throws Exception {
    mockMvc
        .perform(
            put("/callbacks?url=invalidUrl")
                .content("""
                             {"frequencySeconds":30}""")
                .contentType(MediaType.APPLICATION_JSON)
        )
        .andExpect(status().isBadRequest());
  }

  @Test
  void delete$callback_UrlIsSpecifiedAndRemovalIsSuccessful_ReturnsOk() throws Exception {
    mockMvc
        .perform(delete("/callbacks?url=https://test.com"))
        .andExpect(status().isOk());
    verify(callbackService, times(1))
        .removeCallback(argThat(uri -> uri.toString().equals("https://test.com")));
  }

  @Test
  void delete$callback_UrlIsSpecifiedButRemovalIsNotSuccessful_ReturnsOk() throws Exception {
    doThrow(mock(CallbackNotFoundException.class))
        .when(callbackService)
        .removeCallback(any());
    mockMvc
        .perform(delete("/callbacks?url=https://test.com"))
        .andExpect(status().isNotFound());
  }

  @Test
  void delete$callback_UrlIsNotSpecified_Returns400() throws Exception {
    mockMvc
        .perform(delete("/callbacks"))
        .andExpect(status().isBadRequest());
  }

  @Test
  void delete$callback_UrlIsInvalid_Returns400() throws Exception {
    mockMvc
        .perform(delete("/callbacks?url=invalidUrl"))
        .andExpect(status().isBadRequest());
  }

}