package ddd.accounts.domain;

import ddd.core.domain.Amount;

import java.util.Objects;

public class LedgerEntry {

    public enum Type {
        CREDIT,
        DEBIT;

        public LedgerEntry of(Amount amount, Account account) {
            return new LedgerEntry(this, amount, account);
        }
    }

    public final Type type;
    public final Amount amount;
    public final Account account;

    public LedgerEntry(Type type, Amount amount, Account account) {
        this.type = type;
        this.amount = amount;
        this.account = account;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LedgerEntry that = (LedgerEntry) o;
        return type == that.type
            && Objects.equals(amount, that.amount)
            && Objects.equals(account, that.account);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, amount, account);
    }

    @Override
    public String toString() {
        return type + "{" + amount + ", " + account + "}";
    }
}
