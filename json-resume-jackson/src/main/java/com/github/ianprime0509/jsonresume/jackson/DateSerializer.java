package com.github.ianprime0509.jsonresume.jackson;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.github.ianprime0509.jsonresume.api.Date;
import java.io.IOException;
import java.time.format.DateTimeFormatter;

final class DateSerializer extends StdSerializer<Date> {
  private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy[-MM[-dd]]");

  DateSerializer(final Class<Date> t) {
    super(t);
  }

  @Override
  public void serialize(
      final Date value, final JsonGenerator gen, final SerializerProvider provider)
      throws IOException {
    switch (value.kind()) {
      case YEAR:
        gen.writeString(FORMATTER.format(value.year()));
        break;
      case YEAR_MONTH:
        gen.writeString(FORMATTER.format(value.yearMonth()));
        break;
      case YEAR_MONTH_DAY:
        gen.writeString(FORMATTER.format(value.yearMonthDay()));
        break;
    }
  }
}
