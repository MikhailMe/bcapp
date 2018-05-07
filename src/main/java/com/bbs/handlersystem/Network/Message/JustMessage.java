package com.bbs.handlersystem.Network.Message;

import lombok.Getter;
import lombok.Setter;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.Objects;

public class JustMessage extends Message {

    @Getter
    @Setter
    private String message;

    public JustMessage(String message) {
        super(MessageType.MSG_ACK);
        this.message = message;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other)
            return true;
        if (!(other instanceof JustMessage))
            return false;
        if (!super.equals(other))
            return false;
        JustMessage otherMessage = (JustMessage) other;
        return Objects.equals(message, otherMessage.message);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), message);
    }

    @Override
    public String toString() {
        return message;
    }


    @Override
    public void writeExternal(ObjectOutput objectOutput) throws IOException {
        super.writeExternal(objectOutput);
        objectOutput.write(message.getBytes());
    }

    @Override
    public void readExternal(ObjectInput objectInput) throws IOException {
        super.readExternal(objectInput);
        message = objectInput.readLine();
    }
}
