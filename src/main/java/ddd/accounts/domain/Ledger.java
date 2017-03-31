package ddd.accounts.domain;

import java.util.List;

public interface Ledger {

    void log(List<LedgerEntry> entries);
}
