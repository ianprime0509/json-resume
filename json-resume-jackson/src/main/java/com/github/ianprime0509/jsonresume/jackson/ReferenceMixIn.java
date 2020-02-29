package com.github.ianprime0509.jsonresume.jackson;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.github.ianprime0509.jsonresume.core.Reference;
import javax.annotation.Nullable;

@JsonDeserialize(builder = Reference.Builder.class)
abstract class ReferenceMixIn {
  private ReferenceMixIn() {}

  @JsonProperty("priority")
  public abstract int priority();

  @JsonProperty("name")
  public abstract String name();

  @JsonProperty("reference")
  @Nullable
  public abstract String reference();

  abstract static class Builder {
    private Builder() {}

    @JsonProperty("priority")
    public abstract Reference.Builder priority(int priority);

    @JsonProperty("name")
    public abstract Reference.Builder name(String name);

    @JsonProperty("reference")
    public abstract Reference.Builder reference(String reference);

    public abstract Reference build();
  }
}
