package to.marcus.classtab.data.local.contract.query;

import to.marcus.classtab.data.local.contract.ClassTabDB;
import to.marcus.classtab.data.local.contract.SQLQueryStatement;

/**
 * Created by mplienegger on 6/30/2016
 * Limit query to increments of 100 (daily allowance for google custom search API)
 */
public class AllArtistsByLetterIndexQuery implements SQLQueryStatement {
    @Override
    public String sqlQuery(Object params) {
        return String.format("SELECT * FROM %1s WHERE %2s LIKE %3s;",
                ClassTabDB.ArtistTable.TABLE_NAME
                ,ClassTabDB.ArtistTable.COLUMN_ID
                ,"'A%'"
        );
    }

}
