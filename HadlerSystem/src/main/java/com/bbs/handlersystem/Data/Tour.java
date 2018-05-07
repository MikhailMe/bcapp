package com.bbs.handlersystem.Data;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.time.LocalDate;

public class Tour {

    @Getter
    @Setter
    private int tourNumber;

    @Getter
    @Setter
    private LocalDate tourDate;

    public Tour(int tourNumber) {
        this.tourNumber = tourNumber;
    }

    public Tour(int tourNumber,
                @NonNull LocalDate tourDate) {
        this.tourNumber = tourNumber;
        this.tourDate = tourDate;
    }

    @Override
    public String toString() {
        return "Tour #" +
                tourNumber +
                " at " +
                tourDate.toString();
    }
}
