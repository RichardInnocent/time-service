package org.richardinnocent.timeservice.controller.models;

import javax.validation.Validation;
import javax.validation.Validator;

import org.junit.jupiter.api.Test;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;

import static org.junit.jupiter.api.Assertions.*;

class ConfigureCallbackDtoTest {

  private static final Validator VALIDATOR;

  static {
    try (var factory = Validation.buildDefaultValidatorFactory()) {
      VALIDATOR = factory.getValidator();
    }
  }

  private final ConfigureCallbackDto dto = createValidConfigureCallbackDto();

  @Test
  public void equalsAndHashCode_Always_Valid() {
    EqualsVerifier
        .forClass(ConfigureCallbackDto.class)
        .suppress(Warning.NONFINAL_FIELDS, Warning.STRICT_INHERITANCE)
        .verify();
  }

  @Test
  public void validation_ValidObject_IsValid() {
    assertTrue(isValid(dto));
  }

  @Test
  public void validation_FrequencyOf5Seconds_IsValid() {
    dto.setFrequencySeconds(5);
    assertTrue(isValid(dto));
  }

  @Test
  public void validation_FrequencyOf4Seconds_IsInvalid() {
    dto.setFrequencySeconds(4);
    assertFalse(isValid(dto));
  }

  @Test
  public void validation_FrequencyOf4Hours_IsValid() {
    dto.setFrequencySeconds(4*60*60);
    assertTrue(isValid(dto));
  }

  @Test
  public void validation_FrequencyOf4HoursAnd1Second_IsInvalid() {
    dto.setFrequencySeconds(4*60*60+1);
    assertFalse(isValid(dto));
  }

  @Test
  public void validation_CallbackUrlIsNull_IsInvalid() {
    dto.setUrl(null);
    assertFalse(isValid(dto));
  }

  @Test
  public void validation_CallbackUrlIsNotAValidUrl_IsInvalid() {
    dto.setUrl("not a valid URL");
    assertFalse(isValid(dto));
  }

  private ConfigureCallbackDto createValidConfigureCallbackDto() {
    ConfigureCallbackDto dto = new ConfigureCallbackDto();
    dto.setUrl("http://test.com");
    dto.setFrequencySeconds(60);
    return dto;
  }

  private boolean isValid(Object o) {
    return VALIDATOR.validate(o).isEmpty();
  }

}