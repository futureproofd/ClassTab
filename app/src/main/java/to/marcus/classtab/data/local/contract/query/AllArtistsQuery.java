package to.marcus.classtab.data.local.contract.query;

import to.marcus.classtab.data.local.contract.ClassTabDB;
import to.marcus.classtab.data.local.contract.SQLQueryStatement;

/**
 * Created by marcus on 6/29/2016
 */
public class AllArtistsQuery implements SQLQueryStatement {
    @Override
    public String sqlQuery(Object params) {
        return String.format("SELECT * FROM %1s ORDER BY id;"
                ,ClassTabDB.ArtistTable.TABLE_NAME
        );
    }
}
