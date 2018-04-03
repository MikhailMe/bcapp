package Utils;

import org.jetbrains.annotations.NotNull;

public class Pair<First, Second> {

    @NotNull
    private First first;
    @NotNull
    private Second second;

    public Pair(@NotNull final First first,
                @NotNull final Second second) {
        this.first = first;
        this.second = second;
    }

    @NotNull
    public First getFirst() {
        return first;
    }

    public void setFirst(@NotNull First first) {
        this.first = first;
    }

    @NotNull
    public Second getSecond() {
        return second;
    }

    public void setSecond(@NotNull Second second) {
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
        return first.toString() + " : " + second.toString();
    }

}
