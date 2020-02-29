package com.github.ianprime0509.jsonresume.moshi;

import static java.util.Objects.requireNonNull;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.JsonDataException;
import com.squareup.moshi.JsonReader;
import com.squareup.moshi.JsonWriter;
import com.squareup.moshi.Moshi;
import java.io.IOException;
import java.time.YearMonth;
import java.time.format.DateTimeParseException;
import javax.annotation.Nullable;

final class YearMonthJsonAdapter extends JsonAdapter<YearMonth> {
  private final JsonAdapter<String> stringAdapter;

  YearMonthJsonAdapter(final Moshi moshi) {
    stringAdapter = moshi.adapter(String.class).nonNull();
  }

  @Override
  public YearMonth fromJson(final JsonReader reader) throws IOException {
    try {
      return YearMonth.parse(requireNonNull(stringAdapter.fromJson(reader)));
    } catch (final DateTimeParseException e) {
      throw new JsonDataException("Invalid YearMonth at path " + reader.getPath(), e);
    }
  }

  @Override
  public void toJson(final JsonWriter writer, @Nullable final YearMonth value) throws IOException {
    stringAdapter.toJson(writer, requireNonNull(value).toString());
  }
}
