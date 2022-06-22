package org.richardinnocent.timeservice.services.callbacks;

import java.net.URI;

public interface CurrentTimeCallbackTaskFactory {
  CurrentTimeCallback createCallback(URI uri);
}
