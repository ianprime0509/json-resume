package com.github.ianprime0509.jsonresume.jackson;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.github.ianprime0509.jsonresume.api.Date;
import com.github.ianprime0509.jsonresume.api.Education;
import java.util.List;
import javax.annotation.Nullable;

@JsonDeserialize(builder = Education.Builder.class)
abstract class EducationMixIn {
  private EducationMixIn() {}

  @JsonProperty("priority")
  public abstract int priority();

  @JsonProperty("institution")
  public abstract String institution();

  @JsonProperty("area")
  @Nullable
  public abstract String area();

  @JsonProperty("studyType")
  @Nullable
  public abstract String studyType();

  @JsonProperty("startDate")
  public abstract Date startDate();

  @JsonProperty("endDate")
  @Nullable
  public abstract Date endDate();

  @JsonProperty("gpa")
  @Nullable
  public abstract String gpa();

  @JsonProperty("courses")
  public abstract List<String> courses();

  abstract static class Builder {
    private Builder() {}

    @JsonProperty("priority")
    public abstract Education.Builder priority(int priority);

    @JsonProperty("institution")
    public abstract Education.Builder institution(String institution);

    @JsonProperty("area")
    public abstract Education.Builder area(String area);

    @JsonProperty("studyType")
    public abstract Education.Builder studyType(String studyType);

    @JsonProperty("startDate")
    public abstract Education.Builder startDate(Date startDate);

    @JsonProperty("endDate")
    public abstract Education.Builder endDate(Date endDate);

    @JsonProperty("gpa")
    public abstract Education.Builder gpa(String gpa);

    @JsonProperty("courses")
    public abstract Education.Builder courses(List<String> courses);

    public abstract Education build();
  }
}
