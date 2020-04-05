package com.github.ianprime0509.jsonresume.markdown;

import static java.util.Objects.requireNonNull;

import com.github.ianprime0509.jsonresume.core.text.TextWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.Charset;
import java.util.List;

final class MarkdownWriter extends TextWriter {
  private MarkdownWriter(final Writer writer) {
    super(writer, 80);
  }

  static MarkdownWriter create(final Writer writer) {
    return new MarkdownWriter(writer);
  }

  static MarkdownWriter create(final OutputStream outputStream, final Charset charset) {
    return create(new OutputStreamWriter(outputStream, charset));
  }

  public void writeHeading(final String text, final int level) throws IOException {
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
    writeText(heading.toString(), false);
    writeLineBreak();
  }

  public void writeImage(final String altText, final String url) throws IOException {
    requireNonNull(altText, "altText");
    requireNonNull(url, "url");
    writeText("!");
    writeLink(altText, url);
  }

  public void writeLineBreak(final boolean hard) throws IOException {
    if (hard) {
      writeSpace();
      writeSpace();
    }
    writeLineBreak();
  }

  public void writeLink(final String destination) throws IOException {
    requireNonNull(destination, "destination");
    writeText('<' + destination + '>', false);
  }

  public void writeLink(final String label, final String destination) throws IOException {
    requireNonNull(label, "label");
    requireNonNull(destination, "destination");
    writeText('[' + label + "](" + destination + ')', false);
  }

  public void writeList(final List<String> items) throws IOException {
    ensureBlankLines(0);
    for (final String item : items) {
      writeText("-");
      writeSpace();
      writeText(item, true, 2);
      writeLineBreak();
    }
  }

  public void writeParagraph(final String text) throws IOException {
    writeText(text);
    ensureBlankLines(1);
  }

  @Override
  public String toString() {
    return "MarkdownWriter{} " + super.toString();
  }
}
