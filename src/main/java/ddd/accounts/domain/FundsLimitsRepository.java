package ddd.accounts.domain;

import ddd.core.domain.CustomerId;

public interface FundsLimitsRepository {

    FundsLimits findByCustomer(CustomerId id);
}
