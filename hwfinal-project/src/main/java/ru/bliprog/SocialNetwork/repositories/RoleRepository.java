package ru.bliprog.SocialNetwork.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.bliprog.SocialNetwork.entity.Role;


@Repository
public interface RoleRepository extends JpaRepository<Role,Long> {
    Role findByName(String name);
}
