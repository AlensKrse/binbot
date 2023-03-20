package com.binance.crypto.bot.callback.telegram.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@EqualsAndHashCode
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TelegramBotResponseResult {


    @JsonProperty("message_id")
    Long messageId;

    @JsonProperty("sender_chat")
    TelegramBotResponseChat senderChat;

    TelegramBotResponseChat chat;
    Long date;
    String text;

}
