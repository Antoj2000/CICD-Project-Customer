package ie.atu.customer;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {


    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository){
        this.customerRepository = customerRepository;
    }
    public Customer addCustomer(Customer customer)
    {
        return  customerRepository.save(customer);
    }

    public List<Customer> getAllCustomers() {

        return customerRepository.findAll();
    }

    public Optional<Customer> getCustomerByEmail(String employeeId) {
        // Find product by productId (returns Optional)
        return customerRepository.findByEmail(employeeId);
    }

    @Transactional
    public Customer updateCustomerBalance(String email, Customer updatedCustomer) {
        // Find customer by email
        Optional<Customer> existingCustomerOptional = customerRepository.findByEmail(email);

        if (existingCustomerOptional.isPresent()) {
            Customer existingCustomer = existingCustomerOptional.get();
            // Update the balance field with the new balance from the updatedCustomer object
            existingCustomer.setBalance(updatedCustomer.getBalance());

            // Save and return the updated customer
            return customerRepository.save(existingCustomer);
        } else {
            throw new IllegalArgumentException("Customer with email " + email + " not found.");
        }
    }

   /* @Transactional
    public Customer updateCustomer(String email, Customer updatedCustomer) {
        // Check if the person with the given name exists
        Optional<Customer> existingCustomerOptional = customerRepository.findByEmail(email);

        if (existingCustomerOptional.isPresent()) {
            Customer existingCustomer = existingCustomerOptional.get();
            // Update the fields of the existing person with the new values
            existingCustomer.setName(updatedCustomer.getName());
            existingCustomer.setEmail(updatedCustomer.getEmail());
            existingCustomer.setAge(updatedCustomer.getAge());
            existingCustomer.setAddress(updatedCustomer.getAddress());
            existingCustomer.setPhoneNumber(updatedCustomer.getPhoneNumber());
            existingCustomer.setBalance(updatedCustomer.getBalance());

            // Save the updated person back to the database
            return customerRepository.save(existingCustomer);
        } else {
            throw new IllegalArgumentException("Customer : " + email + " not found");
        }
    }*/

    public void deleteCustomer(String email) {
        // Find person by email
        Optional<Customer> customerOptional = customerRepository.findAll().stream()
                .filter(customer -> customer.getEmail().equals(email))
                .findFirst();

        if (customerOptional.isPresent()) {
            // Delete the person
            customerRepository.delete(customerOptional.get());
        } else {
            throw new IllegalArgumentException("Customer " + email + " not found");
        }
    }

}
