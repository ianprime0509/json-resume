package com.github.ianprime0509.jsonresume.core.resource;

import static java.util.Collections.enumeration;
import static java.util.Collections.singletonList;
import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.Enumeration;
import java.util.ResourceBundle;

/**
 * A {@link ResourceBundle} that just contains the contents of a single file using its single
 * property, {@code contents}. This is used to implement locale-dependent files, such as the
 * preamble for ConTeXt-formatted resumes.
 */
public final class SingleFileResourceBundle extends ResourceBundle {
  private static final String CONTENTS_KEY = "contents";

  private final String contents;

  private SingleFileResourceBundle(final String contents) {
    this.contents = requireNonNull(contents, "contents");
  }

  public static SingleFileResourceBundle load(final InputStream inputStream) throws IOException {
    return load(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
  }

  public static SingleFileResourceBundle load(final Reader reader) throws IOException {
    final StringBuilder contents = new StringBuilder();
    final char[] buffer = new char[8192];
    int read;
    while ((read = reader.read(buffer)) > 0) {
      contents.append(buffer, 0, read);
    }
    return new SingleFileResourceBundle(contents.toString());
  }

  @Override
  protected Object handleGetObject(final String key) {
    return requireNonNull(key, "key").equals(CONTENTS_KEY) ? contents : null;
  }

  @Override
  public Enumeration<String> getKeys() {
    return enumeration(singletonList(CONTENTS_KEY));
  }
}
