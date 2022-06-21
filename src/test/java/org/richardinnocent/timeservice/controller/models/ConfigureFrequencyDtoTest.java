package org.richardinnocent.timeservice.controller.models;

import javax.validation.Validation;
import javax.validation.Validator;

import org.junit.jupiter.api.Test;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;

import static org.junit.jupiter.api.Assertions.*;

class ConfigureFrequencyDtoTest {

  private static final Validator VALIDATOR;

  static {
    try (var factory = Validation.buildDefaultValidatorFactory()) {
      VALIDATOR = factory.getValidator();
    }
  }

  @Test
  public void equalsAndHashCode_Always_Valid() {
    EqualsVerifier
        .forClass(ConfigureFrequencyDto.class)
        .suppress(Warning.NONFINAL_FIELDS, Warning.STRICT_INHERITANCE)
        .verify();
  }

  @Test
  public void validation_FrequencyOf5Seconds_IsValid() {
    ConfigureFrequencyDto dto = new ConfigureFrequencyDto();
    dto.setFrequencySeconds(5);
    assertTrue(isValid(dto));
  }

  @Test
  public void validation_FrequencyOf4Seconds_IsInvalid() {
    ConfigureFrequencyDto dto = new ConfigureFrequencyDto();
    dto.setFrequencySeconds(4);
    assertFalse(isValid(dto));
  }

  @Test
  public void validation_FrequencyOf4Hours_IsValid() {
    ConfigureFrequencyDto dto = new ConfigureFrequencyDto();
    dto.setFrequencySeconds(4*60*60);
    assertTrue(isValid(dto));
  }

  @Test
  public void validation_FrequencyOf4HoursAnd1Second_IsInvalid() {
    ConfigureFrequencyDto dto = new ConfigureFrequencyDto();
    dto.setFrequencySeconds(4*60*60+1);
    assertFalse(isValid(dto));
  }

  private boolean isValid(Object o) {
    return VALIDATOR.validate(o).isEmpty();
  }

}