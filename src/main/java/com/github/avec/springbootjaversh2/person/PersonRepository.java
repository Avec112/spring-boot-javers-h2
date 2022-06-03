package com.github.avec.springbootjaversh2.person;

import org.javers.spring.annotation.JaversSpringDataAuditable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
@JaversSpringDataAuditable
public interface PersonRepository extends JpaRepository<Person, Long> {
    Optional<Person> findByFirstnameIgnoreCase(String firstname);
}