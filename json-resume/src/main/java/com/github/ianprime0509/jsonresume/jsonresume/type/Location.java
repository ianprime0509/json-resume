package com.github.ianprime0509.jsonresume.jsonresume.type;

import com.google.auto.value.AutoValue;
import com.squareup.moshi.JsonClass;
import javax.annotation.Nullable;

@AutoValue
@JsonClass(generateAdapter = true, generator = "avm")
public abstract class Location {
  Location() {}

  public static Builder builder() {
    return new AutoValue_Location.Builder();
  }

  @Nullable
  public abstract String address();

  @Nullable
  public abstract String postalCode();

  @Nullable
  public abstract String city();

  @Nullable
  public abstract String countryCode();

  @Nullable
  public abstract String region();

  @AutoValue.Builder
  public abstract static class Builder {
    Builder() {}

    public abstract Builder address(String address);

    public abstract Builder postalCode(String postalCode);

    public abstract Builder city(String city);

    public abstract Builder countryCode(String countryCode);

    public abstract Builder region(String region);

    public abstract Location build();
  }
}
