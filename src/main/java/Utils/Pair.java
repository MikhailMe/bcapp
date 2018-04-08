package Utils;

import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

public class Pair<First, Second> {

    @Getter
    @Setter
    @NotNull
    private First first;

    @Getter
    @Setter
    @NotNull
    private Second second;

    public Pair(@NotNull final First first,
                @NotNull final Second second) {
        this.first = first;
        this.second = second;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 19 * hash + first.hashCode();
        hash = 19 * hash + second.hashCode();
        return hash;
    }

    @Override
    public String toString() {
        return String.format("%-10s %-5s %s", first.toString(), ":", second.toString());
    }

}
