package com.github.avec.springbootjaversh2.person;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PersonRepository extends JpaRepository<Person, Long> {
    Optional<Person> findByFirstnameIgnoreCase(String firstname);
}