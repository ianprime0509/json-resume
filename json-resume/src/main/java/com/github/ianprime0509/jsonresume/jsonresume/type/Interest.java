package com.github.ianprime0509.jsonresume.jsonresume.type;

import com.google.auto.value.AutoValue;
import com.squareup.moshi.JsonClass;
import java.util.List;

@AutoValue
@JsonClass(generateAdapter = true, generator = "avm")
public abstract class Interest implements Prioritizable {
  Interest() {}

  public static Builder builder() {
    return new AutoValue_Interest.Builder().priority(0);
  }

  @Override
  public abstract int priority();

  public abstract String name();

  public abstract List<String> keywords();

  @AutoValue.Builder
  public abstract static class Builder {
    Builder() {}

    public abstract Builder priority(int priority);

    public abstract Builder name(String name);

    public abstract Builder keywords(List<String> keywords);

    public abstract Interest build();
  }
}
