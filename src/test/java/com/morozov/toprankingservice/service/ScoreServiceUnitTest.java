package com.morozov.toprankingservice.service;
import static net.bytebuddy.matcher.ElementMatchers.is;
import static org.junit.jupiter.api.Assertions.*;

import com.morozov.toprankingservice.dto.Score;
import com.morozov.toprankingservice.dto.response.ScoreResponse;
import com.morozov.toprankingservice.entity.ScoreEntity;
import com.morozov.toprankingservice.repository.ScoreRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

class ScoreServiceUnitTest {

    @Test
    @DisplayName("Return 2 entities for 1 page")
    void Should_return_expected_When_page_correct() {

        final ScoreRepository repository = Mockito.mock(ScoreRepository.class);

        final Pageable pageable = PageRequest.of(0, 5, Sort.by("id"));
        final List<ScoreEntity> repoResponse = generateResponse();
        final List<Score> expected = makeServiceResponse(repoResponse);

        Mockito.when(repository.findAll(pageable)).thenReturn(new PageImpl<>(repoResponse, pageable, 2L));

        final ScoreService service = new ScoreService(repository);
        final List<Score> result = service.getAll(0, 5, "id");

        Mockito.verify(repository).findAll(pageable);
        assertEquals(expected, result, "Should return 2 entities");
    }

    @Test
    @DisplayName("Return empty for incorrect page")
    void Should_return_empty_When_page_incorrect() {

        final ScoreRepository repository = Mockito.mock(ScoreRepository.class);

        final Pageable pageable = PageRequest.of(10, 5, Sort.by("id"));
        final List<ScoreEntity> repoResponse = Collections.emptyList();
        final List<Score> expected = Collections.emptyList();

        Mockito.when(repository.findAll(pageable)).thenReturn(new PageImpl<>(repoResponse,
                pageable,
                2L));

        final ScoreService service = new ScoreService(repository);
        final List<Score> result = service.getAll(10, 5, "id");

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

    private List<ScoreEntity> generateResponse() {
        return List.of(new ScoreEntity(UUID.randomUUID(),
                        "player1",
                        5,
                        LocalDateTime.now()),
                new ScoreEntity(UUID.randomUUID(),
                        "player2",
                        4,
                        LocalDateTime.now()));
    }

    private List<Score> makeServiceResponse(List<ScoreEntity> entities) {
        return entities
                .stream()
                .map(scoreEntity -> new Score(scoreEntity.getId(),
                        scoreEntity.getPlayer(),
                        scoreEntity.getScore(),
                        scoreEntity.getTime()))
                .collect(Collectors.toList());
    }
}