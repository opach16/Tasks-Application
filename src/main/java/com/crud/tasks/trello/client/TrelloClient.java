package com.crud.tasks.trello.client;

import com.crud.tasks.domain.TrelloBoardDto;
import com.crud.tasks.domain.TrelloCardDto;
import com.crud.tasks.domain.createdTrelloCard.CreatedTrelloCardDto;
import com.crud.tasks.trello.config.TrelloConfig;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class TrelloClient {

    private final RestTemplate restTemplate;
    private final TrelloConfig trelloConfig;
    private static final Logger LOGGER = LoggerFactory.getLogger(TrelloClient.class);

    public List<TrelloBoardDto> getTrelloBoards() {
        URI uri = getTrelloBoardUri();
        try {
            TrelloBoardDto[] boardsResponse = restTemplate.getForObject(uri, TrelloBoardDto[].class);
            return Optional.ofNullable(boardsResponse)
                    .map(Arrays::asList)
                    .orElse(Collections.emptyList())
                    .stream()
                    .filter(board -> board.getId() != null && board.getName() != null)
                    .toList();
        } catch (RestClientException e) {
            LOGGER.error(e.getMessage(), e);
            return Collections.emptyList();
        }
    }

    public CreatedTrelloCardDto createNewCard(TrelloCardDto trelloCardDto) {
        URI uri = UriComponentsBuilder.fromHttpUrl(trelloConfig.getTrelloApiEndpoint() + "/cards")
                .queryParam("idList", trelloCardDto.getListId())
                .queryParam("key", trelloConfig.getTrelloAppKey())
                .queryParam("token", trelloConfig.getTrelloToken())
                .queryParam("name", trelloCardDto.getName())
                .queryParam("desc", trelloCardDto.getDescription())
                .queryParam("pos", trelloCardDto.getPos())
                .build()
                .encode()
                .toUri();

        return restTemplate.postForObject(uri, null, CreatedTrelloCardDto.class);
    }

    private URI getTrelloBoardUri() {
        return UriComponentsBuilder.fromHttpUrl(trelloConfig.getTrelloApiEndpoint()
                        + "/members/" + trelloConfig.getTrelloUsername() + "/boards")
                .queryParam("key", trelloConfig.getTrelloAppKey())
                .queryParam("token", trelloConfig.getTrelloToken())
                .queryParam("fields", "name,id")
                .queryParam("lists", "all")
                .build()
                .encode()
                .toUri();
    }
}
