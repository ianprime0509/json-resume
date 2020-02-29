package com.github.ianprime0509.jsonresume.core.format;

import com.github.ianprime0509.jsonresume.core.Date;
import javax.annotation.Nullable;

/** A formatter for {@link Date}s and date ranges. */
public interface DateFormatter {
  /**
   * Formats the given {@link Date}.
   *
   * @param date the date to format
   * @return a textual representation of the date
   */
  String format(Date date);

  /**
   * Formats the given {@link Date} range. The end date may be omitted to indicate that the range
   * continues into the present.
   *
   * @param startDate the start of the range
   * @param endDate the end of the range ({@code null} to indicate that the range continues into the
   *     present)
   * @return a textual representation of the date range
   */
  String formatRange(Date startDate, @Nullable Date endDate);
}
