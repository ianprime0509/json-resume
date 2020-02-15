package com.github.ianprime0509.jsonresume.jsonresume.json;

import static java.util.Objects.requireNonNull;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.JsonDataException;
import com.squareup.moshi.JsonReader;
import com.squareup.moshi.JsonWriter;
import com.squareup.moshi.Moshi;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import javax.annotation.Nullable;

final class URIJsonAdapter extends JsonAdapter<URI> {
  private final JsonAdapter<String> stringAdapter;

  URIJsonAdapter(final Moshi moshi) {
    stringAdapter = moshi.adapter(String.class).nonNull();
  }

  @Override
  public URI fromJson(final JsonReader reader) throws IOException {
    try {
      return new URI(requireNonNull(stringAdapter.fromJson(reader)));
    } catch (final URISyntaxException e) {
      throw new JsonDataException("Invalid URI at path " + reader.getPath(), e);
    }
  }

  @Override
  public void toJson(final JsonWriter writer, @Nullable final URI value) throws IOException {
    stringAdapter.toJson(writer, requireNonNull(value).toString());
  }
}
