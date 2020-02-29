package com.github.ianprime0509.jsonresume.jackson;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.github.ianprime0509.jsonresume.core.Award;
import com.github.ianprime0509.jsonresume.core.Date;
import javax.annotation.Nullable;

@JsonDeserialize(builder = Award.Builder.class)
abstract class AwardMixIn {
  private AwardMixIn() {}

  @JsonProperty("priority")
  public abstract int priority();

  @JsonProperty("title")
  public abstract String title();

  @JsonProperty("date")
  @Nullable
  public abstract Date date();

  @JsonProperty("awarder")
  @Nullable
  public abstract String awarder();

  @JsonProperty("summary")
  @Nullable
  public abstract String summary();

  abstract static class Builder {
    private Builder() {}

    @JsonProperty("priority")
    public abstract Award.Builder priority(int priority);

    @JsonProperty("title")
    public abstract Award.Builder title(String title);

    @JsonProperty("date")
    public abstract Award.Builder date(Date date);

    @JsonProperty("awarder")
    public abstract Award.Builder awarder(String awarder);

    @JsonProperty("summary")
    public abstract Award.Builder summary(String summary);

    public abstract AwardMixIn build();
  }
}
