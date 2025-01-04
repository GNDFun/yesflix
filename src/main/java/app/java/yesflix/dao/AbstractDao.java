package app.java.yesflix.dao;

import java.util.List;

public interface AbstractDao<T> {
    T getByPK(Long id);
    List<T> getAll();
    T create (T object);
    T update(T object);
    Integer delete(Long id);
}