package com.github.ianprime0509.jsonresume.jackson;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.github.ianprime0509.jsonresume.core.Date;
import com.github.ianprime0509.jsonresume.core.Work;
import java.net.URI;
import java.util.List;
import javax.annotation.Nullable;

@JsonDeserialize(builder = Work.Builder.class)
abstract class WorkMixIn {
  private WorkMixIn() {}

  @JsonProperty("priority")
  public abstract int priority();

  @JsonProperty("name")
  public abstract String name();

  @JsonProperty("location")
  @Nullable
  public abstract String location();

  @JsonProperty("description")
  @Nullable
  public abstract String description();

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
    public abstract Work.Builder priority(int priority);

    @JsonProperty("name")
    public abstract Work.Builder name(String name);

    @JsonProperty("location")
    public abstract Work.Builder location(String location);

    @JsonProperty("description")
    public abstract Work.Builder description(String description);

    @JsonProperty("position")
    public abstract Work.Builder position(String position);

    @JsonProperty("url")
    public abstract Work.Builder url(URI url);

    @JsonProperty("startDate")
    public abstract Work.Builder startDate(Date startDate);

    @JsonProperty("endDate")
    public abstract Work.Builder endDate(Date endDate);

    @JsonProperty("summary")
    public abstract Work.Builder summary(String summary);

    @JsonProperty("highlights")
    public abstract Work.Builder highlights(List<String> highlights);

    public abstract Work build();
  }
}
