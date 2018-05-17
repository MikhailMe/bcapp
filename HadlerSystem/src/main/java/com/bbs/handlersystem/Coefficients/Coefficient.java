package com.bbs.handlersystem.Coefficients;

import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

public final class Coefficient {

    @Getter
    @Setter
    private int coef;

    public Coefficient(int coef) {
        this.coef = coef;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), coef);
    }

    @Override
    public String toString() {
        return String.valueOf(coef);
    }
}
