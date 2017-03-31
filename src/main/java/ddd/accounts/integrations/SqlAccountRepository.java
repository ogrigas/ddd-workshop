package ddd.accounts.integrations;

import ddd.accounts.domain.Account;
import ddd.accounts.domain.AccountRepository;
import ddd.core.domain.CustomerId;

import java.util.List;

public class SqlAccountRepository implements AccountRepository {

    @Override
    public List<Account> findByCustomer(CustomerId id) {
        // use SQL / Hibernate / whatever
        return null;
    }
}
