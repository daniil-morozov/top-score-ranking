package com.morozov.toprankingservice.controller;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.morozov.toprankingservice.dto.Score;
import com.morozov.toprankingservice.dto.ScoreFilter;
import com.morozov.toprankingservice.service.ScoreService;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.RepeatedTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@WebMvcTest(ScoreController.class)
class ScoreControllerTest {

  private static final DateTimeFormatter FORMATTER =
      new DateTimeFormatterBuilder()
          .appendPattern("yyyy-MM-dd'T'HH:mm:ss")
          .optionalStart()
          .appendFraction(ChronoField.MICRO_OF_SECOND, 0, 6, true)
          .optionalEnd()
          .toFormatter();

  @Autowired private MockMvc mvc;

  @MockBean private ScoreService service;

  @RepeatedTest(3)
  void givenScores_whenGetScores_thenReturnJsonArray() throws Exception {

    final Score score = new Score(UUID.randomUUID(), "player", 100, LocalDateTime.now());
    final List<Score> allScores = List.of(score);

    final Integer pageNo = 0;
    final Integer pageSize = 10;
    final String time = "time";

    given(service.getAll(pageNo, pageSize, time, ScoreFilter.EMPTY)).willReturn(allScores);

    mvc.perform(
            MockMvcRequestBuilders.get("/scores/all")
                .param("page", pageNo.toString())
                .param("size", pageSize.toString())
                .contentType(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isOk())
        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(jsonPath("$", hasSize(1)))
        .andExpect(jsonPath("$[0].id", is(score.getId().toString())))
        .andExpect(jsonPath("$[0].time").value(score.getTime().format(FORMATTER)))
        .andExpect(jsonPath("$[0].score", is(score.getScore())))
        .andExpect(jsonPath("$[0].player", is(score.getPlayer())));

    verify(service, times(1)).getAll(pageNo, pageSize, time, ScoreFilter.EMPTY);
  }

  @RepeatedTest(3)
  void givenScores_whenGetScoresByDate_thenReturnJsonArray() throws Exception {

    final LocalDateTime after = LocalDateTime.now();
    final LocalDateTime before = LocalDateTime.MAX;
    final Score score = new Score(UUID.randomUUID(), "player", 100, after);
    final List<Score> allScores = List.of(score);

    final Integer pageNo = 0;
    final Integer pageSize = 10;
    final String time = "time";

    final String content =
        "{\"after\": \""
            + FORMATTER.format(after)
            + "\",\"before\":  \""
            + FORMATTER.format(before)
            + "\"}";

    given(service.getAll(eq(pageNo), eq(pageSize), eq(time), any(ScoreFilter.class)))
        .willReturn(allScores);

    mvc.perform(
            MockMvcRequestBuilders.get("/scores/all")
                .param("page", pageNo.toString())
                .param("size", pageSize.toString())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(content))
        .andExpect(status().isOk())
        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(jsonPath("$", hasSize(1)))
        .andExpect(jsonPath("$[0].id", is(score.getId().toString())))
        .andExpect(jsonPath("$[0].time").value(score.getTime().format(FORMATTER)))
        .andExpect(jsonPath("$[0].score", is(score.getScore())))
        .andExpect(jsonPath("$[0].player", is(score.getPlayer())));

    verify(service, times(1)).getAll(eq(pageNo), eq(pageSize), eq(time), any(ScoreFilter.class));
  }

  @RepeatedTest(3)
  void givenScores_whenGetScoresByPlayers_thenReturnJsonArray() throws Exception {

    final String player = "player";
    final Score score = new Score(UUID.randomUUID(), player, 100, LocalDateTime.now());
    final List<Score> allScores = List.of(score);

    final Integer pageNo = 0;
    final Integer pageSize = 10;
    final String time = "time";

    final String content = "{\"players\": [\"" + player + "\"]}";

    given(service.getAll(eq(pageNo), eq(pageSize), eq(time), any(ScoreFilter.class)))
        .willReturn(allScores);

    mvc.perform(
            MockMvcRequestBuilders.get("/scores/all")
                .param("page", pageNo.toString())
                .param("size", pageSize.toString())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(content))
        .andExpect(status().isOk())
        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(jsonPath("$", hasSize(1)))
        .andExpect(jsonPath("$[0].id", is(score.getId().toString())))
        .andExpect(jsonPath("$[0].time").value(score.getTime().format(FORMATTER)))
        .andExpect(jsonPath("$[0].score", is(score.getScore())))
        .andExpect(jsonPath("$[0].player", is(score.getPlayer())));

    verify(service, times(1)).getAll(eq(pageNo), eq(pageSize), eq(time), any(ScoreFilter.class));
  }

  @RepeatedTest(3)
  void givenScoreId_whenGetScoreById_thenReturnScore() throws Exception {

    final UUID scoreId = UUID.randomUUID();
    final Score score = new Score(scoreId, "player", 100, LocalDateTime.now());

    given(service.get(scoreId)).willReturn(Optional.of(score));

    mvc.perform(
            MockMvcRequestBuilders.get("/scores/" + scoreId)
                .contentType(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isOk())
        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(jsonPath("$.id", is(score.getId().toString())))
        .andExpect(jsonPath("$.time").value(score.getTime().format(FORMATTER)))
        .andExpect(jsonPath("$.score", is(score.getScore())))
        .andExpect(jsonPath("$.player", is(score.getPlayer())));

    verify(service, times(1)).get(scoreId);
  }

  @RepeatedTest(3)
  void givenScore_whenAddScore_thenReturnCreated() throws Exception {
    willDoNothing().given(service).add(any(Score.class));

    mvc.perform(
            MockMvcRequestBuilders.post("/scores")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(
                    "{\"player\": \"Player\",\"score\": 100,\"time\": \"2021-04-19T13:58:06\"}"))
        .andExpect(status().isCreated())
        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(content().string("Added"));

    verify(service, times(1)).add(any(Score.class));
  }

  @RepeatedTest(3)
  void givenScoreId_whenDeleteScore_thenReturnDeleted() throws Exception {
    UUID scoreId = UUID.randomUUID();
    willDoNothing().given(service).delete(scoreId);

    mvc.perform(MockMvcRequestBuilders.delete("/scores/" + scoreId))
        .andExpect(status().isOk())
        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(content().string("Deleted"));

    verify(service, times(1)).delete(scoreId);
  }
}
