package com.github.ianprime0509.jsonresume.api;

import com.google.auto.value.AutoValue;
import javax.annotation.Nullable;

@AutoValue
public abstract class Reference implements Prioritizable {
  Reference() {}

  public static Builder builder() {
    return new AutoValue_Reference.Builder().priority(0);
  }

  @Override
  public abstract int priority();

  public abstract String name();

  @Nullable
  public abstract String reference();

  @AutoValue.Builder
  public abstract static class Builder {
    Builder() {}

    public abstract Builder priority(int priority);

    public abstract Builder name(String name);

    public abstract Builder reference(String reference);

    public abstract Reference build();
  }
}
