package com.morozov.toprankingservice.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.morozov.toprankingservice.repository.ScoreRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class PlayerScoreHistoryServiceTest {

  @Test
  @DisplayName("Get top score by player")
  void getTop() {
    final ScoreRepository repository = Mockito.mock(ScoreRepository.class);

    final String player = "player";
    final ScoreRepository.TimeScore expected = new TimeScoreImpl(LocalDateTime.now(), 100);

    Mockito.when(repository.getTopScore(player)).thenReturn(expected);

    final PlayerScoreHistoryService service = new PlayerScoreHistoryService(repository);
    final ScoreRepository.TimeScore result = service.getTop(player);

    Mockito.verify(repository).getTopScore(player);
    assertEquals(expected, result, "Should return top score");
  }

  @Test
  @DisplayName("Get lowest score by player")
  void getLowest() {
    final ScoreRepository repository = Mockito.mock(ScoreRepository.class);

    final String player = "player";
    final ScoreRepository.TimeScore expected = new TimeScoreImpl(LocalDateTime.now(), 1);

    Mockito.when(repository.getLowestScore(player)).thenReturn(expected);

    final PlayerScoreHistoryService service = new PlayerScoreHistoryService(repository);
    final ScoreRepository.TimeScore result = service.getLowest(player);

    Mockito.verify(repository).getLowestScore(player);
    assertEquals(expected, result, "Should return lowest score");
  }

  @Test
  @DisplayName("Get average score by player")
  void getAverage() {
    final ScoreRepository repository = Mockito.mock(ScoreRepository.class);

    final String player = "player";
    final Double expected = 100.0;

    Mockito.when(repository.getAverageScore(player)).thenReturn(expected);

    final PlayerScoreHistoryService service = new PlayerScoreHistoryService(repository);
    final Double result = service.getAverage(player);

    Mockito.verify(repository).getAverageScore(player);
    assertEquals(expected, result, "Should return average score");
  }

  @Test
  @DisplayName("Get all scores by player")
  void getAll() {
    final ScoreRepository repository = Mockito.mock(ScoreRepository.class);

    final String player = "player";
    final List<ScoreRepository.TimeScore> expected =
        List.of(new TimeScoreImpl(LocalDateTime.now(), 100));

    Mockito.when(repository.findAllByPlayer(player)).thenReturn(expected);

    final PlayerScoreHistoryService service = new PlayerScoreHistoryService(repository);
    final List<ScoreRepository.TimeScore> result = service.getAll(player);

    Mockito.verify(repository).findAllByPlayer(player);
    assertEquals(expected, result, "Should return all scores by player");
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
