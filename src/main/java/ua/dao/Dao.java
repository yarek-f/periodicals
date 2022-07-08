package ua.dao;

import java.util.List;

public interface Dao<T> {
    int signUp(T item);

    T get(String email);

    void edit(T item, String currentEmail);

    boolean delete(int id);

    List<T> getAll();

    void deactivate(int id);
}
