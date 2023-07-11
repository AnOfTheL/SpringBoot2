package ru.itmentor.spring.boot_security.demo.repository;

import java.util.List;
import java.util.Optional;

public interface Dao<T> {
    void save(T t);

    void deleteById(long id);

    List<T> getAll();

    void update(long id, T t);

    Optional<T> getById(long id);

    Optional<T> getByParam(String param);
}
