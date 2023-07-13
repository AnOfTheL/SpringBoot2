package ru.itmentor.spring.boot_security.demo.service.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.itmentor.spring.boot_security.demo.model.Role;
import ru.itmentor.spring.boot_security.demo.model.User;
import ru.itmentor.spring.boot_security.demo.repository.Dao;
import ru.itmentor.spring.boot_security.demo.service.ServiceApplication;

import java.util.List;
import java.util.Optional;

@Service
public class UserService implements ServiceApplication<User> {
    @Autowired
    private Dao<User> userDao;

    @Autowired
    private ServiceApplication<Role> roleService;

    @Override
    @Transactional
    public void save(User user) {
        userDao.save(user);
    }

    @Override
    @Transactional
    public void deleteById(long id) {
        userDao.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> getAll() {
        return userDao.getAll();
    }

    @Override
    @Transactional
    public void update(long id, User user) {
        userDao.update(id, user);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<User> getById(long id) {
        return userDao.getById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<User> getByParam(String username) {
        return userDao.getByParam(username);
    }

    public void addRoleByService(User user, Role role){
        Optional<Role> roleFromDb = roleService.getByParam(role.getName());
        if (roleFromDb.isPresent() && user.getRoles().stream().noneMatch(roleName->roleName==roleFromDb.orElseThrow(() -> new RuntimeException("Role not found")))) {
            user.addRole(roleFromDb.get());
        }
    }
}
