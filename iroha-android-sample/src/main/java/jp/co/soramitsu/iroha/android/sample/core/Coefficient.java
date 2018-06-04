package jp.co.soramitsu.iroha.android.sample.core;

import java.util.Objects;

import lombok.Getter;
import lombok.Setter;

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