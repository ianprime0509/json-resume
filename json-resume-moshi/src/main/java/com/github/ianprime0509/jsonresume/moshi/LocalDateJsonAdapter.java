package com.github.ianprime0509.jsonresume.moshi;

import static java.util.Objects.requireNonNull;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.JsonDataException;
import com.squareup.moshi.JsonReader;
import com.squareup.moshi.JsonWriter;
import com.squareup.moshi.Moshi;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import javax.annotation.Nullable;

final class LocalDateJsonAdapter extends JsonAdapter<LocalDate> {
  private final JsonAdapter<String> stringAdapter;

  LocalDateJsonAdapter(final Moshi moshi) {
    stringAdapter = moshi.adapter(String.class).nonNull();
  }

  @Override
  public LocalDate fromJson(final JsonReader reader) throws IOException {
    try {
      return LocalDate.parse(requireNonNull(stringAdapter.fromJson(reader)));
    } catch (final DateTimeParseException e) {
      throw new JsonDataException("Invalid LocalDate at path " + reader.getPath(), e);
    }
  }

  @Override
  public void toJson(final JsonWriter writer, @Nullable final LocalDate value) throws IOException {
    stringAdapter.toJson(writer, requireNonNull(value).toString());
  }
}
