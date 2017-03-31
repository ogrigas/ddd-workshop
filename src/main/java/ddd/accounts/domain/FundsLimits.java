package ddd.accounts.domain;

import ddd.core.domain.Amount;

import java.util.Objects;

public class FundsLimits {
    public final Amount reservedBudget;

    public FundsLimits(Amount reservedBudget) {
        this.reservedBudget = reservedBudget;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FundsLimits that = (FundsLimits) o;
        return Objects.equals(reservedBudget, that.reservedBudget);
    }

    @Override
    public int hashCode() {
        return Objects.hash(reservedBudget);
    }

    @Override
    public String toString() {
        return "FundsLimits{" +
            "reservedBudget=" + reservedBudget +
            '}';
    }
}
