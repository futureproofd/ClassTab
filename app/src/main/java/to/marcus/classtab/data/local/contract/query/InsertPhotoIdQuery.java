package to.marcus.classtab.data.local.contract.query;

import to.marcus.classtab.data.local.contract.ClassTabDB;
import to.marcus.classtab.data.local.contract.SQLStatement;

/**
 * Created by marcus on 7/15/2016
 */
public class InsertPhotoIdQuery implements SQLStatement {
    @Override
    public String sqlQuery() {
        //// TODO: 7/15/2016 fix statement 
        return String.format("INSERT %1s  FROM %1s;"
                , ClassTabDB.TabTable.TABLE_NAME
        );
    }
}
