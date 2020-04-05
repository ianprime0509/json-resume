package com.github.ianprime0509.jsonresume.core.format;

import com.github.ianprime0509.jsonresume.core.resource.Utf8PropertiesResourceBundleControl;
import com.ibm.icu.text.DateFormat;
import java.text.MessageFormat;
import java.time.LocalDate;
import java.time.MonthDay;
import java.time.Year;
import java.time.YearMonth;
import java.time.ZoneId;
import java.util.Date;
import java.util.Locale;
import java.util.ResourceBundle;

final class StandardDateFormatter extends AbstractDateFormatter {
  private final String yearFormatSkeleton;
  private final String yearMonthFormatSkeleton;
  private final String yearMonthDayFormatSkeleton;
  private final Locale locale;
  private final String continuingDateRangeFormat;
  private final String closedDateRangeFormat;

  StandardDateFormatter(
      final String yearFormatSkeleton,
      final String yearMonthFormatSkeleton,
      final String yearMonthDayFormatSkeleton,
      final Locale locale) {
    this.yearFormatSkeleton = yearFormatSkeleton;
    this.yearMonthFormatSkeleton = yearMonthFormatSkeleton;
    this.yearMonthDayFormatSkeleton = yearMonthDayFormatSkeleton;
    this.locale = locale;
    final ResourceBundle formatBundle =
        ResourceBundle.getBundle(
            "com.github.ianprime0509.jsonresume.core.format.FormatBundle",
            locale,
            Utf8PropertiesResourceBundleControl.INSTANCE);
    continuingDateRangeFormat = formatBundle.getString("date-range.continuing");
    closedDateRangeFormat = formatBundle.getString("date-range.closed");
  }

  @Override
  public String toString() {
    return "StandardDateFormatter{"
        + "yearFormatSkeleton='"
        + yearFormatSkeleton
        + '\''
        + ", yearMonthFormatSkeleton='"
        + yearMonthFormatSkeleton
        + '\''
        + ", yearMonthDayFormatSkeleton='"
        + yearMonthDayFormatSkeleton
        + '\''
        + ", locale="
        + locale
        + '}';
  }

  @Override
  protected String format(final Year year) {
    return DateFormat.getInstanceForSkeleton(yearFormatSkeleton, locale)
        .format(
            Date.from(
                year.atMonthDay(MonthDay.of(1, 1))
                    .atStartOfDay(ZoneId.systemDefault())
                    .toInstant()));
  }

  @Override
  protected String format(final YearMonth yearMonth) {
    return DateFormat.getInstanceForSkeleton(yearMonthFormatSkeleton, locale)
        .format(Date.from(yearMonth.atDay(1).atStartOfDay(ZoneId.systemDefault()).toInstant()));
  }

  @Override
  protected String format(final LocalDate yearMonthDay) {
    return DateFormat.getInstanceForSkeleton(yearMonthDayFormatSkeleton, locale)
        .format(Date.from(yearMonthDay.atStartOfDay(ZoneId.systemDefault()).toInstant()));
  }

  @Override
  protected String formatContinuingRange(final String formattedStartDate) {
    return MessageFormat.format(continuingDateRangeFormat, formattedStartDate);
  }

  @Override
  protected String formatClosedRange(
      final String formattedStartDate, final String formattedEndDate) {
    return MessageFormat.format(closedDateRangeFormat, formattedStartDate, formattedEndDate);
  }
}
