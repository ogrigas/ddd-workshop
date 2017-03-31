package ddd.core.domain;

import static com.google.common.base.Preconditions.checkArgument;

public class IBAN {
    private final String number;

    public IBAN(String number) {
        checkArgument(number != null, "Missing IBAN number");
        this.number = number;
    }

    public static IBAN generate() {
        return new IBAN("LT009999");
    }

    public static IBAN fromValidString(String number) {
        checkArgument(number != null && number.startsWith("LT"), "Invalid IBAN: %s", number);
        return new IBAN(number);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        return number.equals(((IBAN) o).number);
    }

    @Override
    public int hashCode() {
        return number.hashCode();
    }

    @Override
    public String toString() {
        return number;
    }
}
