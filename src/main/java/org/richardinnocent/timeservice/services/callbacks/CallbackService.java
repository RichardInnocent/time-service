package org.richardinnocent.timeservice.services.callbacks;

import java.net.URI;

public interface CallbackService {
  void addCallback(URI uri, int frequencySeconds) throws CallbackAlreadyExistsException;
  void updateCallback(URI uri, int frequencySeconds) throws CallbackNotFoundException;
  void removeCallback(URI uri) throws CallbackNotFoundException;
}
