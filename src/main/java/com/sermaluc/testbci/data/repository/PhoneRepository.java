package com.sermaluc.testbci.data.repository;

import com.sermaluc.testbci.data.entity.Phone;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PhoneRepository extends CrudRepository<Phone, UUID> {
}
