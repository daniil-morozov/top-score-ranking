package com.morozov.toprankingservice.controller;

import com.morozov.toprankingservice.dto.Score;
import com.morozov.toprankingservice.dto.ScoreFilter;
import com.morozov.toprankingservice.dto.request.AddScoreRequest;
import com.morozov.toprankingservice.dto.request.GetScoreRequest;
import com.morozov.toprankingservice.dto.response.ScoreResponse;
import com.morozov.toprankingservice.service.ScoreService;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/scores",
    produces = MediaType.APPLICATION_JSON_VALUE)
public class ScoreController {

  public static final String SORT_BY = "time";
  private final ScoreService scoreService;

  @Autowired
  public ScoreController(ScoreService scoreService) {
    this.scoreService = scoreService;
  }

  @GetMapping(value = "/all", params = {"page", "size"})
  public ResponseEntity<List<ScoreResponse>> getAll(@RequestParam("page") int page,
      @RequestParam("size") int size,
      @RequestBody(required = false) GetScoreRequest request) {
    ScoreFilter filter = createScoreFilter(request);

    final List<Score> serviceResponse = scoreService.getAll(page,
        size,
        SORT_BY,
        filter);

    final List<ScoreResponse> result = serviceResponse
        .stream()
        .map(ScoreResponse::new)
        .collect(Collectors.toList());

    return new ResponseEntity<>(result, HttpStatus.OK);
  }

  private ScoreFilter createScoreFilter(GetScoreRequest request) {
    ScoreFilter filter;

    if (request == null) {
      filter = ScoreFilter.EMPTY;
    } else {
      filter = new ScoreFilter(
          Optional.ofNullable(request.getPlayers()).orElse(Collections.emptyList())
              .stream()
              .map(name -> name.toLowerCase(Locale.ROOT))
              .collect(Collectors.toList()),
          request.getBefore(),
          request.getAfter()
      );
    }

    return filter;
  }

  @GetMapping(value = "/{scoreId}")
  public ResponseEntity<Optional<ScoreResponse>> get(@PathVariable String scoreId) {

    final Optional<Score> serviceResponse = scoreService.get(UUID.fromString(scoreId));

    final Optional<ScoreResponse> result = serviceResponse.map(ScoreResponse::new);

    if (result.isEmpty()) {
      return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
    }

    return new ResponseEntity<>(result, HttpStatus.OK);
  }

  @PostMapping
  public ResponseEntity<String> add(@RequestBody AddScoreRequest request) {
    if (request.getScore() <= 0) {
      return new ResponseEntity<>("Incorrect score value: must be more than 0",
          HttpStatus.BAD_REQUEST);
    }

    scoreService.add(new Score(UUID.randomUUID(),
        request.getPlayer().toLowerCase(Locale.ROOT),
        request.getScore(),
        request.getTime()));

    return new ResponseEntity<>("Added", HttpStatus.CREATED);
  }

  @DeleteMapping(value = "/{scoreId}")
  public ResponseEntity<String> delete(@PathVariable String scoreId) {
    try {
      scoreService.delete(UUID.fromString(scoreId));
    } catch (EmptyResultDataAccessException ex) {
      return new ResponseEntity<>("No score found with id=" + scoreId, HttpStatus.NOT_FOUND);
    }

    return new ResponseEntity<>("Deleted", HttpStatus.OK);
  }
}
