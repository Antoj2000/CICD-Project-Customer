package ie.atu.customer;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/customer")  //Base URL Mapping
@Validated
public class CustomerController {

    private final CustomerService myService;

    public CustomerController(CustomerService myService) {

        this.myService = myService;
    }

    @PostMapping
    public ResponseEntity<?> addCustomer(@RequestBody @Valid Customer customer, BindingResult result ){
        if (result.hasErrors()){
            List<ErrorDetails> errors = new ArrayList<>();
            result.getFieldErrors().forEach(error -> {
                String fieldName = error.getField();
                String errorMessage = error.getDefaultMessage();
                errors.add(new ErrorDetails(fieldName, errorMessage));
            });
            return ResponseEntity.badRequest().body(errors);
        }
        //Delegates logic to service layer
        Customer savedCustomer = myService.addCustomer(customer);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedCustomer);
    }

    @GetMapping
    public ResponseEntity<List<Customer>> getAllCustomers(){
        List<Customer> customers = myService.getAllCustomers();
        return ResponseEntity.ok(customers);  // Returns 200 OK with list of customers
    }

    @GetMapping("/{email}")
    public ResponseEntity<?> getCustomerByEmail(@PathVariable String email) {
        // Attempt to get the product by its productId
        Optional<Customer> customer = myService.getCustomerByEmail(email);

        if (customer.isPresent()) {
            // If the customer is found, return a response with customer details
            return ResponseEntity.status(HttpStatus.OK).body(customer.get());
        } else {
            // If the customer is not found, return a 404 NOT FOUND response
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Customer with email " + email + " not found.");
        }
    }

    @PutMapping("/balance/{email}")
    // Endpoint to update customer's balance
    public ResponseEntity<?> updateCustomerBalance(@PathVariable String email, @RequestBody @Valid Customer updatedCustomer, BindingResult result) {
        if (result.hasErrors()) {
            List<String> errors = new ArrayList<>();
            result.getFieldErrors().forEach(error -> {
                String errorMessage = error.getDefaultMessage();
                errors.add(errorMessage);
            });
            return ResponseEntity.badRequest().body(errors);
        }

        try {
            // Call the service method to update the customer's balance
            Customer customer = myService.updateCustomerBalance(email, updatedCustomer);

            // Return success response
            return ResponseEntity.status(HttpStatus.OK).body("Balance updated successfully for customer: " + customer.getName());
        } catch (Exception e) {
            // Return error response if customer is not found or any other error occurs
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Customer with email " + email + " not found.");
        }
    }

    @DeleteMapping("/{email}")
    public ResponseEntity<?> deleteCustomer(@PathVariable String email){
        try {
            myService.deleteCustomer(email);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Customer account deleted successfully");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Customer with email: " + email + " not found");
        }
    }




}
