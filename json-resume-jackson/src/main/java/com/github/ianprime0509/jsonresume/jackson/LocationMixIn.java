package com.github.ianprime0509.jsonresume.jackson;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.github.ianprime0509.jsonresume.api.Location;
import javax.annotation.Nullable;

@JsonDeserialize(builder = Location.Builder.class)
abstract class LocationMixIn {
  private LocationMixIn() {}

  @JsonProperty("address")
  @Nullable
  public abstract String address();

  @JsonProperty("postalCode")
  @Nullable
  public abstract String postalCode();

  @JsonProperty("city")
  @Nullable
  public abstract String city();

  @JsonProperty("countryCode")
  @Nullable
  public abstract String countryCode();

  @JsonProperty("region")
  @Nullable
  public abstract String region();

  abstract static class Builder {
    private Builder() {}

    @JsonProperty("address")
    public abstract Location.Builder address(String address);

    @JsonProperty("postalCode")
    public abstract Location.Builder postalCode(String postalCode);

    @JsonProperty("city")
    public abstract Location.Builder city(String city);

    @JsonProperty("countryCode")
    public abstract Location.Builder countryCode(String countryCode);

    @JsonProperty("region")
    public abstract Location.Builder region(String region);

    public abstract Location build();
  }
}
