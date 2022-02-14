package ru.otus.hw28springboot.domain.repository;

import org.springframework.data.repository.CrudRepository;
import ru.otus.hw28springboot.domain.entity.Phone;

public interface PhoneRepository extends CrudRepository<Phone, Long> {
}
