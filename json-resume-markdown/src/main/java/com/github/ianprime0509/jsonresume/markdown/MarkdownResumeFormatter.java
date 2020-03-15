package com.github.ianprime0509.jsonresume.markdown;

import static com.github.ianprime0509.jsonresume.markdown.MoreObjects.requireNonNullElse;
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

/** A {@link ResumeFormatter} that formats resumes as a Markdown documents. */
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
          formatSection(writer, Section.SKILLS, resume.skills(), this::formatSkill);
          break;
        case LANGUAGES:
          formatSection(writer, Section.LANGUAGES, resume.languages(), this::formatLanguage);
          break;
        case INTERESTS:
          formatSection(writer, Section.INTERESTS, resume.interests(), this::formatInterest);
          break;
        case REFERENCES:
          formatSection(writer, Section.REFERENCES, resume.references(), this::formatReference);
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
    writer.writeHeading(heading.toString(), 1).ensureBlankLines(1);

    if (basics.image() != null) {
      writer
          .writeImage(markdownResourceBundle.getString("basics.image.alt-text"), basics.image())
          .ensureBlankLines(1);
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
        writer.writeText(line).writeLineBreak(true);
      }
      writer.ensureBlankLines(1);
    }

    if (basics.email() != null) {
      writer
          .writeText("*" + resourceBundle.getString("basics.email") + "*:")
          .writeSpace()
          .writeLink(basics.email())
          .writeLineBreak(true);
    }
    if (basics.phone() != null) {
      writer
          .writeText("*" + resourceBundle.getString("basics.phone") + "*:")
          .writeSpace()
          .writeLink(basics.phone(), "tel:" + basics.phone().replaceAll("[^0-9+]", ""))
          .writeLineBreak(true);
    }
    if (basics.url() != null) {
      writer
          .writeText("*" + resourceBundle.getString("basics.url") + "*:")
          .writeSpace()
          .writeLink(basics.url().toString())
          .writeLineBreak(true);
    }
    for (final Profile profile : basics.profiles()) {
      writer.writeText("*" + profile.network() + "*:").writeSpace();
      if (profile.url() != null) {
        writer.writeLink(profile.username(), profile.network());
      } else {
        writer.writeText(profile.username());
      }
    }

    if (basics.summary() != null) {
      writer.ensureBlankLines(1).writeText(basics.summary());
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

    writer
        .ensureBlankLines(1)
        .writeHeading(resourceBundle.getString(section.name().toLowerCase()), 2);
    for (final T sectionItem : sectionItems) {
      writer.ensureBlankLines(1);
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
      writer.ensureBlankLines(1).writeParagraph("**" + work.description() + "**");
    }
    if (work.summary() != null) {
      writer.ensureBlankLines(1).writeParagraph(work.summary());
    }

    writer.ensureBlankLines(1);
    if (work.location() != null) {
      writer
          .writeText("*" + resourceBundle.getString("work.location") + "*:")
          .writeSpace()
          .writeText(work.location())
          .writeLineBreak(true);
    }
    if (work.url() != null) {
      writer
          .writeText("*" + resourceBundle.getString("work.url") + "*:")
          .writeSpace()
          .writeLink(work.url().toString())
          .writeLineBreak(true);
    }

    writer.ensureBlankLines(1).writeList(work.highlights());
  }

  private void formatVolunteer(final MarkdownWriter writer, final Volunteer volunteer)
      throws IOException {}

  private void formatEducation(final MarkdownWriter writer, final Education education)
      throws IOException {}

  private void formatAward(final MarkdownWriter writer, final Award award) throws IOException {}

  private void formatPublication(final MarkdownWriter writer, final Publication publication)
      throws IOException {}

  private void formatSkill(final MarkdownWriter writer, final Skill skill) throws IOException {}

  private void formatLanguage(final MarkdownWriter writer, final Language language)
      throws IOException {}

  private void formatInterest(final MarkdownWriter writer, final Interest interest)
      throws IOException {}

  private void formatReference(final MarkdownWriter writer, final Reference reference)
      throws IOException {}

  private void formatProject(final MarkdownWriter writer, final Project project)
      throws IOException {}

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
    void format(MarkdownWriter writer, T sectionItem) throws IOException;
  }
}
