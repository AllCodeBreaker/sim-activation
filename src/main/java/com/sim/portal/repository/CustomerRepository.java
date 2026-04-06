package com.sim.portal.repository;

import com.sim.portal.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, String> {

    Optional<Customer> findByEmailAddressAndDateOfBirth(String emailAddress, LocalDate dateOfBirth);

    Optional<Customer> findByFirstNameAndLastName(String firstName, String lastName);
}
