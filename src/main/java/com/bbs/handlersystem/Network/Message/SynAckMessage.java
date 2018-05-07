package com.bbs.handlersystem.Network.Message;

import lombok.Getter;
import lombok.Setter;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.Objects;

public class SynAckMessage extends Message{

    @Getter
    @Setter
    private String response;

    public SynAckMessage(String response) {
        super(MessageType.MSG_SYN_ACK);
        this.response = response;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other)
            return true;
        if (!(other instanceof SynAckMessage))
            return false;
        if (!super.equals(other))
            return false;
        SynAckMessage otherMessage = (SynAckMessage) other;
        return Objects.equals(response, otherMessage.response);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), response);
    }

    @Override
    public String toString() {
        return response;
    }


    @Override
    public void writeExternal(ObjectOutput objectOutput) throws IOException {
        super.writeExternal(objectOutput);
        objectOutput.write(response.getBytes());
    }

    @Override
    public void readExternal(ObjectInput objectInput) throws IOException {
        super.readExternal(objectInput);
        response = objectInput.readLine();
    }
}
