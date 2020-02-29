package com.github.ianprime0509.jsonresume.core;

import com.google.auto.value.AutoValue;
import javax.annotation.Nullable;

@AutoValue
public abstract class Language implements Prioritizable {
  Language() {}

  public static Builder builder() {
    return new AutoValue_Language.Builder().priority(0);
  }

  @Override
  public abstract int priority();

  public abstract String language();

  @Nullable
  public abstract String fluency();

  @AutoValue.Builder
  public abstract static class Builder {
    Builder() {}

    public abstract Builder priority(int priority);

    public abstract Builder language(String language);

    public abstract Builder fluency(String fluency);

    public abstract Language build();
  }
}
