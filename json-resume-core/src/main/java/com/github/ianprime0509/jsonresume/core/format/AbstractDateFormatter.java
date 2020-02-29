package com.github.ianprime0509.jsonresume.core.format;

import static java.util.Objects.requireNonNull;

import com.github.ianprime0509.jsonresume.core.Date;
import java.time.LocalDate;
import java.time.Year;
import java.time.YearMonth;
import javax.annotation.Nullable;

/**
 * An abstract implementation of {@link DateFormatter} that takes care of several common
 * implementation details.
 */
public abstract class AbstractDateFormatter implements DateFormatter {
  protected abstract String format(Year year);

  protected abstract String format(YearMonth yearMonth);

  protected abstract String format(LocalDate yearMonthDay);

  protected abstract String formatContinuingRange(String formattedStartDate);

  protected abstract String formatClosedRange(String formattedStartDate, String formattedEndDate);

  @Override
  public String format(final Date date) {
    requireNonNull(date, "date");

    switch (date.kind()) {
      case YEAR:
        return format(date.year());
      case YEAR_MONTH:
        return format(date.yearMonth());
      default:
        return format(date.yearMonthDay());
    }
  }

  @Override
  public final String formatRange(final Date startDate, @Nullable final Date endDate) {
    requireNonNull(startDate, "startDate");

    if (endDate == null) {
      return formatContinuingRange(format(startDate));
    }
    // If the dates, truncated to the unit of the least informational, are the same, then we should
    // just format the least informational date by itself (e.g. January, 2017 - 2017 should be
    // formatted as 2017, May, 2015 - May 15, 2015 should be formatted as May, 2015)
    final Date.Kind leastInformationalKind =
        startDate.kind().compareTo(endDate.kind()) <= 0 ? startDate.kind() : endDate.kind();
    switch (leastInformationalKind) {
      case YEAR:
        if (startDate.asYear().equals(endDate.asYear())) {
          return format(startDate.asYear());
        }
        break;
      case YEAR_MONTH:
        if (startDate.asYearMonth().equals(endDate.asYearMonth())) {
          return format(startDate.asYearMonth());
        }
        break;
      case YEAR_MONTH_DAY:
        if (startDate.yearMonthDay().equals(endDate.yearMonthDay())) {
          return format(startDate.yearMonthDay());
        }
        break;
    }
    return formatClosedRange(format(startDate), format(endDate));
  }
}
