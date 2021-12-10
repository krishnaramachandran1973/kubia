package com.cts.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cts.entity.Person;

public interface PersonRepository extends JpaRepository<Person, Long> {

}
