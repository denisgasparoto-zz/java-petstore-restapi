package com.denisgasparoto.restapi.petstore;

import com.denisgasparoto.restapi.petstore.business.customer.CustomerBusiness;
import com.denisgasparoto.restapi.petstore.business.pet.PetBusiness;
import com.denisgasparoto.restapi.petstore.business.service.ServiceBusiness;
import com.denisgasparoto.restapi.petstore.business.specie.SpecieBusiness;
import com.denisgasparoto.restapi.petstore.entity.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Component
public class DataInitializer implements CommandLineRunner {

    private final SpecieBusiness specieBusiness;
    private final CustomerBusiness customerBusiness;
    private final PetBusiness petBusiness;
    private final ServiceBusiness serviceBusiness;

    public DataInitializer(SpecieBusiness specieBusiness,
                           CustomerBusiness customerBusiness,
                           PetBusiness petBusiness,
                           ServiceBusiness serviceBusiness) {
        this.specieBusiness = specieBusiness;
        this.customerBusiness = customerBusiness;
        this.petBusiness = petBusiness;
        this.serviceBusiness = serviceBusiness;
    }

    @Override
    public void run(String... args) {
        System.out.println("Running!");

        // Species
        Specie specie1 = new Specie();
        Specie specie2 = new Specie();

        specie1.setName("Dog");
        specie2.setName("Cat");

        specieBusiness.save(specie1);
        specieBusiness.save(specie2);

        // Customers
        Customer customer1 = new Customer();
        Customer customer2 = new Customer();
        Customer customer3 = new Customer();
        Customer customer4 = new Customer();

        customer1.setName("John");
        customer2.setName("Michael");
        customer3.setName("Chad");
        customer4.setName("Anthony");

        customerBusiness.save(customer1);
        customerBusiness.save(customer2);
        customerBusiness.save(customer3);
        customerBusiness.save(customer4);

        // Pets
        Pet pet1 = new Pet();
        Pet pet2 = new Pet();
        Pet pet3 = new Pet();
        Pet pet4 = new Pet();

        pet1.setCustomer(customer1);
        pet1.setBirthDate(LocalDate.parse("2018-01-01"));
        pet1.setSpecie(specie1);
        pet1.setName("Rex");

        pet2.setCustomer(customer2);
        pet2.setBirthDate(LocalDate.parse("2018-02-01"));
        pet2.setSpecie(specie2);
        pet2.setName("Bob");

        pet3.setCustomer(customer1);
        pet3.setBirthDate(LocalDate.parse("2018-03-01"));
        pet3.setSpecie(specie1);
        pet3.setName("Marley");

        pet4.setCustomer(customer2);
        pet4.setBirthDate(LocalDate.parse("2018-04-01"));
        pet4.setSpecie(specie2);
        pet4.setName("Nina");

        petBusiness.save(pet1);
        petBusiness.save(pet2);
        petBusiness.save(pet3);
        petBusiness.save(pet4);

        // Services
        Service service1 = new Service();
        Service service2 = new Service();
        Service service3 = new Service();
        Service service4 = new Service();

        service1.setDateHour(LocalDateTime.parse("2018-09-01T09:00:00"));
        service1.setObservation("Consulta do Rex");
        service1.setPet(pet1);
        service1.setServiceType(ServiceType.CONSULTATION);
        service1.setValue(new BigDecimal("50.00"));

        service2.setDateHour(LocalDateTime.parse("2018-10-01T10:00:00"));
        service2.setObservation("Banho e Tosa do Bob");
        service2.setPet(pet2);
        service2.setServiceType(ServiceType.BATH_CUT);
        service2.setValue(new BigDecimal("60.00"));

        service3.setDateHour(LocalDateTime.parse("2018-11-01T11:00:00"));
        service3.setObservation("Cirurgia do Marley");
        service3.setPet(pet3);
        service3.setServiceType(ServiceType.SURGERY);
        service3.setValue(new BigDecimal("70.00"));

        service4.setDateHour(LocalDateTime.parse("2018-12-01T12:00:00"));
        service4.setObservation("Vacinação da Nina");
        service4.setPet(pet4);
        service4.setServiceType(ServiceType.VACCINATION);
        service4.setValue(new BigDecimal("80.00"));

        serviceBusiness.save(service1);
        serviceBusiness.save(service2);
        serviceBusiness.save(service3);
        serviceBusiness.save(service4);
    }
}
