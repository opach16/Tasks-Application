package com.crud.tasks.domain.createdTrelloCard;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Trello {
    @JsonProperty("board")
    private int board;
    @JsonProperty("card")
    private int card;
}
