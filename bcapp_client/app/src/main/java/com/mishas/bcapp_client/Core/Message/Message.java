package com.mishas.bcapp_client.Core.Message;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.Locale;
import java.util.Objects;

public abstract class Message<T> implements Externalizable {

    @Getter
    private long id;

    @Getter
    @Setter
    @NonNull
    private T data;

    @Getter
    @Setter
    @NonNull
    private MessageType type;

    private static long idCounter = 0;

    Message(@NonNull final T data,
            @NonNull final MessageType type) {
        this(idCounter++, data, type);
    }

    private Message(long id,
                    @NonNull T data,
                    @NonNull MessageType type) {
        this.id = id;
        this.data = data;
        this.type = type;
    }

    @Override
    public boolean equals(@NonNull Object other) {
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
