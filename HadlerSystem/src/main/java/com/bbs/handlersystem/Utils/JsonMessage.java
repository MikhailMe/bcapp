package com.bbs.handlersystem.Utils;

import com.bbs.handlersystem.Network.Message.MessageType;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.util.Objects;

public final class JsonMessage {

    @Getter
    @Setter
    @NonNull
    private Object data;

    @Getter
    @Setter
    @NonNull
    private MessageType type;


    public JsonMessage(@NonNull final Object data,
                       @NonNull final MessageType type) {
        this.data = data;
        this.type = type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), data, type);
    }

    @Override
    public String toString() {
        return "data = " + data.toString() + " | type = " + type;
    }
}
