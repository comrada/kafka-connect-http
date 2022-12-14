package io.github.comrada.kafka.connect.http.response.timestamp;

import static io.github.comrada.kafka.connect.http.response.timestamp.DateTimeFormatterTimestampParserTest.Fixture.date;
import static io.github.comrada.kafka.connect.http.response.timestamp.DateTimeFormatterTimestampParserTest.Fixture.formatter;
import static java.time.format.DateTimeFormatter.ISO_DATE_TIME;
import static java.util.Collections.emptyMap;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class DateTimeFormatterTimestampParserTest {

  DateTimeFormatterTimestampParser parser;

  @Mock
  DateTimeFormatterTimestampParserConfig config;

  @BeforeEach
  void setUp() {
    parser = new DateTimeFormatterTimestampParser(__ -> config);
  }

  @Test
  void givenFormatter_whenParse_thenDelegated() {

    given(config.getRecordTimestampFormatter()).willReturn(formatter);
    parser.configure(emptyMap());

    assertThat(parser.parse(date)).isEqualTo(OffsetDateTime.parse(date, formatter).toInstant());
  }

  interface Fixture {

    String date = "2011-12-03T10:15:30+01:00";
    DateTimeFormatter formatter = ISO_DATE_TIME.withZone(ZoneId.of("UTC"));
  }
}
