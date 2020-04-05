package com.github.ianprime0509.jsonresume.context;

import static java.util.stream.Collectors.toList;

import com.github.ianprime0509.jsonresume.core.text.TextWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.List;

final class ContextWriter extends TextWriter {
  private ContextWriter(final Writer writer) {
    super(writer, 80);
  }

  static ContextWriter create(final Writer writer) {
    return new ContextWriter(writer);
  }

  @Override
  public String escape(final String s) {
    // TODO
    return super.escape(s);
  }

  public void writeItems(final List<String> items) throws IOException {
    writeRawItems(items.stream().map(this::escape).collect(toList()));
  }

  public void writeRaw(final String text) throws IOException {
    writeText(text, false, 0, false);
  }

  public void writeRawItems(final List<String> items) throws IOException {
    for (final String item : items) {
      writeText("\\item " + item, true, 2, false);
      writeLineBreak();
    }
  }

  public void writeRawLine(final String text) throws IOException {
    writeRaw(text);
    writeLineBreak();
  }
}
