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

public class PortfolioFundsController {

    public final AccountRepository accountRepo;
    public final FundsLimitsRepository limitsRepo;
    public final Ledger ledger;

    public PortfolioFundsController(AccountRepository accountRepo, FundsLimitsRepository limitsRepo, Ledger ledger) {
        this.accountRepo = accountRepo;
        this.limitsRepo = limitsRepo;
        this.ledger = ledger;
    }

    public void deposit(int customerId, String accountNumber, BigDecimal amount) {
        Portfolio portfolio = loadPortfolio(new CustomerId(customerId));
        LedgerEntry entry = portfolio.depositFunds(new IBAN(accountNumber), Amount.of(amount));
        ledger.log(singletonList(entry));
    }

    public void withdraw(int customerId, String accountNumber, BigDecimal amount) {
        Portfolio portfolio = loadPortfolio(new CustomerId(customerId));
        LedgerEntry entry = portfolio.withdrawFunds(new IBAN(accountNumber), Amount.of(amount));
        ledger.log(singletonList(entry));
    }

    public void transfer(int customerId, String fromAccount, String toAccount, BigDecimal amount) {
        Portfolio portfolio = loadPortfolio(new CustomerId(customerId));
        List<LedgerEntry> entries = portfolio.transferFunds(new IBAN(fromAccount), new IBAN(toAccount), Amount.of(amount));
        ledger.log(entries);
    }

    private Portfolio loadPortfolio(CustomerId customerId) {
        List<Account> accounts = accountRepo.findByCustomer(customerId);
        FundsLimits limits = limitsRepo.findByCustomer(customerId);
        return new Portfolio(accounts, limits);
    }
}
