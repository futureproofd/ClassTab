package to.marcus.classtab.data.local.contract.query;

import to.marcus.classtab.data.local.contract.ClassTabDB;
import to.marcus.classtab.data.local.contract.SQLQueryStatement;

/**
 * Created by mplienegger on 7/18/2016
 * Returns only artists with photos
 */
public class AllArtistsWithPhotosQuery implements SQLQueryStatement {
    @Override
    public String sqlQuery(Object params) {
        return String.format("SELECT A.*, P.* FROM %1s A INNER JOIN %2s P ON A."
                +ClassTabDB.ArtistTable.COLUMN_ID+"=P."+ClassTabDB.PhotoTable.COLUMN_ID
                +" WHERE A.%3s = P.%4s ORDER BY "+ClassTabDB.ArtistTable.COLUMN_ID+";"
                ,ClassTabDB.ArtistTable.TABLE_NAME
                ,ClassTabDB.PhotoTable.TABLE_NAME
                ,ClassTabDB.ArtistTable.COLUMN_ID
                ,ClassTabDB.PhotoTable.COLUMN_ID
        );
    }
}
