package com.github.ianprime0509.jsonresume.jsonresume.formatter;

import static java.util.Objects.requireNonNull;

import java.io.Closeable;
import java.io.Flushable;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.Charset;
import java.util.List;

final class MarkdownWriter implements Closeable, Flushable {
  private final Writer writer;
  private final int wrapColumn = 80;
  private int column = 1;
  private int consecutiveLineBreaks = 0;

  private MarkdownWriter(final Writer writer) {
    this.writer = writer;
  }

  public static MarkdownWriter create(final Writer writer) {
    return new MarkdownWriter(writer);
  }

  public static MarkdownWriter create(final OutputStream outputStream, final Charset charset) {
    return create(new OutputStreamWriter(outputStream, charset));
  }

  @Override
  public void close() throws IOException {
    writer.close();
  }

  @Override
  public void flush() throws IOException {
    writer.flush();
  }

  public MarkdownWriter ensureBlankLines(final int nLines) throws IOException {
    if (nLines < 0) {
      throw new IllegalArgumentException("nLines must be non-negative");
    }

    final int neededLineBreaks = nLines - consecutiveLineBreaks + 1;
    for (int i = 0; i < neededLineBreaks; i++) {
      writeLineBreak();
    }
    return this;
  }

  public MarkdownWriter writeHeading(final String text, final int level) throws IOException {
    requireNonNull(text, "text");
    if (level < 1 || level > 6) {
      throw new IllegalArgumentException("level must be between 1 and 6");
    }

    final StringBuilder heading = new StringBuilder();
    for (int i = 0; i < level; i++) {
      heading.append('#');
    }
    heading.append(' ');
    heading.append(text);
    return writeText(heading.toString(), false).writeLineBreak();
  }

  public MarkdownWriter writeLineBreak() throws IOException {
    return writeLineBreak(false);
  }

  public MarkdownWriter writeLineBreak(final boolean hard) throws IOException {
    if (hard) {
      writer.write("  ");
    }
    writer.write('\n');
    column = 1;
    consecutiveLineBreaks++;
    return this;
  }

  public MarkdownWriter writeLink(final String destination) throws IOException {
    return writeText('<' + destination + '>', false);
  }

  public MarkdownWriter writeLink(final String label, final String destination) throws IOException {
    return writeText('[' + label + "](" + destination + ')', false);
  }

  public MarkdownWriter writeList(final List<String> items) throws IOException {
    ensureBlankLines(0);
    for (final String item : items) {
      writeText("-").writeSpace().writeText(item).writeLineBreak();
    }
    return this;
  }

  public MarkdownWriter writeParagraph(final String text) throws IOException {
    return writeText(text).ensureBlankLines(1);
  }

  public MarkdownWriter writeSpace() throws IOException {
    writer.write(' ');
    column++;
    return this;
  }

  public MarkdownWriter writeText(final String text) throws IOException {
    return writeText(text, true);
  }

  public MarkdownWriter writeText(final String text, final boolean wrap) throws IOException {
    return writeText(text, wrap, 0);
  }

  public MarkdownWriter writeText(final String text, final boolean wrap, final int hangingIndent)
      throws IOException {
    requireNonNull(text, "text");
    if (hangingIndent < 0) {
      throw new IllegalArgumentException("hangingIndent must be non-negative");
    }

    boolean firstWord = true;
    for (final String word : text.split("\\p{IsWhite_Space}")) {
      consecutiveLineBreaks = 0;
      // This isn't entirely correct, but it's close enough I guess
      if (wrap && column + word.length() > wrapColumn) {
        writeLineBreak();
        for (int i = 0; i < hangingIndent; i++) {
          writeSpace();
        }
      } else if (!firstWord) {
        writeSpace();
      }
      writer.write(word);
      column += word.length();
      firstWord = false;
    }
    return this;
  }
}
