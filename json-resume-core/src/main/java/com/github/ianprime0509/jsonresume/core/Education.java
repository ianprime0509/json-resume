package com.github.ianprime0509.jsonresume.core;

import static java.util.Collections.emptyList;

import com.google.auto.value.AutoValue;
import java.util.List;
import javax.annotation.Nullable;

@AutoValue
public abstract class Education implements Prioritizable {
  Education() {}

  public static Builder builder() {
    return new AutoValue_Education.Builder().priority(0).courses(emptyList());
  }

  @Override
  public abstract int priority();

  public abstract String institution();

  @Nullable
  public abstract String area();

  @Nullable
  public abstract String studyType();

  public abstract Date startDate();

  @Nullable
  public abstract Date endDate();

  @Nullable
  public abstract String gpa();

  public abstract List<String> courses();

  @AutoValue.Builder
  public abstract static class Builder {
    Builder() {}

    public abstract Builder priority(int priority);

    public abstract Builder institution(String institution);

    public abstract Builder area(String area);

    public abstract Builder studyType(String studyType);

    public abstract Builder startDate(Date startDate);

    public abstract Builder endDate(Date endDate);

    public abstract Builder gpa(String gpa);

    public abstract Builder courses(List<String> courses);

    public abstract Education build();
  }
}
