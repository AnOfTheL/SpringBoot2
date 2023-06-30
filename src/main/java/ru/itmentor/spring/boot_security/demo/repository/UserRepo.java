package ru.itmentor.spring.boot_security.demo.repository;


import ru.itmentor.spring.boot_security.demo.model.User;

import java.util.List;

public interface UserRepo {
    void saveUser(User user);

    void deleteUserById(long id);

    List<User> getAllUsers();

    void updateUser(long id, String name, String lastname, byte age);

    User getUserById(long id);

    User getUserByUsername(String username);
}
