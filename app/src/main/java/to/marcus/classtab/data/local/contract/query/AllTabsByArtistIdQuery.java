package to.marcus.classtab.data.local.contract.query;

import to.marcus.classtab.data.local.contract.ClassTabDB;
import to.marcus.classtab.data.local.contract.SQLStatement;

/**
 * Created by mplienegger on 7/19/2016
 */
public class AllTabsByArtistIdQuery implements SQLStatement {
    @Override
    public String sqlQuery(String params) {
        return String.format("SELECT * FROM %1s WHERE %2s LIKE %3s;"
                ,ClassTabDB.TabTable.TABLE_NAME
                ,ClassTabDB.TabTable.COLUMN_ID
                ,"'"+params+"%'"
        );
    }
}
