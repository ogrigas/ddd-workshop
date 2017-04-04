package ddd;

import ddd.accounts.PortfolioFundsController;
import ddd.accounts.integrations.SqlAccountRepository;
import ddd.accounts.integrations.SqlFundsLimitsRepository;
import ddd.accounts.integrations.SqlLedger;

public class EntryPoint {

    public static void main(String[] args) throws Exception {
        SqlAccountRepository accountRepo = new SqlAccountRepository();
        SqlFundsLimitsRepository limitsRepo = new SqlFundsLimitsRepository();
        SqlLedger ledger = new SqlLedger();

        PortfolioFundsController controller = new PortfolioFundsController(accountRepo, limitsRepo, ledger);

        System.out.println("TODO: start web server at port 8080 and route requests to controller");
    }
}
