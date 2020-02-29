package com.github.ianprime0509.jsonresume.moshi;

import static java.util.Objects.requireNonNull;

import com.github.ianprime0509.jsonresume.core.Date;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.JsonDataException;
import com.squareup.moshi.JsonReader;
import com.squareup.moshi.JsonWriter;
import com.squareup.moshi.Moshi;
import java.io.IOException;
import java.time.LocalDate;
import java.time.Year;
import java.time.YearMonth;
import javax.annotation.Nullable;

final class DateJsonAdapter extends JsonAdapter<Date> {
  private final JsonAdapter<String> stringAdapter;
  private final JsonAdapter<Year> yearAdapter;
  private final JsonAdapter<YearMonth> yearMonthAdapter;
  private final JsonAdapter<LocalDate> localDateAdapter;

  DateJsonAdapter(final Moshi moshi) {
    stringAdapter = moshi.adapter(String.class).nonNull();
    yearAdapter = moshi.adapter(Year.class).nonNull();
    yearMonthAdapter = moshi.adapter(YearMonth.class).nonNull();
    localDateAdapter = moshi.adapter(LocalDate.class).nonNull();
  }

  @Override
  public Date fromJson(final JsonReader reader) throws IOException {
    final String date = requireNonNull(stringAdapter.fromJson(reader));
    switch (date.split("-").length) {
      case 1:
        return Date.ofYear(requireNonNull(yearAdapter.fromJsonValue(date)));
      case 2:
        return Date.ofYearMonth(requireNonNull(yearMonthAdapter.fromJsonValue(date)));
      case 3:
        return Date.ofYearMonthDay(requireNonNull(localDateAdapter.fromJsonValue(date)));
      default:
        throw new JsonDataException("Invalid Date at path " + reader.getPath());
    }
  }

  @Override
  public void toJson(final JsonWriter writer, @Nullable final Date value) throws IOException {
    switch (requireNonNull(value).kind()) {
      case YEAR:
        yearAdapter.toJson(writer, value.year());
        break;
      case YEAR_MONTH:
        yearMonthAdapter.toJson(writer, value.yearMonth());
        break;
      case YEAR_MONTH_DAY:
        localDateAdapter.toJson(writer, value.yearMonthDay());
        break;
      default:
        throw new AssertionError("Impossible kind: " + value.kind());
    }
  }
}
