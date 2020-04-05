package com.github.ianprime0509.jsonresume.markdown;

import static com.github.ianprime0509.jsonresume.markdown.MoreObjects.requireNonNullElse;
import static java.util.Collections.singletonList;
import static java.util.Collections.unmodifiableList;
import static java.util.Objects.requireNonNull;

import com.github.ianprime0509.jsonresume.core.Award;
import com.github.ianprime0509.jsonresume.core.Basics;
import com.github.ianprime0509.jsonresume.core.Education;
import com.github.ianprime0509.jsonresume.core.Interest;
import com.github.ianprime0509.jsonresume.core.Language;
import com.github.ianprime0509.jsonresume.core.Profile;
import com.github.ianprime0509.jsonresume.core.Project;
import com.github.ianprime0509.jsonresume.core.Publication;
import com.github.ianprime0509.jsonresume.core.Reference;
import com.github.ianprime0509.jsonresume.core.Resume;
import com.github.ianprime0509.jsonresume.core.ResumeFormatter;
import com.github.ianprime0509.jsonresume.core.Section;
import com.github.ianprime0509.jsonresume.core.Skill;
import com.github.ianprime0509.jsonresume.core.Volunteer;
import com.github.ianprime0509.jsonresume.core.Work;
import com.github.ianprime0509.jsonresume.core.format.DateFormatter;
import com.github.ianprime0509.jsonresume.core.format.DateStyle;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

/** A {@link ResumeFormatter} that formats resumes as Markdown documents. */
public final class MarkdownResumeFormatter implements ResumeFormatter {
  private final Charset charset;
  private final List<Section> sections;
  private final Locale locale;
  private final ResourceBundle resourceBundle;
  private final ResourceBundle markdownResourceBundle;
  private final DateFormatter dateFormatter;

  private MarkdownResumeFormatter(final Builder builder) {
    this.charset = builder.charset;
    this.sections = unmodifiableList(new ArrayList<>(builder.sections));
    this.locale = builder.locale;
    this.resourceBundle =
        ResourceBundle.getBundle(
            "com.github.ianprime0509.jsonresume.core.ResumeBundle", builder.locale);
    this.markdownResourceBundle =
        ResourceBundle.getBundle(
            "com.github.ianprime0509.jsonresume.markdown.MarkdownBundle", builder.locale);
    this.dateFormatter = builder.dateStyle.getFormatter(builder.locale);
  }

  public static Builder builder() {
    return new Builder();
  }

  @Override
  public void format(final OutputStream outputStream, final Resume resume) throws IOException {
    final MarkdownWriter writer = MarkdownWriter.create(outputStream, charset);

    formatBasics(writer, resume.basics());

    for (final Section section : sections) {
      switch (section) {
        case WORK:
          formatSection(writer, Section.WORK, resume.work(), this::formatWork);
          break;
        case VOLUNTEER:
          formatSection(writer, Section.VOLUNTEER, resume.volunteer(), this::formatVolunteer);
          break;
        case EDUCATION:
          formatSection(writer, Section.EDUCATION, resume.education(), this::formatEducation);
          break;
        case AWARDS:
          formatSection(writer, Section.AWARDS, resume.awards(), this::formatAward);
          break;
        case PUBLICATIONS:
          formatSection(
              writer, Section.PUBLICATIONS, resume.publications(), this::formatPublication);
          break;
        case SKILLS:
          formatSection(
              writer,
              Section.SKILLS,
              resume.skills(),
              SectionItemFormatter.tight(this::formatSkill));
          break;
        case LANGUAGES:
          formatSection(
              writer,
              Section.LANGUAGES,
              resume.languages(),
              SectionItemFormatter.tight(this::formatLanguage));
          break;
        case INTERESTS:
          formatSection(
              writer,
              Section.INTERESTS,
              resume.interests(),
              SectionItemFormatter.tight(this::formatInterest));
          break;
        case REFERENCES:
          formatSection(
              writer,
              Section.REFERENCES,
              resume.references(),
              SectionItemFormatter.tight(this::formatReference));
          break;
        case PROJECTS:
          formatSection(writer, Section.PROJECTS, resume.projects(), this::formatProject);
          break;
      }
    }

    writer.flush();
  }

  @Override
  public String toString() {
    return "MarkdownResumeFormatter{"
        + "charset="
        + charset
        + ", sections="
        + sections
        + ", locale="
        + locale
        + '}';
  }

  private void formatBasics(final MarkdownWriter writer, final Basics basics) throws IOException {
    final StringBuilder heading = new StringBuilder(basics.name());
    if (basics.label() != null) {
      heading.append(" - ").append(basics.label());
    }
    writer.writeHeading(heading.toString(), 1);
    writer.ensureBlankLines(1);

    if (basics.image() != null) {
      writer.writeImage(markdownResourceBundle.getString("basics.image.alt-text"), basics.image());
      writer.ensureBlankLines(1);
    }

    if (basics.location() != null) {
      final String locationFormat = markdownResourceBundle.getString("basics.location.format");
      final String formattedLocation =
          MessageFormat.format(
              locationFormat,
              requireNonNullElse(basics.location().address(), ""),
              requireNonNullElse(basics.location().postalCode(), ""),
              requireNonNullElse(basics.location().city(), ""),
              requireNonNullElse(basics.location().countryCode(), ""),
              requireNonNullElse(basics.location().region(), ""));
      for (final String line : formattedLocation.split("\n")) {
        writer.writeText(line);
        writer.writeLineBreak(true);
      }
      writer.ensureBlankLines(1);
    }

    if (basics.email() != null) {
      writer.writeText("*" + resourceBundle.getString("basics.email") + "*:");
      writer.writeSpace();
      writer.writeLink(basics.email());
      writer.writeLineBreak(true);
    }
    if (basics.phone() != null) {
      writer.writeText("*" + resourceBundle.getString("basics.phone") + "*:");
      writer.writeSpace();
      writer.writeLink(basics.phone(), "tel:" + basics.phone().replaceAll("[^0-9+]", ""));
      writer.writeLineBreak(true);
    }
    if (basics.url() != null) {
      writer.writeText("*" + resourceBundle.getString("basics.url") + "*:");
      writer.writeSpace();
      writer.writeLink(basics.url().toString());
      writer.writeLineBreak(true);
    }
    for (final Profile profile : basics.profiles()) {
      writer.writeText("*" + profile.network() + "*:");
      writer.writeSpace();
      if (profile.url() != null) {
        writer.writeLink(profile.username(), profile.url().toString());
      } else {
        writer.writeText(profile.username());
      }
    }

    if (basics.summary() != null) {
      writer.ensureBlankLines(1);
      writer.writeText(basics.summary());
    }
  }

  private <T> void formatSection(
      final MarkdownWriter writer,
      final Section section,
      final List<T> sectionItems,
      final SectionItemFormatter<T> formatter)
      throws IOException {
    if (sectionItems.isEmpty()) {
      return;
    }

    writer.ensureBlankLines(1);
    writer.writeHeading(resourceBundle.getString(section.name().toLowerCase()), 2);
    writer.ensureBlankLines(1);

    for (final T sectionItem : sectionItems) {
      if (!formatter.isTight()) {
        writer.ensureBlankLines(1);
      }
      formatter.format(writer, sectionItem);
    }
  }

  private void formatWork(final MarkdownWriter writer, final Work work) throws IOException {
    final StringBuilder heading = new StringBuilder(work.name());
    if (work.position() != null) {
      heading.append(" - ").append(work.position());
    }
    heading
        .append(" (")
        .append(dateFormatter.formatRange(work.startDate(), work.endDate()))
        .append(")");
    writer.writeHeading(heading.toString(), 3);

    if (work.description() != null) {
      writer.ensureBlankLines(1);
      writer.writeParagraph("*" + work.description() + "*");
    }
    if (work.summary() != null) {
      writer.ensureBlankLines(1);
      writer.writeParagraph(work.summary());
    }

    writer.ensureBlankLines(1);
    if (work.location() != null) {
      writer.writeText("**" + resourceBundle.getString("work.location") + "**:");
      writer.writeSpace();
      writer.writeText(work.location());
      writer.writeLineBreak(true);
    }
    if (work.url() != null) {
      writer.writeText("**" + resourceBundle.getString("work.url") + "**:");
      writer.writeSpace();
      writer.writeLink(work.url().toString());
      writer.writeLineBreak(true);
    }

    writer.ensureBlankLines(1);
    writer.writeList(work.highlights());
  }

  private void formatVolunteer(final MarkdownWriter writer, final Volunteer volunteer)
      throws IOException {
    final StringBuilder heading = new StringBuilder(volunteer.organization());
    if (volunteer.position() != null) {
      heading.append(" - ").append(volunteer.position());
    }
    heading
        .append(" (")
        .append(dateFormatter.formatRange(volunteer.startDate(), volunteer.endDate()))
        .append(")");
    writer.writeHeading(heading.toString(), 3);

    if (volunteer.summary() != null) {
      writer.ensureBlankLines(1);
      writer.writeParagraph(volunteer.summary());
    }

    writer.ensureBlankLines(1);
    if (volunteer.url() != null) {
      writer.writeText("**" + resourceBundle.getString("volunteer.url") + "**:");
      writer.writeSpace();
      writer.writeLink(volunteer.url().toString());
      writer.writeLineBreak(true);
    }

    writer.ensureBlankLines(1);
    writer.writeList(volunteer.highlights());
  }

  private void formatEducation(final MarkdownWriter writer, final Education education)
      throws IOException {
    final String heading =
        education.institution()
            + " ("
            + dateFormatter.formatRange(education.startDate(), education.endDate())
            + ")";
    writer.writeHeading(heading, 3);

    final StringBuilder degree = new StringBuilder();
    if (education.studyType() != null) {
      degree.append(education.studyType());
    }
    if (education.area() != null) {
      if (degree.length() > 0) {
        degree.append(": ");
      }
      degree.append(education.area());
    }
    if (degree.length() > 0) {
      writer.ensureBlankLines(1);
      writer.writeParagraph(degree.toString());
    }

    writer.ensureBlankLines(1);
    if (education.gpa() != null) {
      writer.writeText("**" + resourceBundle.getString("education.gpa") + "**:");
      writer.writeSpace();
      writer.writeText(education.gpa());
      writer.writeLineBreak(true);
    }

    writer.ensureBlankLines(1);
    writer.writeList(education.courses());
  }

  private void formatAward(final MarkdownWriter writer, final Award award) throws IOException {
    final StringBuilder heading = new StringBuilder(award.title());
    if (award.awarder() != null) {
      heading.append(" - ").append(award.awarder());
    }
    if (award.date() != null) {
      heading.append(" (").append(dateFormatter.format(award.date())).append(")");
    }
    writer.writeHeading(heading.toString(), 3);

    if (award.summary() != null) {
      writer.ensureBlankLines(1);
      writer.writeParagraph(award.summary());
    }
  }

  private void formatPublication(final MarkdownWriter writer, final Publication publication)
      throws IOException {
    final StringBuilder heading = new StringBuilder(publication.name());
    if (publication.publisher() != null) {
      heading.append(" - ").append(publication.publisher());
    }
    if (publication.releaseDate() != null) {
      heading.append(" (").append(dateFormatter.format(publication.releaseDate())).append(")");
    }
    writer.writeHeading(heading.toString(), 3);

    if (publication.summary() != null) {
      writer.ensureBlankLines(1);
      writer.writeParagraph(publication.summary());
    }

    writer.ensureBlankLines(1);
    if (publication.url() != null) {
      writer.writeText("**" + resourceBundle.getString("publications.url") + "**:");
      writer.writeSpace();
      writer.writeLink(publication.url().toString());
    }
  }

  private void formatSkill(final MarkdownWriter writer, final Skill skill) throws IOException {
    final StringBuilder formattedSkill = new StringBuilder(skill.name());
    if (skill.level() != null) {
      formattedSkill.append(" - ").append(skill.level());
    }
    writer.writeList(singletonList(formattedSkill.toString()));
  }

  private void formatLanguage(final MarkdownWriter writer, final Language language)
      throws IOException {
    final StringBuilder formattedLanguage = new StringBuilder(language.language());
    if (language.fluency() != null) {
      formattedLanguage.append(" - ").append(language.fluency());
    }
    writer.writeList(singletonList(formattedLanguage.toString()));
  }

  private void formatInterest(final MarkdownWriter writer, final Interest interest)
      throws IOException {
    writer.writeList(singletonList(interest.name()));
  }

  private void formatReference(final MarkdownWriter writer, final Reference reference)
      throws IOException {
    final StringBuilder formattedReference = new StringBuilder(reference.name());
    if (reference.reference() != null) {
      formattedReference.append(" - ").append(reference.reference());
    }
    writer.writeList(singletonList(formattedReference.toString()));
  }

  private void formatProject(final MarkdownWriter writer, final Project project)
      throws IOException {
    final StringBuilder heading = new StringBuilder(project.name());
    if (!project.roles().isEmpty()) {
      heading.append(" - ").append(String.join(", ", project.roles()));
    }
    if (project.startDate() != null) {
      heading
          .append(" (")
          .append(dateFormatter.formatRange(project.startDate(), project.endDate()))
          .append(")");
    }
    writer.writeHeading(heading.toString(), 3);

    if (project.description() != null) {
      writer.ensureBlankLines(1);
      writer.writeParagraph(project.description());
    }

    writer.ensureBlankLines(1);
    if (project.entity() != null) {
      writer.writeText("**" + resourceBundle.getString("projects.entity") + "**:");
      writer.writeSpace();
      writer.writeText(project.entity());
      writer.writeLineBreak(true);
    }
    if (project.url() != null) {
      writer.writeText("**" + resourceBundle.getString("projects.url") + "**:");
      writer.writeSpace();
      writer.writeText(project.url().toString());
      writer.writeLineBreak(true);
    }

    writer.ensureBlankLines(1);
    writer.writeList(project.highlights());
    // type is deliberately omitted from the output, since I don't see any good place to put it
  }

  public static final class Builder {
    private Charset charset = StandardCharsets.UTF_8;
    private final List<Section> sections = new ArrayList<>();
    private Locale locale = Locale.getDefault();
    private DateStyle dateStyle = DateStyle.FULL;

    private Builder() {}

    public Builder charset(final Charset charset) {
      this.charset = requireNonNull(charset, "charset");
      return this;
    }

    public Builder addSection(final Section section) {
      this.sections.add(requireNonNull(section, "section"));
      return this;
    }

    public Builder locale(final Locale locale) {
      this.locale = requireNonNull(locale, "locale");
      return this;
    }

    public Builder dateStyle(final DateStyle dateStyle) {
      this.dateStyle = requireNonNull(dateStyle, "dateStyle");
      return this;
    }

    public MarkdownResumeFormatter build() {
      return new MarkdownResumeFormatter(this);
    }
  }

  private interface SectionItemFormatter<T> {
    static <T> SectionItemFormatter<T> tight(final SectionItemFormatter<? super T> formatter) {
      return new SectionItemFormatter<T>() {
        @Override
        public void format(final MarkdownWriter writer, final T sectionItem) throws IOException {
          formatter.format(writer, sectionItem);
        }

        @Override
        public boolean isTight() {
          return true;
        }
      };
    }

    void format(MarkdownWriter writer, T sectionItem) throws IOException;

    default boolean isTight() {
      return false;
    }
  }
}
