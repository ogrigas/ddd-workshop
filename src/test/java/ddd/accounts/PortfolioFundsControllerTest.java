package ddd.accounts;

import ddd.accounts.domain.Account;
import ddd.accounts.domain.AccountRepository;
import ddd.accounts.domain.FundsLimits;
import ddd.accounts.domain.FundsLimitsRepository;
import ddd.accounts.domain.Ledger;
import ddd.accounts.domain.LedgerEntry;
import ddd.accounts.domain.ReservedBudgetExceeded;
import ddd.core.domain.Amount;
import ddd.core.domain.CustomerId;
import ddd.core.domain.IBAN;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.List;

import static ddd.accounts.domain.LedgerEntry.Type.CREDIT;
import static ddd.accounts.domain.LedgerEntry.Type.DEBIT;
import static java.util.Arrays.asList;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.mock;

/**
 * Example of interaction testing - these tests mock dependent services/repositories and verify interactions with them.
 *
 * Most tests will have a Given-When-Then structure:
 * - GIVEN zero or more preconditions
 * - WHEN a single action is performed
 * - THEN we assert one or more expectations
 */
public class PortfolioFundsControllerTest {

    private AccountRepository accountsRepo = mock(AccountRepository.class);
    private FundsLimitsRepository limitsRepo = mock(FundsLimitsRepository.class);
    private Ledger ledger = mock(Ledger.class);

    private PortfolioFundsController controller = new PortfolioFundsController(accountsRepo, limitsRepo, ledger);

    @Test
    public void transfers_funds_between_existing_accounts() {
        givenCustomerAccounts(55, asList(
            new Account(new IBAN("LT01"), Amount.of(1000)),
            new Account(new IBAN("LT02"), Amount.of(2000)),
            new Account(new IBAN("LT03"), Amount.of(3000))
        ));
        givenReservedBudget(55, Amount.ZERO);

        controller.transfer(55, "LT03", "LT01", BigDecimal.valueOf(100));

        then(ledger).should().log(asList(
            new LedgerEntry(DEBIT,  Amount.of(100), new Account(new IBAN("LT03"), Amount.of(2900))),
            new LedgerEntry(CREDIT, Amount.of(100), new Account(new IBAN("LT01"), Amount.of(1100)))
        ));
    }

    // in this case the THEN part is the expected exception
    @Test(expected = ReservedBudgetExceeded.class)
    public void maintains_reserved_budget() {
        givenCustomerAccounts(55, asList(
            new Account(new IBAN("LT01"), Amount.of(1000)),
            new Account(new IBAN("LT02"), Amount.of(2000))
        ));
        givenReservedBudget(55, Amount.of(2500));

        controller.withdraw(55, "LT02", BigDecimal.valueOf(501));
    }

    // Below are some helper methods: sometimes it's useful to have these to reduce noise in tests

    private void givenCustomerAccounts(int customerId, List<Account> accounts) {
        given(accountsRepo.findByCustomer(new CustomerId(customerId)))
            .willReturn(accounts);
    }

    private void givenReservedBudget(int customerId, Amount reservedBudget) {
        given(limitsRepo.findByCustomer(new CustomerId(customerId)))
            .willReturn(new FundsLimits(reservedBudget));
    }
}
