package com.sim.portal.config;

import com.sim.portal.entity.*;
import com.sim.portal.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

/**
 * Loads sample data into the database on application startup
 * (only if the tables are empty, to avoid duplication on restart).
 */
@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private SimDetailsRepository simDetailsRepository;

    @Autowired
    private SimOffersRepository simOffersRepository;

    @Autowired
    private CustomerAddressRepository customerAddressRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private CustomerIdentityRepository customerIdentityRepository;

    @Override
    public void run(String... args) {

        // Only seed if tables are empty
        if (simDetailsRepository.count() == 0) {
            seedSimDetails();
        }

        if (customerAddressRepository.count() == 0) {
            seedCustomerAddress();
        }

        if (customerRepository.count() == 0) {
            seedCustomers();
        }

        if (customerIdentityRepository.count() == 0) {
            seedCustomerIdentity();
        }

        System.out.println("✅ Sample data loaded successfully.");
    }

    private void seedSimDetails() {
        SimDetails sim1 = new SimDetails();
        sim1.setServiceNumber("1234567891");
        sim1.setSimNumber("1234567891234");
        sim1.setSimStatus("active");
        simDetailsRepository.save(sim1);

        SimDetails sim2 = new SimDetails();
        sim2.setServiceNumber("1234567892");
        sim2.setSimNumber("1234567891235");
        sim2.setSimStatus("inactive");
        simDetailsRepository.save(sim2);

        // Seed SimOffers after SimDetails
        SimOffers offer1 = new SimOffers();
        offer1.setCallQty(100);
        offer1.setCost(100.0);
        offer1.setDataQty(120);
        offer1.setDuration(10);
        offer1.setOfferName("Free calls and data");
        offer1.setSimDetails(sim1);
        simOffersRepository.save(offer1);

        SimOffers offer2 = new SimOffers();
        offer2.setCallQty(150);
        offer2.setCost(50.0);
        offer2.setDataQty(100);
        offer2.setDuration(15);
        offer2.setOfferName("Free calls");
        offer2.setSimDetails(sim2);
        simOffersRepository.save(offer2);
    }

    private void seedCustomerAddress() {
        CustomerAddress addr1 = new CustomerAddress();
        addr1.setAddress("Jayanagar");
        addr1.setCity("Bangalore");
        addr1.setPincode("560041");
        addr1.setState("Karnataka");
        customerAddressRepository.save(addr1);

        CustomerAddress addr2 = new CustomerAddress();
        addr2.setAddress("Vijaynagar");
        addr2.setCity("Mysore");
        addr2.setPincode("567017");
        addr2.setState("Karnataka");
        customerAddressRepository.save(addr2);
    }

    private void seedCustomers() {
        SimDetails sim1 = simDetailsRepository.findBySimNumber("1234567891234").orElse(null);
        SimDetails sim2 = simDetailsRepository.findBySimNumber("1234567891235").orElse(null);

        CustomerAddress addr1 = customerAddressRepository.findAll().stream()
                .filter(a -> a.getCity().equals("Bangalore")).findFirst().orElse(null);
        CustomerAddress addr2 = customerAddressRepository.findAll().stream()
                .filter(a -> a.getCity().equals("Mysore")).findFirst().orElse(null);

        Customer c1 = new Customer();
        c1.setUniqueIdNumber("1234567891234567");
        c1.setDateOfBirth(LocalDate.of(1990, 12, 12));
        c1.setEmailAddress("smith@abc.com");
        c1.setFirstName("Smith");
        c1.setLastName("John");
        c1.setIdType("Aadhar");
        c1.setState("Karnataka");
        c1.setCustomerAddress(addr1);
        c1.setSimDetails(sim1);
        customerRepository.save(c1);

        Customer c2 = new Customer();
        c2.setUniqueIdNumber("1234567891234568");
        c2.setDateOfBirth(LocalDate.of(1998, 12, 12));
        c2.setEmailAddress("bob@abc.com");
        c2.setFirstName("Bob");
        c2.setLastName("Sam");
        c2.setIdType("Aadhar");
        c2.setState("Karnataka");
        c2.setCustomerAddress(addr2);
        c2.setSimDetails(sim2);
        customerRepository.save(c2);
    }

    private void seedCustomerIdentity() {
        CustomerIdentity id1 = new CustomerIdentity();
        id1.setUniqueIdNumber("1234567891234567");
        id1.setDateOfbirth(LocalDate.of(1990, 12, 12));
        id1.setFirstName("Smith");
        id1.setLastName("John");
        id1.setEmailAddress("smith@abc.com");
        id1.setState("Karnataka");
        customerIdentityRepository.save(id1);

        CustomerIdentity id2 = new CustomerIdentity();
        id2.setUniqueIdNumber("1234567891234568");
        id2.setDateOfbirth(LocalDate.of(1998, 12, 12));
        id2.setFirstName("Bob");
        id2.setLastName("Sam");
        id2.setEmailAddress("bob@abc.com");
        id2.setState("Karnataka");
        customerIdentityRepository.save(id2);
    }
}
