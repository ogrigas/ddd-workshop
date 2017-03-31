package ddd.accounts;

import ddd.accounts.domain.Account;
import ddd.accounts.domain.AccountRepository;
import ddd.accounts.domain.FundsLimits;
import ddd.accounts.domain.FundsLimitsRepository;
import ddd.accounts.domain.Ledger;
import ddd.accounts.domain.ReservedBudgetExceeded;
import ddd.core.domain.CustomerId;
import org.junit.Test;

import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.mock;

public class PortfolioFundsControllerTest {

    private AccountRepository accountsRepo = mock(AccountRepository.class);
    private FundsLimitsRepository limitsRepo = mock(FundsLimitsRepository.class);
    private Ledger ledger = mock(Ledger.class);

    private PortfolioFundsController controller = new PortfolioFundsController(accountsRepo, limitsRepo, ledger);

    @Test
    public void transfers_funds_between_existing_accounts() {
        givenCustomerAccounts(/* TODO */ 0, null);
        givenCustomerLimits(/* TODO */ 0, null);

        controller.transfer(/* TODO */ 0, "", "", null);

        then(ledger).should().log(/* TODO */ null);
    }

    @Test(expected = ReservedBudgetExceeded.class)
    public void maintains_reserved_budget() {
        givenCustomerAccounts(/* TODO */ 0, null);
        givenCustomerLimits(/* TODO */ 0, null);

        controller.transfer(/* TODO */ 0, "", "", null);

        then(ledger).should().log(/* TODO */ null);
    }

    private void givenCustomerAccounts(int customerId, List<Account> accounts) {
        given(accountsRepo.findByCustomer(new CustomerId(customerId))).willReturn(accounts);
    }

    private void givenCustomerLimits(int customerId, FundsLimits limits) {
        given(limitsRepo.findByCustomer(new CustomerId(customerId))).willReturn(limits);
    }
}
