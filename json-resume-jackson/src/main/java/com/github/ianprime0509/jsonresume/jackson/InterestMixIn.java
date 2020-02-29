package com.github.ianprime0509.jsonresume.jackson;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.github.ianprime0509.jsonresume.api.Interest;
import java.util.List;

@JsonDeserialize(builder = Interest.Builder.class)
abstract class InterestMixIn {
  private InterestMixIn() {}

  @JsonProperty("priority")
  public abstract int priority();

  @JsonProperty("name")
  public abstract String name();

  @JsonProperty("keywords")
  public abstract List<String> keywords();

  abstract static class Builder {
    private Builder() {}

    @JsonProperty("priority")
    public abstract Interest.Builder priority(int priority);

    @JsonProperty("name")
    public abstract Interest.Builder name(String name);

    @JsonProperty("keywords")
    public abstract Interest.Builder keywords(List<String> keywords);

    public abstract Interest build();
  }
}
