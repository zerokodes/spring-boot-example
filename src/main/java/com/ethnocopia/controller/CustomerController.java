package com.ethnocopia.controller;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ethnocopia.entity.Customer;
import com.ethnocopia.repository.CustomerRepository;


@RestController
@RequestMapping("/api/v1/customers")
//@RequiredArgsConstructor
public class CustomerController {

	@Autowired
	private CustomerRepository customerRepository;
	
	
	public CustomerController(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }
	

	record NewCustomerRequest(String name, String email, int age) {}
	
	/**
	 * 
	 * @return a list of all customers in the DataBase
	 */
	@GetMapping("/getCustomers")
    public List<Customer> getCustomers() {
        return customerRepository.findAll();
    }
	
	/**
	 * Add Customer to the DataBase
	 * @param request
	 */
	@PostMapping("/addCustomer")
    public void addCustomer(@RequestBody NewCustomerRequest request) throws IOException {
        Customer customer = new Customer();
        customer.setName(request.name);
        customer.setEmail(request.email);
        customer.setAge(request.age);

        customerRepository.save(customer);
    }
	
	/**
	 * Deletes a Customer from the DataBase
	 * @param id
	 */
	@DeleteMapping("{customerId}")
    public void deleteCustomer(@PathVariable("customerId") Integer id) {
        customerRepository.deleteById(id);
    }
	
	/**
	 * Update a Customer's data and save in DataBase
	 * @param id
	 * @param request
	 */
	@PutMapping("{customerId}")
    public void updateCustomer(@PathVariable("customerId") Integer id, @RequestBody NewCustomerRequest request) throws IOException {
        Optional<Customer> customerWrapper = customerRepository.findById(id);
        if (!customerWrapper.isPresent())
            return;

        Customer customer = customerWrapper.get();
        customer.setName(request.name);
        customer.setAge(request.age);
        customer.setEmail(request.email);

        customerRepository.save(customer);
    }
	
    //
//  @GetMapping("/")
//  public GreetResponse greet() {
//      GreetResponse greetResponse = new GreetResponse("hello", List.of("Java", "Golang", "JavaScript"), new Person("Alex", 28, 30_000));
//      return greetResponse;
//  }
//
//  record Person(String name, int age, double savings) {};
//
//  record GreetResponse(String greet, List<String> favProgrammingLanguages, Person person) {};
}
