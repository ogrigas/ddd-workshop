package ddd.accounts.domain;

import ddd.core.domain.Amount;
import ddd.core.domain.IBAN;
import org.junit.Test;

import java.util.List;

import static ddd.accounts.domain.LedgerEntry.Type.CREDIT;
import static ddd.accounts.domain.LedgerEntry.Type.DEBIT;
import static java.util.Arrays.asList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;

/**
 * Example of testing pure domain logic - these are simple state-based tests, i.e. they don't mock anything.
 *
 * Most tests will have a Given-When-Then structure:
 * - GIVEN zero or more preconditions
 * - WHEN a single action is performed
 * - THEN we assert one or more expectations
 *
 * In this case the GIVEN part is common to all test cases and is therefore moved to declaration of fields.
 */
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

        assertThat(entries, contains(
            new LedgerEntry(DEBIT,  Amount.of(100), new Account(new IBAN("LT03"), Amount.of(2900))),
            new LedgerEntry(CREDIT, Amount.of(100), new Account(new IBAN("LT01"), Amount.of(1100)))
        ));
    }

    // in this case the THEN part is the expected exception
    @Test(expected = ReservedBudgetExceeded.class)
    public void maintains_reserved_budget() {
        portfolio.withdrawFunds(new IBAN("LT03"), Amount.of(501));
    }
}
