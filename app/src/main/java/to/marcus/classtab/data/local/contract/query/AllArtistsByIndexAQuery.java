package to.marcus.classtab.data.local.contract.query;

import to.marcus.classtab.data.local.contract.ClassTabDB;
import to.marcus.classtab.data.local.contract.SQLStatement;

/**
 * Created by mplienegger on 6/30/2016.
 */
public class AllArtistsByIndexAQuery implements SQLStatement {
    @Override
    public String sqlQuery(String params) {
        return String.format("SELECT * FROM %1s WHERE %2s LIKE %3s;",
                ClassTabDB.ArtistTable.TABLE_NAME
                ,ClassTabDB.ArtistTable.COLUMN_ID
                ,"'A%'"
        );
    }

}
