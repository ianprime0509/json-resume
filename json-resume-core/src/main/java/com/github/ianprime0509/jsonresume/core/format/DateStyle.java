package com.github.ianprime0509.jsonresume.core.format;

import com.ibm.icu.text.DateFormat;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Locale;

/**
 * A factory for {@link DateFormatter}s that uses a {@link Locale} to return a localized formatter.
 * Instances of this class can be thought of as date styles, such as short or long dates, that are
 * independent of locale but can be associated with a locale to produce a {@code DateFormatter}.
 */
public abstract class DateStyle {
  public static DateStyle FULL =
      withFormatSkeletons(DateFormat.YEAR, DateFormat.YEAR_MONTH, DateFormat.YEAR_MONTH_DAY);
  public static DateStyle ABBREVIATED =
      withFormatSkeletons(
          DateFormat.YEAR, DateFormat.YEAR_ABBR_MONTH, DateFormat.YEAR_ABBR_MONTH_DAY);

  private static DateStyle withFormatSkeletons(
      final String yearFormatSkeleton,
      final String yearMonthFormatSkeleton,
      final String yearMonthDayFormatSkeleton) {
    return new DateStyle() {
      @Override
      public DateFormatter getFormatter(final Locale locale) {
        return new StandardDateFormatter(
            yearFormatSkeleton, yearMonthFormatSkeleton, yearMonthDayFormatSkeleton, locale);
      }
    };
  }

  /**
   * Gets a {@link DateFormatter} using the default locale.
   *
   * @return a {@code DateFormatter} using the default locale
   * @see #getFormatter(Locale)
   */
  public final DateFormatter getFormatter() {
    DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL);
    return getFormatter(Locale.getDefault());
  }

  /**
   * Gets a {@link DateFormatter} using the given locale.
   *
   * @param locale the locale to use for formatting dates
   * @return a {@code DateFormatter} using the given locale
   */
  public abstract DateFormatter getFormatter(Locale locale);
}
