package com.SOU.mockServer.common.util.serializer;

import java.io.IOException;

public interface Deserializer<D,I> {
  D deserialize(I input) throws IOException;
}
