package com.bbs.handlersystem.Network.Models;

import lombok.Getter;
import lombok.NonNull;

public class TextModel {

    @Getter
    private String message;

    public TextModel(@NonNull final String message) {
        this.message = message;
    }

}
