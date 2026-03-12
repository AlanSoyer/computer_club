package dao;

import java.util.List;
import exception.DAOException;

public interface RepositoryDAO<T> {
    Long insert(T entity) throws DAOException;
    void update(T entity) throws DAOException;
    void delete(Long id) throws DAOException;
    T findById(Long id) throws DAOException;
    List<T> findAll() throws DAOException;
}