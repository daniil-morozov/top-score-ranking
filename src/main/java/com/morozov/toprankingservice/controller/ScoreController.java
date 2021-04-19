package com.morozov.toprankingservice.controller;

import com.morozov.toprankingservice.dto.Score;
import com.morozov.toprankingservice.dto.request.ScoreRequest;
import com.morozov.toprankingservice.dto.response.ScoreResponse;
import com.morozov.toprankingservice.service.ScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Locale;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/scores",
        produces = MediaType.APPLICATION_JSON_VALUE)
public class ScoreController {
    private final ScoreService scoreService;

    @Autowired
    public ScoreController(ScoreService scoreService) {
        this.scoreService = scoreService;
    }

    @GetMapping(value = "/all", params = {"page", "size"})
    public ResponseEntity<List<ScoreResponse>> get(@RequestParam("page") int page,
                                                   @RequestParam("size") int size) {
        List<Score> serviceResponse = scoreService.getAll(page, size, "id");
        List<ScoreResponse> result = serviceResponse
                .stream()
                .map(ScoreResponse::new)
                .collect(Collectors.toList());

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<String> add(@RequestBody ScoreRequest request) {
        scoreService.add(new Score(UUID.randomUUID(),
                request.getPlayer().toLowerCase(Locale.ROOT),
                request.getScore(),
                request.getTime()));

        return new ResponseEntity<>("Added", HttpStatus.CREATED);
    }

    @DeleteMapping("/{scoreId}")
    public ResponseEntity<String> delete(@PathVariable String scoreId) {
        try {
            scoreService.delete(UUID.fromString(scoreId));
        }
        catch (EmptyResultDataAccessException ex) {
            return new ResponseEntity<>("No score found with id=" + scoreId, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>("Deleted", HttpStatus.OK);
    }
}
