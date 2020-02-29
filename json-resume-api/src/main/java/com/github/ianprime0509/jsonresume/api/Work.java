package com.github.ianprime0509.jsonresume.api;

import static java.util.Collections.emptyList;

import com.google.auto.value.AutoValue;
import java.net.URI;
import java.util.List;
import javax.annotation.Nullable;

@AutoValue
public abstract class Work implements Prioritizable {
  Work() {}

  public static Builder builder() {
    return new AutoValue_Work.Builder().priority(0).highlights(emptyList());
  }

  @Override
  public abstract int priority();

  public abstract String name();

  @Nullable
  public abstract String location();

  @Nullable
  public abstract String description();

  @Nullable
  public abstract String position();

  @Nullable
  public abstract URI url();

  public abstract Date startDate();

  @Nullable
  public abstract Date endDate();

  @Nullable
  public abstract String summary();

  public abstract List<String> highlights();

  @AutoValue.Builder
  public abstract static class Builder {
    Builder() {}

    public abstract Builder priority(int priority);

    public abstract Builder name(String name);

    public abstract Builder location(String location);

    public abstract Builder description(String description);

    public abstract Builder position(String position);

    public abstract Builder url(URI url);

    public abstract Builder startDate(Date startDate);

    public abstract Builder endDate(Date endDate);

    public abstract Builder summary(String summary);

    public abstract Builder highlights(List<String> highlights);

    public abstract Work build();
  }
}
