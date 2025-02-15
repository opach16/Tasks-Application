package com.crud.tasks.validator;

import com.crud.tasks.domain.TrelloBoard;
import com.crud.tasks.domain.TrelloCard;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TrelloValidator {

    private static final Logger LOGGER = LoggerFactory.getLogger(TrelloValidator.class);

    public void validateCard(final TrelloCard trelloCard) {
        if (trelloCard.getName().contains("test")) {
            LOGGER.info("Someone is testing my application!");
        } else {
            LOGGER.info("Seems that my application is used in proper way");
        }
    }

    public List<TrelloBoard> validateTrelloBoards(final List<TrelloBoard> trelloBoards) {
        LOGGER.info("Starting filtering boards..");
        List<TrelloBoard> fillterdBoards = trelloBoards.stream()
                .filter(board -> !board.getName().equalsIgnoreCase("test"))
                .toList();
        LOGGER.info("Boards have been filtered. Current list size: " + trelloBoards.size());
        return fillterdBoards;
    }
}
