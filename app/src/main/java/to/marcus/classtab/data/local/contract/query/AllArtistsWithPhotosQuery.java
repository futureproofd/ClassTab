package to.marcus.classtab.data.local.contract.query;

import to.marcus.classtab.data.local.contract.ClassTabDB;
import to.marcus.classtab.data.local.contract.SQLStatement;

/**
 * Created by mplienegger on 7/18/2016
 */
public class AllArtistsWithPhotosQuery implements SQLStatement {
    @Override
    public String sqlQuery(String params) {
        return String.format("SELECT A.*, P.* FROM %1s A INNER JOIN %2s P ON A."
                +ClassTabDB.ArtistTable.COLUMN_ID+"=P."+ClassTabDB.PhotoTable.COLUMN_ID
                +" WHERE A.%3s = P.%4s AND A."+ClassTabDB.ArtistTable.COLUMN_ID+" LIKE %5s;"
                ,ClassTabDB.ArtistTable.TABLE_NAME
                ,ClassTabDB.PhotoTable.TABLE_NAME
                ,ClassTabDB.ArtistTable.COLUMN_ID
                ,ClassTabDB.PhotoTable.COLUMN_ID
                ,"'A%'"
        );
    }
}
