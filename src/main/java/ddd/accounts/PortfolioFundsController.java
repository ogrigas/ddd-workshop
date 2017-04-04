package ddd.accounts;

import ddd.accounts.domain.Account;
import ddd.accounts.domain.AccountRepository;
import ddd.accounts.domain.FundsLimits;
import ddd.accounts.domain.FundsLimitsRepository;
import ddd.accounts.domain.Ledger;
import ddd.accounts.domain.LedgerEntry;
import ddd.accounts.domain.Portfolio;
import ddd.core.domain.Amount;
import ddd.core.domain.CustomerId;
import ddd.core.domain.IBAN;

import java.math.BigDecimal;
import java.util.List;

import static java.util.Collections.singletonList;

/**
 * Controllers fulfil the role of Application Services in DDD.
 * To keep the domain pure, these coordinate invocation of domain logic with data retrieval and result persistence.
 * Application Services may operate with raw input/output data
 * or you may have additional layer of Routers/Mappers that convert raw data to/from domain Value Objects.
 */
public class PortfolioFundsController {

    private final AccountRepository accountRepo;
    private final FundsLimitsRepository limitsRepo;
    private final Ledger ledger;

    public PortfolioFundsController(AccountRepository accountRepo, FundsLimitsRepository limitsRepo, Ledger ledger) {
        this.accountRepo = accountRepo;
        this.limitsRepo = limitsRepo;
        this.ledger = ledger;
    }

    public void deposit(int customerId, String accountNumber, BigDecimal amount) {
        // retrieve data
        Portfolio portfolio = loadPortfolio(new CustomerId(customerId));
        // invoke domain logic
        LedgerEntry entry = portfolio.depositFunds(new IBAN(accountNumber), Amount.of(amount));
        // persist results
        ledger.log(singletonList(entry));
    }

    public void withdraw(int customerId, String accountNumber, BigDecimal amount) {
        // retrieve data
        Portfolio portfolio = loadPortfolio(new CustomerId(customerId));
        // invoke domain logic
        LedgerEntry entry = portfolio.withdrawFunds(new IBAN(accountNumber), Amount.of(amount));
        // persist results
        ledger.log(singletonList(entry));
    }

    public void transfer(int customerId, String fromAccount, String toAccount, BigDecimal amount) {
        // retrieve data
        Portfolio portfolio = loadPortfolio(new CustomerId(customerId));
        // invoke domain logic
        List<LedgerEntry> entries = portfolio.transferFunds(new IBAN(fromAccount), new IBAN(toAccount), Amount.of(amount));
        // persist results
        ledger.log(entries);
    }

    private Portfolio loadPortfolio(CustomerId customerId) {
        List<Account> accounts = accountRepo.findByCustomer(customerId);
        FundsLimits limits = limitsRepo.findByCustomer(customerId);
        return new Portfolio(accounts, limits);
    }
}
