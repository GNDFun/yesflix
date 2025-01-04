package app.java.yesflix.service;

import java.util.List;

public interface AbstractService<T> {
    T getByPK(Long id);
    List<T> getAll();
    T create (T object);
    T update(T object);
    Integer delete(Long id);
}