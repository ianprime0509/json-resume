package com.github.ianprime0509.jsonresume.jackson;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.github.ianprime0509.jsonresume.api.Date;
import com.github.ianprime0509.jsonresume.api.Project;
import java.net.URI;
import java.util.List;
import javax.annotation.Nullable;

@JsonDeserialize(builder = Project.Builder.class)
abstract class ProjectMixIn {
  private ProjectMixIn() {}

  @JsonProperty("priority")
  public abstract int priority();

  @JsonProperty("name")
  public abstract String name();

  @JsonProperty("description")
  @Nullable
  public abstract String description();

  @JsonProperty("highlights")
  public abstract List<String> highlights();

  @JsonProperty("keywords")
  public abstract List<String> keywords();

  @JsonProperty("startDate")
  @Nullable
  public abstract Date startDate();

  @JsonProperty("endDate")
  @Nullable
  public abstract Date endDate();

  @JsonProperty("url")
  @Nullable
  public abstract URI url();

  @JsonProperty("roles")
  public abstract List<String> roles();

  @JsonProperty("entity")
  @Nullable
  public abstract String entity();

  @JsonProperty("type")
  @Nullable
  public abstract String type();

  abstract static class Builder {
    private Builder() {}

    @JsonProperty("priority")
    public abstract Project.Builder priority(int priority);

    @JsonProperty("name")
    public abstract Project.Builder name(String name);

    @JsonProperty("description")
    public abstract Project.Builder description(String description);

    @JsonProperty("highlights")
    public abstract Project.Builder highlights(List<String> highlights);

    @JsonProperty("keywords")
    public abstract Project.Builder keywords(List<String> keywords);

    @JsonProperty("startDate")
    public abstract Project.Builder startDate(Date startDate);

    @JsonProperty("endDate")
    public abstract Project.Builder endDate(Date endDate);

    @JsonProperty("url")
    public abstract Project.Builder url(URI url);

    @JsonProperty("roles")
    public abstract Project.Builder roles(List<String> roles);

    @JsonProperty("entity")
    public abstract Project.Builder entity(String entity);

    @JsonProperty("type")
    public abstract Project.Builder type(String type);

    public abstract Project build();
  }
}
