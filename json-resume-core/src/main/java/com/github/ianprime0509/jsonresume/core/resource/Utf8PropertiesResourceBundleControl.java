package com.github.ianprime0509.jsonresume.core.resource;

import static java.util.Objects.requireNonNull;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Locale;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

/**
 * An implementation of {@link ResourceBundle.Control} that loads resources from properties files
 * encoded in UTF-8.
 *
 * <p>Java versions 9 and above handle this natively, but Java 8 only allows ISO-8859-1 in
 * properties files using the default control, hence the motivation for this class.
 */
public final class Utf8PropertiesResourceBundleControl extends ResourceBundle.Control {
  public static final Utf8PropertiesResourceBundleControl INSTANCE =
      new Utf8PropertiesResourceBundleControl();

  private static final String EXTENSION = "properties";

  private Utf8PropertiesResourceBundleControl() {}

  @Override
  public List<String> getFormats(final String baseName) {
    return FORMAT_PROPERTIES;
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

    if (!FORMAT_PROPERTIES.contains(format)) {
      return null;
    }

    final String resourceName = toResourceName(toBundleName(baseName, locale), EXTENSION);
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
      try (final InputStreamReader reader =
              new InputStreamReader(inputStream, StandardCharsets.UTF_8);
          final BufferedReader bufferedReader = new BufferedReader(reader)) {
        return new PropertyResourceBundle(bufferedReader);
      }
    }
    return null;
  }
}
