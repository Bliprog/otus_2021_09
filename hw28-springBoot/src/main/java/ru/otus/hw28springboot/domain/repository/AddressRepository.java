package ru.otus.hw28springboot.domain.repository;

import org.springframework.data.repository.CrudRepository;
import ru.otus.hw28springboot.domain.entity.Address;

public interface AddressRepository extends CrudRepository<Address, Long> {

}
