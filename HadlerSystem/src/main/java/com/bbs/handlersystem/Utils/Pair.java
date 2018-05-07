package com.bbs.handlersystem.Utils;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

public class Pair<First, Second> {

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
        int hash = 3;
        hash = 31 * hash + first.hashCode();
        hash = 31 * hash + second.hashCode();
        return hash;
    }

    @Override
    public String toString() {
        return String.format("%-10s %-5s %s", first.toString(), ":", second.toString());
    }

}
