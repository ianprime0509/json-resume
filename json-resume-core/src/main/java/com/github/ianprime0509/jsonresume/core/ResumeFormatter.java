package com.github.ianprime0509.jsonresume.core;

import java.io.IOException;
import java.io.OutputStream;

/** A formatter for a {@link Resume}. */
public interface ResumeFormatter {
  /**
   * Formats a {@link Resume} to the given {@link OutputStream}.
   *
   * @param outputStream the {@code OutputStream} to which to format the resume. The {@code
   *     OutputStream} is not closed after the resume is written.
   * @param resume the resume to format
   * @throws IOException if an exception occurs while writing the resume data
   */
  void format(OutputStream outputStream, Resume resume) throws IOException;
}
