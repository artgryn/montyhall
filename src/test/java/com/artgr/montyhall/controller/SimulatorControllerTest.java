package com.artgr.montyhall.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.artgr.montyhall.data.presentation.SimulatorResult;
import com.artgr.montyhall.service.SimulatorAndAnalyticsService;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mapping.context.MappingContext;
import org.springframework.data.mongodb.core.convert.DbRefResolver;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

@WebMvcTest(SimulatorController.class)
public class SimulatorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SimulatorAndAnalyticsService service;

    @TestConfiguration
    static class TestConfig {
        @Bean
        public MappingMongoConverter mongoConverter() {
            return new MappingMongoConverter(mock(DbRefResolver.class), mock(MappingContext.class));
        }
    }

    @Test
    public void testSimulationEndpoint() throws Exception {

        ObjectMapper objectMapper = new ObjectMapper();
        SimulatorResult simulation = SimulatorResult.builder()
                                                    .numberOfGames(3)
                                                    .numberOfWins(2)
                                                    .numberOfDecisionChange(1)
                                                    .winsAfterDecisionChange(1)
                                                    .winsWithoutDecisionChange(1)
                                                    .boxChangeToNonChangeWinsRatio(1.0)
                                                    .build();

        when(service.runSimulation()).thenReturn(simulation);

        MvcResult result = mockMvc.perform(get("/simulator"))
                                  .andExpect(status().isOk()).andReturn();

        String actualJson = result.getResponse().getContentAsString();
        SimulatorResult simulationResult = objectMapper.readValue(actualJson, SimulatorResult.class);

        assertNotNull(simulationResult);
        assertEquals(simulationResult.getNumberOfGames(), simulation.getNumberOfGames());
        assertEquals(simulationResult.getNumberOfWins(), simulation.getNumberOfWins());
        assertEquals(simulationResult.getBoxChangeToNonChangeWinsRatio(), simulation.getBoxChangeToNonChangeWinsRatio());
        assertEquals(simulationResult.getNumberOfDecisionChange(), simulation.getNumberOfDecisionChange());
        assertEquals(simulationResult.getWinsWithoutDecisionChange(), simulation.getWinsWithoutDecisionChange());
    }
}
