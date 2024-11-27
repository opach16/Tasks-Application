package com.crud.tasks.controller;

import com.crud.tasks.domain.TrelloBoardDto;
import com.crud.tasks.domain.TrelloCardDto;
import com.crud.tasks.domain.TrelloListDto;
import com.crud.tasks.domain.createdTrelloCard.CreatedTrelloCardDto;
import com.crud.tasks.trello.facade.TrelloFacade;
import com.google.gson.Gson;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringJUnitConfig
@WebMvcTest(TrelloController.class)
class TrelloControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TrelloFacade trelloFacade;

    @Test
    void shouldFetchEmptyTrelloBoards() throws Exception {
        //given
        when(trelloFacade.fetchTrelloBoards()).thenReturn(List.of());
        //when & then
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/v1/trello/boards")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(0)));
    }

    @Test
    void shouldFetchTrelloBoards() throws Exception {
        //given
        List<TrelloListDto> trelloLists = List.of(new TrelloListDto("1", "testList", false));
        List<TrelloBoardDto> trelloBoards = List.of(new TrelloBoardDto("1", "testBoard", trelloLists));
        when(trelloFacade.fetchTrelloBoards()).thenReturn(trelloBoards);
        //when & then
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/v1/trello/boards")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id", Matchers.is("1")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name", Matchers.is("testBoard")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].lists", Matchers.hasSize(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].lists[0].id", Matchers.is("1")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].lists[0].name", Matchers.is("testList")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].lists[0].closed", Matchers.is(false)));
    }

    @Test
    void shouldCreateTrelloCard() throws Exception {
        //given
        TrelloCardDto trelloCard = new TrelloCardDto("testName", "testDesc", "testPos", "1");
        CreatedTrelloCardDto createdTrelloCard = new CreatedTrelloCardDto("1", "testName", "https://test.com");
        when(trelloFacade.createCard(any(TrelloCardDto.class))).thenReturn(createdTrelloCard);
        Gson gson = new Gson();
        String jsonContent = gson.toJson(trelloCard);
        //when & then
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/v1/trello/cards")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .content(jsonContent))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.is("1")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", Matchers.is("testName")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.shortUrl", Matchers.is("https://test.com")));
    }
}