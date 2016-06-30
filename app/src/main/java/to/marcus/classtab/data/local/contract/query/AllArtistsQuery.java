package to.marcus.classtab.data.local.contract.query;

import to.marcus.classtab.data.local.contract.ClassTabDB;
import to.marcus.classtab.data.local.contract.SQLStatement;

/**
 * Created by marcus on 6/29/2016
 */
public class AllArtistsQuery implements SQLStatement {
    @Override
    public String sqlQuery() {
        return String.format("SELECT * FROM %1s;"
                ,ClassTabDB.ArtistTable.TABLE_NAME
        );
    }
}
