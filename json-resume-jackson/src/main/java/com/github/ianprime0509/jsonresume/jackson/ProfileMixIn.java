package com.github.ianprime0509.jsonresume.jackson;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.github.ianprime0509.jsonresume.core.Profile;
import java.net.URI;
import javax.annotation.Nullable;

@JsonDeserialize(builder = Profile.Builder.class)
abstract class ProfileMixIn {
  private ProfileMixIn() {}

  @JsonProperty("network")
  public abstract String network();

  @JsonProperty("username")
  public abstract String username();

  @JsonProperty("url")
  @Nullable
  public abstract URI url();

  abstract static class Builder {
    private Builder() {}

    @JsonProperty("network")
    public abstract Profile.Builder network(String network);

    @JsonProperty("username")
    public abstract Profile.Builder username(String username);

    @JsonProperty("url")
    public abstract Profile.Builder url(URI url);

    public abstract Profile build();
  }
}
