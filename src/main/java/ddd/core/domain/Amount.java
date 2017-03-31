package ddd.core.domain;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static com.google.common.base.Preconditions.checkNotNull;

public class Amount {

    public static final Amount ZERO = Amount.of(0);

    public final BigDecimal value;

    private Amount(BigDecimal value) {
        this.value = checkNotNull(value, "amount").setScale(2, RoundingMode.UP);
    }

    public static Amount of(BigDecimal value) {
        return new Amount(value);
    }

    public static Amount of(double value) {
        return of(BigDecimal.valueOf(value));
    }

    public Amount plus(Amount amount) {
        return new Amount(value.add(amount.value));
    }

    public Amount minus(Amount amount) {
        return new Amount(value.subtract(amount.value));
    }

    public boolean isNegative() {
        return value.compareTo(BigDecimal.ZERO) < 0;
    }

    public boolean isLessThan(Amount other) {
        return value.compareTo(other.value) < 0;
    }

    public boolean isGreaterThan(Amount other) {
        return value.compareTo(other.value) > 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        return value.compareTo(((Amount) o).value) == 0;
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
