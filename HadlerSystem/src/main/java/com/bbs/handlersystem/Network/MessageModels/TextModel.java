package com.bbs.handlersystem.Network.MessageModels;

import lombok.Getter;
import lombok.NonNull;

public class TextModel extends MessageModel{

    @Getter
    @NonNull
    private String message;

    public TextModel(@NonNull final String message) {
        this.message = message;
    }



}
