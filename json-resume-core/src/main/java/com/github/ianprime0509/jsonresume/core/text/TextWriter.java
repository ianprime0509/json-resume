package com.github.ianprime0509.jsonresume.core.text;

import static java.util.Objects.requireNonNull;

import java.io.Closeable;
import java.io.Flushable;
import java.io.IOException;
import java.io.Writer;

/**
 * A class with basic methods for writing plain text output. It is designed to be extended to
 * support more detailed text-based formats, such as Markdown.
 */
public class TextWriter implements Closeable, Flushable {
  private final Writer writer;
  private final int wrapColumn;
  private int column = 1;
  private int consecutiveLineBreaks = 0;

  protected TextWriter(final Writer writer, final int wrapColumn) {
    if (wrapColumn <= 0) {
      throw new IllegalArgumentException("wrapColumn must be at least 1");
    }
    this.writer = requireNonNull(writer, "writer");
    this.wrapColumn = wrapColumn;
  }

  @Override
  public final void close() throws IOException {
    writer.close();
  }

  @Override
  public final void flush() throws IOException {
    writer.flush();
  }

  public void ensureBlankLines(final int nLines) throws IOException {
    if (nLines < 0) {
      throw new IllegalArgumentException("nLines must be non-negative");
    }

    final int neededLineBreaks = nLines - consecutiveLineBreaks + 1;
    for (int i = 0; i < neededLineBreaks; i++) {
      writeLineBreak();
    }
  }

  public String escape(final String s) {
    return s;
  }

  public final void writeLineBreak() throws IOException {
    writer.write('\n');
    column = 1;
    consecutiveLineBreaks++;
  }

  public final void writeSpace() throws IOException {
    writer.write(' ');
    column++;
  }

  public final void writeText(final String text) throws IOException {
    writeText(text, true);
  }

  public final void writeText(final String text, final boolean wrap) throws IOException {
    writeText(text, wrap, 0);
  }

  public final void writeText(final String text, final boolean wrap, final int hangingIndent)
      throws IOException {
    writeText(text, wrap, hangingIndent, true);
  }

  public final void writeText(
      final String text, final boolean wrap, final int hangingIndent, final boolean escape)
      throws IOException {
    requireNonNull(text, "text");
    if (hangingIndent < 0) {
      throw new IllegalArgumentException("hangingIndent must be non-negative");
    }

    boolean firstWord = true;
    for (final String word : text.split("\\p{IsWhite_Space}")) {
      // This isn't entirely correct, but it's close enough I guess
      if (wrap && column + word.length() > wrapColumn) {
        writeLineBreak();
        for (int i = 0; i < hangingIndent; i++) {
          writeSpace();
        }
      } else if (!firstWord) {
        writeSpace();
      }
      writer.write(escape ? escape(word) : word);
      column += word.length();
      firstWord = false;
      consecutiveLineBreaks = 0;
    }
  }

  @Override
  public String toString() {
    return "TextWriter{"
        + "writer="
        + writer
        + ", wrapColumn="
        + wrapColumn
        + ", column="
        + column
        + ", consecutiveLineBreaks="
        + consecutiveLineBreaks
        + '}';
  }
}
