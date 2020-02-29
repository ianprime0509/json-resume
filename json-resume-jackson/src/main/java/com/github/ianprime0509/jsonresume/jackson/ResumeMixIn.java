package com.github.ianprime0509.jsonresume.jackson;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.github.ianprime0509.jsonresume.core.Award;
import com.github.ianprime0509.jsonresume.core.Basics;
import com.github.ianprime0509.jsonresume.core.Education;
import com.github.ianprime0509.jsonresume.core.Interest;
import com.github.ianprime0509.jsonresume.core.Language;
import com.github.ianprime0509.jsonresume.core.Meta;
import com.github.ianprime0509.jsonresume.core.Project;
import com.github.ianprime0509.jsonresume.core.Publication;
import com.github.ianprime0509.jsonresume.core.Reference;
import com.github.ianprime0509.jsonresume.core.Resume;
import com.github.ianprime0509.jsonresume.core.Skill;
import com.github.ianprime0509.jsonresume.core.Volunteer;
import com.github.ianprime0509.jsonresume.core.Work;
import java.util.List;

@JsonDeserialize(builder = Resume.Builder.class)
public abstract class ResumeMixIn {
  private ResumeMixIn() {}

  @JsonProperty("basics")
  public abstract Basics basics();

  @JsonProperty("work")
  public abstract List<Work> work();

  @JsonProperty("volunteer")
  public abstract List<Volunteer> volunteer();

  @JsonProperty("education")
  public abstract List<Education> education();

  @JsonProperty("awards")
  public abstract List<Award> awards();

  @JsonProperty("publications")
  public abstract List<Publication> publications();

  @JsonProperty("skills")
  public abstract List<Skill> skills();

  @JsonProperty("languages")
  public abstract List<Language> languages();

  @JsonProperty("interests")
  public abstract List<Interest> interests();

  @JsonProperty("references")
  public abstract List<Reference> references();

  @JsonProperty("projects")
  public abstract List<Project> projects();

  @JsonProperty("meta")
  public abstract Meta meta();

  abstract static class Builder {
    private Builder() {}

    @JsonProperty("basics")
    public abstract Resume.Builder basics(Basics basics);

    @JsonProperty("work")
    public abstract Resume.Builder work(List<Work> work);

    @JsonProperty("volunteer")
    public abstract Resume.Builder volunteer(List<Volunteer> volunteer);

    @JsonProperty("education")
    public abstract Resume.Builder education(List<Education> education);

    @JsonProperty("awards")
    public abstract Resume.Builder awards(List<Award> awards);

    @JsonProperty("publications")
    public abstract Resume.Builder publications(List<Publication> publications);

    @JsonProperty("skills")
    public abstract Resume.Builder skills(List<Skill> skills);

    @JsonProperty("languages")
    public abstract Resume.Builder languages(List<Language> languages);

    @JsonProperty("interests")
    public abstract Resume.Builder interests(List<Interest> interests);

    @JsonProperty("references")
    public abstract Resume.Builder references(List<Reference> references);

    @JsonProperty("projects")
    public abstract Resume.Builder projects(List<Project> projects);

    @JsonProperty("meta")
    public abstract Resume.Builder meta(Meta meta);

    public abstract Resume build();
  }
}
