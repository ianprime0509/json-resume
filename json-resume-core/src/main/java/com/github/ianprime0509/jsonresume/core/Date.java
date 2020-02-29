package com.github.ianprime0509.jsonresume.core;

import com.google.auto.value.AutoOneOf;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.Year;
import java.time.YearMonth;

/**
 * A date with no associated time zone that may specify just a year, a year and a month or a full
 * date (year, month and day).
 */
@AutoOneOf(Date.Kind.class)
public abstract class Date {
  Date() {}

  public static Date ofYear(final Year year) {
    return AutoOneOf_Date.year(year);
  }

  public static Date ofYearMonth(final YearMonth yearMonth) {
    return AutoOneOf_Date.yearMonth(yearMonth);
  }

  public static Date ofYearMonthDay(final LocalDate yearMonthDay) {
    return AutoOneOf_Date.yearMonthDay(yearMonthDay);
  }

  public abstract Kind kind();

  public abstract Year year();

  public abstract YearMonth yearMonth();

  public abstract LocalDate yearMonthDay();

  public final Year asYear() {
    switch (kind()) {
      case YEAR:
        return year();
      case YEAR_MONTH:
        return Year.from(yearMonth());
      default:
        return Year.from(yearMonthDay());
    }
  }

  public final YearMonth asYearMonth() {
    switch (kind()) {
      case YEAR:
        throw new DateTimeException("Cannot convert year to year and month");
      case YEAR_MONTH:
        return yearMonth();
      default:
        return YearMonth.from(yearMonthDay());
    }
  }

  @Override
  public final String toString() {
    switch (kind()) {
      case YEAR:
        return year().toString();
      case YEAR_MONTH:
        return yearMonth().toString();
      default:
        return yearMonthDay().toString();
    }
  }

  public enum Kind {
    YEAR,
    YEAR_MONTH,
    YEAR_MONTH_DAY,
  }
}
