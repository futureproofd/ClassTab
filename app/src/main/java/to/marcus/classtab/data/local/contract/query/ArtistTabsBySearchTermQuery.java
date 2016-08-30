package to.marcus.classtab.data.local.contract.query;

import to.marcus.classtab.data.local.contract.ClassTabDB;
import to.marcus.classtab.data.local.contract.SQLQueryStatement;

/**
 * Created by mplienegger on 8/29/2016
 */
public class ArtistTabsBySearchTermQuery implements SQLQueryStatement {
    @Override
    public String sqlQuery(Object params) {
        return String.format("SELECT * FROM %1s WHERE substr(%2s,1,3) = "+
                "(SELECT substr("+ ClassTabDB.ArtistTable.COLUMN_ID +",1,3) FROM " + ClassTabDB.ArtistTable.TABLE_NAME +" WHERE " +
                ClassTabDB.ArtistTable.COLUMN_NAME+" LIKE %3s);"
                , ClassTabDB.TabTable.TABLE_NAME
                , ClassTabDB.TabTable.COLUMN_ID
                ,"'%"+params+"%'"
        );
    }
}
