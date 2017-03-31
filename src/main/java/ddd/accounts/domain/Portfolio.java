package ddd.accounts.domain;

import ddd.core.domain.Amount;
import ddd.core.domain.IBAN;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toMap;

public class Portfolio {

    private final Map<IBAN, Account> accounts;
    private final FundsLimits limits;

    public Portfolio(Collection<Account> accounts, FundsLimits limits) {
        this.accounts = accounts.stream().collect(toMap(a -> a.number, a -> a));
        this.limits = limits;
    }

    public LedgerEntry depositFunds(IBAN to, Amount amount) {
        return LedgerEntry.Type.CREDIT.of(amount, account(to).deposit(amount));
    }

    public LedgerEntry withdrawFunds(IBAN from, Amount amount) throws ReservedBudgetExceeded {
        if (amount.isGreaterThan(freeFunds())) {
            throw new ReservedBudgetExceeded();
        }
        return LedgerEntry.Type.DEBIT.of(amount, account(from).withdraw(amount));
    }

    public List<LedgerEntry> transferFunds(IBAN from, IBAN to, Amount amount) {
        return asList(
            LedgerEntry.Type.DEBIT.of(amount, account(from).withdraw(amount)),
            LedgerEntry.Type.CREDIT.of(amount, account(to).deposit(amount))
        );
    }

    private Amount freeFunds() {
        return totalBudget().minus(limits.reservedBudget);
    }

    private Amount totalBudget() {
        return accounts.values().stream().map(a -> a.balance).reduce(Amount.ZERO, Amount::plus);
    }

    private Account account(IBAN number) {
        Account account = accounts.get(number);
        if (account == null) {
            throw new IllegalArgumentException("Account not found: " + number);
        }
        return account;
    }
}
