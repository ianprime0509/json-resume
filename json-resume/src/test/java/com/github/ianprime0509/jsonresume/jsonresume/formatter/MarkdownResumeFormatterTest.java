package com.github.ianprime0509.jsonresume.jsonresume.formatter;

import static java.util.Objects.requireNonNull;

import com.github.ianprime0509.jsonresume.jsonresume.type.Resume;
import com.github.ianprime0509.jsonresume.jsonresume.type.Section;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

class MarkdownResumeFormatterTest {
  public static void main(final String... args) throws IOException {
    final Resume resume =
        Resume.fromJson(
            requireNonNull(
                MarkdownResumeFormatterTest.class
                    .getClassLoader()
                    .getResourceAsStream(
                        "com/github/ianprime0509/jsonresume/jsonresume/sample-complete.json")));

    final MarkdownResumeFormatter formatter =
        MarkdownResumeFormatter.builder()
            .addSection(Section.WORK)
            .addSection(Section.VOLUNTEER)
            .addSection(Section.EDUCATION)
            .addSection(Section.AWARDS)
            .addSection(Section.PUBLICATIONS)
            .addSection(Section.SKILLS)
            .addSection(Section.LANGUAGES)
            .addSection(Section.INTERESTS)
            .addSection(Section.REFERENCES)
            .addSection(Section.PROJECTS)
            .build();
    final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    formatter.format(outputStream, resume);
    System.out.println(new String(outputStream.toByteArray(), StandardCharsets.UTF_8));
  }
}
