package to.marcus.classtab.data.local.contract;

/**
 * Created by marcus on 6/29/2016
 */
public class AllArtistsSQLStatement implements SQLStatement {
    @Override
    public String sqlQuery() {
        return String.format("SELECT * FROM %1s;",
                ClassTabDB.ArtistTable.TABLE_NAME
        );
    }
}
