package to.marcus.classtab.data.local.contract;

/**
 * Created by marcus on 6/29/2016
 */
public interface SQLStatement<T> {
    String sqlQuery(String params);
}
