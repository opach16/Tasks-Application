package com.crud.tasks.domain.createdTrelloCard;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Mail {
    private final String receiverEmail;
    private final String toCc;
    private final String subject;
    private final String message;
}
