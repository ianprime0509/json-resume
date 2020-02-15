package com.github.ianprime0509.jsonresume.jsonresume.json;

import static java.util.Objects.requireNonNull;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.JsonDataException;
import com.squareup.moshi.JsonReader;
import com.squareup.moshi.JsonWriter;
import com.squareup.moshi.Moshi;
import java.io.IOException;
import java.time.Year;
import java.time.format.DateTimeParseException;
import javax.annotation.Nullable;

final class YearJsonAdapter extends JsonAdapter<Year> {
  private final JsonAdapter<String> stringAdapter;

  YearJsonAdapter(final Moshi moshi) {
    stringAdapter = moshi.adapter(String.class).nonNull();
  }

  @Override
  public Year fromJson(final JsonReader reader) throws IOException {
    try {
      return Year.parse(requireNonNull(stringAdapter.fromJson(reader)));
    } catch (final DateTimeParseException e) {
      throw new JsonDataException("Invalid Year at path " + reader.getPath(), e);
    }
  }

  @Override
  public void toJson(final JsonWriter writer, @Nullable final Year value) throws IOException {
    stringAdapter.toJson(writer, requireNonNull(value).toString());
  }
}
