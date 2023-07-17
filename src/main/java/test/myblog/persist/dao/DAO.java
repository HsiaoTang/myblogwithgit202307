package test.myblog.persist.dao;

import java.util.List;

public interface DAO<T, P> {
	T findOne(P p) throws DAOException;
	List<T> findAll() throws DAOException;
	T create(T t) throws DAOException;
	void update(T t) throws DAOException;
	void delete(T t) throws DAOException;
}
