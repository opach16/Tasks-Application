package com.crud.tasks.domain.createdTrelloCard;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class Mail {
    private final String receiverEmail;
    private final String subject;
    private final String message;
}
