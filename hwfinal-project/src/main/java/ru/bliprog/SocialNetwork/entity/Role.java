package ru.bliprog.SocialNetwork.entity;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.Set;

@Data
@Entity
@Table(name="social_roles")
public class Role implements GrantedAuthority {
    @Id
    private final Long id;
    @Column(name = "name")
    private final String name;
    @Transient
    @ManyToMany(mappedBy = "roles")
    private Set<User> users;

    @Override
    public String getAuthority() {
        return getName();
    }
}
