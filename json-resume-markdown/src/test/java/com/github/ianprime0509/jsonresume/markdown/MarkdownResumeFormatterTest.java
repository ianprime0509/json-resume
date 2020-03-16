package com.github.ianprime0509.jsonresume.markdown;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.ianprime0509.jsonresume.core.Resume;
import com.github.ianprime0509.jsonresume.core.Section;
import com.github.ianprime0509.jsonresume.jackson.JsonResumeModule;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayName("MarkdownResumeFormatter")
class MarkdownResumeFormatterTest {
  private ObjectMapper objectMapper;

  @BeforeEach
  void setUpObjectMapper() {
    objectMapper = new ObjectMapper().registerModule(new JsonResumeModule());
  }

  @DisplayName("format")
  @Nested
  class Format {
    private MarkdownResumeFormatter formatter;

    @BeforeEach
    void setUpFormatter() {
      formatter =
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
    }

    @DisplayName("sample-complete")
    @Test
    void sampleComplete() throws IOException {
      final Resume resume = readResume("sample-complete.json");
      assertThat(format(resume)).isEqualTo(readFile("sample-complete.md"));
    }

    private String format(final Resume resume) throws IOException {
      final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
      formatter.format(outputStream, resume);
      return new String(outputStream.toByteArray(), StandardCharsets.UTF_8);
    }

    private String readFile(final String name) throws IOException {
      try (final InputStream inputStream =
          MarkdownResumeFormatterTest.class.getResourceAsStream(name)) {
        final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        final byte[] buffer = new byte[8096];
        int read;
        while ((read = inputStream.read(buffer)) > 0) {
          outputStream.write(buffer, 0, read);
        }
        return new String(outputStream.toByteArray(), StandardCharsets.UTF_8);
      }
    }

    private Resume readResume(final String name) throws IOException {
      return objectMapper.readValue(readFile(name), Resume.class);
    }
  }
}
