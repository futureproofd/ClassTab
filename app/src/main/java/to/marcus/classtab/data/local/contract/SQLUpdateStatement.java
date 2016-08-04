package to.marcus.classtab.data.local.contract;

/**
 * Created by mplienegger on 8/4/2016.
 */
public interface SQLUpdateStatement<P,V> {
    String sqlQuery(P params,V where);
}