package com.github.ianprime0509.jsonresume.jackson;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.github.ianprime0509.jsonresume.core.Skill;
import java.util.List;
import javax.annotation.Nullable;

@JsonDeserialize(builder = Skill.Builder.class)
abstract class SkillMixIn {
  private SkillMixIn() {}

  @JsonProperty("priority")
  public abstract int priority();

  @JsonProperty("name")
  public abstract String name();

  @JsonProperty("level")
  @Nullable
  public abstract String level();

  @JsonProperty("keywords")
  public abstract List<String> keywords();

  abstract static class Builder {
    private Builder() {}

    @JsonProperty("priority")
    public abstract Skill.Builder priority(int priority);

    @JsonProperty("name")
    public abstract Skill.Builder name(String name);

    @JsonProperty("level")
    public abstract Skill.Builder level(String level);

    @JsonProperty("keywords")
    public abstract Skill.Builder keywords(List<String> keywords);

    public abstract Skill build();
  }
}
