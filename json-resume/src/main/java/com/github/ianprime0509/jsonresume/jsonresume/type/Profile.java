package com.github.ianprime0509.jsonresume.jsonresume.type;

import com.google.auto.value.AutoValue;
import com.squareup.moshi.JsonClass;
import java.net.URI;
import javax.annotation.Nullable;

@AutoValue
@JsonClass(generateAdapter = true, generator = "avm")
public abstract class Profile {
  Profile() {}

  public static Builder builder() {
    return new AutoValue_Profile.Builder();
  }

  public abstract String network();

  public abstract String username();

  @Nullable
  public abstract URI url();

  @AutoValue.Builder
  public abstract static class Builder {
    Builder() {}

    public abstract Builder network(String network);

    public abstract Builder username(String username);

    public abstract Builder url(URI url);

    public abstract Profile build();
  }
}
