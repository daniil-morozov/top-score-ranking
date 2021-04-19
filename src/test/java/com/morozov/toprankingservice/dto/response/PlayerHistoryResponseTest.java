package com.morozov.toprankingservice.dto.response;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PlayerHistoryResponseTest {
    @Test
    public void Test_getters() {
        final Integer score = 100;
        final LocalDateTime time = LocalDateTime.now();

        final PlayerHistoryResponse resp = Mockito.mock(PlayerHistoryResponse.class);

        Mockito.when(resp.getScore()).thenReturn(score);
        Mockito.when(resp.getTime()).thenReturn(time);

        assertEquals(score, resp.getScore());
        assertEquals(time, resp.getTime());
    }
}