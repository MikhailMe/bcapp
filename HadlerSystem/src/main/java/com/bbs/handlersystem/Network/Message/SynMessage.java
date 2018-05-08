package com.bbs.handlersystem.Network.Message;

import lombok.Getter;
import lombok.Setter;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.Objects;

public class SynMessage extends Message {

    @Getter
    @Setter
    private String request;

    public SynMessage(String response) {
        super(MessageType.MSG_GET_LIST_GAMES_SYN);
        this.request = response;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other)
            return true;
        if (!(other instanceof SynMessage))
            return false;
        if (!super.equals(other))
            return false;
        SynMessage otherMessage = (SynMessage) other;
        return Objects.equals(request, otherMessage.request);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), request);
    }

    @Override
    public String toString() {
        return request;
    }


    @Override
    public void writeExternal(ObjectOutput objectOutput) throws IOException {
        super.writeExternal(objectOutput);
        objectOutput.write(request.getBytes());
    }

    @Override
    public void readExternal(ObjectInput objectInput) throws IOException {
        super.readExternal(objectInput);
        request = objectInput.readLine();
    }

}
