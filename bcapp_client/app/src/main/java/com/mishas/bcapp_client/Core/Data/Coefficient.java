package com.mishas.bcapp_client.Core.Data;

import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

public final class Coefficient {

    @Getter
    @Setter
    private float coef;

    public Coefficient() {
        this.coef = -1.0f;
    }

    public Coefficient(final float coef) {
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
