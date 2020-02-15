package com.github.ianprime0509.jsonresume.jsonresume.json;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.JsonDataException;
import com.squareup.moshi.Moshi;
import java.io.IOException;
import java.time.Year;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

@DisplayName("YearJsonAdapter")
class YearJsonAdapterTest {
  private JsonAdapter<Year> adapter;

  @BeforeEach
  void setUpAdapter() {
    final Moshi moshi = new Moshi.Builder().add(JsonResumeJsonAdapterFactory.getInstance()).build();
    adapter = moshi.adapter(Year.class);
  }

  @DisplayName("fromJson")
  @Nested
  class FromJson {
    @DisplayName("With a valid year, returns the parsed Year.")
    @ParameterizedTest(name = "The JSON {0} should be parsed as the Year {1}.")
    @CsvSource({"\"1996\",1996", "\"2020\",2020"})
    void validYears(final String json, final Year year) throws IOException {
      assertThat(adapter.fromJson(json)).isEqualTo(year);
    }

    @DisplayName("With an invalid year, throws a JsonDataException.")
    @ParameterizedTest(name = "The JSON {0} should be recognized as invalid.")
    @ValueSource(strings = {"\"ABC\"", "\"\"", "\"123A\"", "\"1234-56\""})
    void invalidYears(final String json) {
      //noinspection ResultOfMethodCallIgnored
      assertThatThrownBy(() -> adapter.fromJson(json))
          .isInstanceOf(JsonDataException.class)
          .hasMessageContaining("Invalid Year");
    }
  }

  @DisplayName("toJson")
  @Nested
  class ToJson {
    @DisplayName("Writes Years as strings.")
    @ParameterizedTest(name = "The Year {0} should be written as {1}.")
    @CsvSource({"1996,\"1996\"", "2020,\"2020\""})
    void test(final Year year, final String json) {
      assertThat(adapter.toJson(year)).isEqualTo(json);
    }
  }
}
