package com.morozov.toprankingservice.service;

import com.morozov.toprankingservice.dto.Score;
import com.morozov.toprankingservice.dto.ScoreFilter;
import com.morozov.toprankingservice.entity.ScoreEntity;
import com.morozov.toprankingservice.repository.ScoreRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class ScoreService {

  private final ScoreRepository repository;

  @Autowired
  public ScoreService(ScoreRepository repository) {
    this.repository = repository;
  }

  public List<Score> getAll(Integer pageNo, Integer pageSize, String sortBy, ScoreFilter filter) {
    final Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
    final Page<ScoreEntity> pagedResult = makeRequest(filter, paging);

    if (pagedResult != null && pagedResult.hasContent()) {
      return pagedResult.getContent().stream()
          .map(
              scoreEntity ->
                  new Score(
                      scoreEntity.getId(),
                      scoreEntity.getPlayer(),
                      scoreEntity.getScore(),
                      scoreEntity.getTime()))
          .collect(Collectors.toList());
    } else {
      return new ArrayList<>();
    }
  }

  private Page<ScoreEntity> makeRequest(ScoreFilter filter, Pageable paging) {
    Page<ScoreEntity> pagedResult = null;

    if (filter.getPlayers() != null && !filter.getPlayers().isEmpty()) {
      if (filter.getBefore() != null && filter.getAfter() != null) {
        pagedResult =
            repository.findByPlayerInAndTimeBetween(
                filter.getPlayers(), filter.getAfter(), filter.getBefore(), paging);
      } else if (filter.getBefore() != null) {
        pagedResult =
            repository.findByPlayerInAndTimeBefore(filter.getPlayers(), filter.getBefore(), paging);
      } else if (filter.getAfter() != null) {
        pagedResult =
            repository.findByPlayerInAndTimeAfter(filter.getPlayers(), filter.getAfter(), paging);
      } else {
        pagedResult = repository.findByPlayerIn(filter.getPlayers(), paging);
      }
    } else {
      if (filter.getBefore() != null && filter.getAfter() != null) {
        pagedResult = repository.findByTimeBetween(filter.getAfter(), filter.getBefore(), paging);
      } else if (filter.getBefore() != null) {
        pagedResult = repository.findByTimeBefore(filter.getBefore(), paging);
      } else if (filter.getAfter() != null) {
        pagedResult = repository.findByTimeAfter(filter.getAfter(), paging);
      } else {
        pagedResult = repository.findAll(paging);
      }
    }

    return pagedResult;
  }

  public Optional<Score> get(UUID scoreId) {
    final Optional<ScoreEntity> response = repository.findById(scoreId);

    return response.map(Score::new);
  }

  public void add(Score score) {
    repository.save(
        new ScoreEntity(score.getId(), score.getPlayer(), score.getScore(), score.getTime()));
  }

  public void delete(UUID scoreId) {
    repository.deleteById(scoreId);
  }
}
