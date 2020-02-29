package com.github.ianprime0509.jsonresume.jackson;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.github.ianprime0509.jsonresume.api.Date;
import java.io.IOException;
import java.time.LocalDate;
import java.time.Year;
import java.time.YearMonth;
import java.time.format.DateTimeParseException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

@DisplayName("DateDeserializer")
class DateDeserializerTest {
  private ObjectMapper objectMapper;

  @BeforeEach
  void setUpObjectMapper() {
    final SimpleModule module = new SimpleModule();
    module.addDeserializer(Date.class, new DateDeserializer(Date.class));
    objectMapper = new ObjectMapper().registerModule(module);
  }

  @DisplayName("deserialize")
  @Nested
  class Deserialize {
    @DisplayName("With a valid year, returns the parsed Year.")
    @ParameterizedTest(name = "The JSON {0} should be parsed as the Year {1}.")
    @CsvSource({"\"2007\",2007", "\"1996\",1996"})
    void validYears(final String json, final Year year) throws IOException {
      assertThat(objectMapper.readValue(json, Date.class)).isEqualTo(Date.ofYear(year));
    }

    @DisplayName("With a valid year and month, returns the parsed YearMonth.")
    @ParameterizedTest(name = "The JSON {0} should be parsed as the YearMonth {1}.")
    @CsvSource({"\"2006-02\",2006-02", "\"1996-05\",1996-05", "\"2020-11\",2020-11"})
    void validYearMonths(final String json, final YearMonth yearMonth) throws IOException {
      assertThat(objectMapper.readValue(json, Date.class)).isEqualTo(Date.ofYearMonth(yearMonth));
    }

    @DisplayName("With a valid year, month and day, returns the parsed LocalDate.")
    @ParameterizedTest(name = "The JSON {0} should be parsed as the LocalDate {1}.")
    @CsvSource({
      "\"2012-06-27\",2012-06-27",
      "\"1996-05-09\",1996-05-09",
      "\"2013-11-30\",2013-11-30"
    })
    void validYearMonthDays(final String json, final LocalDate localDate) throws IOException {
      assertThat(objectMapper.readValue(json, Date.class))
          .isEqualTo(Date.ofYearMonthDay(localDate));
    }

    @DisplayName("With too many components, throws a JsonDataException.")
    @ParameterizedTest(name = "The JSON {0} should be recognized as invalid.")
    @ValueSource(strings = {"\"2019-13-18-20\"", "\"2020-12-12-12-12-12\""})
    void tooManyComponents(final String json) {
      assertThatThrownBy(() -> objectMapper.readValue(json, Date.class))
          .isInstanceOf(JsonMappingException.class)
          .hasMessageContaining("Invalid date")
          .hasCauseInstanceOf(DateTimeParseException.class);
    }

    @DisplayName("With an invalid component in the date, throws a JsonDataException.")
    @ParameterizedTest(name = "The JSON {0} should be recognized as invalid.")
    @ValueSource(strings = {"\"201A\"", "\"2017--05\"", "\"2018-B\"", "\"2018-06-0D\""})
    void invalidComponents(final String json) {
      assertThatThrownBy(() -> objectMapper.readValue(json, Date.class))
          .isInstanceOf(JsonMappingException.class)
          .hasMessageContaining("Invalid date")
          .hasCauseInstanceOf(DateTimeParseException.class);
    }
  }
}
