package com.github.ianprime0509.jsonresume.jackson;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.github.ianprime0509.jsonresume.api.Date;
import java.io.IOException;
import java.time.LocalDate;
import java.time.Year;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.TemporalAccessor;

final class DateDeserializer extends StdDeserializer<Date> {
  private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy[-MM[-dd]]");

  DateDeserializer(final Class<?> vc) {
    super(vc);
  }

  @Override
  public Date deserialize(final JsonParser p, final DeserializationContext ctxt)
      throws IOException {
    try {
      final TemporalAccessor accessor =
          FORMATTER.parseBest(
              p.readValueAs(String.class), LocalDate::from, YearMonth::from, Year::from);
      if (accessor instanceof Year) {
        return Date.ofYear((Year) accessor);
      } else if (accessor instanceof YearMonth) {
        return Date.ofYearMonth((YearMonth) accessor);
      } else {
        return Date.ofYearMonthDay((LocalDate) accessor);
      }
    } catch (final DateTimeParseException e) {
      throw JsonMappingException.from(p, "Invalid date", e);
    }
  }
}
