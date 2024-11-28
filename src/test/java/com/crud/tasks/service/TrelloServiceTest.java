package com.crud.tasks.service;

import com.crud.tasks.domain.TrelloBoardDto;
import com.crud.tasks.domain.TrelloCardDto;
import com.crud.tasks.domain.TrelloListDto;
import com.crud.tasks.domain.createdTrelloCard.CreatedTrelloCardDto;
import com.crud.tasks.domain.createdTrelloCard.Mail;
import com.crud.tasks.trello.client.TrelloClient;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class TrelloServiceTest {

    @Autowired
    private TrelloService trelloService;

    @MockBean
    private SimpleEmailService emailService;

    @MockBean
    private TrelloClient trelloClient;

    @Test
    void shouldFetchTrelloBoards() {
        //given
        List<TrelloListDto> trelloListsDto = List.of(new TrelloListDto("1", "testName", false));
        List<TrelloBoardDto> trelloBoardsDto = List.of(new TrelloBoardDto("1", "testName", trelloListsDto));
        when(trelloClient.getTrelloBoards()).thenReturn(trelloBoardsDto);
        //when
        List<TrelloBoardDto> fetchedTrelloBoards = trelloService.fetchTrelloBoards();
        //then
        assertEquals(1, fetchedTrelloBoards.size());
        assertEquals("1", fetchedTrelloBoards.get(0).getId());
        assertEquals("testName", fetchedTrelloBoards.get(0).getName());
        assertEquals(trelloListsDto, fetchedTrelloBoards.get(0).getLists());
    }

    @Test
    void createTrelloCard() {
        //given
        TrelloCardDto trelloCardDto = new TrelloCardDto("testName", "testDesc", "testPos", "1");
        CreatedTrelloCardDto createdTrelloCardDto = new CreatedTrelloCardDto("1", "testName", "https://test.com");
        when(trelloClient.createNewCard(trelloCardDto)).thenReturn(createdTrelloCardDto);
        //when
        CreatedTrelloCardDto fetchedCreatedTrelloCard = trelloService.createTrelloCard(trelloCardDto);
        //then
        assertEquals("1", fetchedCreatedTrelloCard.getId());
        assertEquals("testName", fetchedCreatedTrelloCard.getName());
        assertEquals("https://test.com", fetchedCreatedTrelloCard.getShortUrl());
        verify(emailService, times(1)).send(any(Mail.class));
    }
}