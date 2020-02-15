package com.github.ianprime0509.jsonresume.jsonresume.type;

import static java.util.Collections.emptyList;

import com.google.auto.value.AutoValue;
import com.squareup.moshi.JsonClass;
import java.net.URI;
import java.util.List;
import javax.annotation.Nullable;

@AutoValue
@JsonClass(generateAdapter = true, generator = "avm")
public abstract class Basics {
  Basics() {}

  public static Builder builder() {
    return new AutoValue_Basics.Builder().profiles(emptyList());
  }

  public abstract String name();

  @Nullable
  public abstract String label();

  @Nullable
  public abstract String image();

  @Nullable
  public abstract String email();

  @Nullable
  public abstract String phone();

  @Nullable
  public abstract URI url();

  @Nullable
  public abstract String summary();

  @Nullable
  public abstract Location location();

  public abstract List<Profile> profiles();

  @AutoValue.Builder
  public abstract static class Builder {
    Builder() {}

    public abstract Builder name(String name);

    public abstract Builder label(String label);

    public abstract Builder image(String image);

    public abstract Builder email(String email);

    public abstract Builder phone(String phone);

    public abstract Builder url(URI url);

    public abstract Builder summary(String summary);

    public abstract Builder location(Location location);

    public abstract Builder profiles(List<Profile> profiles);

    public abstract Basics build();
  }
}
