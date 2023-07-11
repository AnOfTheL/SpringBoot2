package ru.itmentor.spring.boot_security.demo.model;

import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "t_roles")
public class Role implements GrantedAuthority {
    @Id
    @GeneratedValue
    private Long id;
    @Column (nullable = false, unique = true)
    private String name;
    @ManyToMany (cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
                },
                mappedBy = "roles")
    private Set<User> users = new HashSet<>();

    public Role(String name) {
        this.name = name;
    }

    public Role() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    @Override
    public String getAuthority() {
        return getName();
    }

    @Override
    public String toString() {
        return "Role {" +
                "id = " + id +
                " name = " + name +
                "}";
    }
}
