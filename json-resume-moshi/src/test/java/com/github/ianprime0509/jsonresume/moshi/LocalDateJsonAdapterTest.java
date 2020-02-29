package com.github.ianprime0509.jsonresume.moshi;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.JsonDataException;
import com.squareup.moshi.Moshi;
import java.io.IOException;
import java.time.LocalDate;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

@DisplayName("LocalDateJsonAdapter")
class LocalDateJsonAdapterTest {
  private JsonAdapter<LocalDate> adapter;

  @BeforeEach
  void setUpAdapter() {
    final Moshi moshi = new Moshi.Builder().add(JsonResumeJsonAdapterFactory.getInstance()).build();
    adapter = moshi.adapter(LocalDate.class);
  }

  @DisplayName("fromJson")
  @Nested
  class FromJson {
    @DisplayName("With a valid year, month and day, returns the parsed LocalDate.")
    @ParameterizedTest(name = "The JSON {0} should be parsed as the LocalDate {1}.")
    @CsvSource({"\"1996-05-09\",1996-05-09", "\"2020-11-17\",2020-11-17"})
    void validYears(final String json, final LocalDate localDate) throws IOException {
      Assertions.assertThat(adapter.fromJson(json)).isEqualTo(localDate);
    }

    @DisplayName("With an invalid year, month and day, throws a JsonDataException.")
    @ParameterizedTest(name = "The JSON {0} should be recognized as invalid.")
    @ValueSource(strings = {"\"ABC-BA-CD\"", "\"\"", "\"-123-5\"", "\"--\""})
    void invalidYears(final String json) {
      //noinspection ResultOfMethodCallIgnored
      assertThatThrownBy(() -> adapter.fromJson(json))
          .isInstanceOf(JsonDataException.class)
          .hasMessageContaining("Invalid LocalDate");
    }
  }

  @DisplayName("toJson")
  @Nested
  class ToJson {
    @DisplayName("Writes LocalDates as dash-separated strings.")
    @ParameterizedTest(name = "The LocalDate {0} should be written as {1}.")
    @CsvSource({"1996-05-09,\"1996-05-09\"", "2020-12-31,\"2020-12-31\""})
    void test(final LocalDate localDate, final String json) {
      Assertions.assertThat(adapter.toJson(localDate)).isEqualTo(json);
    }
  }
}
