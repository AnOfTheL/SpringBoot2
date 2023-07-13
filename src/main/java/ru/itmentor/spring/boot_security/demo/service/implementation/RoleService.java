package ru.itmentor.spring.boot_security.demo.service.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.itmentor.spring.boot_security.demo.model.Role;
import ru.itmentor.spring.boot_security.demo.repository.Dao;
import ru.itmentor.spring.boot_security.demo.service.ServiceApplication;

import java.util.List;
import java.util.Optional;

@Service
public class RoleService implements ServiceApplication<Role> {
    @Autowired
    private Dao<Role> roleDao;

    @Override
    @Transactional
    public void save(Role role) {
        roleDao.save(role);
    }

    @Override
    @Transactional
    public void deleteById(long id) {
        roleDao.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Role> getAll() {
        return roleDao.getAll();
    }

    @Override
    @Transactional
    public void update(long id, Role role) {
        roleDao.update(id, role);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Role> getById(long id) {
        return roleDao.getById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Role> getByParam(String name) {
        return roleDao.getByParam(name);
    }
}
