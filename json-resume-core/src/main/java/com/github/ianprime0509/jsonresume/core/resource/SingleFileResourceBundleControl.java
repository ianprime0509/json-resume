package com.github.ianprime0509.jsonresume.core.resource;

import static java.util.Collections.singletonList;
import static java.util.Objects.requireNonNull;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * An implementation of {@link ResourceBundle.Control} to load {@link SingleFileResourceBundle}s.
 */
public final class SingleFileResourceBundleControl extends ResourceBundle.Control {
  private static final String FORMAT = "single-file";

  private final String extension;

  private SingleFileResourceBundleControl(final String extension) {
    this.extension = requireNonNull(extension, "extension");
  }

  public static SingleFileResourceBundleControl create(final String extension) {
    return new SingleFileResourceBundleControl(extension);
  }

  @Override
  public List<String> getFormats(final String baseName) {
    return singletonList(FORMAT);
  }

  @Override
  public ResourceBundle newBundle(
      final String baseName,
      final Locale locale,
      final String format,
      final ClassLoader loader,
      final boolean reload)
      throws IOException {
    requireNonNull(baseName, "baseName");
    requireNonNull(locale, "locale");
    requireNonNull(format, "format");
    requireNonNull(loader, "loader");

    if (!format.equals(FORMAT)) {
      return null;
    }

    final String resourceName = toResourceName(toBundleName(baseName, locale), extension);
    InputStream inputStream = null;
    if (reload) {
      // This part was just copied from the Javadoc for ResourceBundle.Control
      final URL url = loader.getResource(resourceName);
      if (url != null) {
        final URLConnection connection = url.openConnection();
        if (connection != null) {
          connection.setUseCaches(false);
          inputStream = connection.getInputStream();
        }
      }
    } else {
      inputStream = loader.getResourceAsStream(resourceName);
    }

    if (inputStream != null) {
      try (final BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream)) {
        return SingleFileResourceBundle.load(bufferedInputStream);
      }
    }
    return null;
  }
}
