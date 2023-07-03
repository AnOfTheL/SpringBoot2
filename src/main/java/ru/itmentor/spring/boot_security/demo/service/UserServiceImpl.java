package ru.itmentor.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.itmentor.spring.boot_security.demo.model.User;
import ru.itmentor.spring.boot_security.demo.repository.UserRepo;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepo userRepo;

    @Override
    @Transactional
    public void saveUser(User user) { userRepo.saveUser(user); }

    @Override
    @Transactional(readOnly = true)
    public List<User> getAllUsers(){
        return userRepo.getAllUsers();
    }

    @Override
    @Transactional(readOnly = true)
    public User getUserById(long id) {
        return userRepo.getUserById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public User getUserByUsername(String username) { return userRepo.getUserByUsername(username); }

    @Override
    @Transactional
    public void updateUser(long id, String name, String lastName, byte age) {
        userRepo.updateUser(id, name, lastName, age);
    }

    @Override
    @Transactional
    public void deleteUserById(long id) {
        userRepo.deleteUserById(id);
    }
}
