package com.morozov.toprankingservice.service;

import com.morozov.toprankingservice.repository.ScoreRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PlayerScoreHistoryService {

  private final ScoreRepository repository;

  @Autowired
  public PlayerScoreHistoryService(ScoreRepository repository) {
    this.repository = repository;
  }

  public ScoreRepository.TimeScore getTop(String player) {
    return repository.getTopScore(player);
  }

  public ScoreRepository.TimeScore getLowest(String player) {
    return repository.getLowestScore(player);
  }

  public Double getAverage(String player) {
    return repository.getAverageScore(player);
  }

  public List<ScoreRepository.TimeScore> getAll(String player) {
    return repository.findAllByPlayer(player);
  }
}
