package com.morozov.toprankingservice.service;

import com.morozov.toprankingservice.dto.Score;
import com.morozov.toprankingservice.entity.ScoreEntity;
import com.morozov.toprankingservice.repository.ScoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ScoreService {

    private final ScoreRepository repository;

    @Autowired
    public ScoreService(ScoreRepository repository) {
        this.repository = repository;
    }

    public List<Score> getAll(Integer pageNo, Integer pageSize, String sortBy)
    {
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));

        Page<ScoreEntity> pagedResult = repository.findAll(paging);

        if(pagedResult.hasContent()) {
            return pagedResult.getContent()
                    .stream()
                    .map(scoreEntity -> new Score(scoreEntity.getId(),
                            scoreEntity.getPlayer(),
                            scoreEntity.getScore(),
                            scoreEntity.getTime()))
                    .collect(Collectors.toList());
        } else {
            return new ArrayList<>();
        }
    }

    public void add(Score score) {
        repository.save(new ScoreEntity(score.getId(),
                score.getPlayer(),
                score.getScore(),
                score.getTime()));
    }

    public void delete(UUID scoreId) {
        repository.deleteById(scoreId);
    }
}
