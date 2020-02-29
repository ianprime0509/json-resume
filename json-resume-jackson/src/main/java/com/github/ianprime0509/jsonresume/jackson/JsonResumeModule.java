package com.github.ianprime0509.jsonresume.jackson;

import com.fasterxml.jackson.core.util.VersionUtil;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.github.ianprime0509.jsonresume.api.Award;
import com.github.ianprime0509.jsonresume.api.Basics;
import com.github.ianprime0509.jsonresume.api.Date;
import com.github.ianprime0509.jsonresume.api.Education;
import com.github.ianprime0509.jsonresume.api.Interest;
import com.github.ianprime0509.jsonresume.api.Language;
import com.github.ianprime0509.jsonresume.api.Location;
import com.github.ianprime0509.jsonresume.api.Meta;
import com.github.ianprime0509.jsonresume.api.Profile;
import com.github.ianprime0509.jsonresume.api.Project;
import com.github.ianprime0509.jsonresume.api.Publication;
import com.github.ianprime0509.jsonresume.api.Reference;
import com.github.ianprime0509.jsonresume.api.Resume;
import com.github.ianprime0509.jsonresume.api.Skill;
import com.github.ianprime0509.jsonresume.api.Volunteer;
import com.github.ianprime0509.jsonresume.api.Work;

public class JsonResumeModule extends SimpleModule {
  public JsonResumeModule() {
    super(
        JsonResumeModule.class.getName(),
        VersionUtil.parseVersion(
            "0.1.0-SNAPSHOT", "com.github.ianprime0509.json-resume", "json-resume-jackson"));

    addDeserializer(Date.class, new DateDeserializer(Date.class));
    addSerializer(Date.class, new DateSerializer(Date.class));

    addValueInstantiator(Award.Builder.class, BuilderValueInstantiator.using(Award::builder));
    setMixInAnnotation(Award.class, AwardMixIn.class);
    setMixInAnnotation(Award.Builder.class, AwardMixIn.Builder.class);

    addValueInstantiator(Basics.Builder.class, BuilderValueInstantiator.using(Basics::builder));
    setMixInAnnotation(Basics.class, BasicsMixIn.class);
    setMixInAnnotation(Basics.Builder.class, BasicsMixIn.Builder.class);

    addValueInstantiator(
        Education.Builder.class, BuilderValueInstantiator.using(Education::builder));
    setMixInAnnotation(Education.class, EducationMixIn.class);
    setMixInAnnotation(Education.Builder.class, EducationMixIn.Builder.class);

    addValueInstantiator(Interest.Builder.class, BuilderValueInstantiator.using(Interest::builder));
    setMixInAnnotation(Interest.class, InterestMixIn.class);
    setMixInAnnotation(Interest.Builder.class, InterestMixIn.Builder.class);

    addValueInstantiator(Language.Builder.class, BuilderValueInstantiator.using(Language::builder));
    setMixInAnnotation(Language.class, LanguageMixIn.class);
    setMixInAnnotation(Language.Builder.class, LanguageMixIn.Builder.class);

    addValueInstantiator(Location.Builder.class, BuilderValueInstantiator.using(Location::builder));
    setMixInAnnotation(Location.class, LocationMixIn.class);
    setMixInAnnotation(Location.Builder.class, LocationMixIn.Builder.class);

    addValueInstantiator(Meta.Builder.class, BuilderValueInstantiator.using(Meta::builder));
    setMixInAnnotation(Meta.class, MetaMixIn.class);
    setMixInAnnotation(Meta.Builder.class, MetaMixIn.Builder.class);

    addValueInstantiator(Profile.Builder.class, BuilderValueInstantiator.using(Profile::builder));
    setMixInAnnotation(Profile.class, ProfileMixIn.class);
    setMixInAnnotation(Profile.Builder.class, ProfileMixIn.Builder.class);

    addValueInstantiator(Project.Builder.class, BuilderValueInstantiator.using(Project::builder));
    setMixInAnnotation(Project.class, ProjectMixIn.class);
    setMixInAnnotation(Project.Builder.class, ProjectMixIn.Builder.class);

    addValueInstantiator(
        Publication.Builder.class, BuilderValueInstantiator.using(Publication::builder));
    setMixInAnnotation(Publication.class, PublicationMixIn.class);
    setMixInAnnotation(Publication.Builder.class, PublicationMixIn.Builder.class);

    addValueInstantiator(
        Reference.Builder.class, BuilderValueInstantiator.using(Reference::builder));
    setMixInAnnotation(Reference.class, ReferenceMixIn.class);
    setMixInAnnotation(Reference.Builder.class, ReferenceMixIn.Builder.class);

    addValueInstantiator(Resume.Builder.class, BuilderValueInstantiator.using(Resume::builder));
    setMixInAnnotation(Resume.class, ResumeMixIn.class);
    setMixInAnnotation(Resume.Builder.class, ResumeMixIn.Builder.class);

    addValueInstantiator(Skill.Builder.class, BuilderValueInstantiator.using(Skill::builder));
    setMixInAnnotation(Skill.class, SkillMixIn.class);
    setMixInAnnotation(Skill.Builder.class, SkillMixIn.Builder.class);

    addValueInstantiator(
        Volunteer.Builder.class, BuilderValueInstantiator.using(Volunteer::builder));
    setMixInAnnotation(Volunteer.class, VolunteerMixIn.class);
    setMixInAnnotation(Volunteer.Builder.class, VolunteerMixIn.Builder.class);

    addValueInstantiator(Work.Builder.class, BuilderValueInstantiator.using(Work::builder));
    setMixInAnnotation(Work.class, WorkMixIn.class);
    setMixInAnnotation(Work.Builder.class, WorkMixIn.Builder.class);
  }
}
