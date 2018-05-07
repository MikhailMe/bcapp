package com.bbs.handlersystem.Network.Message;

import lombok.Getter;
import lombok.Setter;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.Objects;

public class AckMessage extends Message {

    @Getter
    @Setter
    private String confirmation;

    public AckMessage(String confirmation){
        super(MessageType.MSG_ACK);
        this.confirmation = confirmation;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other)
            return true;
        if (!(other instanceof AckMessage))
            return false;
        if (!super.equals(other))
            return false;
        AckMessage otherMessage = (AckMessage) other;
        return Objects.equals(confirmation, otherMessage.confirmation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), confirmation);
    }

    @Override
    public String toString() {
        return confirmation;
    }


    @Override
    public void writeExternal(ObjectOutput objectOutput) throws IOException {
        super.writeExternal(objectOutput);
        objectOutput.write(confirmation.getBytes());
    }

    @Override
    public void readExternal(ObjectInput objectInput) throws IOException {
        super.readExternal(objectInput);
        confirmation = objectInput.readLine();
    }
}
