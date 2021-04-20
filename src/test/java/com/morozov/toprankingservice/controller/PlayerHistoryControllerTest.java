package com.morozov.toprankingservice.controller;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.morozov.toprankingservice.repository.ScoreRepository;
import com.morozov.toprankingservice.service.PlayerScoreHistoryService;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import org.junit.jupiter.api.RepeatedTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@WebMvcTest(PlayerHistoryController.class)
class PlayerHistoryControllerTest {

  public static final Random RANDOM = new Random();
  private static final DateTimeFormatter FORMATTER = new DateTimeFormatterBuilder()
      .appendPattern("yyyy-MM-dd'T'HH:mm:ss")
      .optionalStart()
      .appendFraction(ChronoField.MICRO_OF_SECOND, 0, 6, true)
      .optionalEnd()
      .toFormatter();
  @Autowired
  private MockMvc mvc;

  @MockBean
  private PlayerScoreHistoryService service;

  @RepeatedTest(3)
  void getAll() throws Exception {
    final String player = "player";
    final ScoreRepository.TimeScore score = new TimeScoreImpl(LocalDateTime.now(),
        RANDOM.nextInt());
    final List<ScoreRepository.TimeScore> allScores = List.of(score);

    given(service.getAll(player)).willReturn(allScores);

    mvc.perform(MockMvcRequestBuilders.get("/playerscorehistory/" + player))
        .andExpect(status().isOk())
        .andExpect(content()
            .contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(jsonPath("$", hasSize(1)))
        .andExpect(jsonPath("$[0].time").value(score.getTime().format(FORMATTER)))
        .andExpect(jsonPath("$[0].score", is(score.getScore())));

    verify(service, times(1)).getAll(player);
  }

  @RepeatedTest(3)
  void getTop() throws Exception {
    final String player = "player";
    final ScoreRepository.TimeScore score = new TimeScoreImpl(LocalDateTime.now(),
        RANDOM.nextInt());

    given(service.getTop(player)).willReturn(score);

    mvc.perform(MockMvcRequestBuilders.get("/playerscorehistory/top/" + player))
        .andExpect(status().isOk())
        .andExpect(content()
            .contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(jsonPath("$.time").value(score.getTime().format(FORMATTER)))
        .andExpect(jsonPath("$.score", is(score.getScore())));

    verify(service, times(1)).getTop(player);
  }

  @RepeatedTest(3)
  void getLowest() throws Exception {
    final String player = "player";
    final ScoreRepository.TimeScore score = new TimeScoreImpl(LocalDateTime.now(),
        RANDOM.nextInt());

    given(service.getLowest(player)).willReturn(score);

    mvc.perform(MockMvcRequestBuilders.get("/playerscorehistory/lowest/" + player))
        .andExpect(status().isOk())
        .andExpect(content()
            .contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(jsonPath("$.time").value(score.getTime().format(FORMATTER)))
        .andExpect(jsonPath("$.score", is(score.getScore())));

    verify(service, times(1)).getLowest(player);
  }

  @RepeatedTest(3)
  void getAverage() throws Exception {
    final String player = "player";
    final Double score = RANDOM.nextDouble();

    given(service.getAverage(player)).willReturn(score);

    mvc.perform(MockMvcRequestBuilders.get("/playerscorehistory/avg/" + player))
        .andExpect(status().isOk())
        .andExpect(content()
            .contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(content().string(score.toString()));

    verify(service, times(1)).getAverage(player);
  }

  static class TimeScoreImpl implements ScoreRepository.TimeScore {

    private final LocalDateTime time;
    private final Integer score;

    public TimeScoreImpl(LocalDateTime time, Integer score) {
      this.time = time;
      this.score = score;
    }

    @Override
    public LocalDateTime getTime() {
      return time;
    }

    @Override
    public Integer getScore() {
      return score;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
      TimeScoreImpl timeScore = (TimeScoreImpl) o;
      return time.equals(timeScore.time) && score.equals(timeScore.score);
    }

    @Override
    public int hashCode() {
      return Objects.hash(time, score);
    }
  }
}