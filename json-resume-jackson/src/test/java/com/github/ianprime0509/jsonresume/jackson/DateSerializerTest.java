package com.github.ianprime0509.jsonresume.jackson;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.github.ianprime0509.jsonresume.api.Date;
import java.time.LocalDate;
import java.time.Year;
import java.time.YearMonth;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

@DisplayName("DateSerializer")
class DateSerializerTest {
  private ObjectMapper objectMapper;

  @BeforeEach
  void setUpObjectMapper() {
    final SimpleModule module = new SimpleModule();
    module.addSerializer(Date.class, new DateSerializer(Date.class));
    objectMapper = new ObjectMapper().registerModule(module);
  }

  @DisplayName("serialize")
  @Nested
  class Serialize {
    @DisplayName("Writes Years as strings.")
    @ParameterizedTest(name = "The Year {0} should be written as {1}.")
    @CsvSource({"2014,\"2014\"", "1996,\"1996\""})
    void years(final Year year, final String json) throws JsonProcessingException {
      assertThat(objectMapper.writeValueAsString(Date.ofYear(year))).isEqualTo(json);
    }

    @DisplayName("Writes YearMonths as dash-separated strings.")
    @ParameterizedTest(name = "The YearMonth {0} should be written as {1}.")
    @CsvSource({"2016-11,\"2016-11\"", "1996-05,\"1996-05\""})
    void yearMonths(final YearMonth yearMonth, final String json) throws JsonProcessingException {
      assertThat(objectMapper.writeValueAsString(Date.ofYearMonth(yearMonth))).isEqualTo(json);
    }

    @DisplayName("Writes LocalDates as dash-separated strings.")
    @ParameterizedTest(name = "The LocalDate {0} should be written as {1}.")
    @CsvSource({"1996-05-09,\"1996-05-09\"", "2005-11-14,\"2005-11-14\""})
    void yearMonthDays(final LocalDate localDate, final String json)
        throws JsonProcessingException {
      assertThat(objectMapper.writeValueAsString(Date.ofYearMonthDay(localDate))).isEqualTo(json);
    }
  }
}
