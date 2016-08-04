package to.marcus.classtab.data.local.contract.query;

import to.marcus.classtab.data.local.contract.ClassTabDB;
import to.marcus.classtab.data.local.contract.SQLQueryStatement;

/**
 * Created by marcus on 7/4/2016
 */
public class AllTabsQuery implements SQLQueryStatement {
    @Override
    public String sqlQuery(Object params) {
        return String.format("SELECT * FROM %1s;"
                ,ClassTabDB.TabTable.TABLE_NAME
        );
    }
}
