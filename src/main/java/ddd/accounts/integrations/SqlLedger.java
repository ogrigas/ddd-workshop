package ddd.accounts.integrations;

import ddd.accounts.domain.Ledger;
import ddd.accounts.domain.LedgerEntry;

import java.util.List;

public class SqlLedger implements Ledger {

    @Override
    public void log(List<LedgerEntry> entries) {
        // use SQL / Hibernate / whatever
    }
}
