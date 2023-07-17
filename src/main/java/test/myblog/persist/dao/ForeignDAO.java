package test.myblog.persist.dao;

import java.util.List;

public interface ForeignDAO<T, P> extends DAO<T, P> {
	public List<P> findByForeginRef(T... refIDs) throws DAOException;
	public List<P> findIDsByForeginRef(T refID) throws DAOException;
}
