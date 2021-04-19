package com.morozov.toprankingservice.controller;

import com.morozov.toprankingservice.dto.response.PlayerHistoryResponse;
import com.morozov.toprankingservice.service.PlayerScoreHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/playerscorehistory",
        produces = MediaType.APPLICATION_JSON_VALUE)
public class PlayerHistoryController {
    private final PlayerScoreHistoryService playerScoreHistoryService;

    @Autowired
    public PlayerHistoryController(PlayerScoreHistoryService playerScoreHistoryService) {
        this.playerScoreHistoryService = playerScoreHistoryService;
    }

    @GetMapping("/{player}")
    public ResponseEntity<List<PlayerHistoryResponse>> getAll(@PathVariable String player) {
        List<PlayerHistoryResponse> result = playerScoreHistoryService.getAll(
                player.toLowerCase(Locale.ROOT))
                .stream()
                .map(PlayerHistoryResponse::new)
                .collect(Collectors.toList());

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/top/{player}")
    public ResponseEntity<PlayerHistoryResponse> getTop(@PathVariable String player) {
        PlayerHistoryResponse result = new PlayerHistoryResponse(
                playerScoreHistoryService.getTop(
                        player.toLowerCase(Locale.ROOT)));

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/lowest/{player}")
    public ResponseEntity<PlayerHistoryResponse> getLowest(@PathVariable String player) {
        PlayerHistoryResponse result = new PlayerHistoryResponse(
                playerScoreHistoryService.getLowest(
                        player.toLowerCase(Locale.ROOT)));

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/avg/{player}")
    public ResponseEntity<Double> getAverage(@PathVariable String player) {
        Double result =
                playerScoreHistoryService.getAverage(player.toLowerCase(Locale.ROOT));

        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
