package com.github.ianprime0509.jsonresume.moshi;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.github.ianprime0509.jsonresume.core.Date;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.JsonDataException;
import com.squareup.moshi.Moshi;
import java.io.IOException;
import java.time.LocalDate;
import java.time.Year;
import java.time.YearMonth;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

@DisplayName("DateJsonAdapter")
class DateJsonAdapterTest {
  private JsonAdapter<Date> adapter;

  @BeforeEach
  void setUpAdapter() {
    final Moshi moshi = new Moshi.Builder().add(JsonResumeJsonAdapterFactory.getInstance()).build();
    adapter = moshi.adapter(Date.class);
  }

  @DisplayName("fromJson")
  @Nested
  class FromJson {
    @DisplayName("With a valid year, returns the parsed Year.")
    @ParameterizedTest(name = "The JSON {0} should be parsed as the Year {1}.")
    @CsvSource({"\"2007\",2007", "\"1996\",1996"})
    void validYears(final String json, final Year year) throws IOException {
      Assertions.assertThat(adapter.fromJson(json)).isEqualTo(Date.ofYear(year));
    }

    @DisplayName("With a valid year and month, returns the parsed YearMonth.")
    @ParameterizedTest(name = "The JSON {0} should be parsed as the YearMonth {1}.")
    @CsvSource({"\"2006-02\",2006-02", "\"1996-05\",1996-05", "\"2020-11\",2020-11"})
    void validYearMonths(final String json, final YearMonth yearMonth) throws IOException {
      Assertions.assertThat(adapter.fromJson(json)).isEqualTo(Date.ofYearMonth(yearMonth));
    }

    @DisplayName("With a valid year, month and day, returns the parsed LocalDate.")
    @ParameterizedTest(name = "The JSON {0} should be parsed as the LocalDate {1}.")
    @CsvSource({
      "\"2012-06-27\",2012-06-27",
      "\"1996-05-09\",1996-05-09",
      "\"2013-11-30\",2013-11-30"
    })
    void validYearMonthDays(final String json, final LocalDate localDate) throws IOException {
      Assertions.assertThat(adapter.fromJson(json)).isEqualTo(Date.ofYearMonthDay(localDate));
    }

    @DisplayName("With too many components, throws a JsonDataException.")
    @ParameterizedTest(name = "The JSON {0} should be recognized as invalid.")
    @ValueSource(strings = {"\"2019-13-18-20\"", "\"2020-12-12-12-12-12\""})
    void tooManyComponents(final String json) {
      //noinspection ResultOfMethodCallIgnored
      assertThatThrownBy(() -> adapter.fromJson(json))
          .isInstanceOf(JsonDataException.class)
          .hasMessageContaining("Invalid Date");
    }

    @DisplayName("With an invalid component in the date, throws a JsonDataException.")
    @ParameterizedTest(name = "The JSON {0} should be recognized as invalid.")
    @ValueSource(strings = {"\"201A\"", "\"2017--05\"", "\"2018-B\"", "\"2018-06-0D\""})
    void invalidComponents(final String json) {
      //noinspection ResultOfMethodCallIgnored
      assertThatThrownBy(() -> adapter.fromJson(json))
          .isInstanceOf(JsonDataException.class)
          .hasMessageContaining("Invalid");
    }
  }

  @DisplayName("toJson")
  @Nested
  class ToJson {
    @DisplayName("Writes Years as strings.")
    @ParameterizedTest(name = "The Year {0} should be written as {1}.")
    @CsvSource({"2014,\"2014\"", "1996,\"1996\""})
    void years(final Year year, final String json) {
      Assertions.assertThat(adapter.toJson(Date.ofYear(year))).isEqualTo(json);
    }

    @DisplayName("Writes YearMonths as dash-separated strings.")
    @ParameterizedTest(name = "The YearMonth {0} should be written as {1}.")
    @CsvSource({"2016-11,\"2016-11\"", "1996-05,\"1996-05\""})
    void yearMonths(final YearMonth yearMonth, final String json) {
      Assertions.assertThat(adapter.toJson(Date.ofYearMonth(yearMonth))).isEqualTo(json);
    }

    @DisplayName("Writes LocalDates as dash-separated strings.")
    @ParameterizedTest(name = "The LocalDate {0} should be written as {1}.")
    @CsvSource({"1996-05-09,\"1996-05-09\"", "2005-11-14,\"2005-11-14\""})
    void yearMonthDays(final LocalDate localDate, final String json) {
      Assertions.assertThat(adapter.toJson(Date.ofYearMonthDay(localDate))).isEqualTo(json);
    }
  }
}
