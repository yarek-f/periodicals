package ua.dao;

import java.util.List;

public interface Dao<T> {
    int signUp(T item);

    T get(String email);

    int update(T items, int id);
    void edit(T item, String currentEmail);

    boolean delete(int id);

    List<T> getAll();

    void clearTable();
    void deactivate(int id);
}
