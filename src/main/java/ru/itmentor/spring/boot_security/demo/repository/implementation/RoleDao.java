package ru.itmentor.spring.boot_security.demo.repository.implementation;

import org.springframework.stereotype.Repository;
import ru.itmentor.spring.boot_security.demo.model.Role;
import ru.itmentor.spring.boot_security.demo.repository.Dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Repository
public class RoleDao implements Dao<Role> {
    @PersistenceContext
    private EntityManager em;

    @Override
    public void save(Role role) {
        em.joinTransaction();
        em.persist(role);
        em.flush();
    }

    @Override
    public void deleteById(long id) {
        em.joinTransaction();
        Role role = em.find(Role.class, id);
        em.remove(role);
        em.flush();
    }

    @Override
    public List<Role> getAll() {
        return em.createQuery("from Role", Role.class).getResultList();
    }

    @Override
    public void update(long id, Role updatedRole) {
        em.joinTransaction();

        Role user = em.find(Role.class, id);
        user.setName(updatedRole.getName());

        em.flush();
    }

    @Override
    public Optional<Role> getById(long id) {
        return Optional.ofNullable(em.find(Role.class, id));
    }

    @Override
    public Optional<Role> getByParam(String name) {
        return Optional.ofNullable(
                em.createQuery("from Role where name =: name",  Role.class)
                        .setParameter("name", name)
                        .getResultList()
                        .stream().findFirst().orElse(null));
    }
}
