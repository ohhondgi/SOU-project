package com.SOU.mockServer.common.util.serializer;

import java.io.IOException;

public interface Serializer<D,O> {
  void serialize(D data, O output) throws IOException;
}
