package service;

import java.sql.SQLException;
import java.util.List;

public interface IService<T> {
    List<T> selectAll();
    void insert(T t) throws SQLException;
    T getById (int id);
    boolean delete (int id) throws SQLException;
    boolean update (T t) throws SQLException;
}
