package ddd.accounts.domain;

import ddd.core.domain.CustomerId;

import java.util.Set;

public class PortfolioLookup {

    public final AccountRepository accountRepository;
    public final FundsLimitsRepository fundsLimitsRepository;

    public PortfolioLookup(AccountRepository accountRepository, FundsLimitsRepository fundsLimitsRepository) {
        this.accountRepository = accountRepository;
        this.fundsLimitsRepository = fundsLimitsRepository;
    }
}
