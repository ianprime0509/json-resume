package com.github.ianprime0509.jsonresume.core;

import static java.util.Collections.emptyList;

import com.google.auto.value.AutoValue;
import java.util.List;
import javax.annotation.Nullable;

@AutoValue
public abstract class Skill implements Prioritizable {
  Skill() {}

  public static Builder builder() {
    return new AutoValue_Skill.Builder().priority(0).keywords(emptyList());
  }

  @Override
  public abstract int priority();

  public abstract String name();

  @Nullable
  public abstract String level();

  public abstract List<String> keywords();

  @AutoValue.Builder
  public abstract static class Builder {
    Builder() {}

    public abstract Builder priority(int priority);

    public abstract Builder name(String name);

    public abstract Builder level(String level);

    public abstract Builder keywords(List<String> keywords);

    public abstract Skill build();
  }
}
