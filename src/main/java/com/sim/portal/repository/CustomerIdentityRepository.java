package com.sim.portal.repository;

import com.sim.portal.entity.CustomerIdentity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerIdentityRepository extends JpaRepository<CustomerIdentity, String> {

    Optional<CustomerIdentity> findByUniqueIdNumber(String uniqueIdNumber);

    Optional<CustomerIdentity> findByFirstNameAndLastName(String firstName, String lastName);
}
