package jp.co.soramitsu.iroha.android.sample.core;

import java.time.LocalDate;
import java.util.Objects;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

public final class Tour {

    @Getter
    @Setter
    private int tourNumber;

    @Getter
    @Setter
    private LocalDate tourDate;

    public Tour(final int tourNumber) {
        this.tourNumber = tourNumber;
    }

    public Tour(final int tourNumber,
                @NonNull final LocalDate tourDate) {
        this.tourNumber = tourNumber;
        this.tourDate = tourDate;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), tourNumber, tourDate);
    }

    @Override
    public String toString() {
        return "Tour #" +
                tourNumber +
                " at " +
                tourDate.toString();
    }
}