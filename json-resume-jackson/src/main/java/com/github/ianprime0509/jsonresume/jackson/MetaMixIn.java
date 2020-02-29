package com.github.ianprime0509.jsonresume.jackson;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.github.ianprime0509.jsonresume.core.Meta;
import java.net.URI;
import java.time.LocalDateTime;
import javax.annotation.Nullable;

@JsonDeserialize(builder = Meta.Builder.class)
abstract class MetaMixIn {
  private MetaMixIn() {}

  @JsonProperty("canonical")
  @Nullable
  public abstract URI canonical();

  @JsonProperty("version")
  @Nullable
  public abstract String version();

  @JsonProperty("lastModified")
  @Nullable
  public abstract LocalDateTime lastModified();

  abstract static class Builder {
    private Builder() {}

    @JsonProperty("canonical")
    public abstract Meta.Builder canonical(URI canonical);

    @JsonProperty("version")
    public abstract Meta.Builder version(String version);

    @JsonProperty("lastModified")
    public abstract Meta.Builder lastModified(LocalDateTime lastModified);

    public abstract MetaMixIn build();
  }
}
