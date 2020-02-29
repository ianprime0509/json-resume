package com.github.ianprime0509.jsonresume.jackson;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.github.ianprime0509.jsonresume.api.Language;
import javax.annotation.Nullable;

@JsonDeserialize(builder = Language.Builder.class)
abstract class LanguageMixIn {
  private LanguageMixIn() {}

  @JsonProperty("priority")
  public abstract int priority();

  @JsonProperty("language")
  public abstract String language();

  @JsonProperty("fluency")
  @Nullable
  public abstract String fluency();

  abstract static class Builder {
    private Builder() {}

    @JsonProperty("priority")
    public abstract Language.Builder priority(int priority);

    @JsonProperty("language")
    public abstract Language.Builder language(String language);

    @JsonProperty("fluency")
    public abstract Language.Builder fluency(String fluency);

    public abstract Language build();
  }
}
