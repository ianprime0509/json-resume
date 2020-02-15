package com.github.ianprime0509.jsonresume.jsonresume.json;

import com.github.ianprime0509.jsonresume.jsonresume.type.Date;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.Types;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.net.URI;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Year;
import java.time.YearMonth;
import java.util.Set;
import javax.annotation.Nullable;

public final class JsonResumeJsonAdapterFactory implements JsonAdapter.Factory {
  public static final JsonResumeJsonAdapterFactory INSTANCE = new JsonResumeJsonAdapterFactory();

  private JsonResumeJsonAdapterFactory() {}

  public static JsonResumeJsonAdapterFactory getInstance() {
    return INSTANCE;
  }

  @Nullable
  @Override
  public JsonAdapter<?> create(
      final Type type, final Set<? extends Annotation> annotations, final Moshi moshi) {
    final Class<?> rawType = Types.getRawType(type);
    if (Date.class.isAssignableFrom(rawType)) {
      return new DateJsonAdapter(moshi);
    } else if (LocalDate.class.isAssignableFrom(rawType)) {
      return new LocalDateJsonAdapter(moshi);
    } else if (LocalDateTime.class.isAssignableFrom(rawType)) {
      return new LocalDateTimeJsonAdapter(moshi);
    } else if (URI.class.isAssignableFrom(rawType)) {
      return new URIJsonAdapter(moshi);
    } else if (Year.class.isAssignableFrom(rawType)) {
      return new YearJsonAdapter(moshi);
    } else if (YearMonth.class.isAssignableFrom(rawType)) {
      return new YearMonthJsonAdapter(moshi);
    }
    return null;
  }
}
