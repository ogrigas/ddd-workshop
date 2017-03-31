package ddd.accounts.domain;

import ddd.core.domain.Amount;
import ddd.core.domain.IBAN;

import java.util.Objects;

import static com.google.common.base.Preconditions.checkArgument;

public class Account {
    public final IBAN number;
    public final Amount balance;

    public Account(IBAN number, Amount balance) {
        this.number = number;
        this.balance = balance;
    }

    public static Account open(IBAN number) {
        return new Account(number, Amount.ZERO);
    }

    public Account deposit(Amount amount) {
        checkArgument(!amount.isNegative(), "Cannot deposit negative amount: %s", amount);
        Amount newBalance = balance.plus(amount);
        return new Account(number, newBalance);
    }

    public Account withdraw(Amount amount) {
        checkArgument(!amount.isNegative(), "Cannot withdraw negative amount: %s", amount);
        Amount newBalance = balance.minus(amount);
        if (newBalance.isNegative()) {
            throw new InsufficientAccountFunds();
        }
        return new Account(number, newBalance);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return Objects.equals(number, account.number)
            && Objects.equals(balance, account.balance);
    }

    @Override
    public int hashCode() {
        return Objects.hash(number, balance);
    }

    @Override
    public String toString() {
        return "Account{" + number + ", balance=" + balance + "}";
    }
}
