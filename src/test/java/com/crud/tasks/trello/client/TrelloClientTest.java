package com.crud.tasks.trello.client;

import com.crud.tasks.domain.TrelloBoardDto;
import com.crud.tasks.domain.TrelloCardDto;
import com.crud.tasks.domain.createdTrelloCard.Badges;
import com.crud.tasks.domain.createdTrelloCard.CreatedTrelloCard;
import com.crud.tasks.trello.config.TrelloConfig;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TrelloClientTest {

    @InjectMocks
    private TrelloClient trelloClient;

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private TrelloConfig trelloConfig;

    @Test
    void shouldFetchTrelloBoards() throws URISyntaxException {
        //given
        when(trelloConfig.getTrelloApiEndpoint()).thenReturn("https://test.com");
        when(trelloConfig.getTrelloAppKey()).thenReturn("test");
        when(trelloConfig.getTrelloToken()).thenReturn("test");
        when(trelloConfig.getTrelloUsername()).thenReturn("test");
        TrelloBoardDto[] trelloBoards = new TrelloBoardDto[1];
        trelloBoards[0] = new TrelloBoardDto("test_id", "test_board", new ArrayList<>());
        URI uri = new URI("https://test.com/members/test/boards?key=test&token=test&fields=name,id&lists=all");
        when(restTemplate.getForObject(uri, TrelloBoardDto[].class)).thenReturn(trelloBoards);
        //when
        List<TrelloBoardDto> fetchedTrelloBoards = trelloClient.getTrelloBoards();
        //then
        assertEquals(1, fetchedTrelloBoards.size());
        assertEquals("test_id", fetchedTrelloBoards.get(0).getId());
        assertEquals("test_board", fetchedTrelloBoards.get(0).getName());
        assertEquals(0, fetchedTrelloBoards.get(0).getLists().size());
    }

    @Test
    void shouldCreateCard() throws URISyntaxException {
        //given
        TrelloCardDto trelloCardDto = new TrelloCardDto("testName", "testDesc", "testPos", "testId");
        when(trelloConfig.getTrelloApiEndpoint()).thenReturn("https://test.com");
        when(trelloConfig.getTrelloAppKey()).thenReturn("test");
        when(trelloConfig.getTrelloToken()).thenReturn("test");
        URI uri = new URI("https://test.com/cards?idList=testId&key=test&token=test&name=testName&desc=testDesc&pos=testPos");
        CreatedTrelloCard createdTrelloCard = new CreatedTrelloCard("1", "Task task", "https://test.com", new Badges());
        when(restTemplate.postForObject(uri, null, CreatedTrelloCard.class)).thenReturn(createdTrelloCard);
        //when
        CreatedTrelloCard fetchedTrelloCard = trelloClient.createNewCard(trelloCardDto);
        //then
        assertEquals(createdTrelloCard.getId(), fetchedTrelloCard.getId());
        assertEquals(createdTrelloCard.getName(), fetchedTrelloCard.getName());
        assertEquals(createdTrelloCard.getShortUrl(), fetchedTrelloCard.getShortUrl());
        assertEquals(createdTrelloCard.getBadges(), fetchedTrelloCard.getBadges());
    }
}