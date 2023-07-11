package ru.itmentor.spring.boot_security.demo.repository.implementation;

import org.springframework.stereotype.Repository;
import ru.itmentor.spring.boot_security.demo.model.User;
import ru.itmentor.spring.boot_security.demo.repository.Dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Repository
public class UserDao implements Dao<User> {
    @PersistenceContext
    private EntityManager em;

    @Override
    public void save(User user) {
        em.joinTransaction();
        em.persist(user);
        em.flush();
    }

    @Override
    public void deleteById(long id) {
        em.joinTransaction();
        User user = em.find(User.class, id);
        em.remove(user);
        em.flush();
    }

    @Override
    public List<User> getAll() {
        return em.createQuery("from User", User.class).getResultList();
    }

    @Override
    public void update(long id, User updatedUser) {
        em.joinTransaction();

        User user = em.find(User.class, id);
        user.setName(updatedUser.getName());
        user.setLastname(updatedUser.getLastname());
        user.setAge(updatedUser.getAge());

        em.flush();
    }

    @Override
    public Optional<User> getById(long id) {
        return Optional.ofNullable(em.find(User.class, id));
    }

    @Override
    public Optional<User> getByParam(String username) {
        return Optional.ofNullable(
                em.createQuery("from User where username =: username",  User.class)
                .setParameter("username", username)
                .getResultList()
                .stream().findFirst().orElse(null));
    }
}
