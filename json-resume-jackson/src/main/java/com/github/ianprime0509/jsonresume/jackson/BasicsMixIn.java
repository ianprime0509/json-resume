package com.github.ianprime0509.jsonresume.jackson;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.github.ianprime0509.jsonresume.api.Basics;
import com.github.ianprime0509.jsonresume.api.Location;
import com.github.ianprime0509.jsonresume.api.Profile;
import java.net.URI;
import java.util.List;
import javax.annotation.Nullable;

@JsonDeserialize(builder = Basics.Builder.class)
abstract class BasicsMixIn {
  private BasicsMixIn() {}

  @JsonProperty("name")
  public abstract String name();

  @JsonProperty("label")
  @Nullable
  public abstract String label();

  @JsonProperty("image")
  @Nullable
  public abstract String image();

  @JsonProperty("email")
  @Nullable
  public abstract String email();

  @JsonProperty("phone")
  @Nullable
  public abstract String phone();

  @JsonProperty("url")
  @Nullable
  public abstract URI url();

  @JsonProperty("summary")
  @Nullable
  public abstract String summary();

  @JsonProperty("location")
  @Nullable
  public abstract Location location();

  @JsonProperty("profiles")
  public abstract List<Profile> profiles();

  abstract static class Builder {
    private Builder() {}

    @JsonProperty("name")
    public abstract Basics.Builder name(String name);

    @JsonProperty("label")
    public abstract Basics.Builder label(String label);

    @JsonProperty("image")
    public abstract Basics.Builder image(String image);

    @JsonProperty("email")
    public abstract Basics.Builder email(String email);

    @JsonProperty("phone")
    public abstract Basics.Builder phone(String phone);

    @JsonProperty("url")
    public abstract Basics.Builder url(URI url);

    @JsonProperty("summary")
    public abstract Basics.Builder summary(String summary);

    @JsonProperty("location")
    public abstract Basics.Builder location(Location location);

    @JsonProperty("profiles")
    public abstract Basics.Builder profiles(List<Profile> profiles);

    public abstract Basics build();
  }
}
