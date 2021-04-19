package com.morozov.toprankingservice.dto;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ScoreFilterTest {
    @Test
    public void Test_getters() {

        final List<String> players = List.of("player1");
        final LocalDateTime after = LocalDateTime.now();
        final LocalDateTime before = LocalDateTime.MAX;

        final ScoreFilter pojo = Mockito.mock(ScoreFilter.class);
        Mockito.when(pojo.getPlayers()).thenReturn(players);
        Mockito.when(pojo.getBefore()).thenReturn(before);
        Mockito.when(pojo.getAfter()).thenReturn(after);

        assertEquals(after, pojo.getAfter());
        assertEquals(before, pojo.getBefore());
        assertEquals(players, pojo.getPlayers());
    }
}