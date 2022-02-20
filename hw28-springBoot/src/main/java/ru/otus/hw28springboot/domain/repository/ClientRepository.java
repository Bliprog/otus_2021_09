package ru.otus.hw28springboot.domain.repository;

import org.springframework.data.repository.CrudRepository;
import ru.otus.hw28springboot.domain.entity.Client;

import java.util.List;

public interface ClientRepository extends CrudRepository<Client, Long> {
    List<Client> findAll();
}
