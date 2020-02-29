package com.github.ianprime0509.jsonresume.markdown;

import static java.util.Objects.requireNonNull;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.ianprime0509.jsonresume.core.Resume;
import com.github.ianprime0509.jsonresume.core.Section;
import com.github.ianprime0509.jsonresume.jackson.JsonResumeModule;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

class MarkdownResumeFormatterTest {
  public static void main(final String... args) throws IOException {
    final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JsonResumeModule());

    final Resume resume;
    try (final InputStream resumeInput =
        requireNonNull(
            MarkdownResumeFormatterTest.class
                .getClassLoader()
                .getResourceAsStream(
                    "com/github/ianprime0509/jsonresume/markdown/sample-complete.json"))) {
      resume = objectMapper.readValue(resumeInput, Resume.class);
    }

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
