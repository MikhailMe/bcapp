package com.bbs.handlersystem.Network.Message;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.NonNull;

import java.util.Objects;

public final class JsonMessage<T> extends Message<T> {

    @NonNull
    private static Gson builder;

    static {
        builder = new GsonBuilder().setPrettyPrinting().create();
    }

    public JsonMessage(@NonNull final T data,
                       @NonNull final MessageType type) {
        super(data, type);
    }

    @NonNull
    public String toJson() {
        return new GsonBuilder().setPrettyPrinting().create().toJson(this);
    }

    // TODO: why i exist
    public String fromJson() {
        return builder.fromJson(getData().toString(), this.getClass().getGenericSuperclass());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getData(), getType());
    }

    @Override
    public String toString() {
        return "data = " + getData().toString() + " | type = " + getType();
    }

}
