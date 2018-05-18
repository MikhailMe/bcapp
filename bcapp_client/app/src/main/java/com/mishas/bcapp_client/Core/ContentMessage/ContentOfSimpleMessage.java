package com.mishas.bcapp_client.Core.ContentMessage;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.util.Objects;

public final class ContentOfSimpleMessage {

    @Getter
    @Setter
    @NonNull
    private String text;

    public ContentOfSimpleMessage(@NonNull final String text) {
        this.text = text;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), text);
    }

    @Override
    public String toString() {
        return text;
    }

}
