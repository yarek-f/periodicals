package ua.dao;

import java.util.List;

public interface Dao<T> {
    int signUp(T item);

    T get(String email);

    int update(T items, int id);

    boolean delete(String email);

    List<T> getAll();

    void clearTable();
}
