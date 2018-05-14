package com.bbs.handlersystem.Network.client;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.util.Objects;

public class Request {

    @Getter
    @Setter
    @NonNull
    private String request;

    Request(String request) {
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
