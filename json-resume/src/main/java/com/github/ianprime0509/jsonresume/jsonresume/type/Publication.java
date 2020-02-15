package com.github.ianprime0509.jsonresume.jsonresume.type;

import com.google.auto.value.AutoValue;
import com.squareup.moshi.JsonClass;
import java.net.URI;
import javax.annotation.Nullable;

@AutoValue
@JsonClass(generateAdapter = true, generator = "avm")
public abstract class Publication implements Prioritizable {
  Publication() {}

  public static Builder builder() {
    return new AutoValue_Publication.Builder().priority(0);
  }

  @Override
  public abstract int priority();

  public abstract String name();

  @Nullable
  public abstract String publisher();

  @Nullable
  public abstract Date releaseDate();

  @Nullable
  public abstract URI url();

  @Nullable
  public abstract String summary();

  @AutoValue.Builder
  public abstract static class Builder {
    Builder() {}

    public abstract Builder priority(int priority);

    public abstract Builder name(String name);

    public abstract Builder publisher(String publisher);

    public abstract Builder releaseDate(Date releaseDate);

    public abstract Builder url(URI url);

    public abstract Builder summary(String summary);

    public abstract Publication build();
  }
}
