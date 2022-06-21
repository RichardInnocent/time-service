package org.richardinnocent.timeservice.controller.models;

import org.junit.jupiter.api.Test;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;

class TimeUpdateDtoTest {

  @Test
  public void equalsAndHashCode_Always_Valid() {
    EqualsVerifier.forClass(TimeUpdateDto.class).suppress(Warning.STRICT_INHERITANCE).verify();
  }

}