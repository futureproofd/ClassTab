package to.marcus.classtab.data.local.contract.query;

import to.marcus.classtab.data.local.contract.ClassTabDB;
import to.marcus.classtab.data.local.contract.SQLStatement;

/**
 * Created by marcus on 7/18/2016
 */
public class AllPhotosQuery implements SQLStatement {
    @Override
    public String sqlQuery() {
        return String.format("SELECT * FROM %1s;"
                ,ClassTabDB.PhotoTable.TABLE_NAME
        );
    }
}
