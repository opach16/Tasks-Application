package com.crud.tasks.mapper;

import com.crud.tasks.domain.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class TrelloMapperTest {

    @Autowired
    TrelloMapper trelloMapper;

    @Test
    void testMapToBoardDto() {
        //given
        TrelloBoard trelloBoard1 = new TrelloBoard("1", "test1", List.of(new TrelloList("1", "test1", false)));
        TrelloBoard trelloBoard2 = new TrelloBoard("2", "test2", List.of(new TrelloList("2", "test2", true)));
        TrelloBoardDto trelloBoardDto1 = new TrelloBoardDto("1", "test1", List.of(new TrelloListDto("1", "test1", false)));
        TrelloBoardDto trelloBoardDto2 = new TrelloBoardDto("2", "test2", List.of(new TrelloListDto("2", "test2", true)));
        List<TrelloBoard> trelloBoards = List.of(trelloBoard1, trelloBoard2);
        List<TrelloBoardDto> expectedTrelloBoardsDto = List.of(trelloBoardDto1, trelloBoardDto2);
        //when
        List<TrelloBoardDto> retrievedTrelloBoardsDto = trelloMapper.mapToBoardDto(trelloBoards);
        //then
        assertEquals(expectedTrelloBoardsDto, retrievedTrelloBoardsDto);
    }

    @Test
    void testMapToBoards() {
        //given
        TrelloBoard trelloBoard1 = new TrelloBoard("1", "test1", List.of(new TrelloList("1", "test1", false)));
        TrelloBoard trelloBoard2 = new TrelloBoard("2", "test2", List.of(new TrelloList("2", "test2", true)));
        TrelloBoardDto trelloBoardDto1 = new TrelloBoardDto("1", "test1", List.of(new TrelloListDto("1", "test1", false)));
        TrelloBoardDto trelloBoardDto2 = new TrelloBoardDto("2", "test2", List.of(new TrelloListDto("2", "test2", true)));
        List<TrelloBoard> expectedTrelloBoards = List.of(trelloBoard1, trelloBoard2);
        List<TrelloBoardDto> trelloBoardsDto = List.of(trelloBoardDto1, trelloBoardDto2);
        //when
        List<TrelloBoard> retrievedTrelloBoards = trelloMapper.mapToBoards(trelloBoardsDto);
        //then
        assertEquals(expectedTrelloBoards, retrievedTrelloBoards);
    }

    @Test
    void testMapToListDto() {
        //given
        List<TrelloList> trelloLists = List.of(
                new TrelloList("1", "test1", false),
                new TrelloList("2", "test2", true));
        List<TrelloListDto> expectedTrelloListsDto = List.of(
                new TrelloListDto("1", "test1", false),
                new TrelloListDto("2", "test2", true));
        //when
        List<TrelloListDto> retrievedTrelloListsDto = trelloMapper.mapToListDto(trelloLists);
        //then
        assertEquals(expectedTrelloListsDto, retrievedTrelloListsDto);
    }

    @Test
    void testMapToLists() {
        //given
        List<TrelloListDto> trelloListsDto = List.of(
                new TrelloListDto("1", "test1", false),
                new TrelloListDto("2", "test2", true));
        List<TrelloList> expectedTrelloLists = List.of(
                new TrelloList("1", "test1", false),
                new TrelloList("2", "test2", true));
        //when
        List<TrelloList> retrievedTrelloLists = trelloMapper.mapToList(trelloListsDto);
        //then
        assertEquals(expectedTrelloLists, retrievedTrelloLists);
    }

    @Test
    void testMapToTrelloCardDto() {
        //given
        TrelloCard trelloCard = new TrelloCard("1", "test1", "test1", "1");
        TrelloCardDto expectedTrelloCardDto = new TrelloCardDto("1", "test1", "test1", "1");
        //when
        TrelloCardDto retrievedTrelloCardDto = trelloMapper.mapToTrelloCardDto(trelloCard);
        //then
        assertEquals(expectedTrelloCardDto, retrievedTrelloCardDto);
    }

    @Test
    void testMapToTrelloCard() {
        //given
        TrelloCardDto trelloCardDto = new TrelloCardDto("1", "test1", "test1", "1");
        TrelloCard expectedTrelloCard = new TrelloCard("1", "test1", "test1", "1");
        //when
        TrelloCard retrievedTrelloCard = trelloMapper.mapToTrelloCard(trelloCardDto);
        //then
        assertEquals(expectedTrelloCard, retrievedTrelloCard);
    }
}
