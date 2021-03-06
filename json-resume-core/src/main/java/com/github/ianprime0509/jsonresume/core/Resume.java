package com.github.ianprime0509.jsonresume.core;

import static java.util.Collections.emptyList;

import com.google.auto.value.AutoValue;
import java.util.List;

@AutoValue
public abstract class Resume {
  Resume() {}

  public static Builder builder() {
    return new AutoValue_Resume.Builder()
        .work(emptyList())
        .volunteer(emptyList())
        .education(emptyList())
        .awards(emptyList())
        .publications(emptyList())
        .skills(emptyList())
        .languages(emptyList())
        .interests(emptyList())
        .references(emptyList())
        .projects(emptyList())
        .meta(Meta.EMPTY);
  }

  public abstract Basics basics();

  public abstract List<Work> work();

  public abstract List<Volunteer> volunteer();

  public abstract List<Education> education();

  public abstract List<Award> awards();

  public abstract List<Publication> publications();

  public abstract List<Skill> skills();

  public abstract List<Language> languages();

  public abstract List<Interest> interests();

  public abstract List<Reference> references();

  public abstract List<Project> projects();

  public abstract Meta meta();

  @AutoValue.Builder
  public abstract static class Builder {
    Builder() {}

    public abstract Builder basics(Basics basics);

    public abstract Builder work(List<Work> work);

    public abstract Builder volunteer(List<Volunteer> volunteer);

    public abstract Builder education(List<Education> education);

    public abstract Builder awards(List<Award> awards);

    public abstract Builder publications(List<Publication> publications);

    public abstract Builder skills(List<Skill> skills);

    public abstract Builder languages(List<Language> languages);

    public abstract Builder interests(List<Interest> interests);

    public abstract Builder references(List<Reference> references);

    public abstract Builder projects(List<Project> projects);

    public abstract Builder meta(Meta meta);

    public abstract Resume build();
  }
}
