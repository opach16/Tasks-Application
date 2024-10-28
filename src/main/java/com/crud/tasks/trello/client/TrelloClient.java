package com.crud.tasks.trello.client;

import com.crud.tasks.domain.TrelloBoardDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
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

    @Value("${trello.api.endpoint.prod}")
    private String trelloApiEndpoint;
    @Value("${trello.app.key}")
    private String trelloAppKey;
    @Value("${trello.app.token}")
    private String trelloToken;
    @Value("${trello.app.username}")
    private String trelloUsername;

    public List<TrelloBoardDto> getTrelloBoards() {
        URI url = getTrelloBoardUri(trelloApiEndpoint, trelloUsername, trelloAppKey, trelloToken);
        TrelloBoardDto[] boardsResponse = restTemplate.getForObject(url, TrelloBoardDto[].class);
        return Optional.ofNullable(boardsResponse)
                .map(Arrays::asList)
                .orElse(Collections.emptyList())
                .stream()
                .filter(board -> board.getId() != null && board.getName() != null)
                .filter(board -> board.getName().contains("Kodilla"))
                .toList();
    }

    private URI getTrelloBoardUri(String endpoint, String username, String key, String token) {
        return UriComponentsBuilder.fromHttpUrl(endpoint + "/members/" + username + "/boards")
                .queryParam("key", key)
                .queryParam("token", token)
                .queryParam("fields", "name,id")
                .build()
                .encode()
                .toUri();
    }
}
