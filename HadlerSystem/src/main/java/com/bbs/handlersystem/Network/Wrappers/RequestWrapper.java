package com.bbs.handlersystem.Network.Helpers;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.util.Objects;

public final class RequestWrapper {

    @Getter
    @Setter
    @NonNull
    private String request;

    public RequestWrapper(@NonNull final String request) {
        this.request = request;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), request);
    }

    @Override
    public String toString() {
        return request;
    }

}
