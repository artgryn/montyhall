package com.artgr.montyhall.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.artgr.montyhall.data.presentation.GameInput;
import com.artgr.montyhall.data.presentation.GameResult;
import com.artgr.montyhall.service.GameService;
import com.artgr.montyhall.utils.BoxNames;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.bson.types.ObjectId;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mapping.context.MappingContext;
import org.springframework.data.mongodb.core.convert.DbRefResolver;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

@WebMvcTest(GameController.class)
public class GameControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GameService gameService;

    @TestConfiguration
    static class TestConfig {
        @Bean
        public MappingMongoConverter mongoConverter() {
            return new MappingMongoConverter(mock(DbRefResolver.class), mock(MappingContext.class));
        }
    }

    @Test
    public void testGameEndpoint() throws Exception {

        ObjectMapper objectMapper = new ObjectMapper();
        GameResult game = GameResult.builder()
                                    .gameId(ObjectId.get().toHexString())
                                    .selectedBox(BoxNames.BOX_1.getName())
                                    .winnerBox(BoxNames.BOX_1.getName())
                                    .emptyBox(BoxNames.BOX_2.getName())
                                    .isWinner(true)
                                    .build();

        when(gameService.quickGame(any(GameInput.class))).thenReturn(game);

        MvcResult result = mockMvc.perform(post("/game")
                                               .content(objectMapper.writeValueAsString(GameInput.builder().build()))
                                               .contentType(MediaType.APPLICATION_JSON)
                                               .accept(MediaType.APPLICATION_JSON))
                                  .andExpect(status().isOk()).andReturn();

        String actualJson = result.getResponse().getContentAsString();
        GameResult gameResult = objectMapper.readValue(actualJson, GameResult.class);

        assertNotNull(gameResult);
        assertEquals(gameResult.getGameId(), game.getGameId());
        assertEquals(gameResult.getEmptyBox(), game.getEmptyBox());
        assertEquals(gameResult.getWinnerBox(), game.getWinnerBox());
    }
}
