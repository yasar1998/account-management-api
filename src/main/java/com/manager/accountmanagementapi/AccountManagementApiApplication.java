package com.manager.accountmanagementapi;

import com.manager.accountmanagementapi.entity.Customer;
import com.manager.accountmanagementapi.repository.CustomerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AccountManagementApiApplication implements CommandLineRunner {

	private final CustomerRepository customerRepository;

	public AccountManagementApiApplication(CustomerRepository customerRepository) {
		this.customerRepository = customerRepository;
	}

	public static void main(String[] args) {
		SpringApplication.run(AccountManagementApiApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Customer customer = new Customer("Yashar", "Mustafayev");
		Customer customer1 = new Customer("Elvin", "Memmedov");
		customerRepository.save(customer);
		customerRepository.save(customer1);
	}
}
