package ddd.accounts.domain;

import ddd.core.domain.Amount;
import ddd.core.domain.IBAN;
import org.junit.Test;

import java.util.List;

import static ddd.accounts.domain.LedgerEntry.Type.CREDIT;
import static ddd.accounts.domain.LedgerEntry.Type.DEBIT;
import static java.util.Arrays.asList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.hasSize;

public class PortfolioTest {

    private List<Account> accounts = asList(
        new Account(new IBAN("LT01"), Amount.of(1000)),
        new Account(new IBAN("LT02"), Amount.of(2000)),
        new Account(new IBAN("LT03"), Amount.of(3000))
    );

    private Portfolio portfolio = new Portfolio(accounts, new FundsLimits(Amount.of(5500)));

    @Test
    public void transfers_funds_between_existing_accounts() {
        List<LedgerEntry> entries = portfolio.transferFunds(new IBAN("LT03"), new IBAN("LT01"), Amount.of(100));

        assertThat(entries, hasSize(2));
        assertThat(entries, hasItems(
            new LedgerEntry(DEBIT,  Amount.of(100), new Account(new IBAN("LT03"), Amount.of(2900))),
            new LedgerEntry(CREDIT, Amount.of(100), new Account(new IBAN("LT01"), Amount.of(1100)))
        ));
    }

    @Test(expected = ReservedBudgetExceeded.class)
    public void maintains_reserved_budget() {
        portfolio.withdrawFunds(new IBAN("LT03"), Amount.of(501));
    }
}
