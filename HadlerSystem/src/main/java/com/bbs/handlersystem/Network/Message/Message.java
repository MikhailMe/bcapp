package com.bbs.handlersystem.Network.Message;

import lombok.Getter;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.Locale;
import java.util.Objects;

public abstract class Message implements Externalizable {

    @Getter
    private long id;

    @Getter
    private MessageType type;

    private static long idCounter = 0;

    public Message() {
    }

    protected Message(MessageType type) {
        this(idCounter++, type);
    }

    protected Message(long id, MessageType type) {
        this.id = id;
        this.type = type;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other)
            return true;
        if (!(other instanceof Message))
            return false;
        Message message = (Message) other;
        return Objects.equals(id, message.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), id);
    }

    @Override
    public String toString() {
        return String.format(Locale.ENGLISH, "type=%s, id=%d", type, id);
    }

    @Override
    public void writeExternal(ObjectOutput objectOutput) throws IOException {
        objectOutput.writeLong(id);
        objectOutput.writeByte(type.ordinal());
    }

    @Override
    public void readExternal(ObjectInput objectInput) throws IOException {
        id = objectInput.readLong();
        type = MessageType.values()[objectInput.readByte()];
    }


}
