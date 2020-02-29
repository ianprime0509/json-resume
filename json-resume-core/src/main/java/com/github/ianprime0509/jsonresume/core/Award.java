package com.github.ianprime0509.jsonresume.core;

import com.google.auto.value.AutoValue;
import javax.annotation.Nullable;

@AutoValue
public abstract class Award implements Prioritizable {
  Award() {}

  public static Builder builder() {
    return new AutoValue_Award.Builder().priority(0);
  }

  @Override
  public abstract int priority();

  public abstract String title();

  @Nullable
  public abstract Date date();

  @Nullable
  public abstract String awarder();

  @Nullable
  public abstract String summary();

  @AutoValue.Builder
  public abstract static class Builder {
    Builder() {}

    public abstract Builder priority(int priority);

    public abstract Builder title(String title);

    public abstract Builder date(Date date);

    public abstract Builder awarder(String awarder);

    public abstract Builder summary(String summary);

    public abstract Award build();
  }
}
