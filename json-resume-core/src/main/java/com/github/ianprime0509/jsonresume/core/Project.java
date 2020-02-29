package com.github.ianprime0509.jsonresume.core;

import static java.util.Collections.emptyList;

import com.google.auto.value.AutoValue;
import java.net.URI;
import java.util.List;
import javax.annotation.Nullable;

@AutoValue
public abstract class Project implements Prioritizable {
  Project() {}

  public static Builder builder() {
    return new AutoValue_Project.Builder()
        .priority(0)
        .highlights(emptyList())
        .keywords(emptyList())
        .roles(emptyList());
  }

  @Override
  public abstract int priority();

  public abstract String name();

  @Nullable
  public abstract String description();

  public abstract List<String> highlights();

  public abstract List<String> keywords();

  @Nullable
  public abstract Date startDate();

  @Nullable
  public abstract Date endDate();

  @Nullable
  public abstract URI url();

  public abstract List<String> roles();

  @Nullable
  public abstract String entity();

  @Nullable
  public abstract String type();

  @AutoValue.Builder
  public abstract static class Builder {
    Builder() {}

    public abstract Builder priority(int priority);

    public abstract Builder name(String name);

    public abstract Builder description(String description);

    public abstract Builder highlights(List<String> highlights);

    public abstract Builder keywords(List<String> keywords);

    public abstract Builder startDate(Date startDate);

    public abstract Builder endDate(Date endDate);

    public abstract Builder url(URI url);

    public abstract Builder roles(List<String> roles);

    public abstract Builder entity(String entity);

    public abstract Builder type(String type);

    public abstract Project build();
  }
}
