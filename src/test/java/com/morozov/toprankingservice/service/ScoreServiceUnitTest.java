package com.morozov.toprankingservice.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.morozov.toprankingservice.dto.Score;
import com.morozov.toprankingservice.dto.ScoreFilter;
import com.morozov.toprankingservice.entity.ScoreEntity;
import com.morozov.toprankingservice.repository.ScoreRepository;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

class ScoreServiceUnitTest {

  @Test
  @DisplayName("Return 2 entities for 1 page")
  void Should_return_expected_When_page_correct() {

    final ScoreRepository repository = Mockito.mock(ScoreRepository.class);

    final Pageable pageable = PageRequest.of(0, 5, Sort.by("id"));
    final List<ScoreEntity> repoResponse = generateResponse();
    final List<Score> expected = makeServiceResponse(repoResponse);

    Mockito.when(repository.findAll(pageable))
        .thenReturn(new PageImpl<>(repoResponse, pageable, 2L));

    final ScoreService service = new ScoreService(repository);
    final List<Score> result = service.getAll(0, 5, "id", ScoreFilter.EMPTY);

    Mockito.verify(repository).findAll(pageable);
    assertEquals(expected, result, "Should return 2 entities");
  }

  @Test
  @DisplayName("Return 2 entities for 1 page with correct names")
  void Should_return_expected_When_players_correct() {

    final ScoreRepository repository = Mockito.mock(ScoreRepository.class);

    final Pageable pageable = PageRequest.of(0, 5, Sort.by("id"));
    final List<String> players = List.of("player1", "player2");
    final List<ScoreEntity> repoResponse = generateResponse();
    final List<Score> expected = makeServiceResponse(repoResponse);

    Mockito.when(repository.findByPlayerIn(players, pageable))
        .thenReturn(new PageImpl<>(repoResponse, pageable, 2L));

    final ScoreService service = new ScoreService(repository);
    final List<Score> result = service.getAll(0, 5, "id", new ScoreFilter(players, null, null));

    Mockito.verify(repository).findByPlayerIn(players, pageable);
    assertEquals(expected, result, "Should return 2 entities");
  }

  @Test
  @DisplayName("Return 2 entities for 1 page with correct names and before date")
  void Should_return_expected_When_players_and_before_correct() {

    final ScoreRepository repository = Mockito.mock(ScoreRepository.class);

    final Pageable pageable = PageRequest.of(0, 5, Sort.by("id"));
    final List<String> players = List.of("player1", "player2");
    final LocalDateTime before = LocalDateTime.now();

    final List<ScoreEntity> repoResponse = generateResponse();
    final List<Score> expected = makeServiceResponse(repoResponse);

    Mockito.when(repository.findByPlayerInAndTimeBefore(players, before, pageable))
        .thenReturn(new PageImpl<>(repoResponse, pageable, 2L));

    final ScoreService service = new ScoreService(repository);
    final List<Score> result = service.getAll(0, 5, "id", new ScoreFilter(players, before, null));

    Mockito.verify(repository).findByPlayerInAndTimeBefore(players, before, pageable);
    assertEquals(expected, result, "Should return 2 entities");
  }

  @Test
  @DisplayName("Return 2 entities for 1 page with correct names and within before-after date")
  void Should_return_expected_When_players_and_within_before_after_correct() {

    final ScoreRepository repository = Mockito.mock(ScoreRepository.class);

    final Pageable pageable = PageRequest.of(0, 5, Sort.by("id"));
    final List<String> players = List.of("player1", "player2");
    final LocalDateTime before = LocalDateTime.MAX;
    final LocalDateTime after = LocalDateTime.now();

    final List<ScoreEntity> repoResponse = generateResponse();
    final List<Score> expected = makeServiceResponse(repoResponse);

    Mockito.when(repository.findByPlayerInAndTimeBetween(players, after, before, pageable))
        .thenReturn(new PageImpl<>(repoResponse, pageable, 2L));

    final ScoreService service = new ScoreService(repository);
    final List<Score> result = service.getAll(0, 5, "id", new ScoreFilter(players, before, after));

    Mockito.verify(repository).findByPlayerInAndTimeBetween(players, after, before, pageable);
    assertEquals(expected, result, "Should return 2 entities");
  }

  @Test
  @DisplayName("Return 2 entities for 1 page with correct names and after date")
  void Should_return_expected_When_players_and_after_correct() {

    final ScoreRepository repository = Mockito.mock(ScoreRepository.class);

    final Pageable pageable = PageRequest.of(0, 5, Sort.by("id"));
    final List<String> players = List.of("player1", "player2");
    final LocalDateTime after = LocalDateTime.now();

    final List<ScoreEntity> repoResponse = generateResponse();
    final List<Score> expected = makeServiceResponse(repoResponse);

    Mockito.when(repository.findByPlayerInAndTimeAfter(players, after, pageable))
        .thenReturn(new PageImpl<>(repoResponse, pageable, 2L));

    final ScoreService service = new ScoreService(repository);
    final List<Score> result = service.getAll(0, 5, "id", new ScoreFilter(players, null, after));

    Mockito.verify(repository).findByPlayerInAndTimeAfter(players, after, pageable);
    assertEquals(expected, result, "Should return 2 entities");
  }

  @Test
  @DisplayName("Return 2 entities for 1 page with before date")
  void Should_return_expected_When_before_correct() {

    final ScoreRepository repository = Mockito.mock(ScoreRepository.class);

    final Pageable pageable = PageRequest.of(0, 5, Sort.by("id"));
    final LocalDateTime before = LocalDateTime.now();

    final List<ScoreEntity> repoResponse = generateResponse();
    final List<Score> expected = makeServiceResponse(repoResponse);

    Mockito.when(repository.findByTimeBefore(before, pageable))
        .thenReturn(new PageImpl<>(repoResponse, pageable, 2L));

    final ScoreService service = new ScoreService(repository);
    final List<Score> result = service.getAll(0, 5, "id", new ScoreFilter(null, before, null));

    Mockito.verify(repository).findByTimeBefore(before, pageable);
    assertEquals(expected, result, "Should return 2 entities");
  }

  @Test
  @DisplayName("Return 2 entities for 1 page with after date")
  void Should_return_expected_When_after_correct() {

    final ScoreRepository repository = Mockito.mock(ScoreRepository.class);

    final Pageable pageable = PageRequest.of(0, 5, Sort.by("id"));
    final LocalDateTime after = LocalDateTime.now();

    final List<ScoreEntity> repoResponse = generateResponse();
    final List<Score> expected = makeServiceResponse(repoResponse);

    Mockito.when(repository.findByTimeAfter(after, pageable))
        .thenReturn(new PageImpl<>(repoResponse, pageable, 2L));

    final ScoreService service = new ScoreService(repository);
    final List<Score> result = service.getAll(0, 5, "id", new ScoreFilter(null, null, after));

    Mockito.verify(repository).findByTimeAfter(after, pageable);
    assertEquals(expected, result, "Should return 2 entities");
  }

  @Test
  @DisplayName("Return 2 entities for 1 page between after and before")
  void Should_return_expected_When_between_after_before_correct() {

    final ScoreRepository repository = Mockito.mock(ScoreRepository.class);

    final Pageable pageable = PageRequest.of(0, 5, Sort.by("id"));
    final LocalDateTime after = LocalDateTime.now();
    final LocalDateTime before = LocalDateTime.MAX;

    final List<ScoreEntity> repoResponse = generateResponse();
    final List<Score> expected = makeServiceResponse(repoResponse);

    Mockito.when(repository.findByTimeBetween(after, before, pageable))
        .thenReturn(new PageImpl<>(repoResponse, pageable, 2L));

    final ScoreService service = new ScoreService(repository);
    final List<Score> result = service.getAll(0, 5, "id", new ScoreFilter(null, before, after));

    Mockito.verify(repository).findByTimeBetween(after, before, pageable);
    assertEquals(expected, result, "Should return 2 entities");
  }

  @Test
  @DisplayName("Return empty for incorrect page")
  void Should_return_empty_When_page_incorrect() {

    final ScoreRepository repository = Mockito.mock(ScoreRepository.class);

    final Pageable pageable = PageRequest.of(10, 5, Sort.by("id"));
    final List<ScoreEntity> repoResponse = Collections.emptyList();
    final List<Score> expected = Collections.emptyList();

    Mockito.when(repository.findAll(pageable))
        .thenReturn(new PageImpl<>(repoResponse, pageable, 2L));

    final ScoreService service = new ScoreService(repository);
    final List<Score> result = service.getAll(10, 5, "id", ScoreFilter.EMPTY);

    Mockito.verify(repository).findAll(pageable);
    assertEquals(expected, result, "Should return empty for incorrect page");
  }

  @Test
  @DisplayName("Return ok when score exists")
  void Should_return_ok_When_score_exists() {

    final ScoreRepository repository = Mockito.mock(ScoreRepository.class);

    final UUID request = UUID.randomUUID();

    Mockito.doNothing().when(repository).deleteById(request);

    final ScoreService service = new ScoreService(repository);
    service.delete(request);

    Mockito.verify(repository).deleteById(request);
  }

  @Test
  @DisplayName("Throw exception when score doesn't exist")
  void Should_throw_emptySet_When_score_doesnt_exist() {

    final ScoreRepository repository = Mockito.mock(ScoreRepository.class);

    final UUID request = UUID.randomUUID();

    Mockito.doThrow(new EmptyResultDataAccessException(1)).when(repository).deleteById(request);

    final ScoreService service = new ScoreService(repository);

    Assertions.assertThrows(EmptyResultDataAccessException.class, () -> service.delete(request));
  }

  @Test
  @DisplayName("Return score by score id when score exists")
  void Should_return_score_When_score_exists() {

    final ScoreRepository repository = Mockito.mock(ScoreRepository.class);

    final UUID request = UUID.randomUUID();
    final ScoreEntity repoResponse = generateEntity();
    Mockito.when(repository.findById(request)).thenReturn(Optional.of(repoResponse));

    final ScoreService service = new ScoreService(repository);
    service.get(request);

    Mockito.verify(repository).findById(request);
  }

  private List<ScoreEntity> generateResponse() {
    return List.of(
        generateEntity(), new ScoreEntity(UUID.randomUUID(), "player2", 4, LocalDateTime.now()));
  }

  private ScoreEntity generateEntity() {
    return new ScoreEntity(UUID.randomUUID(), "player1", 5, LocalDateTime.now());
  }

  private List<Score> makeServiceResponse(List<ScoreEntity> entities) {
    return entities.stream()
        .map(
            scoreEntity ->
                new Score(
                    scoreEntity.getId(),
                    scoreEntity.getPlayer(),
                    scoreEntity.getScore(),
                    scoreEntity.getTime()))
        .collect(Collectors.toList());
  }
}
