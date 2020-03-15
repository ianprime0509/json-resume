package com.github.ianprime0509.jsonresume.markdown;

import static java.util.Objects.requireNonNull;

import javax.annotation.Nullable;

final class MoreObjects {
  private MoreObjects() {}

  /** A "backport" of the equivalent method on {@code Objects} in Java 9+. */
  static <T> T requireNonNullElse(@Nullable final T obj, final T defaultObj) {
    return obj != null ? obj : requireNonNull(defaultObj, "defaultObj");
  }
}
