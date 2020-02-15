package com.github.ianprime0509.jsonresume.jsonresume.json;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.JsonDataException;
import com.squareup.moshi.Moshi;
import java.io.IOException;
import java.time.YearMonth;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

@DisplayName("YearMonthJsonAdapter")
class YearMonthJsonAdapterTest {
  private JsonAdapter<YearMonth> adapter;

  @BeforeEach
  void setUpAdapter() {
    final Moshi moshi = new Moshi.Builder().add(JsonResumeJsonAdapterFactory.getInstance()).build();
    adapter = moshi.adapter(YearMonth.class);
  }

  @DisplayName("fromJson")
  @Nested
  class FromJson {
    @DisplayName("With a valid year and month, returns the parsed YearMonth.")
    @ParameterizedTest(name = "The JSON {0} should be parsed as the YearMonth {1}.")
    @CsvSource({"\"1996-05\",1996-05", "\"2020-11\",2020-11"})
    void validYears(final String json, final YearMonth yearMonth) throws IOException {
      assertThat(adapter.fromJson(json)).isEqualTo(yearMonth);
    }

    @DisplayName("With an invalid year and month, throws a JsonDataException.")
    @ParameterizedTest(name = "The JSON {0} should be recognized as invalid.")
    @ValueSource(strings = {"\"ABC-BA\"", "\"\"", "\"-123A\"", "\"1256\""})
    void invalidYears(final String json) {
      //noinspection ResultOfMethodCallIgnored
      assertThatThrownBy(() -> adapter.fromJson(json))
          .isInstanceOf(JsonDataException.class)
          .hasMessageContaining("Invalid YearMonth");
    }
  }

  @DisplayName("toJson")
  @Nested
  class ToJson {
    @DisplayName("Writes YearMonths as dash-separated strings.")
    @ParameterizedTest(name = "The YearMonth {0} should be written as {1}.")
    @CsvSource({"1996-05,\"1996-05\"", "2020-12,\"2020-12\""})
    void test(final YearMonth yearMonth, final String json) {
      assertThat(adapter.toJson(yearMonth)).isEqualTo(json);
    }
  }
}
