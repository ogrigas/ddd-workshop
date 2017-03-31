package ddd.accounts.integrations;

import ddd.accounts.domain.FundsLimits;
import ddd.accounts.domain.FundsLimitsRepository;
import ddd.core.domain.CustomerId;

public class SqlFundsLimitsRepository implements FundsLimitsRepository {

    @Override
    public FundsLimits findByCustomer(CustomerId id) {
        // use SQL / Hibernate / whatever
        return null;
    }
}
