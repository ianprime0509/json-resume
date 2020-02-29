package com.github.ianprime0509.jsonresume.jackson;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.github.ianprime0509.jsonresume.api.Date;
import com.github.ianprime0509.jsonresume.api.Publication;
import java.net.URI;
import javax.annotation.Nullable;

@JsonDeserialize(builder = Publication.Builder.class)
abstract class PublicationMixIn {
  private PublicationMixIn() {}

  @JsonProperty("priority")
  public abstract int priority();

  @JsonProperty("name")
  public abstract String name();

  @JsonProperty("publisher")
  @Nullable
  public abstract String publisher();

  @JsonProperty("releaseDate")
  @Nullable
  public abstract Date releaseDate();

  @JsonProperty("url")
  @Nullable
  public abstract URI url();

  @JsonProperty("summary")
  @Nullable
  public abstract String summary();

  abstract static class Builder {
    private Builder() {}

    @JsonProperty("priority")
    public abstract Publication.Builder priority(int priority);

    @JsonProperty("name")
    public abstract Publication.Builder name(String name);

    @JsonProperty("publisher")
    public abstract Publication.Builder publisher(String publisher);

    @JsonProperty("releaseDate")
    public abstract Publication.Builder releaseDate(Date releaseDate);

    @JsonProperty("url")
    public abstract Publication.Builder url(URI url);

    @JsonProperty("summary")
    public abstract Publication.Builder summary(String summary);

    public abstract Publication build();
  }
}
