package org.richardinnocent.timeservice.controller.models;

import org.junit.jupiter.api.Test;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;

class CallbackDtoTest {

  @Test
  public void equalsAndHashCode_Always_Valid() {
    EqualsVerifier
        .forClass(CallbackDto.class)
        .suppress(Warning.NONFINAL_FIELDS, Warning.STRICT_INHERITANCE)
        .verify();
  }

}