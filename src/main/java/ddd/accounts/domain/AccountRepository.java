package ddd.accounts.domain;

import ddd.core.domain.CustomerId;

import java.util.List;

public interface AccountRepository {

    List<Account> findByCustomer(CustomerId id);
}
