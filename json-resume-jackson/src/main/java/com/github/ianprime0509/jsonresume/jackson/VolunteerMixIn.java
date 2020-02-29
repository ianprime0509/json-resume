package com.github.ianprime0509.jsonresume.jackson;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.github.ianprime0509.jsonresume.core.Date;
import com.github.ianprime0509.jsonresume.core.Volunteer;
import java.net.URI;
import java.util.List;
import javax.annotation.Nullable;

@JsonDeserialize(builder = Volunteer.Builder.class)
abstract class VolunteerMixIn {
  private VolunteerMixIn() {}

  @JsonProperty("priority")
  public abstract int priority();

  @JsonProperty("organization")
  public abstract String organization();

  @JsonProperty("position")
  @Nullable
  public abstract String position();

  @JsonProperty("url")
  @Nullable
  public abstract URI url();

  @JsonProperty("startDate")
  public abstract Date startDate();

  @JsonProperty("endDate")
  @Nullable
  public abstract Date endDate();

  @JsonProperty("summary")
  @Nullable
  public abstract String summary();

  @JsonProperty("highlights")
  public abstract List<String> highlights();

  abstract static class Builder {
    private Builder() {}

    @JsonProperty("priority")
    public abstract Volunteer.Builder priority(int priority);

    @JsonProperty("organization")
    public abstract Volunteer.Builder organization(String organization);

    @JsonProperty("position")
    public abstract Volunteer.Builder position(String position);

    @JsonProperty("url")
    public abstract Volunteer.Builder url(URI url);

    @JsonProperty("startDate")
    public abstract Volunteer.Builder startDate(Date startDate);

    @JsonProperty("endDate")
    public abstract Volunteer.Builder endDate(Date endDate);

    @JsonProperty("summary")
    public abstract Volunteer.Builder summary(String summary);

    @JsonProperty("highlights")
    public abstract Volunteer.Builder highlights(List<String> highlights);

    public abstract Volunteer build();
  }
}
