package com.github.ianprime0509.jsonresume.core;

import com.google.auto.value.AutoValue;
import java.net.URI;
import java.time.LocalDateTime;
import javax.annotation.Nullable;

@AutoValue
public abstract class Meta {
  static final Meta EMPTY = Meta.builder().build();

  Meta() {}

  public static Builder builder() {
    return new AutoValue_Meta.Builder();
  }

  @Nullable
  public abstract URI canonical();

  @Nullable
  public abstract String version();

  @Nullable
  public abstract LocalDateTime lastModified();

  @AutoValue.Builder
  public abstract static class Builder {
    Builder() {}

    public abstract Builder canonical(URI canonical);

    public abstract Builder version(String version);

    public abstract Builder lastModified(LocalDateTime lastModified);

    public abstract Meta build();
  }
}
