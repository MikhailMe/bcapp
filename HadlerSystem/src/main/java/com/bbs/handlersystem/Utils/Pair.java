package com.bbs.handlersystem.Utils;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.util.Objects;

public final class Pair<First, Second> {

    @Getter
    @Setter
    @NonNull
    private First first;

    @Getter
    @Setter
    @NonNull
    private Second second;

    public Pair(@NonNull final First first,
                @NonNull final Second second) {
        this.first = first;
        this.second = second;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), first, second);
    }

    @Override
    public String toString() {
        return String.format("%-10s %-5s %s", first.toString(), ":", second.toString());
    }

}
