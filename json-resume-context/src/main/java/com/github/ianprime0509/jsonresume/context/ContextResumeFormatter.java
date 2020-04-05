package com.github.ianprime0509.jsonresume.context;

import static com.github.ianprime0509.jsonresume.context.MoreObjects.requireNonNullElse;
import static java.util.Collections.unmodifiableList;
import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.toList;

import com.github.ianprime0509.jsonresume.core.Award;
import com.github.ianprime0509.jsonresume.core.Basics;
import com.github.ianprime0509.jsonresume.core.Education;
import com.github.ianprime0509.jsonresume.core.Interest;
import com.github.ianprime0509.jsonresume.core.Language;
import com.github.ianprime0509.jsonresume.core.Location;
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
import com.github.ianprime0509.jsonresume.core.resource.SingleFileResourceBundleControl;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

/** A {@link ResumeFormatter} that formats resumes as ConTeXt documents. */
public final class ContextResumeFormatter implements ResumeFormatter {
  private final List<Section> sections;
  private final Locale locale;
  private final ResourceBundle resourceBundle;
  private final String preamble;
  private final DateFormatter dateFormatter;
  private final String paperSize;

  private ContextResumeFormatter(final Builder builder) {
    this.sections = unmodifiableList(new ArrayList<>(builder.sections));
    this.locale = builder.locale;
    this.resourceBundle =
        ResourceBundle.getBundle(
            "com.github.ianprime0509.jsonresume.core.ResumeBundle", builder.locale);

    final String preambleCommon =
        ResourceBundle.getBundle(
                "com.github.ianprime0509.jsonresume.context.preamble-common",
                builder.locale,
                SingleFileResourceBundleControl.create("tex"))
            .getString("contents");
    final String preambleLocaleDependent =
        ResourceBundle.getBundle(
                "com.github.ianprime0509.jsonresume.context.preamble-locale-dependent",
                builder.locale,
                SingleFileResourceBundleControl.create("tex"))
            .getString("contents");

    this.preamble = preambleCommon + "\n" + preambleLocaleDependent;
    this.dateFormatter = builder.dateStyle.getFormatter(builder.locale);
    this.paperSize = builder.paperSize;
  }

  public static Builder builder() {
    return new Builder();
  }

  @Override
  public void format(final OutputStream outputStream, final Resume resume) throws IOException {
    final Writer baseWriter = new OutputStreamWriter(outputStream, StandardCharsets.UTF_8);
    baseWriter.write(preamble);
    final ContextWriter writer = ContextWriter.create(baseWriter);

    writer.writeRawLine("\\setuppapersize[" + paperSize + "]");
    writer.ensureBlankLines(1);
    final Basics basics = resume.basics();
    // Set up resume-dependent properties
    writer.writeRawLine("\\setvariables[resume][name={" + writer.escape(basics.name()) + "}]");
    if (basics.label() != null) {
      writer.writeRawLine("\\setvariables[resume][label={" + writer.escape(basics.label()) + "}]");
    }
    writer.writeRawLine(
        "\\setupinteraction[title={\\getvariable{resume}{name}},author={\\getvariable{resume}{name}}]");
    writer.writeRawLine("\\starttext");

    formatBasics(writer, basics);
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
          formatSection(writer, Section.SKILLS, resume.skills(), this::formatSkills);
          break;
        case LANGUAGES:
          formatSection(writer, Section.LANGUAGES, resume.languages(), this::formatLanguages);
          break;
        case INTERESTS:
          formatSection(writer, Section.INTERESTS, resume.interests(), this::formatInterests);
          break;
        case REFERENCES:
          formatSection(writer, Section.REFERENCES, resume.references(), this::formatReference);
          break;
        case PROJECTS:
          formatSection(writer, Section.PROJECTS, resume.projects(), this::formatProject);
          break;
      }
    }

    writer.writeRawLine("\\stoptext");

    writer.flush();
  }

  @Override
  public String toString() {
    return "ContextResumeFormatter{"
        + "sections="
        + sections
        + ", locale="
        + locale
        + ", paperSize='"
        + paperSize
        + '\''
        + '}';
  }

  private void formatBasics(final ContextWriter writer, final Basics basics) throws IOException {
    writer.ensureBlankLines(1);
    writer.writeRawLine("\\startHeader");

    writer.writeRawLine("\\startHeaderDetails");
    if (basics.email() != null) {
      writer.writeRaw("\\Email{" + writer.escape(basics.email()) + "}");
      writer.writeRawLine("\\crlf");
    }
    if (basics.phone() != null) {
      writer.writeRaw("\\Phone{" + writer.escape(basics.phone()) + "}");
      writer.writeRawLine("\\crlf");
    }
    if (basics.url() != null) {
      writer.writeRaw("\\DirectURL{" + writer.escape(basics.url().toString()) + "}");
      writer.writeRawLine("\\crlf");
    }
    if (basics.location() != null) {
      final Location location = basics.location();
      writer.writeRawLine(
          "\\blank[halfline]\\Location{"
              + writer.escape(requireNonNullElse(location.address(), ""))
              + "}{"
              + writer.escape(requireNonNullElse(location.postalCode(), ""))
              + "}{"
              + writer.escape(requireNonNullElse(location.city(), ""))
              + "}{"
              + writer.escape(requireNonNullElse(location.countryCode(), ""))
              + "}{"
              + writer.escape(requireNonNullElse(location.region(), ""))
              + "}\\crlf");
    }
    // TODO: image, social media
    writer.writeRawLine("\\stopHeaderDetails");

    if (basics.summary() != null) {
      writer.writeRawLine("\\startSummary");
      writer.writeText(basics.summary());
      writer.ensureBlankLines(0);
      writer.writeRawLine("\\stopSummary");
    }

    writer.writeRawLine("\\stopHeader");
  }

  private <T> void formatSection(
      final ContextWriter writer,
      final Section section,
      final List<T> sectionItems,
      final SectionContentsFormatter<T> formatter)
      throws IOException {
    if (sectionItems.isEmpty()) {
      return;
    }

    writer.ensureBlankLines(1);
    writer.writeRawLine(
        "\\startSection[title={"
            + writer.escape(resourceBundle.getString(section.name().toLowerCase()))
            + "}]");
    writer.ensureBlankLines(1);
    formatter.format(writer, sectionItems);
    writer.writeRawLine("\\stopSection");
  }

  private <T> void formatSection(
      final ContextWriter writer,
      final Section section,
      final List<T> sectionItems,
      final SectionItemFormatter<T> formatter)
      throws IOException {
    formatSection(
        writer, section, sectionItems, SectionContentsFormatter.fromItemFormatter(formatter));
  }

  private void formatWork(final ContextWriter writer, final Work work) throws IOException {
    writer.writeRawLine(
        "\\startSectionItem[title={"
            + writer.escape(work.name())
            + "}][info1={"
            + writer.escape(dateFormatter.formatRange(work.startDate(), work.endDate()))
            + "},info2={"
            + writer.escape(requireNonNullElse(work.position(), ""))
            + "},info3={"
            + writer.escape(requireNonNullElse(work.location(), ""))
            + "}]");
    // TODO: where can we put URL in all of this?

    if (work.description() != null) {
      writer.ensureBlankLines(1);
      writer.writeRaw("{\\em");
      writer.writeSpace();
      writer.writeText(work.description());
      writer.writeRawLine("}\\crlf");
      writer.writeRawLine("\\blank[halfline]");
    }

    if (work.summary() != null) {
      writer.ensureBlankLines(1);
      writer.writeText(work.summary());
      writer.writeLineBreak();
      writer.writeRawLine("\\blank[halfline]");
    }

    writer.ensureBlankLines(1);
    writer.writeRawLine("\\startHighlights");
    writer.writeItems(work.highlights());
    writer.writeRawLine("\\stopHighlights");
    writer.writeRawLine("\\stopSectionItem");
  }

  private void formatVolunteer(final ContextWriter writer, final Volunteer volunteer)
      throws IOException {
    writer.writeRawLine(
        "\\startSectionItem[title={"
            + writer.escape(volunteer.organization())
            + "},info1={"
            + writer.escape(dateFormatter.formatRange(volunteer.startDate(), volunteer.endDate()))
            + "},info2={"
            + writer.escape(requireNonNullElse(volunteer.position(), ""))
            + "}]");
    // TODO: where can we put URL in all of this?

    if (volunteer.summary() != null) {
      writer.ensureBlankLines(1);
      writer.writeText(volunteer.summary());
      writer.writeLineBreak();
      writer.writeRawLine("\\blank[halfline]");
    }

    writer.ensureBlankLines(1);
    writer.writeRawLine("\\startHighlights");
    writer.writeItems(volunteer.highlights());
    writer.writeRawLine("\\stopHighlights");
    writer.writeRawLine("\\stopSectionItem");
  }

  private void formatEducation(final ContextWriter writer, final Education education)
      throws IOException {
    final StringBuilder study = new StringBuilder();
    if (education.studyType() != null) {
      study.append(education.studyType());
    }
    if (education.area() != null) {
      if (study.length() > 0) {
        study.append(" --- ");
      }
      study.append(education.area());
    }

    final String gpa =
        education.gpa() != null
            ? resourceBundle.getString("education.gpa") + " " + education.gpa()
            : "";

    writer.writeRawLine(
        "\\startSectionItem[title={"
            + writer.escape(education.institution())
            + "}][info1={"
            + writer.escape(dateFormatter.formatRange(education.startDate(), education.endDate()))
            + "},info2={"
            + writer.escape(study.toString())
            + "},info3={"
            + writer.escape(gpa)
            + "}]");

    writer.ensureBlankLines(1);
    writer.writeRawLine("\\startHighlights");
    writer.writeItems(education.courses());
    writer.writeRawLine("\\stopHighlights");
    writer.writeRawLine("\\stopSectionItem");
  }

  private void formatAward(final ContextWriter writer, final Award award) throws IOException {
    final String date =
        award.date() != null ? writer.escape(dateFormatter.format(award.date())) : "";
    writer.writeRawLine(
        "\\startSectionItem[title={"
            + writer.escape(award.title())
            + "}][info1={"
            + date
            + "},info2={"
            + writer.escape(requireNonNullElse(award.awarder(), ""))
            + "}]");

    if (award.summary() != null) {
      writer.ensureBlankLines(1);
      writer.writeText(award.summary());
      writer.writeLineBreak();
      writer.writeRawLine("\\blank[halfline]");
    }
    writer.writeRawLine("\\stopSectionItem");
  }

  private void formatPublication(final ContextWriter writer, final Publication publication)
      throws IOException {
    final String date =
        publication.releaseDate() != null
            ? writer.escape(dateFormatter.format(publication.releaseDate()))
            : "";
    writer.writeRawLine(
        "\\startSectionItem[title={"
            + writer.escape(publication.name())
            + "}][info1={"
            + date
            + "},info2={"
            + writer.escape(requireNonNullElse(publication.publisher(), ""))
            + "}]");

    if (publication.summary() != null) {
      writer.ensureBlankLines(1);
      writer.writeText(publication.summary());
      writer.writeLineBreak();
      writer.writeRawLine("\\blank[halfline]");
    }
    writer.writeRawLine("\\stopSectionItem");
  }

  private void formatSkills(final ContextWriter writer, final List<Skill> skills)
      throws IOException {
    writer.writeRawLine("\\startHighlights");
    writer.writeRawItems(
        skills.stream()
            .map(
                skill -> {
                  final String name = "{\\bf " + writer.escape(skill.name()) + "}";
                  if (skill.level() != null) {
                    return name + " --- " + writer.escape(skill.level());
                  } else {
                    return name;
                  }
                })
            .collect(toList()));
    writer.writeRawLine("\\stopHighlights");
  }

  private void formatLanguages(final ContextWriter writer, final List<Language> languages)
      throws IOException {
    writer.writeRawLine("\\startHighlights");
    writer.writeRawItems(
        languages.stream()
            .map(
                language -> {
                  final String name = "{\\bf " + writer.escape(language.language()) + "}";
                  if (language.fluency() != null) {
                    return name + " --- " + writer.escape(language.fluency());
                  } else {
                    return name;
                  }
                })
            .collect(toList()));
    writer.writeRawLine("\\stopHighlights");
  }

  private void formatInterests(final ContextWriter writer, final List<Interest> interests)
      throws IOException {
    writer.writeRawLine("\\startHighlights");
    writer.writeRawItems(
        interests.stream()
            .map(interest -> "{\\bf " + writer.escape(interest.name()) + "}")
            .collect(toList()));
    writer.writeRawLine("\\stopHighlights");
  }

  private void formatReference(final ContextWriter writer, final Reference reference)
      throws IOException {
    writer.writeRawLine("\\startSectionItem[title={" + writer.escape(reference.name()) + "}]");
    if (reference.reference() != null) {
      writer.writeText(reference.reference());
      writer.writeLineBreak();
    }
    writer.writeRawLine("\\stopSectionItem");
  }

  private void formatProject(final ContextWriter writer, final Project project) throws IOException {
    final String date =
        project.startDate() != null
            ? dateFormatter.formatRange(project.startDate(), project.endDate())
            : "";
    writer.writeRawLine(
        "\\startSectionItem[title={"
            + writer.escape(project.name())
            + "}][info1={"
            + writer.escape(date)
            + "},info2={"
            + writer.escape(String.join(", ", project.roles()))
            + "},info3={"
            + writer.escape(requireNonNullElse(project.entity(), ""))
            + "}]");
    // TODO: where can we put URL in all of this?

    if (project.description() != null) {
      writer.ensureBlankLines(1);
      writer.writeRaw("{\\em");
      writer.writeSpace();
      writer.writeText(project.description());
      writer.writeRawLine("}\\crlf");
      writer.writeRawLine("\\blank[halfline]");
    }

    writer.ensureBlankLines(1);
    writer.writeRawLine("\\startHighlights");
    writer.writeItems(project.highlights());
    writer.writeRawLine("\\stopHighlights");
    writer.writeRawLine("\\stopSectionItem");
  }

  public static final class Builder {
    private final List<Section> sections = new ArrayList<>();
    private Locale locale = Locale.getDefault();
    private DateStyle dateStyle = DateStyle.FULL;
    private String paperSize;

    private Builder() {}

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

    public Builder paperSize(final String paperSize) {
      this.paperSize = requireNonNull(paperSize, "paperSize");
      return this;
    }

    public ContextResumeFormatter build() {
      // Set a sensible default paper size according to the chosen locale
      if (paperSize == null) {
        final ResourceBundle contextBundle =
            ResourceBundle.getBundle(
                "com.github.ianprime0509.jsonresume.context.ContextBundle", locale);
        paperSize(contextBundle.getString("paper-size.default"));
      }
      return new ContextResumeFormatter(this);
    }
  }

  private interface SectionContentsFormatter<T> {
    static <T> SectionContentsFormatter<T> fromItemFormatter(
        final SectionItemFormatter<? super T> formatter) {
      return (writer, sectionItems) -> {
        for (final T sectionItem : sectionItems) {
          writer.ensureBlankLines(1);
          formatter.format(writer, sectionItem);
        }
      };
    }

    void format(final ContextWriter writer, final List<T> sectionItems) throws IOException;
  }

  private interface SectionItemFormatter<T> {
    void format(final ContextWriter writer, final T item) throws IOException;
  }
}
